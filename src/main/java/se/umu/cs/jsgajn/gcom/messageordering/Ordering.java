package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface Ordering {
    public Message prepareOutgoingMessage(Message m);
    public void put(Message m);
    public Message take();
}
