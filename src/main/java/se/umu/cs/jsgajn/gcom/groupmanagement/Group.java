package se.umu.cs.jsgajn.gcom.groupmanagement;

import se.umu.cs.jsgajn.gcom.testapp.Server;

public interface Group {
	// List<Server> servers;
	// Server.host och Server.host bör finnas
	
	public void closeGroup();
	public void addMember(Server server);
	public void removeMember(Server server);
}
