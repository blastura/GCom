package se.umu.cs.jsgajn.gcom;

import java.io.Serializable;
import java.util.UUID;
import java.util.List;

import se.umu.cs.jsgajn.gcom.ordering.VectorClock;

/**
 * Interface to represent messages sent by GCom, all client messages will be 
 * packed in an instance of this interface, the constructor should be used for
 * this object insertion. The client message can then be retrieved with the method
 * {@link #getMessage()}.
 * 
 * @author dit06ajn, dit06jsg
  */
public interface Message extends Serializable, Comparable<Message> {
    public MessageType getMessageType();
    public UUID getOriginUID();
    public UUID getGroupViewUID();
    public Object getMessage();
    public UUID getUID();
    
    /**
     * Returns true if this message is a system message, this will make it skip
     * OrderingModule.
     * @return true if this message is a system message, false otherwise.
     */
    public boolean isSystemMessage();
    
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

    /**
     * Gives the message a sequence number. Gets this from the group leader.
     * 
     * @param number
     */
    public void setSequnceNumber(int number);
    

    /**
     * Returns the message sequence number
     */
    public int getSequnceNumber();

    /**
     * Returns who set the message sequence number
     */
    public UUID getSequncerUID();
    
    
    /**
     * Sets the UUID of the sequencer for this message.
     * 
     * @param sequencerUID The UUID this message got its sequence number from.
     */
    public void setSequncerUID(UUID sequencerUID);
}
