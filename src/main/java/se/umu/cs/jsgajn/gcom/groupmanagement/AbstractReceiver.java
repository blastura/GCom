package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public abstract class AbstractReceiver<T extends Serializable> implements Remote {
    
    public static String STUB_NAME;
    protected AbstractReceiver stub;
    
    public AbstractReceiver() 
    throws RemoteException, AlreadyBoundException, NotBoundException {
                
        this.stub = (AbstractReceiver) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(1099); // TODO: change 1099
        registry.bind(STUB_NAME, stub);
        
    }
    
}
