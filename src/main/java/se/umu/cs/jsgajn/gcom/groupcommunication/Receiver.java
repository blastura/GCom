package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Receiver<T extends Serializable> extends Remote {
    
    /**
     * For receiving remote messages from other group members. This method is
     * called remotely.
     * 
     * @param m The message to receive.
     * @throws RemoteException
     */
    public void receive(Message<T> m) throws RemoteException;
}
