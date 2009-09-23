package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import se.umu.cs.jsgajn.gcom.groupcommunication.AbstractHeader;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageTypes;

public abstract class AbstractGroupMember implements GroupMember {
    private String groupName;
    //private GroupMember stub;
    private GNS gns;
    private Group group;
    private Receiver groupLeader;
    private Receiver receiver;

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
        
        receiver = new ReceiverMember();
        
        this.gns = connectToGns(gnsHost, gnsPort);
        this.groupLeader = gns.connect(receiver, groupName);
        
        // TODO: joina via multicast ?
        //this.group = groupLeader.joinGroup(receiver);
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

    public Group joinGroup(Receiver member) {
        if (this.group == null) {
            System.out.println("I am my on master?");
            Group result =  new GroupImpl(this.groupName, receiver);
            result.add(receiver);
            this.group = result;
            // Multicastar ut listan på nya grupper
            multicast(new MessageImpl(result, new AbstractHeader(MessageTypes.GROUPCHANGE)));
            return result;
        }
        System.out.println("Group exists you got it");
        this.group.add(member);
        // Multicastar ut nya grupplistan
        multicast(new MessageImpl(this.group, new AbstractHeader(MessageTypes.GROUPCHANGE)));
        return this.group;
    }
}
