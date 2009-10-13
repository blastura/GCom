package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import java.util.Map;
import java.util.HashMap;

//import se.umu.cs.jsgajn.gcom.debug.Debugger;

public class BasicMulticast implements Multicast {
    private static final Logger logger = LoggerFactory.getLogger(BasicMulticast.class);
    //private static final Debugger debugger = Debugger.getDebugger();

    private Map<GroupMember, RemoteException> crashed =
        new HashMap<GroupMember, RemoteException>();

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m, GroupView g) throws MemberCrashException {
        // Add this PID to the current path the message has traveled
        m.addToPath(GroupModule.PID);
        crashed.clear();
        for (GroupMember member : g) {
            try {
                member.getReceiver().receive(m);
                logger.debug("Sent message to: " + member);
            } catch (RemoteException re) {
                if (!(re.getCause() instanceof java.net.ConnectException)) {
                    logger.error("RemoteException not ConnectException");
                    re.printStackTrace();
                }
                
                logger.info("Receiver exception: " + member.getPID());
                crashed.put(member, re);
            }
        }
        
        if (!crashed.isEmpty()) {
            //throw new MemberCrashException();
            throw new MemberCrashException(crashed);
        }
    }

    public boolean deliverCheck(Message m, GroupView g) {
        return true;
    }
}