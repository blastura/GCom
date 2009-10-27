package se.umu.cs.jsgajn.gcom.debug;

import java.util.ArrayList;
import java.util.UUID;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.communication.Receiver;
import se.umu.cs.jsgajn.gcom.management.GroupView;
import se.umu.cs.jsgajn.gcom.ordering.VectorClock;

public interface DebugHandler {
    /**
     * Populate the received messages-table 
     * with Message m.
     * 
     * @param m
     */
    public void messageReceived(Message m);
    
    /**
     * Populate the received messages-table 
     * with Message m.
     * 
     * @param m
     */
    public void messageDelivered(Message m);
    
    /**
     * Update group member-table with members 
     * of GroupView g.
     * 
     * @param g
     */
    public void groupChange(GroupView g);
    
    /**
     * Exit the current process
     */
    public void crash();
    
    /**
     * Nothing
     */
    public void block();
    
    /**
     * Update the vector clock-table with contents of
     * VectorClock vc
     * 
     * @param vc
     */
    public void updateVectorClock(VectorClock<UUID> vc);
    
    /**
     * Puts Message m in the debuggers hold queue and does 
     * not return it to the process. Saves Receiver r so it
     * know who want the message when its time to send it back.
     * 
     * @param m
     * @param r
     * @return
     */
    public boolean holdMessage(Message m, Receiver r);
    
    /**
     * Returns true if the model has been initialized.
     * @param hack
     * @return
     */
    public boolean isModelInitialized(int hack);
    
    /**
     * Updates the ordering hold list with the contents of 
     * ArrayList<Message holdList.
     * 
     * @param holdList
     */
    public void updateOrderingHoldList(ArrayList<Message> holdList);
    
    /** 
     * Will block until debugger want's to release it 
     * 
     * @param m
     */
    public void sequencerHoldMessage(Message m);
}
