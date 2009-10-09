package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupViewImpl implements GroupView {
    private static final long serialVersionUID = 1L;
    private List<GroupMember> members;
    private String name;
    private GroupMember groupLeader;
    private final UID ID;

    public GroupViewImpl(String name, GroupMember groupLeader) {
        this.members = new ArrayList<GroupMember>();
        this.name = name;
        this.groupLeader = groupLeader;
        this.ID = new UID();
        add(groupLeader);
    }

    public String getName() {
        return name;
    }

    public GroupMember getGroupLeaderGroupMember() {
        return groupLeader;
    }
    
    public UID getID() {
        return ID;
    }
    
    public boolean add(GroupMember m) {
        return members.add(m);
    }

    public Iterator<GroupMember> iterator() {
        return members.iterator();
    }

    public int size() {
        return members.size();
    }

    public boolean remove(GroupMember r) {
        // TODO Auto-generated method stub
        throw new Error("not implemented");
    }
    
    public List getMembers() {
    	return members;
    }
}
