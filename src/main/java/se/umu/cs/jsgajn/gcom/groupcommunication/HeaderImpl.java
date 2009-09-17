package se.umu.cs.jsgajn.gcom.groupcommunication;

public class HeaderImpl implements Header {
    private MessageTypes messageType;
    
    public HeaderImpl(MessageTypes messageType) {
        this.messageType = messageType;
    }
    public MessageTypes getMessageType() {
        return this.messageType;
    }
}
