package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import se.umu.cs.jsgajn.gcom.debug.Debugger;
import java.rmi.NoSuchObjectException;
import java.util.concurrent.BlockingQueue;

public class CommunicationsModuleImpl implements CommunicationModule {
    private static final Logger logger = LoggerFactory.getLogger(CommunicationsModuleImpl.class);
    private static final Debugger debugger = Debugger.getDebugger();
    
    private BlockingQueue<Message> receiveQueue;
    private Receiver receiver;
    private Receiver receiverStub;
    private Multicast mMethod;
    private Module orderingModule;
    private GroupModule groupModule;
    private Thread messageReceiverThread;
    private Registry registry;
    private boolean running;

    /**
     * Creates a new <code>CommunicationsModuleImpl</code> instance with a
     * register on port defined by {@link java.rmi.Register#REGISTRY_PORT}.
     *
     * @param groupModule TODO: only to get GroupViews
     * @exception RemoteException if an error occurs
     * @exception AlreadyBoundException if an error occurs
     * @exception NotBoundException if an error occurs
     */
    public CommunicationsModuleImpl(GroupModule groupModule)
        throws RemoteException, AlreadyBoundException, NotBoundException {
        this(groupModule, Registry.REGISTRY_PORT);
    }
        
    public CommunicationsModuleImpl(GroupModule groupModule, final int port)
        throws RemoteException, AlreadyBoundException, NotBoundException,IllegalArgumentException {
        this.groupModule = groupModule;

        // TODO: make port optional
        this.registry = LocateRegistry.createRegistry(port);
        this.receiveQueue = new LinkedBlockingQueue<Message>();

        this.receiver = new ReceiverImpl(this.receiveQueue, GroupModule.PID);
        this.receiverStub =
            (Receiver) UnicastRemoteObject.exportObject(receiver, 0);
        registry.bind(Receiver.STUB_NAME, receiverStub);

        // Create thread to handle messages
        this.messageReceiverThread = new Thread(new MessageReceiver(),
                                                "CommunicationsModule thread");
        logger.debug("CommunicationsModuleImpl receiveing messages at port: " + port);
    }

    public void start() {
        if (this.mMethod == null) {
            throw new IllegalStateException("Multicast method is not set");
        }
        if (this.orderingModule == null) {
            throw new IllegalStateException("Ordering module is not set");
        }
        
        this.running = true;
        this.messageReceiverThread.start();
        logger.debug("Started CommunicationsModule: " + mMethod);
    }

    public void stop() {
        logger.debug("Stopping CommunicationsModule");
        this.running = false;
        try {
            UnicastRemoteObject.unexportObject(this.registry, true);
        } catch (NoSuchObjectException e) {
            logger.warn("Couldn't unregister registry: " + e.getMessage());
        }
    }

    public void setOrderingModule(Module m) {
        this.orderingModule = m;
    }

    public void setMulticastMethod(Multicast m) {
        this.mMethod = m;
    }

    public void send(Message m, GroupView g) {
        try {
            mMethod.multicast(m, g);
        } catch (MemberCrashException e) {
            logger.warn("MemberCrashException", e.getMessage());

            groupModule.handleMemberCrashException(e);
        }
    }

    private class MessageReceiver implements Runnable {
        public void run() {
            try {
                while (running) {
                    handleMessage(receiveQueue.take());
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        public void handleMessage(Message m) {
            debugger.messageReceived(m);
            
            if (debugger.holdMessage(m, getReceiver())) {
                return;
            }
            
            if (mMethod.deliverCheck(m, groupModule.getGroupView())) {
                deliver(m);
            }
        }
    }

    public Receiver getReceiver() {
        return this.receiver;
    }

    public void deliver(Message m) {
        logger.debug("Message delivered");
        orderingModule.deliver(m);
    }
}