package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public class GroupMember implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Receiver receiver;
    private final UUID PID;

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
    
    public UUID getPID() {
        return this.PID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GroupMember))
            return false;
        GroupMember oMem = (GroupMember) o;
        return getPID().equals(oMem.getPID());
    }

    @Override
    public int hashCode() {
        return getPID().hashCode();
    }
    
    @Override
    public String toString() {
        return getPID().equals(GroupModule.PID)
            ? "ME"
            : getPID().toString();
    }
}
