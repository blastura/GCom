package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;

import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast;
import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast.type;
import se.umu.cs.jsgajn.gcom.messageordering.Ordering;

public class GroupSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private GroupMember leader;
    private type multicastType;
    private Ordering.type orderingType;
    private boolean isNew = true;
    private String name;

    public GroupSettings(String name, GroupMember leader, Multicast.type multicastType, Ordering.type orderingType) {
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

    public type getMulticastType() {
        return multicastType;
    }

    public Ordering.type getOrderingType() {
        return orderingType;
    }
}
