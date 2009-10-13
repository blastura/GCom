package se.umu.cs.jsgajn.gcom.groupcommunication;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public interface Multicast {
    public boolean deliverCheck(Message m, GroupView g) throws MemberCrashException;
    public void multicast(Message m, GroupView g) throws MemberCrashException;
}
