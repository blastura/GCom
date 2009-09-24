package se.umu.cs.jsgajn.gcom.groupcommunication;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public interface Multicast {
    public void multicast(Message m, GroupView g);
}
