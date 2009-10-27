package se.umu.cs.jsgajn.gcom.testapp;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.management.ManagementModule;
import se.umu.cs.jsgajn.gcom.management.ManagementModuleImpl;

public class Controller implements Client {

    private ManagementModule managementModule;
    private Model model;
    private String nick;
    private ArrayList<String> members;
    
    public Controller(Model model) {
        this.model = model;       
    }

    public void init() {
        this.members = new ArrayList<String>();
        
        String host = model.getHost().getText();
        int hostPort = Integer.parseInt(model.getHostPort().getText());
        int localPort = Integer.parseInt(model.getLocalPort().getText());
        String channel = model.getChannel().getText();
        this.nick = model.getNick().getText();

        model.getMembers().addElement(nick);
        members.add(nick);
        
        try {
            managementModule = new ManagementModuleImpl(this, host, hostPort, channel, localPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
    
    public void send(){
        System.out.println(model.getMessage().getText());
        managementModule.send(new ChatMessage(
                model.getMessage().getText(), 
                model.getNick().getText()));
        model.getMessage().setText(null);
    }

    public void deliver(Object m) {
        String messenger = ((ChatMessage) m).getName();
        String message = ((ChatMessage) m).getMessage();
        
        if (!members.contains(messenger)) {
            members.add(messenger);
            model.getMembers().addElement(messenger);
        }
        model.getChat().append("\n" + messenger + ": " + message);
    }
    
}
