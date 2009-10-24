package se.umu.cs.jsgajn.gcom.ordering;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.MessageCouldNotBeSentException;
import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.management.CrashList;
import se.umu.cs.jsgajn.gcom.management.CrashListImpl;
import se.umu.cs.jsgajn.gcom.management.GroupView;

public class TotalOrdering implements Ordering {
    private static final Logger logger = LoggerFactory.getLogger(TotalOrdering.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private BlockingQueue<Message> receiveQueue;
    private BlockingQueue<Message> deliverQueue;

    private CrashList crashed = new CrashListImpl();

    private boolean running = false;
    private int latestReceivedSequenceNumber = 0;

    private UUID leaderUUID = null;

    public TotalOrdering() {
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();

        this.running = true;
        new Thread(new MessageHandler(), "Total order thread").start();
    }

    public Message prepareOutgoingMessage(Message m, GroupView g) throws MessageCouldNotBeSentException {
        int sequenceNumber;
        UUID sequencerUID;
        try {
            sequenceNumber =
                g.getGroupLeaderGroupMember().getReceiver().getSequenceNumber(m);
            sequencerUID = g.getGroupLeaderGroupMember().getReceiver().getPID();

            m.setSequncerUID(sequencerUID);
            m.setSequnceNumber(sequenceNumber);
            logger.debug("OUT: Prepared outgoing message: " + m);
        } catch (RemoteException e) {
            logger.debug("Error, problem with getSequenceNumber in prepOutMess.");
            crashed =  new CrashListImpl(g.getGroupLeaderGroupMember());
            throw new MessageCouldNotBeSentException(crashed);
        }
        return m;
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
                this.latestReceivedSequenceNumber = (m.getSequnceNumber()-1);
                this.leaderUUID = m.getSequncerUID();
                logger.debug("We got new sequencer, m.getSequcen() = " + latestReceivedSequenceNumber);
            }
            
            // Om detta är första meddelandet den får
            if(this.latestReceivedSequenceNumber == 0) {
                this.latestReceivedSequenceNumber = (m.getSequnceNumber()-1);
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

                    if (deliverCheck(m)) {
                        deliverQueue.put(m);

                        for (Iterator<Message> i = holdBackSortedSet.iterator(); i.hasNext();) {
                            Message holdMessage = i.next();
                            logger.debug("Looping holdback m.getSeq: {}", holdMessage.getSequnceNumber());
                            if (deliverCheck(holdMessage)) {
                                deliverQueue.put(holdMessage);
                                i.remove();
                            }
                        }
                    } else {
                        if (!holdBackSortedSet.add(m)) {
                            logger.error("Fuck, error in totalorderholdback");
                            System.exit(0);
                        }
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
            if(sequenceNumber == (TotalOrdering.this.latestReceivedSequenceNumber + 1)) {
                TotalOrdering.this.latestReceivedSequenceNumber++;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return OrderingType.TOTAL_ORDER.toString();
    }
}
