package se.umu.cs.jsgajn.gcom.groupcommunication;

public class HeaderImpl implements Header {
    private MessageType messageType;
    
    public HeaderImpl(MessageType messageType) {
        this.messageType = messageType;
    }
    public MessageType getMessageType() {
        return this.messageType;
    }
}
