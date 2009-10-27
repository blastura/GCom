package se.umu.cs.jsgajn.gcom.management;

import java.io.Serializable;
import java.util.UUID;
import java.util.Collection;
import java.util.List;

/**
 * Interface to represent the composition of a group.
 * 
 * @author dit06ajn, dit06jsg
  */
public interface GroupView extends Iterable<GroupMember>, Serializable {
    /**
     * @return The unique id of this GroupView.
     */
    public UUID getID();
    
    /**
     * @return The name of this group.
     */
    public String getName();
    
    /**
     * @return The leader of the group.
     */
    public GroupMember getGroupLeaderGroupMember();
    
    /**
     * Add a new member to the GroupView.
     * @param m The new member.
     * @return true if the memeber was added.
     */
    public boolean add(GroupMember m);
    
    /**
     * Removes a member from the group.
     * @param m The member to remove.
     * @return true if the member was removed.
     */
    public boolean remove(GroupMember m);
    
    /**
     * ?
     * 
     * @param id
     * @return
     */
    public boolean remove(int id);
    
    /**
     * Remove all members in specified collection.
     * @param gm All members to remove.
     * @return true if the members where removed.
     */
    public boolean removeAll(Collection<GroupMember> gm);

    /**
     * @return the number of members in the group.
     */
    public int size();
    
    /**
     * @return true if the group contains no members.
     */
    public boolean isEmpty();

    /**
     * @return A list of all members in this group.
     */
    public List<GroupMember> getAll();
    
    /**
     * @return The member with the highest unique id.
     */
    public UUID getHighestUUID();
    
    /**
     * @param groupMember A new group leder for this group.
     */
    public void setNewLeader(GroupMember groupMember);
    
    /**
     * All members with higher UUID than the specified.
     * 
     * @param uid The UUID to check against.
     * @return All members with higher UUID than param uid.
     */
    public List<GroupMember> getMembersWithHigherUUID(UUID uid);
}
