package se.umu.cs.jsgajn.gcom.debug;

import se.umu.cs.jsgajn.gcom.groupcommunication.Message;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Debugger implements DebugHandler {
    private static final Logger logger = LoggerFactory.getLogger(Debugger.class);
    private static final Debugger INSTANCE = new Debugger();
    private static DebugHandler debugger;
    
    private Debugger() {} // Prevent init

    public static void setDebugHandler(DebugHandler d) {
        debugger = d;
    }
    
    public void messageReceived(Message m) {
        logger.debug("Received: " + m);
        if (debugger != null) {
            debugger.messageReceived(m);
        }
    }
    
    public void messageDelivered(Message m) {
        logger.debug("Delivered: " + m);
        if (debugger != null) {
            debugger.messageDelivered(m);
        }
    }
    
    public static synchronized Debugger getDebugger() {
        return INSTANCE;
    }
}