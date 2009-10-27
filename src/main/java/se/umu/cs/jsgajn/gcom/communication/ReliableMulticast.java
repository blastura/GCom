package se.umu.cs.jsgajn.gcom.communication;

import java.util.HashSet;
import java.util.Set;

import se.umu.cs.jsgajn.gcom.MemberCrashException;
import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.MessageType;
import se.umu.cs.jsgajn.gcom.management.ManagementModule;
import se.umu.cs.jsgajn.gcom.management.ManagementModuleImpl;
import se.umu.cs.jsgajn.gcom.management.GroupView;
import se.umu.cs.jsgajn.gcom.management.GroupViewImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReliableMulticast implements Multicast {
    private static final Logger logger = LoggerFactory.getLogger(ReliableMulticast.class);
    
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
        /*
        if(m.getMessageType().equals(MessageType.GROUPCHANGE)){
            g = new GroupViewImpl(g, g.getMembersWithHigherUUID(ManagementModule.PID));
        }
        */
        bMulticast.multicast(m, g);
    }

    /** 
     * If a message is not received before and is not sent by own member, this
     * message will be multicasted to all members of the specified GroupView.
     * 
     * @see se.umu.cs.jsgajn.gcom.communication.Multicast#deliverCheck(se.umu.cs.jsgajn.gcom.Message, se.umu.cs.jsgajn.gcom.management.GroupView)
     */
    public boolean deliverCheck(Message m, GroupView g) {
        //System.out.println(m.getUID() + " \n " + m.getOriginUID() + " \n " + m.toString() + "\n" +  m.getMessage().toString() +"\n\n");
        if (!received.contains(m)) {
            received.add(m);
            if (!m.getOriginUID().equals(ManagementModule.PID)) {
                // TODO: which groupview to send this to?
                try {
                    if(m.getMessageType().equals(MessageType.GROUPCHANGE)){
                        g = (GroupView) m.getMessage();
                    }
                    multicast(m, g);
                } catch (MemberCrashException e) {
                    logger.info("Reliable multicast detect membercrash: {}, will be ignored",
                                e.getMessage());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return MulticastType.RELIABLE_MULTICAST.toString();
    }
}