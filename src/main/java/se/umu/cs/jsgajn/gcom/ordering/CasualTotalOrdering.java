package se.umu.cs.jsgajn.gcom.ordering;

import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.MessageCouldNotBeSentException;
import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.management.CrashList;
import se.umu.cs.jsgajn.gcom.management.CrashListImpl;
import se.umu.cs.jsgajn.gcom.management.GroupView;

public class CasualTotalOrdering implements Ordering {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(CasualTotalOrdering.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private BlockingQueue<Message> receiveQueue;
    private BlockingQueue<Message> deliverQueue;

    private boolean running = false;
    private int latestReceivedSequenceNumber = 0;
    private Ordering total;
    private Ordering fifo;
    private transient HashMap<UUID, Queue<Message>> holdQueues;

    private UUID leaderUUID = null;
    
    public CasualTotalOrdering() {
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();
        this.running = true;

        this.total = new TotalOrdering();
        this.fifo = new FIFOOrdering();
        this.holdQueues = new HashMap<UUID, Queue<Message>>();
        new Thread(new MessageHandler(), "Message sorter thread").start();
        new Thread(new MessageSorter(), "Message sorter thread").start();
    }

    public Message prepareOutgoingMessage(Message m, GroupView g) throws MessageCouldNotBeSentException {
        // Check if CT-ordering is set in receiver for group leader
        // if not. Set.
        try {
            if (!g.getGroupLeaderGroupMember().getReceiver().orderingExist()) {
                g.getGroupLeaderGroupMember().getReceiver().createOrdering();
            }
        } catch (RemoteException e) {
            CrashList cl = new CrashListImpl();
            cl.add(g.getGroupLeaderGroupMember());
            throw new MessageCouldNotBeSentException(cl);
        }
        this.fifo.prepareOutgoingMessage(m, g);
        Message mPrep = this.total.prepareOutgoingMessage(m, g);
        return mPrep;
    }

    public void put(Message m) {
        try {
            // Körs första gången endast
            if (this.leaderUUID == null) {
                this.leaderUUID = m.getSequncerUID();
                logger.debug("No leader UUID, set new! " + this.leaderUUID);
            }

            // Om det är en ny ledare
            if (!this.leaderUUID.equals(m.getSequncerUID())) {
                this.latestReceivedSequenceNumber = (m.getSequnceNumber() - 1);
                this.leaderUUID = m.getSequncerUID();
                logger.debug("We got new sequencer, m.getSequcen() = " + latestReceivedSequenceNumber);
            }

            // Om detta är första meddelandet den får
            if (this.latestReceivedSequenceNumber == 0) {
                this.latestReceivedSequenceNumber = (m.getSequnceNumber() - 1);
                logger.debug("No sequencenumber, got " + latestReceivedSequenceNumber);
            }

            receiveQueue.put(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message take() {
        try {
            return deliverQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Error("Couldn't wait for queue");
        }
    }

    private class MessageHandler implements Runnable {

        private SortedSet<Message> holdBackSortedSet = new TreeSet<Message>(new Comparator<Message>() {
                public int compare(Message m1, Message m2) {
                    return m1.getSequnceNumber() - m2.getSequnceNumber();
                }
            });

        public void run() {
            while (running) {
                try {
                    Message m = receiveQueue.take();
                    logger.debug("Got from queue: {} {}", m.getSequnceNumber(), m.getMessage());
                    if (deliverCheck(m)) {
                        deliverQueue.put(m);

                        // Check every message in holdBackSortedSet and deliver if
                        // possible
                        // for (Message holdMessage : holdBackSortedSet) {
                        for (Iterator<Message> i = holdBackSortedSet.iterator(); i.hasNext();) {
                            Message holdMessage = i.next();
                            logger.debug("Looping holdback m.getSeq: {}", holdMessage.getSequnceNumber());
                            if (deliverCheck(holdMessage)) {
                                deliverQueue.put(holdMessage);
                                i.remove();
                            }
                        }
                    } else {
                        holdBackSortedSet.add(m);
                    }

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        /**
         * Method to make sure a Message is not lost from the sending process.
         *
         * @param m The message to check.
         * @return true if message is next in line to be delivered, false
         *         otherwise.
         */
        private boolean deliverCheck(final Message m) {
            int sequenceNumber = m.getSequnceNumber();
            logger.debug("Delivercheck: Message got " + sequenceNumber + " compare with " + (latestReceivedSequenceNumber+1));
            if (sequenceNumber == (CasualTotalOrdering.this.latestReceivedSequenceNumber + 1)) {
                CasualTotalOrdering.this.latestReceivedSequenceNumber++;
                return true;
            } else {
                return false;
            }
        }
    }


    /***** Sequencer stuff ****************************************************/
    private void putInOrdering(Message m) {
        // If its the first time a client asks for a sequence number
        if (!holdQueues.containsKey(m.getOriginUID())) {
            holdQueues.put(m.getOriginUID(), new LinkedBlockingQueue<Message>());
        }
        fifo.put(m);
    }

    public Message setSequenceNumber(Message m) {
        putInOrdering(m);
        LinkedBlockingQueue<Message> queue =
            (LinkedBlockingQueue<Message>) holdQueues.get(m.getOriginUID());

        try {
            return queue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Error("Couldn't wait for queue.");
        }
    }

    /**
     * Sorts the messages into different queues depending on with origin is has
     */
    private class MessageSorter implements Runnable {
        final AtomicInteger sequenceNumber = new AtomicInteger(0);
        public void run() {
            while (running) {
                try {
                    Message m = fifo.take();
                    m.setSequnceNumber(sequenceNumber.incrementAndGet());
                    LinkedBlockingQueue<Message> queue =
                        (LinkedBlockingQueue<Message>) holdQueues.get(m.getOriginUID());

                    queue.put(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return OrderingType.CASUALTOTAL_ORDERING.toString();
    }
}
