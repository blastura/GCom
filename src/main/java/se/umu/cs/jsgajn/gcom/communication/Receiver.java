package se.umu.cs.jsgajn.gcom.communication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.ordering.CasualTotalOrdering;
import se.umu.cs.jsgajn.gcom.ordering.Ordering;


/**
 * Remote interface for receivers of a group member.
 * 
 * @author dit06ajn, dit06jsg
 */
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
    public Message prepWithSequenceNumber(Message m) throws RemoteException;
    
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
