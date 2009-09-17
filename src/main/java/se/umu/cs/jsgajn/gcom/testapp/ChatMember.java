package se.umu.cs.jsgajn.gcom.testapp;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import org.apache.log4j.Logger;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.AbstractGroupMember;
import se.umu.cs.jsgajn.gcom.groupmanagement.Group;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;

public class ChatMember extends AbstractGroupMember {
    private static final Logger logger = Logger.getLogger(ChatMember.class);
    private GroupMember chatfriend;

    public ChatMember(String gnsHost, int gnsPort, String groupName)
            throws RemoteException, AlreadyBoundException, NotBoundException {
        super(gnsHost, gnsPort, groupName);

        /*
         * System.err.println("Server ready"); Scanner sc = new
         * Scanner(System.in); String msg = sc.nextLine(); if
         * (msg.equals("conn")) { connect(host, port); } while(true) { msg =
         * sc.nextLine(); chatfriend.receive(new ChatMessage(msg)); }
         */
    }

    public boolean receive(Message<?> m) {
        logger.debug(m.getMessage());
        System.out.println(m.getMessage());
        return true;
    }

    public static void main(String[] args) {
        try {
            new ChatMember(args[0], Integer.parseInt(args[1]), args[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            // Om det är nåt fel hos GNSen
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // Om man inte kan binda sig själv till sitt register
            // t.ex. om man redan är bunden dit
            e.printStackTrace();
        } catch (NotBoundException e) {
            // Ifall man försöker ansluta till GNSen men den inte
            // gick att binda tidigare
            e.printStackTrace();
        }
    }

    public GroupMember joinGroup(String name) {
        // TODO Auto-generated method stub
        return null;
    }


    public boolean removeFromGroup(GroupMember member) {
        // TODO Auto-generated method stub
        return false;
    }

    public Group addToGroup(GroupMember member) {
        // TODO Auto-generated method stub
        return null;
    }
}
