package se.umu.cs.jsgajn.gcom.messageordering;

import java.rmi.server.UID;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class Total implements Ordering {
    private static final Logger logger = LoggerFactory.getLogger(FIFO.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private BlockingQueue<Message> receiveQueue;
    private BlockingQueue<Message> deliverQueue;

    private boolean running = false;
    
	public Total() {
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();
        this.running = true;
        new Thread(new MessageHandler(), "Total order thread").start();
	}
	
	public Message prepareOutgoingMessage(Message m, GroupView g) {
		int sequenceNumber = 
			g.getGroupLeaderGroupMember().getReceiver().getSequenceNumber();
        
		VectorClock<UID> vc = new VectorClock<UID>(GroupModule.PID,
				sequenceNumber);
        m.setVectorClock(vc);
        logger.debug("OUT: Prepared outgoing message: " + m);
        return m;
	}

	public void put(Message m) {
		try {
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
        private Queue<Message> holdBackQueue = new LinkedList<Message>();
        private int latestReceivedSequenceNumber = 0;
        public void run() {
            while (running) {
                try {
                    Message m = receiveQueue.take();

                    if (deliverCheck(m)) {
                        deliverQueue.put(m);
                        // TODO: Sort
                        // Check every message in holdBackQueue and deliver if
                        // possible
                        for (Message holdMessage : holdBackQueue) {
                            if (deliverCheck(holdMessage)) {
                                deliverQueue.put(holdMessage);
                            }
                        }
                    } else {
                        holdBackQueue.add(m);
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
            int sequenceNumber = m.getVectorClock().get();
            if(sequenceNumber == (this.latestReceivedSequenceNumber + 1)) {
            	this.latestReceivedSequenceNumber++;
            	return true;
            } else {
            	return false;
            }
        }
    }	
}
