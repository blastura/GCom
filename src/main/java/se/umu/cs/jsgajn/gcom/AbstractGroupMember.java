package se.umu.cs.jsgajn.gcom;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public abstract class AbstractGroupMember implements GroupMember {
	
	public AbstractGroupMember() throws RemoteException {
		Registry registry = LocateRegistry.getRegistry("localhost");
		GroupMember stub = (GroupMember) UnicastRemoteObject.exportObject(this, 0);
		
	}
}	
