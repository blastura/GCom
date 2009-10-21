package se.umu.cs.jsgajn.gcom.messageordering;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

/**
 * Implementation of FIFO ordering. "If a correct process issues multicast(g, m)
 * and then multicasts(g, m'), then every correct process that delivers m' will
 * deliver m before m'", from DISTRIBUTED SYSTEMS CONCEPTS AND DESIGN, George
 * Coulouris, Jean Dollimore, Tim Kindberg.
 *
 * @author dit06ajn, dit06jsg
 * @version 1.0
 */
public class FIFO implements Ordering {
    private static final Logger logger = LoggerFactory.getLogger(FIFO.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private BlockingQueue<Message> receiveQueue;
    private BlockingQueue<Message> deliverQueue;

    private AtomicInteger msgCounter = new AtomicInteger(0);
    private boolean running = false;

    /** Should contain information about the number of messages this module has
     * received from other GroupMembers */
    private VectorClock<UUID> vc  = new VectorClock<UUID>(GroupModule.PID);

    public FIFO() {
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();
        this.running = true;
        new Thread(new MessageHandler(), "FIFO thread").start();
    }

    public Message take() {
        try {
            return deliverQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Will put a message in the modules receiveQueue and create a new counter
     * for the sending process if this is the first message received from that
     * process. Initial value for that counter will be set from the value the
     * sending process piggybacked on this message.
     *
     * This means that messages can be lost when connecting to an existing group
     * if messages are received after the first received that was sent before
     * first message which set the counter.
     * TODO: fix?
     *
     * @param m The Message to put in this Ordering Module.
     */
    public void put(final Message m) {
        try {
            if (!vc.containsKey(m.getOriginUID())) {
                logger.debug("New process added to VectorClock, process {} has sent {} messages",
                             m.getOriginUID(), m.getVectorClock().get());
                // Add new process with init value from message - 1 since we
                // have not confirmed this message should be delivered yet.
                vc.newProcess(m.getOriginUID(), (m.getVectorClock().get() - 1));
            }

            receiveQueue.put(m);
        } catch (InterruptedException e) {
            // TODO: How do we handle this
            logger.error("TODO: Warning vc is not in sync anymore");
            e.printStackTrace();
        }
    }

    public Message prepareOutgoingMessage(Message m, GroupView g) {
        // Tick counter for sent messages
        msgCounter.incrementAndGet();
        VectorClock<UUID> vc = new VectorClock<UUID>(GroupModule.PID,
                                                   msgCounter.get());
        m.setVectorClock(vc);
        logger.debug("OUT: Prepared outgoing message: " + m);
        return m;
    }

    private class MessageHandler implements Runnable {
        //         private SortedSet<Message> holdBackSortedSet = new TreeSet<Message>(new Comparator<Message>() {
        //                 public int compare(Message m1, Message m2) {
        //                     return m1.getVectorClock().get() - m2.getVectorClock().get();
        //                 }
        //             });
        private Queue<Message> holdBackQueue =
            new LinkedList<Message>();

        
        public void run() {
            while (running) {
                try {
                    Message m = receiveQueue.take();

                    if (deliverCheck(m)) {
                        deliverQueue.put(m);
                        // Check every message in holdBackQueue and
                        // deliver if possible
                        for (Iterator<Message> i = holdBackQueue.iterator(); i.hasNext();) {
                            Message holdMessage = i.next();
                            logger.debug("Looping holdback({}) \nm.vc: {}",
                                         holdBackQueue.size(),
                                         holdMessage.getVectorClock());
                            if (deliverCheck(holdMessage)) {
                                deliverQueue.put(holdMessage);
                                // TODO: , optimize
                                i.remove(); // remove element from holdBackQueue
                                i = holdBackQueue.iterator(); // restart for-loop
                            }
                        }
                    } else {
                        if (!holdBackQueue.add(m)) {
                            logger.error("================ ERROR ================ message not added to sorted Set {}", m);
                            System.err.println("FIFO: Message not added to sorted set");
                            System.exit(1);
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
            int otherHasSent = m.getVectorClock().get();
            int hasReceived = vc.get(m.getOriginUID());
            // If this message is the next in order for sending process
            if (otherHasSent == (hasReceived + 1)) {
                logger.debug("otherHasSent: {}, hasReceived: {}, from: {}",
                             new Object[] {otherHasSent,
                                           hasReceived,
                                           m.getOriginUID().equals(GroupModule.PID) ? "ME" : m.getOriginUID().toString()});

                // Tick counter for receiving message process
                vc.tick(m.getOriginUID());
                debugger.updateVectorClock(vc);
                return true;
            } else { // TODO: what if message are before the ones we already
                     // received, how do we get rid of them?
                logger.info("Message lost, will hold it diff {}, message = {}",
                            (otherHasSent - hasReceived), m);
                return false;
            }
        }
    }
}
