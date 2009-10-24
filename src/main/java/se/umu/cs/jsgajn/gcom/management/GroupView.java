package se.umu.cs.jsgajn.gcom.management;

import java.io.Serializable;
import java.util.UUID;
import java.util.Collection;
import java.util.List;

public interface GroupView extends Iterable<GroupMember>, Serializable {
    public UUID getID();
    public String getName();
    public GroupMember getGroupLeaderGroupMember();
    public boolean add(GroupMember r);
    public boolean remove(GroupMember r);
    public boolean remove(int id);
    public boolean removeAll(Collection<GroupMember> gm);
    public int size();
    public boolean isEmpty();
    public List<GroupMember> getAll();
    public UUID getHighestUUID();
    public void setNewLeader(GroupMember groupMember);
    public List<GroupMember> getMembersWithHigherUUID(UUID uid);
}
