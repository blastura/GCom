package se.umu.cs.jsgajn.gcom.testapp;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public class ChatMessage implements Message<String> {
    private static final long serialVersionUID = 1L;
    private String msg;

	public ChatMessage(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return this.msg;
	}

}
