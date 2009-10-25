package se.umu.cs.jsgajn.gcom.debug;

import java.util.ArrayList;
import java.util.UUID;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.communication.Receiver;
import se.umu.cs.jsgajn.gcom.management.GroupView;
import se.umu.cs.jsgajn.gcom.ordering.VectorClock;

public interface DebugHandler {
    public void messageReceived(Message m);
    public void messageDelivered(Message m);
    public void groupChange(GroupView g);
    public void crash();
    public void block();
    public void updateVectorClock(VectorClock<UUID> vc);
    public boolean holdMessage(Message m, Receiver r);
    public boolean isModelInitialized(int hack);
    public void updateOrderingHoldList(ArrayList<Message> holdList);
}
