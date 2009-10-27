package se.umu.cs.jsgajn.gcom.ordering;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.MessageCouldNotBeSentException;
import se.umu.cs.jsgajn.gcom.management.GroupView;

/**
 * Interface for different ordering implementations. The implementation is though
 * of as a queue where you can put messages and when you take them they will be
 * in the specific order guaranteed by the implementation.
 * 
 * @author dit06ajn, dit06jsg
  */
public interface Ordering {
    /**
     * Prepare a message with necessary information for ordering messages.
     * 
     * @param m The message to prepare.
     * @param g The groupview the message is sent to.
     * @return The prepared message.
     * @throws MessageCouldNotBeSentException If a message could not be sent.
     *         For example if a sequencer is used but no sequence number can got.
     */
    public Message prepareOutgoingMessage(Message m, GroupView g) throws MessageCouldNotBeSentException;
    
    /**
     * Put a message in the ordering, this is used for incoming messages.
     * @param m
     */
    public void put(Message m);
    
    /**
     * Will block until a message can be delivered in the specific guranteed by
     * the implementation.
     * 
     * @return The ordered message.
     */
    public Message take();
}
