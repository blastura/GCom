package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupmanagement.GNS;

public interface CommunicationModule extends Module {
    public GNS connectToGns(String host, int port) throws RemoteException,
    NotBoundException;

    public Receiver getReceiver();
}
