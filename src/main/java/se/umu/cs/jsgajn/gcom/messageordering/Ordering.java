package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface Ordering {
    public void add(Message m);
    public Message takeDelivered();
}
