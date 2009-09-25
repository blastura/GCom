package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class GNSImpl implements GNS {
    
    private transient GNS stub;
    public transient Map<String, GroupSettings> groups;
    
    public GNSImpl() throws RemoteException, AlreadyBoundException {
        this.groups = new HashMap<String, GroupSettings>();
        
        this.stub = (GNS) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(1099); // TODO: change 1099
        registry.bind(GNS.STUB_NAME, stub);
        
        System.out.println("Server is running.");
    }
    
    public GroupSettings connect(GroupSettings gs) {
        GroupSettings group = groups.get(gs.getName());  
        if (group == null) {
            gs.setIsNew(true);
            groups.put(gs.getName(), gs);
            return gs;
        } else {
            group.setIsNew(false);
            return group;
        }
    }
    
    public static void main(String args[]) {
        try {
            new GNSImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

}
