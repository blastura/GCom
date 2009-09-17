package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;

import se.umu.cs.jsgajn.gcom.testapp.Server;

public interface GNS {
    public GroupMember connect(GroupMember gm, String groupName);
}
