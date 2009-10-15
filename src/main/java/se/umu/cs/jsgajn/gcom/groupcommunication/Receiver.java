package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UID;


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
    
    /**
     * Return a unique process identifier for this receiver.
     * 
     * @return A unique process identifier for this receiver.
     * @throws RemoteException
     */
    public UID getPID() throws RemoteException;
    
    
    /**
     * Return the sequence number
     * 
     * @return
     */
    public int getSequenceNumber() throws RemoteException;
}
