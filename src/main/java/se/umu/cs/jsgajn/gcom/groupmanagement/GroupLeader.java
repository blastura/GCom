package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;

public interface GroupLeader extends Remote {
    public boolean addToGroup(GroupMember member);
    public boolean removeFromGroup(GroupMember member);
}
