package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupLeader extends Remote {
	/**
	 * Should be used to add a member in a group, GroupLeader is responsible for
	 * multicasting new group construction to all members of the group including
	 * the newly added member.
	 * 
	 * @param member The new member.
	 * @return A Group.
	 */
	public Group joinGroup(Receiver member) throws RemoteException;
	
	public boolean removeFromGroup(Receiver member) throws RemoteException;
}
