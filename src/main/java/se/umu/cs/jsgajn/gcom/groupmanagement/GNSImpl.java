package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class GNSImpl implements GNS {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(GNSImpl.class); 
    private transient GNS stub;
    private Properties prop;
    private int port = 1099;
    public transient Map<String, GroupSettings> groups;

    public GNSImpl() throws AlreadyBoundException, RemoteException {
        this(new HashMap<String, GroupSettings>());
    }

    public GNSImpl(Map<String, GroupSettings> groups) throws AlreadyBoundException, RemoteException {
        this.prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("/application.properties"));
            this.port = Integer.parseInt(prop.getProperty("gns-port"));
        } catch (IOException e) {
            logger.warn("application.properties not found");
        }

        this.groups = groups;
        this.stub = (GNS) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(this.port);
        registry.bind(GNS.STUB_NAME, stub);
        System.out.println("Server is running at port " + this.port);
    }

    public GroupSettings connect(GroupSettings gs) {
        GroupSettings group = groups.get(gs.getName()); 
        if (group == null) {
            gs.setIsNew(true);
            groups.put(gs.getName(), gs);
            save();
            logger.info("New group created");
            return gs;
        } else {
            group.setIsNew(false);
            logger.info("Existing group '" + group.getName() + "' served to:" + gs.getLeader());
            return group;
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, GroupSettings> restore(String restoreFile) {
        // Restore serialized groupssettings
        try {
            ObjectInputStream buffer = new ObjectInputStream(new FileInputStream(restoreFile));
            return (Map<String, GroupSettings>) buffer.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Restore failed: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Restore failed: " + e.getMessage());
            return null;           
        }
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
    
    private static Map<String, GroupSettings> filterOutCrashedGroups(Map<String, GroupSettings> groups) {
        //Set<String> crachedGroups = new HashSet<String>();
        for (GroupSettings gs : groups.values()) {
            try {
                gs.getLeader().getReceiver().getPID();
            } catch (RemoteException e) {
                logger.info("Group '" + gs.getName() + "' crasched");
                //crachedGroups.add(gs.getName());
                groups.remove(gs.getName());
            }
        }
        
//        for (String name : crachedGroups) {
//            groups.remove(name);
//        }
        return groups;
    }


    public static void main(String args[]) {
        try {
            if (args.length == 1) {
                Map<String, GroupSettings> restoredGroups = restore(args[0]);
                if (restoredGroups != null) {
                    logger.info("Restored groups: " + restoredGroups);
                    restoredGroups = filterOutCrashedGroups(restoredGroups);
                    new GNSImpl(restoredGroups);
                }
            } else {
                new GNSImpl();
            }            
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
