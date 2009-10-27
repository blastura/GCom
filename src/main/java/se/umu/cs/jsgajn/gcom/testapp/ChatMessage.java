package se.umu.cs.jsgajn.gcom.testapp;

import java.io.Serializable;

public class ChatMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private String msg;
    private String name;
    
    public ChatMessage(String msg, String name) {
        this.msg = msg;
        this.name = name;
    }

    public String getMessage() {
        return this.msg;
    }
    public String getName() {
        return this.name;
    }
}