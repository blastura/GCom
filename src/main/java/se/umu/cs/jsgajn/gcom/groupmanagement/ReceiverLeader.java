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

import se.umu.cs.jsgajn.gcom.groupcommunication.AbstractHeader;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageTypes;

public class ReceiverLeader implements Receiver {
    // This will not be sent when object is serialized
    private transient BlockingQueue<Message> q;
    private Receiver stub;

    public ReceiverLeader() 
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
    
    public Group joinGroup(Receiver member) {
        if (this.group == null) {
            System.out.println("I am my on master?");
            Group result =  new GroupImpl(this.groupName, receiver);
            result.add(receiver);
            this.group = result;
            // Multicastar ut listan på nya grupper
            multicast(new MessageImpl(result, new AbstractHeader(MessageTypes.GROUPCHANGE)));
            return result;
        }
        System.out.println("Group exists you got it");
        this.group.add(member);
        // Multicastar ut nya grupplistan
        multicast(new MessageImpl(this.group, new AbstractHeader(MessageTypes.GROUPCHANGE)));
        return this.group;
    }
    
}
