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
	
	public AbstractGroupMember(String gnsHost, int gnsPort, String groupName) {
		try {
			this.stub = (GroupMember) UnicastRemoteObject.exportObject(
					this, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("self", stub);
			if (connectToGns(gnsHost, gnsPort)) {
				gns.connect(stub, groupName);
			}
		} catch (RemoteException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO - fix error message
			e.printStackTrace();
		}
	}

	private boolean connectToGns(String host, int port) {
		try {
			Registry gnsReg = LocateRegistry.getRegistry(host, port);
			this.gns = (GNS) gnsReg.lookup("GNS");
		} catch (RemoteException e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
