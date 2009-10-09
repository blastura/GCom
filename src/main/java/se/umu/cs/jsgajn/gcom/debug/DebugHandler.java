package se.umu.cs.jsgajn.gcom.debug;

import java.util.Queue;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public interface DebugHandler {
    public void messageReceived(Message m);
    public void messageDelivered(Message m);
    public void groupChange(GroupView g);
    public void crash();
    public void block();
    public boolean holdMessage(Message m, Receiver r);
    public boolean doHold();
    public void holdMessage(Message m);
    public boolean hasHoldMessages();
    public Queue<Message> getHoldMessages();
}
