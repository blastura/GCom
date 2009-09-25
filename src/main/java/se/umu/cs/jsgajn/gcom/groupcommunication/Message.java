package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.server.UID;

public interface Message extends Serializable {
    public MessageType getMessageType();
    public UID getOriginUID();
    public UID getGroupViewUID();
    public Object getMessage();
    public UID getUID();
}
