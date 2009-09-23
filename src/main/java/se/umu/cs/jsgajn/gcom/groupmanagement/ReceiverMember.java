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

public class ReceiverMember implements Receiver {
    // This will not be sent when object is serialized
    private transient BlockingQueue<Message> q;
    private Receiver stub;

    public ReceiverMember() 
    throws RemoteException, AlreadyBoundException, NotBoundException{
        this.q = new LinkedBlockingQueue<Message>();

        this.stub = (Receiver) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(1099); // TODO: change 1099
        registry.bind(Receiver.STUB_NAME, stub);
    }
    
    /**
     * Used to get message from message queue.
     * 
     * @return The oldest message in queue.
     */
    public Message getMessage() {
        return q.poll();
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
