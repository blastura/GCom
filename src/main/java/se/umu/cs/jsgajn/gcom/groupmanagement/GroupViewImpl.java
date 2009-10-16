package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

public class GroupViewImpl implements GroupView {
	private static final long serialVersionUID = 1L;
	private List<GroupMember> members;
	private String name;
	private GroupMember groupLeader;
	private UID id;

	public GroupViewImpl(String name, GroupMember groupLeader) {
		this.members = new ArrayList<GroupMember>();
		this.name = name;
		this.groupLeader = groupLeader;
		this.id = new UID();
		add(groupLeader);
	}

	public String getName() {
		return name;
	}

	public GroupMember getGroupLeaderGroupMember() {
		return groupLeader;
	}

	public UID getID() {
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
		this.id = new UID();
	}

	public boolean isEmpty() {
	return members.isEmpty();
	}

	public List<GroupMember> getAll() {
		return members;
	}

	public boolean remove(int id) {
		members.remove(id);
		return true;
	}

}
