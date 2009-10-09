package se.umu.cs.jsgajn.gcom.messageordering;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import java.rmi.server.UID;

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
    private BlockingQueue<Message> holdBackQueue;
    private BlockingQueue<Message> deliverQueue;
    private AtomicInteger msgCounter = new AtomicInteger(0);
    private boolean running = false;
    
    /** Should contain information about the number of messages this module has
     * received from other GroupMembers */
    private VectorClock<UID> vc  = new VectorClock<UID>(GroupModule.PID);
    
    public FIFO() {
        this.holdBackQueue = new LinkedBlockingQueue<Message>();
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

    public void put(Message m) {
        try {
            holdBackQueue.put(m);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Message prepareOutgoingMessage(Message m) {
        logger.debug("Prepare outgoing message: " + m);
        // Tick counter for sent messages
        msgCounter.incrementAndGet();
        VectorClock<UID> vc = new VectorClock<UID>(GroupModule.PID,
                                                   msgCounter.get());
        m.setVectorClock(vc);
        return m;
    }

    private class MessageHandler implements Runnable {
        public void run() {
            while (running) { 
                try {
                    Message m = holdBackQueue.take();
                    if (deliverCheck(m)) {
                        deliverQueue.put(m);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private boolean deliverCheck(Message m) {
            // TODO: Auto-generated method stub
            logger.debug("Checking deliver. TODO: implement");
            if (!vc.containsKey(m.getOriginUID())) {
                logger.debug("New process added to VectorClock");
                vc.newProcess(m.getOriginUID());
            }
            
            // Tick counter for receiving message process
            vc.tick(m.getOriginUID());
            
            int otherCounter = m.getVectorClock().get();
            int ownCounterForProcess = vc.get(m.getOriginUID());
            logger.debug("OwnCounterForProcess: {}, inMessageCounter: {}", ownCounterForProcess, otherCounter);
            return true;
        }
    }
}
