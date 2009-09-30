package se.umu.cs.jsgajn.gcom.testapp;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModuleImpl;

public class ChatMember implements Client {
    private static final Logger logger = Logger.getLogger(ChatMember.class);
    private GroupModule groupMember;
    
    public ChatMember(String gnsHost, int gnsPort, String groupName)
        throws RemoteException, AlreadyBoundException, NotBoundException {
        this.groupMember = new GroupModuleImpl(this, gnsHost, gnsPort, groupName);

        Scanner sc = new Scanner(System.in);  
        String msg;
        while (true) {
            System.out.print(GroupModule.PID + " - message: ");
            msg = sc.nextLine();
            groupMember.send(msg);
        }
    }

    public void deliver(Object m) {
        System.out.println((String)m);
    }
    
    private static void usage() {
        System.out.println("Usage: java ChatMember [host] [port] [groupname]");
        System.out.println("Usage: java ChatMember [host] [groupname] // port 1099 will be used");

    }

    public static void main(String[] args) {
        try {
            if (args.length == 3) {
                new ChatMember(args[0], Integer.parseInt(args[1]), args[2]);
            } else if (args.length == 2) {
                new ChatMember(args[0], 1099, args[1]);
            } else {
                usage();
            }
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
}
