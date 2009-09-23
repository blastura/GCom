package se.umu.cs.jsgajn.gcom.testapp;

import se.umu.cs.jsgajn.gcom.groupcommunication.Header;
import se.umu.cs.jsgajn.gcom.groupcommunication.AbstractHeader;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageTypes;

public class ChatMessage implements Message<String> {
    private static final long serialVersionUID = 1L;
    private String msg;
    private Header header;

    public ChatMessage(String msg) {
        this.msg = msg;
        this.header = new AbstractHeader(MessageTypes.CLIENTMESSAGE);
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