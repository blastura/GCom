package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CrashListImpl implements CrashList {
	
	private static final long serialVersionUID = 1L;
	private List<GroupMember> crashedMembers;

	public CrashListImpl() {
		this.crashedMembers = new ArrayList<GroupMember>();
	}
	
	public boolean add(GroupMember r) {
		this.crashedMembers.add(r);
		return false;
	}

	public List<GroupMember> getAll() {
		return this.crashedMembers;
	}

	public boolean isEmpty() {
		return this.crashedMembers.isEmpty();
	}

	public Iterator<GroupMember> iterator() { 
		return crashedMembers.iterator();
	}

}
