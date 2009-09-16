package se.umu.cs.jsgajn.gcom;

public interface ChatServer {
	// List<Client> clients; 	
	public void connect("Client");
	public void say("String");
	// When someone says something send to other clients;
}
