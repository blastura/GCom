package se.umu.cs.jsgajn.gcom.testapp;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.Client;
import se.umu.cs.jsgajn.gcom.management.ManagementModule;
import se.umu.cs.jsgajn.gcom.management.ManagementModuleImpl;

public class Controller implements Client {

    ManagementModule managementModule;
    Model model;
    
    public Controller(Model model) {
        this.model = model;       
    }

    public void init() {
        
        String host = model.getHost().getText();
        int hostPort = Integer.parseInt(model.getHostPort().getText());
        int localPort = Integer.parseInt(model.getLocalPort().getText());
        String channel = model.getChannel().getText();
        
        
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
        managementModule.send(model.getMessage().getText());
        model.getMessage().setText(null);
    }

    public void deliver(Object message) {
        model.getChat().append("\n" + (String)message);
    }

}
