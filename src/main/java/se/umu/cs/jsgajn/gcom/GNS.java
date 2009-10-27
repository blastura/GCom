package se.umu.cs.jsgajn.gcom;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.management.GroupMember;
import se.umu.cs.jsgajn.gcom.management.GroupSettings;

/**
 * Remote interface for group name server instances.
 * 
 * @author dit06ajn, dit06jsg
  */
public interface GNS extends Remote, Serializable {
    /**
     * String to use for binding the GNS instance in a register.
     */
    public static final String STUB_NAME = "GNSstub";
    
    
    /**
     * Receive GroupSettings for a particular group. If no group exists with the
     * specified GroupSettings a new one is created and the same GroupSettings is
     * returned. Otherwise the existing GroupSettings of the group is returned. 
     * 
     * @param gs The preferred GroupSettings, will contain a group name.
     * @return The GroupSettings to use.
     * @throws RemoteException If the GNS throws an exception.
     */
    public GroupSettings connect(GroupSettings gs) throws RemoteException;
    
    /**
     * If a leader is crashed in an existing group, this method should be called
     * by the new leader.
     * 
     * @param gm The new leader.
     * @param groupName The group name.
     * @throws RemoteException If the GNS throws an exception.
     */
    public void setNewLeader(GroupMember gm, String groupName) throws RemoteException;
}
