package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GNS extends Remote, Serializable {
    public static final String STUB_NAME = "GNS";
    public GroupMember connect(GroupMember gm, String groupName) throws RemoteException;
    public Group getGroup(String name) throws RemoteException;
}
