package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Collection;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public interface GroupView extends Collection<Receiver>, Serializable {
    // List<Server> servers;
    // Server.host och Server.host bör finnas
    public void closeGroup();
    public String getName();
    public Receiver getGroupLeaderReceiver();
}
