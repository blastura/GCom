package se.umu.cs.jsgajn.gcom.testapp;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import java.util.Properties;
import java.io.IOException;
import java.rmi.AlreadyBoundException;

public class Server implements Hello {
    private static final Logger logger = Logger.getLogger(Server.class);
    private Properties properties;
    private String registryHost;
    private int registryPort;

    public Server() {
        this.properties = new Properties();
        try {
            properties.load(Server.class.getResourceAsStream("/resources/application.properties"));
            this.registryHost = properties.getProperty("registry-host");
            this.registryPort = Integer.parseInt(properties.getProperty("registry-port"));
            System.out.println(registryHost);
            System.out.println(registryPort);
            System.out.println(System.getProperty("msg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Hello stub = (Hello) UnicastRemoteObject.exportObject(this, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(registryHost, registryPort);
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO - fix error message
            e.printStackTrace();
        }
    }

    public String sayHello() {
        logger.debug("executing sayHello()");
        //throw new RuntimeException("fel");
        return "Hello, world! (" + System.getProperty("msg") + ")";
    }

    public static void main(String args[]) {
        BasicConfigurator.configure();
        new Server();
    }
}