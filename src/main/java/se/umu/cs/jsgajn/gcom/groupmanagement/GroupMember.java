package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UID;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public class GroupMember implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Receiver receiver;
    private final UID PID;

    /**
     * Creates a new GroupMember to use for communication, will get PID from
     * Receiver.
     * 
     * @param receiver The receiver to store, and ask for PID.
     * @throws RemoteException If Receiver doesn't return PID
     *         without throwing exceptions.
     */
    public GroupMember(Receiver receiver) throws RemoteException {
        this.receiver = receiver;
        this.PID = receiver.getPID();
    }

    public Receiver getReceiver() {
        return this.receiver;
    }

    public UID getPID() {
        return this.PID;
    }
}
