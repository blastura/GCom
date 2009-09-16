package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.Remote;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface GroupMember extends Remote {
	public boolean receive(Message m);
}
