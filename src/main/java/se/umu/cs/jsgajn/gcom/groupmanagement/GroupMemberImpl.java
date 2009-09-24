package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.groupcommunication.BasicMulticast;
import se.umu.cs.jsgajn.gcom.groupcommunication.CommunicationsModel;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast;
import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;
import se.umu.cs.jsgajn.gcom.messageordering.FIFO;
import se.umu.cs.jsgajn.gcom.messageordering.Ordering;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingModule;

public class GroupMemberImpl implements GroupMember {
    private Client client;

    private String groupName;
    private GNS gns;
    private GroupView group;
    private OrderingModule orderingModule;
    private CommunicationsModel communicationModule;
    private Receiver receiverLeader;
    private GroupLeader gl;
    private Receiver receiver;

    /**
     * @param gnsHost GNS host
     * @param gnsPort GNS port
     * @param groupName Group name to connect to
     * @throws RemoteException If GNS throws exception
     * @throws AlreadyBoundException If it's not possible to bind this to own register.
     * @throws NotBoundException If GNS stub is not found in GNS register.
     */
    public GroupMemberImpl(Client client, String gnsHost, int gnsPort, String groupName)
        throws RemoteException, AlreadyBoundException, NotBoundException {
        this.client = client;
        // TODO: dynamic loading of multicast module
        this.orderingModule = new OrderingModule(new FIFO());
        this.communicationModule = new CommunicationsModel(new BasicMulticast(),
                                                           this.orderingModule);

        this.groupName = groupName;
        // TODO: which model to use

        this.gns = communicationModule.connectToGns(gnsHost, gnsPort);
        GroupSettings gs = gns.connect(new GroupSettings(groupName, communicationModule.getReceiver(),
                                                         Multicast.type.BASIC_MULTICAST, Ordering.type.FIFO));
        
        if (gs.isEmpty()) { // Group is empty I am leader
            this.gl = new GroupLeader();
        } else {
            MessageImpl joinMessage =
                new MessageImpl(receiver, MessageType.JOIN, ID);
            // Couldn't these just as well be multicasted to everyone, why need
            // the leader to send groupchange? Howto create/get first group?
            gs.getLeader().receive(joinMessage);
        }

        new Thread(new MessageDeliverer()).start();
    }




    public void send(Object clientMessage) {
        Message m = new MessageImpl(clientMessage,
                MessageType.CLIENTMESSAGE, ID);
        communicationModule.multicast(m, this.group);
    }

    /** GroupLeader ************/
    private class GroupLeader {
        public boolean removeFromGroup(GroupMember member) {
            // TODO Auto-generated method stub
            return false;
        }

        public GroupView joinGroup(Receiver member) {
            // TODO: fix this
            if (group == null) {
                System.out.println("I am my on master?");
                group =  new GroupViewImpl(groupName, receiver);
                group.add(receiver);
                // Multicastar ut listan på nya grupper
                communicationModule.multicast(new MessageImpl(group, MessageType.GROUPCHANGE, ID),
                                              group);
                return group;
            }
            System.out.println("Group exists you got it");
            group.add(member);
            // Multicastar ut nya grupplistan
            communicationModule.multicast(new MessageImpl(group, MessageType.GROUPCHANGE, ID),
                                          group);
            return group;

        }
    }

    private class MessageDeliverer implements Runnable {
        public void run() {
            while (true) {
                handleDelivered(orderingModule.takeDelivered());
            }
        }

        public void handleDelivered(Message m) {
            System.out.println("Got message!");
            MessageType type = m.getMessageType();
            switch (type) {
            case GROUPCHANGE:
                System.out.println("We have a new member!!");
                group = (GroupView)m.getMessage();
                break;
            case CLIENTMESSAGE:
                client.deliver(m.getMessage());
                break;
            case JOIN:
                if (gl == null) {
                    System.err.println("Got join message but I'm not leader");
                } else {
                    gl.joinGroup((Receiver)m.getMessage());
                }
                break;
            default:
                System.out.println("error i header");
            }
        }
    }
}


