package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.server.UID;

public class MessageImpl implements Message {

    private UID id;
    private MessageType messageType;
    private UID originID;    
    private Object m;
    
    public MessageImpl(Object m, MessageType messageType, UID originID) {
        this.m = m;
        this.messageType = messageType;
        this.originID = originID;
        this.id = new UID();
    }

    public Object getMessage() {
        return m;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }
    public UID getOriginUID() {
        return originID;
    }
    
    public UID getUID() {
        return id;
    }
}
