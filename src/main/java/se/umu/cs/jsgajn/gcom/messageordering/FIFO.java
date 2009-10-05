package se.umu.cs.jsgajn.gcom.messageordering;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public class FIFO implements Ordering {
    private static final Logger logger = LoggerFactory.getLogger(FIFO.class);
    private BlockingQueue<Message> holdBackQueue;
    private BlockingQueue<Message> deliverQueue;

    public FIFO() {
        this.holdBackQueue = new LinkedBlockingQueue<Message>();
        this.deliverQueue = new LinkedBlockingQueue<Message>();
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
        // TODO: set counters, vectorclocks and stuff.
        logger.debug("Prepare outgoing message: " + m);
        return m;
    }

    private class MessageHandler implements Runnable {
        public void run() {
            while (true) { 
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
            // TODO Auto-generated method stub
            logger.debug("Checking deliver. TODO: implement");
            return true;
        }
    }
}
