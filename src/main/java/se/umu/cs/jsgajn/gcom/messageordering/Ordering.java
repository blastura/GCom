package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface Ordering {
    public static enum type {FIFO}
    public void deliver(Message m);
    public Message takeDelivered();
}
