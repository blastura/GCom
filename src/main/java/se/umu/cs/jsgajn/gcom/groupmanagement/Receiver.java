package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface Receiver extends Remote {
    public static final String STUB_NAME = "snoppen";
    /**
     * For receiving remote messages from other group members. This method is
     * called remotely.
     * 
     * @param m The message to receive.
     * @throws RemoteException
     */
    public void receive(Message m) throws RemoteException;
    public Message getMessage() throws InterruptedException;
}
