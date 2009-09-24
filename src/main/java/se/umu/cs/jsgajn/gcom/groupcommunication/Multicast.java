package se.umu.cs.jsgajn.gcom.groupcommunication;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public interface Multicast {
    public static enum type {BASIC_MULTICAST, RELIABLE_MULTICAST}
    public boolean deliverCheck(Message m, GroupView g);
    public void multicast(Message m, GroupView g);
}
