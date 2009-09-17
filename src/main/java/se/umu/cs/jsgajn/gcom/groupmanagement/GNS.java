package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;

public interface GNS extends Remote{
    public static final String STUB_NAME = "GNS";
    public GroupMember connect(GroupMember gm, String groupName);
    public Group getGroup(String name);
}
