package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.RemoteException;

public interface ReceiverGNS {

    public Receiver connect(Receiver gm, String groupName) throws RemoteException;
}
