package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public abstract class AbstractGroupMember implements GroupMember {
	private GroupMember stub;
	private GNS gns;
	private Group group;
	private GroupLeader groupLeader;

	/**
	 * @param gnsHost GNS host
	 * @param gnsPort GNS port
	 * @param groupName Group name to connect to
	 * @throws RemoteException If GNS throws exception
	 * @throws AlreadyBoundException If it's not possible to bind this to own registry.
	 * @throws NotBoundException If GNS stub is not found in GNS register.
	 */
	public AbstractGroupMember(String gnsHost, int gnsPort, String groupName)
			throws RemoteException, AlreadyBoundException, NotBoundException {
		this.stub = (GroupMember) UnicastRemoteObject.exportObject(this, 0);
		Registry registry = LocateRegistry.createRegistry(1099); // TODO: change 1099
		registry.bind("self", stub);
		this.gns = connectToGns(gnsHost, gnsPort);
		this.groupLeader = gns.connect(stub, groupName);
		this. group = groupLeader.addToGroup(this);
	}

	/**
	 * @param host Host to GNS
	 * @param port Port to GNS
	 * @return GNS stub
	 * @throws RemoteException If GNS throws exception
	 * @throws NotBoundException If GNS stub can't be found.
	 */
	private GNS connectToGns(String host, int port) throws RemoteException,
			NotBoundException {
		Registry gnsReg = LocateRegistry.getRegistry(host, port);
		return (GNS) gnsReg.lookup("GNS");
	}
	
	public void multicast(Message<?> m) {
		for (GroupMember member : this.group) {
			member.receive(m);
		}
		
	}
}
