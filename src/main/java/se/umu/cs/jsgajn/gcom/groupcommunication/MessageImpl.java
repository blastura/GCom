package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import se.umu.cs.jsgajn.gcom.messageordering.VectorClock;

/**
 * Default implementation of {@link Message} interface.
 *
 * @author dit06ajn, dit06jsg
 * @version 1.0
 */
public class MessageImpl implements Message {
    private static final long serialVersionUID = 1L;
    private final UID ID;
    private MessageType messageType;
    private List<UID> path;
    private final UID originID;
    private final UID groupViewUID;
    private Object m;
    private VectorClock<UID> vc;
    
    public MessageImpl(Object m, MessageType messageType,
                       final UID originID, final UID groupViewUID) {
        this.m = m;
        this.messageType = messageType;
        this.originID = originID;
        this.groupViewUID = groupViewUID;
        this.ID = new UID();
        this.path = new ArrayList<UID>();
        
        //path.add(originID);  
        // This is done first time message is sent
        // TODO: test
    }
    
    public VectorClock<UID> getVectorClock() {
        return this.vc;
    }
    
    
    public void setVectorClock(VectorClock<UID> vc) {
        if (!getOriginUID().equals(vc.getID())) {
            throw new IllegalArgumentException("Vector clock origin should be the same as Message origin");
        }
        this.vc = vc;
    }
    
    public Object getMessage() {
        return m;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }
    public UID getOriginUID() {
        return originID;
    }

    public UID getGroupViewUID() {
        return this.groupViewUID;
    }

    public UID getUID() {
        return ID;
    }

    public void addToPath(UID pid) {
        this.path.add(pid);
    }

    public List<UID> getPath() {
        // Shallow copy
        return new ArrayList<UID>(this.path);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this.m)
            return true;
        if (!(o instanceof Message))
            return false;
        Message oMsg = (Message) o;
        return getUID().equals(oMsg.getUID());
    }

    @Override
    public int hashCode() {
        return getUID().hashCode();
    }

    @Override
    public String toString() {
        return "[vc: " + getVectorClock() + ", origin: " + this.originID + ", object: '" + this.m.toString()
            + "', messageID: " + this.ID + "]";
    }
}