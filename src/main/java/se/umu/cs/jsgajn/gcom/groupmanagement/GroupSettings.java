package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;

import se.umu.cs.jsgajn.gcom.groupcommunication.MulticastType;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingType;

public class GroupSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private GroupMember leader;
    private MulticastType multicastType;
    private OrderingType orderingType;
    private boolean isNew = true;
    private String name;

    public GroupSettings(String name, GroupMember leader,
                         MulticastType multicastType,
                         OrderingType orderingType) {
        this.name = name;
        this.leader = leader;
        this.multicastType = multicastType;
        this.orderingType = orderingType;
    }

    public String getName() {
        return this.name;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
    }

    public GroupMember getLeader() {
        return leader;
    }

    public MulticastType getMulticastType() {
        return multicastType;
    }

    public OrderingType getOrderingType() {
        return orderingType;
    }

    @Override
    public String toString() {
        return "[" + this.getName() + ", "
            + this.getMulticastType() + ", "
            + this.getOrderingType() + "]";
    }

	public void setLeader(GroupMember gm) {
		this.leader = gm;
	}
}
