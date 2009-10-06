package se.umu.cs.jsgajn.gcom.debug;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

public interface Debugger { 
    public void messageReceived(Message m);
    public void messageDelivered(Message m);
}