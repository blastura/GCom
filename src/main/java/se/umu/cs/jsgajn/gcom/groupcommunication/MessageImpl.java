package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import se.umu.cs.jsgajn.gcom.messageordering.VectorClock;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;

/**
 * Default implementation of {@link Message} interface.
 *
 * @author dit06ajn, dit06jsg
 * @version 1.0
 */
public class MessageImpl implements Message {
    private static final long serialVersionUID = 1L;
    private final UUID ID;
    private int sequenceNumber;
    private MessageType messageType;
    private List<UUID> path;
    private final UUID originID;
    private final UUID groupViewUID;
    private UUID sequencerUID;
    private Object m;
    private VectorClock<UUID> vc;

    public MessageImpl(Object m, MessageType messageType,
                       final UUID originID, final UUID groupViewUID) {
        this.m = m;
        this.messageType = messageType;
        this.originID = originID;
        this.groupViewUID = groupViewUID;
        this.ID = UUID.randomUUID();
        this.path = new ArrayList<UUID>();

        //path.add(originID);
        // This is done first time message is sent
        // TODO: test
    }

    public VectorClock<UUID> getVectorClock() {
        return this.vc;
    }


    public void setVectorClock(VectorClock<UUID> vc) {
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
    public UUID getOriginUID() {
        return originID;
    }

    public UUID getGroupViewUID() {
        return this.groupViewUID;
    }

    public UUID getUID() {
        return ID;
    }

    public void addToPath(UUID pid) {
        this.path.add(pid);
    }

    public List<UUID> getPath() {
        // Shallow copy
        return new ArrayList<UUID>(this.path);
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
        String origin = this.originID.equals(GroupModule.PID) ? "ME" : this.originID.toString();
        return "[origin: " + origin
            + ", vc: " + getVectorClock()
            + ", object: '" + this.m.toString()
            + "', messageID: " + this.ID + "]";
    }

    public int compareTo(final Message other) {
        return this.vc.compareTo(other.getVectorClock());
    }

	public void setSequnceNumber(int number) {
		this.sequenceNumber = number;
	}

	public int getSequnceNumber() {
		return this.sequenceNumber;
	}

	public void setSequncerUID(UUID sequencerUID) {
		this.sequencerUID = sequencerUID;
	}
	
	public UUID getSequncerUID() {
		return sequencerUID;
	}
}