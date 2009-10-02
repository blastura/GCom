package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;
import se.umu.cs.jsgajn.gcom.messageordering.OrderingModuleImpl;

public class CommunicationsModuleImpl implements CommunicationModule {
    private static final Logger logger = Logger.getLogger(CommunicationsModuleImpl.class);
    private LinkedBlockingQueue<Message> receiveQueue;
    private Receiver receiver;
    private Receiver receiverStub;
    private Multicast mMethod;
    private Module orderingModule;
    private GroupModule groupModule;
    private Thread messageReceiverThread;
    
    public CommunicationsModuleImpl(GroupModule groupModule)
            throws RemoteException, AlreadyBoundException, NotBoundException {
        this.groupModule = groupModule;
        
        // TODO: change 1099
        Registry registry = LocateRegistry.createRegistry(1099); 
        this.receiveQueue = new LinkedBlockingQueue<Message>();

        this.receiver = new ReceiverImpl(this.receiveQueue, GroupModule.PID);
        this.receiverStub = 
            (Receiver) UnicastRemoteObject.exportObject(receiver, 0);
        registry.bind(Receiver.STUB_NAME, receiverStub);

        // Create thread to handle messages
        this.messageReceiverThread = new Thread(new MessageReceiver(),
                "CommunicationsModule thread");
    }
    
    public void start() {
        if (this.mMethod == null) {
            throw new IllegalStateException("Multicast method is not set");
        }
        if (this.orderingModule == null) {
            throw new IllegalStateException("Ordering module is not set");
        }
        this.messageReceiverThread.start();
        logger.debug("Started CommunicationsModule: " + mMethod);
    }
    
    public void setOrderingModule(Module m) {
        this.orderingModule = m;
    }

    public void setMulticastMethod(Multicast m) {
        this.mMethod = m;    
    }
    
    public void send(Message m, GroupView g) {
        mMethod.multicast(m, g);
    }

    private class MessageReceiver implements Runnable {
        public void run() {
            try {
                while (true) { 
                    Message m = receiveQueue.take();
                    if (mMethod.deliverCheck(m, groupModule.getGroupView())) {
                        orderingModule.deliver(m);
                    }
                }
            } catch (InterruptedException e) { 
                System.out.println(e);
            }
        }
    }

    public Receiver getReceiver() {
        return this.receiver;
    }

    public void deliver(Message m) {
        // Communicates directly  with receiver through queue
        throw new UnsupportedOperationException();
    }
}