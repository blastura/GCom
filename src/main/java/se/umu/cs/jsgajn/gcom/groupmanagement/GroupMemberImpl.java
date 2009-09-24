package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UID;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.groupcommunication.BasicMulticast;
import se.umu.cs.jsgajn.gcom.groupcommunication.HeaderImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageType;
import se.umu.cs.jsgajn.gcom.groupcommunication.Multicast;
import se.umu.cs.jsgajn.gcom.messageordering.FIFO;
import se.umu.cs.jsgajn.gcom.messageordering.Ordering;

public class GroupMemberImpl implements GroupMember {
    private Client client;

    private String groupName;
    private GNS gns;
    private GroupView group;
    private Ordering orderingModule;
    private Multicast communicationModule;
    private Receiver receiverLeader;
    private BlockingQueue<Message> receiveQueue;
    private GroupLeader gl;
    private Receiver receiver;
    private Receiver receiverStub;

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
        this.communicationModule = new BasicMulticast();

        this.groupName = groupName;
        this.orderingModule = new FIFO();  // TODO: which model to use
        this.gl = new GroupLeader();

        // TODO: change 1099
        Registry registry = LocateRegistry.createRegistry(1099); 

        this.receiveQueue = new LinkedBlockingQueue<Message>();
        receiver = new ReceiverImpl(this.receiveQueue);
        this.receiverStub = 
            (Receiver) UnicastRemoteObject.exportObject(receiver, 0);
        registry.bind(Receiver.STUB_NAME, receiverStub);

        this.gns = connectToGns(gnsHost, gnsPort);
        this.receiverLeader = gns.connect(receiver, groupName);

        // Start thread to handle messages
        new Thread(new MessageReceiver()).start();
        new Thread(new MessageDeliverer()).start();

        MessageImpl joinMessage = 
            new MessageImpl(receiver, new HeaderImpl(MessageType.JOIN));
        receiverLeader.receive(joinMessage);
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

    public void send(Object clientMessage) {
        Message m = 
            new MessageImpl(clientMessage,
                    new HeaderImpl(MessageType.CLIENTMESSAGE));
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
                communicationModule.multicast(new MessageImpl(group, new HeaderImpl(MessageType.GROUPCHANGE)),
                        group);
                return group;
            }
            System.out.println("Group exists you got it");
            group.add(member);
            // Multicastar ut nya grupplistan
            communicationModule.multicast(new MessageImpl(group, new HeaderImpl(MessageType.GROUPCHANGE)),
                    group);
            return group;

        }
    }

    private class MessageReceiver implements Runnable {
        public void run() {
            try {
                while (true) { 
                    /*
                    Message m = receiveQueue.take();
                    communicationModule.doStuff();
                    */
                    orderingModule.add(receiveQueue.take());
                }
            } catch (InterruptedException e) { 
                System.out.println(e);
            }
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
            MessageType type = m.getHeader().getMessageType();
            switch (type) {
            case GROUPCHANGE:
                System.out.println("We have a new member!!");
                group = (GroupView)m.getMessage();
                break;
            case CLIENTMESSAGE:
                client.deliver(m.getMessage());
                break;
            case JOIN:
                gl.joinGroup((Receiver)m.getMessage());
                break;
            default:
                System.out.println("error i header");
            }       
        }
    }
}


