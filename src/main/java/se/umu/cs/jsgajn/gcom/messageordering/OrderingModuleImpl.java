package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

/**
 * Module to handle different orderings of messages.
 *
 * @author dit06ajn, dit06jsg
 */
public class OrderingModuleImpl implements OrderingModule {
    private Ordering ordering;
    private Module communicationsModule;
    private Module groupManagementModule;

    public OrderingModuleImpl(Module groupManagementModule,
                              Ordering ordering) {
        this.groupManagementModule = groupManagementModule;
        this.ordering = ordering;

        new Thread(new DeliverHandler()).start();
    }

    public void setCommunicationsModule(Module m) {
        this.communicationsModule = m;
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

    public void setDeliverReceiver(Module m) {
        // TODO Auto-generated method stub
        throw new Error("TODO: fix");

    }

    public void setSendReceiver(Module m) {
        // TODO Auto-generated method stub
        this.communicationsModule = m;
    }
}
