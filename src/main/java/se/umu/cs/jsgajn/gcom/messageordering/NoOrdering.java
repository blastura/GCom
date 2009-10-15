package se.umu.cs.jsgajn.gcom.messageordering;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class NoOrdering implements Ordering { 
    private BlockingQueue<Message> msgQueue;
    
    public NoOrdering() {
        this.msgQueue = new LinkedBlockingQueue<Message>();
    }

    public Message prepareOutgoingMessage(Message m, GroupView g) {
        return m;
    }
    
    public Message take() {
        try {
            return msgQueue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void put(Message m) {
        try {
            msgQueue.put(m);
        } catch (InterruptedException e) {
            // TODO: Auto-generated catch block
            e.printStackTrace();
        }
    }
}
