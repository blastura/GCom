package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.server.UID;

public class MessageImpl implements Message {
    private static final long serialVersionUID = 1L;
    private final UID ID;
    private MessageType messageType;
    private UID originID;
    private UID groupViewUID;
    private Object m;
 
    public MessageImpl(Object m, MessageType messageType, UID originID, UID groupViewUID) {
        this.m = m;
        this.messageType = messageType;
        this.originID = originID;
        this.groupViewUID = groupViewUID;
        this.ID = new UID();
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

    public UID getGroupViewUID() {
        return this.groupViewUID;
    }
    
    public UID getUID() {
        return ID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message))
            return false;
        Message oMsg = (Message) o;
        return getUID().equals(oMsg.getUID());
    }
    
    @Override
    public int hashCode() {
        return getUID().hashCode();
    }
    
    @Override
    public String toString() {
        return "[origin: " + this.originID + ", object: " + this.m.toString() + ", "
        + "messageID: " + this.ID + "]";
    }
}