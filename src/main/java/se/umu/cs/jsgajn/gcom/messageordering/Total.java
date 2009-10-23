package se.umu.cs.jsgajn.gcom.messageordering;

import java.rmi.RemoteException;
import java.util.UUID;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.groupcommunication.MemberCrashException;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.CrashList;
import se.umu.cs.jsgajn.gcom.groupmanagement.CrashListImpl;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class Total implements Ordering {
    private static final Logger logger = LoggerFactory.getLogger(Total.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private BlockingQueue<Message> receiveQueue;
    private BlockingQueue<Message> deliverQueue;

    private CrashList crashed = new CrashListImpl();

    private boolean running = false;
    private int latestReceivedSequenceNumber = 0;
    private GroupView g;

    private UUID leaderUUID = null;

    public Total() {
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();

        this.running = true;
        new Thread(new MessageHandler(), "Total order thread").start();
    }

    public Message prepareOutgoingMessage(Message m, GroupView g) throws MemberCrashException {
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
            crashed =  new CrashListImpl();
            crashed.add(g.getGroupLeaderGroupMember());
            throw new MemberCrashException(crashed);
        }
        return m;
    }

    public void put(Message m) {
        try {
            if (this.leaderUUID == null) {
                this.leaderUUID = m.getSequncerUID();
            }
            if (!this.leaderUUID.equals(m.getSequncerUID())) {
                this.latestReceivedSequenceNumber = m.getSequnceNumber();
                this.leaderUUID = m.getSequncerUID();
            } else {
                int sequenceNumber = m.getSequnceNumber();
                if(this.latestReceivedSequenceNumber == 0) {
                    this.latestReceivedSequenceNumber = (sequenceNumber-1);
                    logger.debug("No sequencenumber, got " + latestReceivedSequenceNumber);
                }
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
            if(sequenceNumber == (Total.this.latestReceivedSequenceNumber + 1)) {
                Total.this.latestReceivedSequenceNumber++;
                return true;
            } else {
                return false;
            }
        }
    }	
}
