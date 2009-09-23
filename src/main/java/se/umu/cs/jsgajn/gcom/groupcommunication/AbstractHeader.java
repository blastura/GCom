package se.umu.cs.jsgajn.gcom.groupcommunication;

public class AbstractHeader implements Header {
    private MessageTypes messageType;
    
    public AbstractHeader(MessageTypes messageType) {
        this.messageType = messageType;
    }
    public MessageTypes getMessageType() {
        return this.messageType;
    }
}
