package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.List;

import se.umu.cs.jsgajn.gcom.messageordering.VectorClock;

public interface Message extends Serializable, Comparable<Message> {
    public MessageType getMessageType();
    public UID getOriginUID();
    public UID getGroupViewUID();
    public Object getMessage();
    public UID getUID();
    
    /**
     * Sets the vector clock of this message, should probably only be set once.
     *
     * @param vc The VectorClock of this Message.
     */
    public void setVectorClock(VectorClock<UID> vc);
    
    /**
     * Gets the vector clock of this message, should probably only be set by
     * first sender.
     *
     * @return The VectorClock of this Message.
     */
    public VectorClock<UID> getVectorClock();
    
    
    /**
     * Add process id to path for every member of a group this message passed 
     * through.
     * 
     * @param pid The process id of the current group member.
     */
    public void addToPath(UID pid);
    
    /**
     * Get the path this message has traveled. First message in list should be 
     * the group member who first sent the message, that is same as returned by
     * {@link #getOriginUID()}.
     * 
     * @return The path this message has traveled.
     */
    public List<UID>getPath();
}
