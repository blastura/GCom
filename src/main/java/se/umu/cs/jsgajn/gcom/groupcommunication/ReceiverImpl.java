package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ReceiverImpl<T extends Serializable> implements Receiver<T> {
    // This will not be sent when object is serialized
    private transient BlockingQueue<Message<T>> q;

    public ReceiverImpl() {
        this.q = new LinkedBlockingQueue<Message<T>>();
    }
    
    /**
     * Used to get message from message queue.
     * 
     * @return The oldest message in queue.
     */
    public Message<T> getMessage() {
        return q.poll();
    }

    public void receive(Message<T> m) throws RemoteException {
        // Simply add message to blockingQueue, if queue is busy, it will block.
        try {
            q.put(m);
        } catch (InterruptedException e) {
            // If queue is interrupted while waiting for insertion into queue.
            // TODO: How shall we handle this?
            e.printStackTrace();
        }
    }
}
