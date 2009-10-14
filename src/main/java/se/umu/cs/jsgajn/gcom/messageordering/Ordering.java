package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public interface Ordering {
    public Message prepareOutgoingMessage(Message m, GroupView g);
    public void put(Message m);
    public Message take();
}
