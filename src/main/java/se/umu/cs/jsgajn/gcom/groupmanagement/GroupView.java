package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.Collection;

public interface GroupView extends Iterable<GroupMember>, Serializable {
    public UID getID();
    public String getName();
    public GroupMember getGroupLeaderGroupMember();
    public boolean add(GroupMember r);
    public boolean remove(GroupMember r);
    public boolean removeAll(Collection<GroupMember> gm);
    public int size();
}
