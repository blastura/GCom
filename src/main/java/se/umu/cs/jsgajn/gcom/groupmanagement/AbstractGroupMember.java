package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public abstract class AbstractGroupMember implements GroupMember {
	private GroupMember stub;
	private GNS gns;

	public AbstractGroupMember(String gnsHost, int gnsPort, String groupName)
			throws RemoteException, AlreadyBoundException, NotBoundException {
		this.stub = (GroupMember) UnicastRemoteObject.exportObject(this, 0);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("self", stub);
		this.gns = connectToGns(gnsHost, gnsPort);
		gns.connect(stub, groupName);
	}

	private GNS connectToGns(String host, int port) throws RemoteException,
			NotBoundException {
		Registry gnsReg = LocateRegistry.getRegistry(host, port);
		return (GNS) gnsReg.lookup("GNS");
	}
}
