package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public class GroupViewImpl implements GroupView {
    private static final long serialVersionUID = 1L;
    private List<Receiver> receivers;
    private String name;
    private Receiver groupLeader;
    private final UID ID;

    public GroupViewImpl(String name, Receiver groupLeader) {
        this.receivers = new ArrayList<Receiver>();
        this.name = name;
        this.groupLeader = groupLeader;
        this.ID = new UID();
        add(groupLeader);
    }

    public String getName() {
        return name;
    }

    public Receiver getGroupLeaderReceiver() {
        return groupLeader;
    }
    
    public UID getID() {
        return ID;
    }
    
    public boolean add(Receiver r) {
        return receivers.add(r);
    }

    public Iterator<Receiver> iterator() {
        return receivers.iterator();
    }

    public int size() {
        return receivers.size();
    }
}
