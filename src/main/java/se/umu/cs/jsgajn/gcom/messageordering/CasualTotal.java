package se.umu.cs.jsgajn.gcom.messageordering;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.groupcommunication.MemberCrashException;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.CrashList;
import se.umu.cs.jsgajn.gcom.groupmanagement.CrashListImpl;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageCouldNotBeSentException;

public class CasualTotal implements Ordering {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(Total.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private BlockingQueue<Message> receiveQueue;
    private BlockingQueue<Message> deliverQueue;

    private boolean running = false;
    private int latestReceivedSequenceNumber = 0;

    private Ordering total;
    private Ordering casual;
    private transient HashMap<UUID, Queue<Message>> holdQueues;

    public CasualTotal() {
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();
        this.running = true;

        this.total = new Total();
        this.casual = new CasualOrdering();
        this.holdQueues = new HashMap<UUID, Queue<Message>>();
        new Thread(new MessageHandler(), "Message sorter thread").start();
        new Thread(new MessageSorter(), "Message sorter thread").start();
    }

    public Message prepareOutgoingMessage(Message m, GroupView g) throws MessageCouldNotBeSentException {
        // Check if CT-ordering is set in receiver for group leader
        // if not. Set.
        try {
            if(!g.getGroupLeaderGroupMember().getReceiver().orderingExist()) {
                g.getGroupLeaderGroupMember().getReceiver().createOrdering();
            }
        } catch (RemoteException e) {
            CrashList cl = new CrashListImpl();
            cl.add(g.getGroupLeaderGroupMember());
            throw new MessageCouldNotBeSentException(cl);
        }
        // TODO: Jonny kolla igenom, tog bort try catch MemberCrashException
        this.casual.prepareOutgoingMessage(m, g);
        this.total.prepareOutgoingMessage(m, g);
        return m;
    }

    public void put(Message m) {
        try {
            int sequenceNumber = m.getSequnceNumber();
            if(this.latestReceivedSequenceNumber == 0) {
                this.latestReceivedSequenceNumber = (sequenceNumber-1);
                logger.debug("No sequencenumber, got " + latestReceivedSequenceNumber);
            }
            logger.debug("Send to queue: {} {}", m.getSequnceNumber(), m.getMessage());
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
            return null;
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
                        //for (Message holdMessage : holdBackSortedSet) {
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
            if(sequenceNumber == (CasualTotal.this.latestReceivedSequenceNumber + 1)) {
                CasualTotal.this.latestReceivedSequenceNumber++;
                return true;
            } else {
                return false;
            }
        }
    }

    public void askForSequenceNumber(Message m) {
        // If its the first time a client asks for a sequence number
        if(!holdQueues.containsKey(m.getOriginUID())) {
            holdQueues.put(m.getOriginUID(), new LinkedBlockingQueue<Message>());
        }
        casual.put(m);

    }
    public Message getSequenceNumber(Message m) {
        LinkedBlockingQueue<Message> queue =
            (LinkedBlockingQueue<Message>) holdQueues.get(m.getOriginUID());

        try {
            Message m2 = queue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sorts the messages into diffrent queues depending on with origin is has
     * @author Jonny
     *
     */
    private class MessageSorter implements Runnable {

        public void run() {
            while (running) {
                try {
                    Message m = casual.take();

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
