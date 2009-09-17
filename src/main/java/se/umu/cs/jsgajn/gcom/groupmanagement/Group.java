package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Collection;

public interface Group extends Collection<GroupMember>, Serializable {
    // List<Server> servers;
    // Server.host och Server.host bör finnas
    public void closeGroup();
    public String getName();
    public GroupMember getGroupLeader();
}
