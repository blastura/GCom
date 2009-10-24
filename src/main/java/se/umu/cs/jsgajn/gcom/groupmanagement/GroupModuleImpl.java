package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.debug.Debugger;
import se.umu.cs.jsgajn.gcom.groupcommunication.CommunicationModule;
import se.umu.cs.jsgajn.gcom.groupcommunication.CommunicationsModuleImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MemberCrashException;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageCouldNotBeSentException;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast;
import se.umu.cs.jsgajn.gcom.groupcommunication.MulticastType;
import se.umu.cs.jsgajn.gcom.groupcommunication.Multicasts;
import se.umu.cs.jsgajn.gcom.messageordering.Ordering;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingModule;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingModuleImpl;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingType;
import se.umu.cs.jsgajn.gcom.messageordering.Orderings;

/**
 * author dit06ajn, dit06jsg
 */
public class GroupModuleImpl implements GroupModule {
    private static final Logger logger = LoggerFactory.getLogger(GroupModuleImpl.class);
    private static final Debugger debugger = Debugger.getDebugger();

    private Client client;
    private GNS gns;
    private GroupView groupView;
    private OrderingModule orderingModule;
    private CommunicationModule communicationModule;
    private GroupLeader gl;
    //private Receiver receiver;

    // Own group member
    private GroupMember groupMember;

    // Queue to contain newly delivered messages
    private LinkedBlockingQueue<Message> receiveQueue;

    // Queue to contain messages that should be sent
    private PriorityBlockingQueue<FIFOEntry<Message>> sendQueue;

    private Thread messageReceiverThread;
    private Thread messageSenderThread;
    private boolean running;

    public GroupModuleImpl(Client client, String gnsHost, int gnsPort, String groupName)
        throws RemoteException, AlreadyBoundException, NotBoundException {
        this(client, gnsHost, gnsPort, groupName, Registry.REGISTRY_PORT);
    }

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
    public GroupModuleImpl(final Client client, final String gnsHost, final int gnsPort,
                           final String groupName, final int clientPort)
        throws RemoteException, AlreadyBoundException, NotBoundException,IllegalArgumentException {
        this.client = client;
        this.receiveQueue = new LinkedBlockingQueue<Message>();
        this.sendQueue = new PriorityBlockingQueue<FIFOEntry<Message>>();

        this.orderingModule = new OrderingModuleImpl(this);
        this.communicationModule = new CommunicationsModuleImpl(this, clientPort);
        this.communicationModule.setOrderingModule(this.orderingModule);
        this.orderingModule.setCommunicationsModule(this.communicationModule);

        this.groupMember = new GroupMember(communicationModule.getReceiver());
        // Temp groupview
        this.groupView =  new GroupViewImpl(groupName, this.groupMember);
        GroupSettings gs = initGroupSettings(groupMember, groupName);

        this.gns = getGNS(gnsHost, gnsPort);
        // TODO: what happens if another client connects directly after this
        //       client. Not all threads are started.
        gs = gns.connect(gs);
        Ordering o = Orderings.newInstance(gs.getOrderingType());
        this.orderingModule.setOrdering(o);
        Multicast m = Multicasts.newInstance(gs.getMulticastType());
        this.communicationModule.setMulticastMethod(m);

        // Start modules
        this.orderingModule.start();
        this.communicationModule.start();

        if (gs.isNew()) { // Group is empty I am leader
            logger.debug("Got new group from GNS, this member is leader");
            this.gl = new GroupLeaderImpl();
            debugger.groupChange(this.groupView);
        } else {
            logger.debug("Got existing group from GNS, sending join message");
            MessageImpl joinMessage =
                new MessageImpl(this.groupMember,
                                MessageType.JOIN,
                                PID,
                                groupView.getID());
            this.groupView = new GroupViewImpl(groupName, gs.getLeader());
            this.groupView.add(this.groupMember);
            //.getReceiver().receive(joinMessage);
            send(joinMessage, this.groupView);
        }

        this.messageReceiverThread = new Thread(new MessageReceiver(), "GroupModule Receive-Thread");
        this.messageSenderThread = new Thread(new MessageSender(), "GroupModule Send-Thread");

        start();
    }

    public void start() {
        logger.debug("Starting GroupModuleImpl");
        //Module is started in constructor
        this.running = true;
        this.messageReceiverThread.start();
        this.messageSenderThread.start();
    }

    public void stop() {
        logger.debug("Stopping GroupModuleImpl");
        this.running = false;
        this.communicationModule.stop();
        this.orderingModule.stop();
    }

    private GroupSettings initGroupSettings(GroupMember leader, String groupName) {
        Properties prop = new Properties();
        Properties sys = System.getProperties();
        String ordering = "FIFO";
        String multicastMethod = "BASIC_MULTICAST";
        try {
            prop.load(this.getClass().getResourceAsStream("/application.properties"));

            // Get ordering
            if (sys.containsKey("gcom.ordering")) {
                ordering = sys.getProperty("gcom.ordering");
            } else if (prop.containsKey("gcom.ordering")) {
                ordering = prop.getProperty("gcom.ordering");
            }

            // Get multicastMethod
            if (sys.containsKey("gcom.multicast")) {
                multicastMethod = sys.getProperty("gcom.multicast");
            } else if (prop.containsKey("gcom.multicast")) {
                multicastMethod = prop.getProperty("gcom.multicast");
            }
        } catch (IOException e) {
            logger.warn("application.properties not found");
        }

        OrderingType otype = OrderingType.valueOf(ordering);
        MulticastType mtype = MulticastType.valueOf(multicastMethod);
        if (otype == null || mtype == null) {
            throw new Error("Coudn't initialize groupsettings");
        }
        logger.debug("Init groupsettings: " + mtype + ", " + otype);
        return new GroupSettings(groupName, leader, mtype, otype);
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
        send(m, this.groupView);
    }

    /**
     * If not groupchange-messages will send message
     * to {@link OrderingModule} -> {@link CommunicationModule} ->
     * every member of the {@link GroupView}.
     *
     * If groupchange-message will send directly to {@link CommunicationModule}
     *
     * @param m The Message to send.
     * @param g The GroupView Message should be sent to.
     */
    public void send(Message m, GroupView g) {
        sendQueue.put(new FIFOEntry<Message>(m));
    }

    public GroupView getGroupView() {
        // Used in reliable multicast to resend all messages that are note yet
        // received
        GroupView gCopy;
        synchronized (groupView) {
            gCopy = new GroupViewImpl(groupView);
        }
        return gCopy;
    }

    /** GroupLeader ************/
    private class GroupLeaderImpl implements GroupLeader {
        public void removeFromGroup(GroupMember member) {
            throw new Error("TODO: not implemented");
        }

        public void addMemberToGroup(GroupMember member) {
            // TODO: fix this
            GroupView groupViewCopy;
            synchronized (groupView) {
                groupView.add(member);
                groupViewCopy = new GroupViewImpl(groupView);
            }
            // Multicast new groupView
            send(new MessageImpl(groupViewCopy, MessageType.GROUPCHANGE, PID, groupViewCopy.getID()),
                 groupView); // TODO: Important don't send with wrong send!!!!
        }
    }

    /**
     * Thread to handle message sending.
     */
    private class MessageSender implements Runnable {
        public void run() {
            while (running) {
                try {
                    FIFOEntry<Message> fifoEntry = sendQueue.take();
                    Message m = fifoEntry.getEntry();
                    GroupView groupViewCopy;
                    synchronized (groupView) {
                        groupViewCopy = new GroupViewImpl(groupView);
                    }
                    try {
                        orderingModule.send(m, groupViewCopy);
                    } catch (MemberCrashException e) {
                        logger.debug("Caught MemberCrashException");
                        handleMemberCrashException(e);
                    } catch (MessageCouldNotBeSentException e) {
                        logger.debug("Caught MessageCouldNotBeSentException fifoEntry: {}",
                                     fifoEntry);
                        handleMemberCrashException(new MemberCrashException(e.getCrashedMembers()));
                        // Put message back in queue
                        sendQueue.put(fifoEntry);
                        logger.debug("FIFOEntry put back in queue, fifoEntry: [{}]", fifoEntry);
                    }
                } catch (InterruptedException e) {
                    // TODO - fix error message
                    e.printStackTrace();
                }
            }
        }

        private void handleMemberCrashException(MemberCrashException e) {
            synchronized (groupView) {
                CrashList crashedMembers = e.getCrashedMembers();

                // Is it the leader?
                if (crashedMembers.contains(groupView.getGroupLeaderGroupMember())) {
                    handleLeaderCrash(groupView.getGroupLeaderGroupMember());
                } else {
                    // Am I leader
                    if (groupView.getGroupLeaderGroupMember().getPID().equals(GroupModule.PID)) {

                        // TODO: sync or copy ?
                        boolean changed = groupView.removeAll(crashedMembers.getAll());
                        logger.debug("After remove all: {}",crashedMembers.getAll());
                        if (!changed) {
                            logger.warn("Tried to remove crashed members, but none were removed");
                        }
                        Message groupChangeMessage =
                            new MessageImpl(groupView, MessageType.GROUPCHANGE, GroupModule.PID,
                                            groupView.getID());
                        send(groupChangeMessage, groupView);
                    }
                }
            }
        }

        private void handleLeaderCrash(GroupMember leader) {
            synchronized (groupView) {
                //groupView.remove(groupView.getGroupLeaderGroupMember());
                groupView.remove(leader);

                // Am I the new leader?
                if (GroupModule.PID.equals(groupView.getHighestUUID())) {
                    try {
                        gns.setNewLeader(GroupModuleImpl.this.groupMember, groupView.getName());
                        groupView.setNewLeader(GroupModuleImpl.this.groupMember);
                        GroupModuleImpl.this.gl = new GroupLeaderImpl(); // TODO: Sync erros?
                        Message groupChangeMessage =
                            new MessageImpl(groupView, MessageType.GROUPCHANGE, GroupModule.PID,
                                            groupView.getID());
                        logger.debug("Incredible! I am the new leader! ");
                        send(groupChangeMessage, groupView);
                    } catch (RemoteException e1) {
                        logger.debug("Error, GNS cant change groupleader");
                        e1.printStackTrace();
                    }
                } else {
                    Message leaderCrashMessage = new MessageImpl(
                                                                 leader, MessageType.LEADERCRASH,
                                                                 GroupModule.PID, groupView.getID());
                    send(leaderCrashMessage, groupView);
                }
            }
        }
    }

    /**
     * Thread to handle message receiving.
     */
    private class MessageReceiver implements Runnable {
        public void run() {
            while (running) {
                try {
                    handleDelivered(receiveQueue.take());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void handleDelivered(Message m) {
            logger.info("Got message!");
            MessageType type = m.getMessageType();
            switch (type) {
            case GROUPCHANGE:
                logger.info("GROUPCHANGE");
                synchronized (groupView) {
                    groupView = (GroupView) m.getMessage();
                }
                debugger.groupChange(groupView);
                break;
            case CLIENTMESSAGE:
                logger.info("CLIENTMESSAGE");
                client.deliver(m.getMessage());
                break;
            case LEADERCRASH:
                logger.info("LEADERCRASH");
                // TODO: what now?
                handleLeaderCrash((GroupMember)m.getMessage());

                break;
            case JOIN:
                if (gl == null) {
                    logger.error("Got join message but I'm not leader");
                } else {
                    logger.info("Join message new member, (THIS module is the group leader)");
                    gl.addMemberToGroup((GroupMember) m.getMessage());
                }
                break;
            default:
                logger.error("Unkwon MessageType in message: " + m);
            }
        }

        private void handleLeaderCrash(GroupMember crashedLeader) {
            // TODO: verify and test this!
            // Am I the new leader?
            if (GroupModule.PID.equals(groupView.getHighestUUID())) {
                try {
                    gns.setNewLeader(GroupModuleImpl.this.groupMember, groupView.getName());
                    groupView.setNewLeader(GroupModuleImpl.this.groupMember);
                    GroupModuleImpl.this.gl = new GroupLeaderImpl(); // TODO: Sync erros?
                    Message groupChangeMessage =
                        new MessageImpl(groupView, MessageType.GROUPCHANGE, GroupModule.PID,
                                        groupView.getID());
                    logger.debug("Incredible! I am the new leader! ");
                    send(groupChangeMessage, groupView);
                } catch (RemoteException e1) {
                    logger.debug("Error, GNS cant change groupleader");
                    e1.printStackTrace();
                }
            }
        }
    }

    public void deliver(Message m) {
        try {
            debugger.messageDelivered(m);
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
    private GNS getGNS(String host, int port) throws RemoteException,
                                                     NotBoundException {
        Registry gnsReg = LocateRegistry.getRegistry(host, port);
        return (GNS) gnsReg.lookup(GNS.STUB_NAME);
    }

    protected static class FIFOEntry<E extends Comparable<? super E>>
        implements Comparable<FIFOEntry<E>> {
        final static AtomicLong seq = new AtomicLong();
        final long seqNum;
        final E entry;
        public FIFOEntry(E entry) {
            seqNum = seq.getAndIncrement();
            this.entry = entry;
        }

        public E getEntry() { return entry; }

        public int compareTo(FIFOEntry<E> other) {
            int res = entry.compareTo(other.entry);
            if (res == 0 && other.entry != this.entry)
                res = (seqNum < other.seqNum ? -1 : 1);
            return res;
        }

        @Override
        public String toString() {
            return "Seq: " + seqNum + ", entry: " + entry.toString();
        }
    }
}