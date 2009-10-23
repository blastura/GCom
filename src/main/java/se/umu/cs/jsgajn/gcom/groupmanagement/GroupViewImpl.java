package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

public class GroupViewImpl implements GroupView {    
    private static final long serialVersionUID = 1L;
    private List<GroupMember> members;
    private String name;
    private GroupMember groupLeader;
    private UUID id;

    public GroupViewImpl(GroupView gv) {
        this.members = new ArrayList<GroupMember>(gv.getAll());
        this.name = gv.getName();
        this.groupLeader = gv.getGroupLeaderGroupMember();
        this.id = gv.getID();
    }

    public GroupViewImpl(GroupView gv, List<GroupMember> members) {
        this.members = members;
        this.name = gv.getName();
        this.groupLeader = gv.getGroupLeaderGroupMember();
        this.id = gv.getID();
    }
    
    public GroupViewImpl(String name, GroupMember groupLeader) {
        this.members = new ArrayList<GroupMember>();
        this.name = name;
        this.groupLeader = groupLeader;
        this.id = UUID.randomUUID();
        add(groupLeader);
    }

    public String getName() {
        return name;
    }

    public GroupMember getGroupLeaderGroupMember() {
        return groupLeader;
    }

    public UUID getID() {
        return id;
    }

    public boolean add(GroupMember m) {
        groupChanged();
        return members.add(m);
    }

    public Iterator<GroupMember> iterator() {
        return members.iterator();
    }

    public int size() {
        return members.size();
    }

    public boolean remove(GroupMember gm) {
        groupChanged();
        return members.remove(gm);
    }

    public boolean removeAll(Collection<GroupMember> gm) {
        groupChanged();
        return members.removeAll(gm);
    }

    /**
     * Should be called by all methods that change the state of this group.
     */
    private void groupChanged() {
        this.id = UUID.randomUUID();
    }

    public boolean isEmpty() {
        return members.isEmpty();
    }

    public List<GroupMember> getAll() {
        // TODO: danger
        return members;
    }

    public boolean remove(int id) {
        members.remove(id);
        return true;
    }

    public UUID getHighestUUID() {
        return Collections.max(members).getPID();
    }

    public void setNewLeader(GroupMember groupMember) {
        this.groupLeader = groupMember;
    }

    public List<GroupMember> getMembersWithHigherUUID(UUID uid) {
        List<GroupMember> membersWithHigher = new ArrayList<GroupMember>();
        for (GroupMember gm : members) {
            if (gm.getPID().compareTo(uid) == -1) {
                membersWithHigher.add(gm);
            }
        }
        return membersWithHigher;
    }
}
