package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface GroupMember extends GroupLeader,Remote {
    public boolean receive(Message<?> m) throws RemoteException;
    public GroupMember joinGroup(String name);
}
