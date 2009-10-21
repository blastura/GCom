package se.umu.cs.jsgajn.gcom.groupcommunication;

public class MessageCouldNotBeSentException extends Exception {

	private static final long serialVersionUID = 1L;
	private Message m;
	
	public MessageCouldNotBeSentException(Message m) {
		this.m = m;
	}
	
	public Message get() {
		return m;
	}

}
