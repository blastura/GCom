package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.server.UID;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public interface GroupView extends Iterable<Receiver>, Serializable {
    public UID getID();
    public String getName();
    public Receiver getGroupLeaderReceiver();
    public boolean add(Receiver r);
    public int size();
}
