package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;

import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupcommunication.CommunicationModule;
import se.umu.cs.jsgajn.gcom.groupcommunication.CommunicationsModelImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import se.umu.cs.jsgajn.gcom.groupcommunication.MulticastType;
import se.umu.cs.jsgajn.gcom.groupcommunication.ReliableMulticast;
import se.umu.cs.jsgajn.gcom.messageordering.FIFO;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingModuleImpl;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingType;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingModule;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

/**
 * @author dit06ajn
 *
 */
public class GroupModuleImpl implements GroupModule {
    private Client client;

    private String groupName;
    private GNS gns;
    private GroupView groupView;
    private OrderingModule orderingModule;
    private CommunicationModule communicationModule;
    //private Receiver receiverLeader;
    private GroupLeader gl;
    //private Receiver receiver;

    // Own group member
    private GroupMember groupMember;

    // Queue to contain newly delivered messages
    private LinkedBlockingQueue<Message> receiveQueue;

    /**
     * Implementations responsible for management of group, communication with
     * group and ordering of messages. Clients should use this class to send
     * messages and receive messages with a group.
     *
     * @param gnsHost GNS host
     * @param gnsPort GNS port
     * @param groupName Group name to connect to
     * @throws RemoteException If GNS throws exception
     * @throws AlreadyBoundException If it's not possible to bind this to own register.
     * @throws NotBoundException If GNS stub is not found in GNS register.
     */
    public GroupModuleImpl(Client client, String gnsHost, int gnsPort, String groupName)
        throws RemoteException, AlreadyBoundException, NotBoundException {
        this.client = client;
        this.receiveQueue = new LinkedBlockingQueue<Message>();

        // TODO: dynamic loading of multicast module
        // TODO: fix order of modules, they refer to each other
        this.orderingModule = new OrderingModuleImpl(this, new FIFO());
        this.communicationModule =
            new CommunicationsModelImpl(this, new ReliableMulticast());
        this.communicationModule.setOrderingModule(this.orderingModule);
        this.orderingModule.setCommunicationsModule(this.communicationModule);

        this.groupMember = new GroupMember(communicationModule.getReceiver());

        this.groupView =  new GroupViewImpl(groupName, this.groupMember);
        this.groupName = groupName;

        // TODO: which model to use
        this.gns = connectToGns(gnsHost, gnsPort);
        GroupSettings gs =
            gns.connect(new GroupSettings(groupName, this.groupMember,
                                          MulticastType.RELIABLE_MULTICAST, OrderingType.FIFO));


        if (gs.isNew()) { // Group is empty I am leader
            System.out.println("Group created");
            // TODO: Test if setIsNew(false) will affect GNSImpl
            this.gl = new GroupLeaderImpl();

        } else {
            System.out.println("Try join group");
            MessageImpl joinMessage =
                new MessageImpl(this.groupMember,
                                MessageType.JOIN, PID, groupView.getID());

            gs.getLeader().getReceiver().receive(joinMessage);
        }

        new Thread(new MessageReceiver()).start();
    }


    /**
     * Will send package Object in appropriate Message and send it to every
     * group member.
     *
     * @param clientMessage The Object to send.
     */
    public void send(Object clientMessage) {
        Message m = new MessageImpl(clientMessage,
                                    MessageType.CLIENTMESSAGE, PID, groupView.getID());
        orderingModule.send(m, this.groupView);
        send(m, this.groupView);
    }
    
    /**
     * Will send message to {@link OrderingModule} -> {@link CommunicationModule} ->
     * every member of the {@link GroupView}.
     *
     * @param m The Message to send.
     * @param g The GroupView Message should be sent to.
     */
    public void send(Message m, GroupView g) {
        orderingModule.send(m, this.groupView);
    }

    public GroupView getGroupView() {
        return groupView;
    }

    /** GroupLeader ************/
    private class GroupLeaderImpl implements GroupLeader {
        public void removeFromGroup(GroupMember member) {
            throw new Error("TODO: not implemented");
        }

        public void addMemberToGroup(GroupMember member) {
            // TODO: fix this
            groupView.add(member);

            // Multicast new groupView
            orderingModule.send(new MessageImpl(groupView,
                                                MessageType.GROUPCHANGE, PID, groupView.getID()), groupView);
        }
    }

    private class MessageReceiver implements Runnable {
        public void run() {
            while (true) {
                try {
                    handleDelivered(receiveQueue.take());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void handleDelivered(Message m) {
            System.out.println("Got message!");
            MessageType type = m.getMessageType();
            switch (type) {
            case GROUPCHANGE:
                System.out.println("We have a new member!!");
                groupView = (GroupView) m.getMessage();
                break;
            case CLIENTMESSAGE:
                client.deliver(m.getMessage());
                break;
            case MEMBERCRASH:
                handelCrash((GroupMember)(m.getMessage()));
                break;
            case JOIN:
                if (gl == null) {
                    System.err.println("Got join message but I'm not leader");
                } else {
                    gl.addMemberToGroup((GroupMember) m.getMessage());
                }
                break;
            default:
                System.out.println("error i header");
            }
        }

        private void handelCrash(GroupMember m) {
            // TODO: Election om de e ledaren annars groupchange
        }
    }

    public void deliver(Message m) {
        try {
            receiveQueue.put(m);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param host Host to GNS
     * @param port Port to GNS
     * @return GNS stub
     * @throws RemoteException If GNS throws exception
     * @throws NotBoundException If GNS stub can't be found.
     */
    private GNS connectToGns(String host, int port) throws RemoteException,
                                                          NotBoundException {
        Registry gnsReg = LocateRegistry.getRegistry(host, port);
        return (GNS) gnsReg.lookup(GNS.STUB_NAME);
    }
}

