package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class BasicMulticast implements Multicast {
    private static final Logger logger = LoggerFactory.getLogger(BasicMulticast.class);
    private Set<GroupMember> crashed = new HashSet<GroupMember>();

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m, GroupView g) {
        // Add this PID to the current path the message has traveled
        m.addToPath(GroupModule.PID);
        for (GroupMember member : g) {
            if(!crashed.contains(member)){
                try {
                    member.getReceiver().receive(m);
                } catch (RemoteException e) {
                    crashed.add(member);
                    
                    Message crashMessage = new MessageImpl(member, 
                            MessageType.MEMBERCRASH, GroupModule.PID, g.getID());
                    multicast(crashMessage, g);
                    logger.debug("Oh, no! This bitch crashed: " + member.getPID());
                }
            }
        }
    }

    public boolean deliverCheck(Message m, GroupView g) {
        return true;
    }
}