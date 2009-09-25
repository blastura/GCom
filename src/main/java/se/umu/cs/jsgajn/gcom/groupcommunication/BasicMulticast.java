package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.RemoteException;
import java.rmi.server.UID;
import java.util.HashSet;
import java.util.Set;

import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class BasicMulticast implements Multicast {

    private Set<GroupMember> crashed = new HashSet<GroupMember>();

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m, GroupView g) {

        for (GroupMember member : g) {
            if(!crashed.contains(member)){
                try {
                    member.getReceiver().receive(m);
                } catch (RemoteException e) {
                    crashed.add(member);

                    Message crashMessage = new MessageImpl(member, 
                            MessageType.MEMBERCRASH, GroupModule.PID, g.getID());
                    multicast(crashMessage, g);

                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean deliverCheck(Message m, GroupView g) {
        // TODO Auto-generated method stub
        return true;
    }
}