package se.umu.cs.jsgajn.gcom.communication;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.ordering.CasualTotalOrdering;
import se.umu.cs.jsgajn.gcom.debug.Debugger;

public class ReceiverImpl implements Receiver, Serializable {
    private static final Debugger debugger = Debugger.getDebugger();
    private static final long serialVersionUID = 1L;
    // This will not be sent when object is serialized
    private transient BlockingQueue<Message> q;
    private final UUID PID;
    private transient AtomicInteger sequenceNumber;
    private transient CasualTotalOrdering casualTotal;

    public ReceiverImpl(BlockingQueue<Message> q, final UUID processID)
        throws RemoteException, AlreadyBoundException, NotBoundException,IllegalArgumentException {
        this.q = q;
        this.PID = processID;
        this.sequenceNumber = new AtomicInteger(0);
        this.casualTotal = null;
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

    public Message prepWithSequenceNumber(Message m) throws RemoteException {
        // Will block if active
        debugger.sequencerHoldMessage(m);
            
        if (casualTotal != null) {
            //casualTotal.askForSequenceNumber(m);
            return casualTotal.setSequenceNumber(m);
        }
        m.setSequnceNumber(sequenceNumber.incrementAndGet());
        return m;
    }

    public void createOrdering() throws RemoteException {
        if (casualTotal != null) {
            this.casualTotal = new CasualTotalOrdering();
        }
    }

    public boolean orderingExist() throws RemoteException {
        return casualTotal != null;
    }
}
