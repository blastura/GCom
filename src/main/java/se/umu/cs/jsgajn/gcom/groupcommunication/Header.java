package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;

public interface Header extends Serializable {
    public MessageType getMessageType();
}
