package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public interface GNS extends Remote, Serializable {
    public static final String STUB_NAME = "MURRAN";
    public GroupSettings connect(GroupSettings gs) throws RemoteException;
}
