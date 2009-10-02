package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModule;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

public class CommunicationsModelImpl implements CommunicationModule {
    private LinkedBlockingQueue<Message> receiveQueue;
    private Receiver receiver;
    private Receiver receiverStub;
    private Multicast mMethod;
    private Module orderingModule;
    private GroupModule groupModule;
    
    public CommunicationsModelImpl(GroupModule groupModule, Multicast mMethod)
            throws RemoteException, AlreadyBoundException, NotBoundException {
        this.mMethod = mMethod;
        this.groupModule = groupModule;
        
        // TODO: change 1099
        Registry registry = LocateRegistry.createRegistry(1099); 
        this.receiveQueue = new LinkedBlockingQueue<Message>();

        this.receiver = new ReceiverImpl(this.receiveQueue, GroupModule.PID);
        this.receiverStub = 
            (Receiver) UnicastRemoteObject.exportObject(receiver, 0);
        registry.bind(Receiver.STUB_NAME, receiverStub);

        // Start thread to handle messages
        new Thread(new MessageReceiver()).start();
    }
    
    public void setOrderingModule(Module m) {
        this.orderingModule = m;
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
                        deliver(m);
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
        orderingModule.deliver(m);
    }
}
