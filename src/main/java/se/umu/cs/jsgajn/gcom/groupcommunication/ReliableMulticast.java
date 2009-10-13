package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.util.HashSet;
import java.util.Set;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class ReliableMulticast implements Multicast {
    private BasicMulticast bMulticast;
    private Set<Message> received;

    public ReliableMulticast() {
        this.bMulticast = new BasicMulticast();
        this.received = new HashSet<Message>();
    }

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m, GroupView g) throws MemberCrashException {
        bMulticast.multicast(m, g);
    }

    public boolean deliverCheck(Message m, GroupView g) throws MemberCrashException {
        //System.out.println(m.getUID() + " \n " + m.getOriginUID() + " \n " + m.toString() + "\n" +  m.getMessage().toString() +"\n\n");
        if (!received.contains(m)) {
            received.add(m);
            if (!m.getOriginUID().equals(GroupModule.PID)) {
                // TODO: which groupview to send this to?
                multicast(m, g);
            } 
            return true;
        }
        return false;
    }
}