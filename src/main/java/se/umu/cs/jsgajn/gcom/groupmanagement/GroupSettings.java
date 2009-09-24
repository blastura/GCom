package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;

import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast;
import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;
import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast.type;
import se.umu.cs.jsgajn.gcom.messageordering.Ordering;

public class GroupSettings implements Serializable {

    private Receiver leader;
    private type multicastType;
    private Ordering.type orderingType;
    private boolean empty = true;
    private String name;

    public GroupSettings(String name, Receiver leader, Multicast.type multicastType, Ordering.type orderingType) {
        this.name = name;
        this.leader = leader;
        this.multicastType = multicastType;
        this.orderingType = orderingType;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void touch() {
        this.empty = false;
    }
    
    public boolean isEmpty() {
        return empty;
    }

    public Receiver getLeader() {
        return leader;
    }

    public type getMulticastType() {
        return multicastType;
    }

    public Ordering.type getOrderingType() {
        return orderingType;
    }
}
