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
    private GroupView groupView;
    private OrderingModule orderingModule;
    private CommunicationsModel communicationModule;
    //private Receiver receiverLeader;
    private GroupLeader gl;
    //private Receiver receiver;

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

        if (gs.isNew()) { // Group is empty I am leader
            System.out.println("Group created");
            // TODO: Test if setIsNew(false) will affect GNSImpl
            this.gl = new GroupLeaderImpl();
            this.groupView =  new GroupViewImpl(groupName, communicationModule.getReceiver());

        } else {
            System.out.println("Try join group");
            MessageImpl joinMessage =
                new MessageImpl(communicationModule.getReceiver(), 
                        MessageType.JOIN, ID);

            gs.getLeader().receive(joinMessage);
        }

        new Thread(new MessageDeliverer()).start();
    }

    public void send(Object clientMessage) {
        Message m = new MessageImpl(clientMessage,
                MessageType.CLIENTMESSAGE, ID);
        communicationModule.multicast(m, this.groupView);
    }

    /** GroupLeader ************/
    private class GroupLeaderImpl implements GroupLeader {
        public void removeFromGroup(Receiver member) {
            // TODO Auto-generated method stub
        }

        public void addMemberToGroup(Receiver member) {
            // TODO: fix this
            groupView.add(member);

            // Multicast new groupView
            communicationModule.multicast(new MessageImpl(groupView, 
                    MessageType.GROUPCHANGE, ID), groupView);
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
                groupView = (GroupView)m.getMessage();
                break;
            case CLIENTMESSAGE:
                client.deliver(m.getMessage());
                break;
            case JOIN:
                if (gl == null) {
                    System.err.println("Got join message but I'm not leader");
                } else {
                    gl.addMemberToGroup((Receiver)m.getMessage());
                }
                break;
            default:
                System.out.println("error i header");
            }
        }
    }
}

