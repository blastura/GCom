package se.umu.cs.jsgajn.gcom.communication;

import se.umu.cs.jsgajn.gcom.MemberCrashException;
import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.management.GroupView;

/**
 * Interface for different multicast methods.
 * 
 * @author dit06ajn, dit06jsg
  */
public interface Multicast {
    /**
     * Checks if a message can be delivered up the stack.
     * 
     * @param m The message to check.
     * @param g the group the message should be delivered to.
     * @return true if the message can be delivered, false otherwise.
     */
    public boolean deliverCheck(Message m, GroupView g);
    
    /**
     * Multicasts a message to the specified GroupView.
     * 
     * @param m The message to multicast.
     * @param g The GroupView to multicast to.
     * @throws MemberCrashException If a member has crashed, this should be
     *         first when the message are sent to all correct processes.
     */
    public void multicast(Message m, GroupView g) throws MemberCrashException;
}
