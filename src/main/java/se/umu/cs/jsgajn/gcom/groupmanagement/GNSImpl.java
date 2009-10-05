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
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GNSImpl implements GNS {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(GNSImpl.class); 
    private transient GNS stub;
    public transient Map<String, GroupSettings> groups;

    public GNSImpl() throws AlreadyBoundException, RemoteException {
        this(new HashMap<String, GroupSettings>(), -1);
    }

    public GNSImpl(Map<String, GroupSettings> groups) throws AlreadyBoundException, RemoteException {
        this(groups, -1); 
    }

    public GNSImpl(int port) throws AlreadyBoundException, RemoteException {
        this(new HashMap<String, GroupSettings>(), port);
    }

    public GNSImpl(Map<String, GroupSettings> groups, int port) throws AlreadyBoundException, RemoteException {
        if (port < 1) {
            String portString = System.getProperty("gcom.gns.port");
            if (portString == null) { // Try application properties
                try {
                    Properties prop = new Properties();
                    prop.load(this.getClass().getResourceAsStream("/application.properties"));
                    portString = prop.getProperty("gcom.gns.port");
                } catch (IOException e) {
                    logger.warn("application.properties not found");
                }
            }
            if (portString == null) {
                port = Registry.REGISTRY_PORT;
            } else {
                try {
                    port = Integer.parseInt(portString);
                } catch (NumberFormatException e) {
                    logger.warn("GNS port number not a number: " + e.getMessage());
                    port = Registry.REGISTRY_PORT;
                }
            }
        }

        this.groups = groups;
        this.stub = (GNS) UnicastRemoteObject.exportObject(this, 0);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind(GNS.STUB_NAME, stub);
        System.out.println("Server is running at port " + port);
    }

    public GroupSettings connect(GroupSettings gs) {
        GroupSettings group = groups.get(gs.getName()); 
        if (group == null) {
            gs.setIsNew(true);
            groups.put(gs.getName(), gs);
            save();
            logger.info("New group created: " + gs);
            return gs;
        } else {
            group.setIsNew(false);
            logger.info("Existing group '" + group + "' served to:" + gs.getLeader());
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

    // TODO: should this be done?
    private static Map<String, GroupSettings> filterOutCrashedGroups(Map<String, GroupSettings> groups) {
        //Set<String> crachedGroups = new HashSet<String>();
        for (GroupSettings gs : groups.values()) {
            try {
                gs.getLeader().getReceiver().getPID();
            } catch (RemoteException e) {
                logger.info("Group '" + gs + "' crasched");
                //crachedGroups.add(gs.getName());
                groups.remove(gs.getName());
            }
        }

        //      for (String name : crachedGroups) {
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
                    //restoredGroups = filterOutCrashedGroups(restoredGroups);
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
