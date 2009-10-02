package se.umu.cs.jsgajn.gcom.messageordering;

import org.apache.log4j.Logger;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupModuleImpl;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

/**
 * Module to handle different orderings of messages.
 *
 * @author dit06ajn, dit06jsg
 */
public class OrderingModuleImpl implements OrderingModule {
    private static final Logger logger = Logger.getLogger(OrderingModuleImpl.class);
    private Ordering ordering;
    private Module communicationsModule;
    private Module groupManagementModule;
    private Thread deliverHandlerThread;

    public OrderingModuleImpl(Module groupManagementModule) {
        this.groupManagementModule = groupManagementModule;
        this.deliverHandlerThread = new Thread(new DeliverHandler());
    }
    
    public void start() {
        if (this.ordering == null) {
            throw new IllegalStateException("Ordering is not set");
        }
        if (this.communicationsModule == null) {
            throw new IllegalStateException("GroupModule module is not set");
        }
        this.deliverHandlerThread.start();
        logger.debug("Started OrderingModule: " + ordering);
    } 

    public void setCommunicationsModule(Module m) {
        this.communicationsModule = m;
    }
    
    public void setOrdering(Ordering ordering) {
        this.ordering = ordering; 
    }

    public void send(Message m, GroupView g) {
        communicationsModule.send(ordering.prepareOutgoingMessage(m), g);
    }

    public void deliver(Message m) {
        ordering.put(m);
    }

    private class DeliverHandler implements Runnable {
        public void run() {
            while (true) {
                groupManagementModule.deliver(ordering.take());
            }
        }
    }
}
