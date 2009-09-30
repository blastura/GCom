package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import se.umu.cs.jsgajn.gcom.testapp.ChatMember;

public class GNSImpl implements GNS {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(GNSImpl.class); 
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

    private void restore() {
        // Restore serialized groupssettings      
    }

    private void save() {
        try {
            OutputStream buffer = new BufferedOutputStream(new FileOutputStream( "GroupSettingMap.ser" ));
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {
                output.writeObject(this.groups);
            } finally {
                output.close();
            }
        } catch (IOException e) {
            logger.warn("Cannot save groupsettings.");
        }
    }
}
