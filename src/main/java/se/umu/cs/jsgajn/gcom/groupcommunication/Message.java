package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.util.UUID;
import java.util.List;

import se.umu.cs.jsgajn.gcom.messageordering.VectorClock;

public interface Message extends Serializable, Comparable<Message> {
    public MessageType getMessageType();
    public UUID getOriginUID();
    public UUID getGroupViewUID();
    public Object getMessage();
    public UUID getUID();
    
    /**
     * Sets the vector clock of this message, should probably only be set once.
     *
     * @param vc The VectorClock of this Message.
     */
    public void setVectorClock(VectorClock<UUID> vc);
    
    /**
     * Gets the vector clock of this message, should probably only be set by
     * first sender.
     *
     * @return The VectorClock of this Message.
     */
    public VectorClock<UUID> getVectorClock();
    
    
    /**
     * Add process id to path for every member of a group this message passed 
     * through.
     * 
     * @param pid The process id of the current group member.
     */
    public void addToPath(UUID pid);
    
    /**
     * Get the path this message has traveled. First message in list should be 
     * the group member who first sent the message, that is same as returned by
     * {@link #getOriginUID()}.
     * 
     * @return The path this message has traveled.
     */
    public List<UUID>getPath();
}
