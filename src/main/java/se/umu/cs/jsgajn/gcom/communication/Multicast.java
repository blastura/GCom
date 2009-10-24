package se.umu.cs.jsgajn.gcom.communication;

import se.umu.cs.jsgajn.gcom.MemberCrashException;
import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.management.GroupView;

public interface Multicast {
    public boolean deliverCheck(Message m, GroupView g);
    public void multicast(Message m, GroupView g) throws MemberCrashException;
}
