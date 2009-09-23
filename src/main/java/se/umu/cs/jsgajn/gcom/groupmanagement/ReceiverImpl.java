package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public class ReceiverImpl implements Receiver {
    // This will not be sent when object is serialized
    private transient BlockingQueue<Message> q;
    private Receiver stub;

    public ReceiverImpl() 
    throws RemoteException, AlreadyBoundException, NotBoundException{
        this.q = new LinkedBlockingQueue<Message>();
    }
    
    /**
     * Used to get message from message queue.
     * 
     * @return The oldest message in queue.
     * @throws InterruptedException 
     */
    public Message getMessage() throws InterruptedException {
        return q.take();
    }
    

    public void receive(Message m) throws RemoteException {
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
