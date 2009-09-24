package se.umu.cs.jsgajn.gcom.messageordering;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public class FIFO implements Ordering {
    BlockingQueue<Message> q = new LinkedBlockingQueue<Message>();
    BlockingQueue<Message> deliverQueue = new LinkedBlockingQueue<Message>();
    
    public FIFO() {
        new Thread(new MessageHandler()).start();
    }
    
    public Message takeDelivered() {
        try {
            return deliverQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
    private void handleMessage(Message take) {
        deliverQueue.add(take);        
    }

    public void deliver(Message m) {
        try {
            q.put(m);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private class MessageHandler implements Runnable {
        public void run() {
            try {
                while (true) { 
                    handleMessage(q.take());
                }
            } catch (InterruptedException e) { 
                System.out.println(e);
           }
        }

    }
}
