package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import se.umu.cs.jsgajn.gcom.groupcommunication.HeaderImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageTypes;

public abstract class AbstractGroupMember implements GroupMember {
    private String groupName;
    private GNS gns;
    private GroupView group;

    private Receiver receiverLeader;

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
    public AbstractGroupMember(String gnsHost, int gnsPort, String groupName)
    throws RemoteException, AlreadyBoundException, NotBoundException {
        this.groupName = groupName;

        // TODO: change 1099
        Registry registry = LocateRegistry.createRegistry(1099); 

        receiver = new ReceiverImpl();
        this.receiverStub = 
            (Receiver) UnicastRemoteObject.exportObject(receiver, 0);
        registry.bind(Receiver.STUB_NAME, receiverStub);

        this.gns = connectToGns(gnsHost, gnsPort);
        this.receiverLeader = gns.connect(receiver, groupName);

        // TODO: joina via multicast ?
        //this.group = groupLeader.joinGroup(receiver);
        MessageImpl m = 
            new MessageImpl(receiver, new HeaderImpl(MessageTypes.JOIN));
        receiverLeader.receive(m);
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

    /**
     * Send message to all members of the group.
     * TODO: move method to interface or Ordering-layer.
     * @param m The message to send.
     */
    public void multicast(Message m) {
        for (Receiver member : this.group) {
            try {
                member.receive(m);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public abstract void received(Message m);

    /*
     * (non-Javadoc)
     * 
     * @see se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember#receive(se.umu.cs.jsgajn.gcom.groupcommunication.Message)
     */
    public void receive(Message m) {
        // TODO: implement ordering and delivering and stuff.
        MessageTypes type = m.getHeader().getMessageType();

        switch (type) {
        case GROUPCHANGE:
            System.out.println("We have a new member!!");
            break;
        case CLIENTMESSAGE:
            joinGroup((Receiver)m.getMessage());
            break;
        case JOIN:
            System.out.println(m.getMessage());
            break;
        default:
            System.out.println("error i header");
        }
    }

    /** GroupLeader ************/
    public boolean removeFromGroup(GroupMember member) {
        // TODO Auto-generated method stub
        return false;
    }

    public GroupView joinGroup(Receiver member) {
        if (this.group == null) {
            System.out.println("I am my on master?");
            GroupView result =  new GroupViewImpl(this.groupName, receiver);
            result.add(receiver);
            this.group = result;
            // Multicastar ut listan på nya grupper
            multicast(new MessageImpl(result, new HeaderImpl(MessageTypes.GROUPCHANGE)));
            return result;
        }
        System.out.println("Group exists you got it");
        this.group.add(member);
        // Multicastar ut nya grupplistan
        multicast(new MessageImpl(this.group, new HeaderImpl(MessageTypes.GROUPCHANGE)));
        return this.group;
    }
    
    class MessageReceiver implements Runnable {
        public void run() {
            try {
                while(true) { 
                    consume(receiver.getMessage());
                }
            } catch (InterruptedException e) { 
                System.out.println(e);
           }
        }
        
        public void consume(Message m) {
            System.out.println("heja");
        }
    }
}


/*
if (this.group == null) {
    System.out.println("I am my on master?");
    GroupView result =  new GroupViewImpl(this.groupName, receiver);
    result.add(receiver);
    this.group = result;
    // Multicastar ut listan på nya grupper
    multicast(new MessageImpl(result, new HeaderImpl(MessageTypes.GROUPCHANGE)));
    return result;
}
System.out.println("Group exists you got it");
this.group.add(member);
// Multicastar ut nya grupplistan
multicast(new MessageImpl(this.group, new HeaderImpl(MessageTypes.GROUPCHANGE)));
return this.group;
*/


