package se.umu.cs.jsgajn.gcom;

import java.rmi.Remote;

public interface GroupMember {
	public boolean receive(Message m);
	
}
