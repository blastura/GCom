package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import se.umu.cs.jsgajn.gcom.messageordering.CasualTotal;
import se.umu.cs.jsgajn.gcom.messageordering.Ordering;


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
    public UUID getPID() throws RemoteException;
    
    
    /**
     * Return the sequence number
     * 
     * @return
     */
    public int getSequenceNumber(Message m) throws RemoteException;
    
    /**
     * Sets orderingmodule. Is used in Casual-Total ordering.
     * @throws RemoteException 
     */
    public void createOrdering() throws RemoteException;
    

    /**
     * Get orderingmodule. Is used in Casual-Total ordering.
     * @throws RemoteException
     */
    public boolean orderingExist() throws RemoteException;
}
