package se.umu.cs.jsgajn.gcom.management;

import java.io.Serializable;

import se.umu.cs.jsgajn.gcom.communication.MulticastType;
import se.umu.cs.jsgajn.gcom.ordering.OrderingType;

/**
 * Class to represent different settings for a group.
 *
 * @author dit06ajn, dit06jsg
 */
public class GroupSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private GroupMember leader;
    private MulticastType multicastType;
    private OrderingType orderingType;
    private boolean isNew = true;
    private String name;
    
    /**
     * Creates a new instance of groups settings.
     *
     * @param name The name of the group.
     * @param leader The leader of the group.
     * @param multicastType the type of multicast to use.
     * @param orderingType the type of ordering to use.
     */
    public GroupSettings(String name, GroupMember leader,
                         MulticastType multicastType,
                         OrderingType orderingType) {
        this.name = name;
        this.leader = leader;
        this.multicastType = multicastType;
        this.orderingType = orderingType;
    }

    /**
     * @return The name of the group.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param isNew Set if the group newly created, this is used by
     * the GNS.
     */
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * @return true if the group is just created by the GNS.
     */
    public boolean isNew() {
        return isNew;
    }

    /**
     * @return The group leader.
     */
    public GroupMember getLeader() {
        return leader;
    }

    /**
     * @return The multicast type.
     */
    public MulticastType getMulticastType() {
        return multicastType;
    }

    /**
     * @return The ordering type.
     */
    public OrderingType getOrderingType() {
        return orderingType;
    }

    @Override
    public String toString() {
        return "[" + this.getName() + ", "
            + this.getMulticastType() + ", "
            + this.getOrderingType() + "]";
    }

    /**
     * @param gm Set a new group leader.
     */
    public void setLeader(GroupMember gm) {
        this.leader = gm;
    }
}
