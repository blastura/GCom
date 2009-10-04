package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.List;

public interface Message extends Serializable {
    public MessageType getMessageType();
    public UID getOriginUID();
    public UID getGroupViewUID();
    public Object getMessage();
    public UID getUID();
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
