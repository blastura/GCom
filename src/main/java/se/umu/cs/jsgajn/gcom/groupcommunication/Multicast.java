package se.umu.cs.jsgajn.gcom.groupcommunication;

public interface Multicast {
	public void deliver();
	public void send(Message m);
}
