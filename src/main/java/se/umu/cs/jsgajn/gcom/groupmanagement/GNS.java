package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;

import se.umu.cs.jsgajn.gcom.testapp.Server;

public interface GNS {
	//List<Group> groups;
	
	public void addGroup();
	public void removeGroup();
	
	public Group joinGroup(GroupMember gm, String groupName);
	
	public void leaveGroup(GroupMember gm, String groupName);
	
	// Ska returnera en medlem i en grupp
	public GroupMember lookUp(String name);
}
