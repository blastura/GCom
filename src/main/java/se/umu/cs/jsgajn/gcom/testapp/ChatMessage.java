package se.umu.cs.jsgajn.gcom.testapp;

import se.umu.cs.jsgajn.gcom.groupcommunication.Header;
import se.umu.cs.jsgajn.gcom.groupcommunication.HeaderImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;

public class ChatMessage implements Message {
    private static final long serialVersionUID = 1L;
    private String msg;
    private Header header;

    public ChatMessage(String msg) {
        this.msg = msg;
        this.header = new HeaderImpl(MessageType.CLIENTMESSAGE);
    }

    public void setHeader(Header h) {
        this.header = h;
    }

    public String getMessage() {
        return this.msg;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader() {
        
    }
}