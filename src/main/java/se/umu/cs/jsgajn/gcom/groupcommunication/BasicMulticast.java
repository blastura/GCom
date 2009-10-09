package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import se.umu.cs.jsgajn.gcom.debug.Debugger;

public class BasicMulticast implements Multicast {
    private static final Logger logger = LoggerFactory.getLogger(BasicMulticast.class);
    private static final Debugger debugger = Debugger.getDebugger();
    
    private Set<GroupMember> crashed = new HashSet<GroupMember>();

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m, GroupView g) {
        // Add this PID to the current path the message has traveled

        m.addToPath(GroupModule.PID);
        int i = 0;
        for (GroupMember member : g) {
            if(!crashed.contains(member)){
                try {
                	/*
                    if (i == 1) {
                        debugger.block();
                    }
                    */
                    member.getReceiver().receive(m);
                    //debugger.possibleCrash(i, size);
                } catch (RemoteException e) {
                    crashed.add(member);
                    
                    Message crashMessage = new MessageImpl(member, 
                            MessageType.MEMBERCRASH, GroupModule.PID, g.getID());
                    multicast(crashMessage, g);
                    logger.debug("Oh, no! This bitch crashed: " + member.getPID());
                }
            }
            i++;
        }
    }

    public boolean deliverCheck(Message m, GroupView g) {
        return true;
    }
}