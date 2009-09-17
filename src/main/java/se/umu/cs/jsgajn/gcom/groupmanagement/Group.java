package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.util.Collection;

import se.umu.cs.jsgajn.gcom.testapp.Server;

public interface Group extends Collection<GroupMember> {
    // List<Server> servers;
    // Server.host och Server.host bör finnas
    public void closeGroup();
}
