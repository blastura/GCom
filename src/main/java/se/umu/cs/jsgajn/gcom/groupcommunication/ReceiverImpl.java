package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import se.umu.cs.jsgajn.gcom.messageordering.CasualTotal;

public class ReceiverImpl implements Receiver, Serializable {
    private static final long serialVersionUID = 1L;
    // This will not be sent when object is serialized
    private transient BlockingQueue<Message> q;
    private final UUID PID;
    private AtomicInteger sequenceNumber;
    private CasualTotal ordering;

    public ReceiverImpl(BlockingQueue<Message> q, final UUID processID)
        throws RemoteException, AlreadyBoundException, NotBoundException,IllegalArgumentException {
        this.q = q;
        this.PID = processID;
        this.sequenceNumber = new AtomicInteger(0);
        this.ordering = null;
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

    public UUID getPID() throws RemoteException {
        return this.PID;
    }

    public int getSequenceNumber(Message m) {
        if (ordering != null) {
            ordering.askForSequenceNumber(m);
            ordering.getSequenceNumber(m);
        }
        return sequenceNumber.incrementAndGet();
    }

    public void createOrdering() {
        this.ordering = new CasualTotal();
    }

    public boolean orderingExist() {
        if (ordering == null) {
            return false;
        } else {
            return true;
        }
    }
}
