package se.umu.cs.jsgajn.gcom.communication;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.MemberCrashException;
import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.management.CrashList;
import se.umu.cs.jsgajn.gcom.management.CrashListImpl;
import se.umu.cs.jsgajn.gcom.management.GroupMember;
import se.umu.cs.jsgajn.gcom.management.ManagementModule;
import se.umu.cs.jsgajn.gcom.management.GroupView;

//import se.umu.cs.jsgajn.gcom.debug.Debugger;

public class BasicMulticast implements Multicast {
    private static final Logger logger = LoggerFactory.getLogger(BasicMulticast.class);
    //private static final Debugger debugger = Debugger.getDebugger();

    private CrashList crashed;

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m, GroupView g) throws MemberCrashException {
        // Add this PID to the current path the message has traveled
        m.addToPath(ManagementModule.PID);
        crashed =  new CrashListImpl();
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
                crashed.add(member);
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