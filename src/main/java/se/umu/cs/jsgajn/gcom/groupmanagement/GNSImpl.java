package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

public class GNSImpl implements GNS {
    
    private transient GNS stub;
    public transient Collection<Group> groups;
    
    public GNSImpl() throws RemoteException, AlreadyBoundException {
        this.groups = new ArrayList<Group>();
        
        this.stub = (GNS) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(1099); // TODO: change 1099
        registry.bind(GNS.STUB_NAME, stub);
        
        System.out.println("Server is running.");
    }
    
    public Receiver connect(Receiver gm, String groupName) {
        Group group = getGroup(groupName);
        
        if (group != null) {
            return group.getGroupLeaderReceiver();
        } else {
            GroupImpl newGroup = new GroupImpl(groupName, gm);
            groups.add(newGroup);
            return newGroup.getGroupLeaderReceiver();            
        }
    }
   
    public Group getGroup(String name) {
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
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
