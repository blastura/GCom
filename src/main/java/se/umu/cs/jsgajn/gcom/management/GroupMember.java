package se.umu.cs.jsgajn.gcom.management;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

import se.umu.cs.jsgajn.gcom.communication.Receiver;

public class GroupMember implements Serializable, Comparable<GroupMember> {
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
    
    public int compareTo(GroupMember other) {
        return getPID().compareTo(other.getPID());
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
        return getPID().equals(ManagementModule.PID)
            ? "ME"
            : getPID().toString();
    }
}
