package se.umu.cs.jsgajn.gcom.ordering;

import se.umu.cs.jsgajn.gcom.MemberCrashException;
import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.MessageCouldNotBeSentException;
import se.umu.cs.jsgajn.gcom.management.GroupView;

public interface Ordering {
    public Message prepareOutgoingMessage(Message m, GroupView g) throws MessageCouldNotBeSentException;
    public void put(Message m);
    public Message take();
}
