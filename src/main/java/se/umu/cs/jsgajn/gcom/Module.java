package se.umu.cs.jsgajn.gcom;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

/**
 * @author dit06ajn, dit06jsg
 */
public interface Module {
    
    /**
     * Sends a new message to this Module. Used for outgoing messages.
     * 
     * @param m The Message to send.
     * @param g The GroupView to send to.
     */
    public void send(Message m, GroupView g);
    
    /**
     * Called by a different Module to when message should be delivered to this
     * Module. Usually only used to put message in a queue.
     * 
     * @param m The Message to deliver.
     */
    public void deliver(Message m);
    
    public void setSendReceiver(Module m);
    public void setDeliverReceiver(Module m);
}
