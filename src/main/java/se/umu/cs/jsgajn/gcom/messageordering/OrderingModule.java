package se.umu.cs.jsgajn.gcom.messageordering;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public class OrderingModule implements Ordering {

    private Ordering ordering;

    public OrderingModule(Ordering ordering) {
        this.ordering = ordering;
        
    }
    
    public void deliver(Message m) {
        // TODO Auto-generated method stub
        ordering.deliver(m);
        
    }

    public Message takeDelivered() {
        // TODO Auto-generated method stub
        return ordering.takeDelivered();
    }

}
