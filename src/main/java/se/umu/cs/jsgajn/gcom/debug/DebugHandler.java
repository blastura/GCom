package se.umu.cs.jsgajn.gcom.debug;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface DebugHandler {
    public void messageReceived(Message m);
    public void messageDelivered(Message m);
    public void crash();
    public void block();
}
