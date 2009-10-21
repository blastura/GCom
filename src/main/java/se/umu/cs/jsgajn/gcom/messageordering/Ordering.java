package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.groupcommunication.MemberCrashException;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public interface Ordering {
    public Message prepareOutgoingMessage(Message m, GroupView g) throws MemberCrashException;
    public void put(Message m);
    public Message take();
}
