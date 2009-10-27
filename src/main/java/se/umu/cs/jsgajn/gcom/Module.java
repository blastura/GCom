package se.umu.cs.jsgajn.gcom;

import se.umu.cs.jsgajn.gcom.management.GroupView;

/**
 * Used by the different modules in the GCom stack. Provides message passing
 * methods.
 * 
 * @author dit06ajn, dit06jsg
 */
public interface Module {
    
    /**
     * Sends a new message to this Module. Used for outgoing messages.
     * 
     * @param m The Message to send.
     * @param g The GroupView to send to.
     * @throws MessageCouldNotBeSentException 
     */
    public void send(Message m, GroupView g) throws MemberCrashException, MessageCouldNotBeSentException;
    
    /**
     * Called by a different Module to when message should be delivered to this
     * Module. Usually only used to put message in a queue.
     * 
     * @param m The Message to deliver.
     */
    public void deliver(Message m);
    
    /**
     * Starts module. Ex: start message handling threads.
     */
    public void start();
    
    /**
     * Shutdown module. Ex: kill threads, unregister created registers.
     */
    public void stop();
}
