package se.umu.cs.jsgajn.gcom.GroupManagement;

import java.rmi.Remote;

import se.umu.cs.jsgajn.gcom.Group;
import se.umu.cs.jsgajn.gcom.GroupMember;
import se.umu.cs.jsgajn.gcom.Server;

public interface ManagementService {
	//List<Group> groups;
	
	public void addGroup();
	public void removeGroup();
	
	public Group joinGroup(GroupMember gm, String groupName);
	
	public void leaveGroup(GroupMember gm, String groupName);
	
	// Ska returnera en medlem i en grupp
	public Remote lookUp(String name);
}
