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
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;

public class ChatMember implements GroupMember {
    private static final Logger logger = Logger.getLogger(ChatMember.class);
    private GroupMember chatfriend;

    public ChatMember(String host, int port) {

        try {
            GroupMember stub = (GroupMember) UnicastRemoteObject.exportObject(
                    this, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("GroupMember", stub);

            System.err.println("Server ready");
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            if (msg.equals("conn")) {
                connect(host, port);
            }
            while(true) {
                msg = sc.nextLine();
                chatfriend.receive(new ChatMessage(msg));
            }
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO - fix error message
            e.printStackTrace();
        }
    }

    public boolean receive(Message<?> m) {
        logger.debug(m.getMessage());
        System.out.println(m.getMessage());
        return true;
    }

    public boolean connect(String host, int port) {
        try {

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(host, port);

            chatfriend = (GroupMember) registry.lookup("GroupMember");

        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        new ChatMember(args[0], Integer.parseInt(args[1]));
    }
}
