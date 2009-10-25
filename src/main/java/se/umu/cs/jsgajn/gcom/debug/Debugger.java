package se.umu.cs.jsgajn.gcom.debug;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Queue;

import se.umu.cs.jsgajn.gcom.Message;
import se.umu.cs.jsgajn.gcom.communication.Receiver;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import se.umu.cs.jsgajn.gcom.management.GroupView;
import se.umu.cs.jsgajn.gcom.ordering.VectorClock;

public class Debugger implements DebugHandler {
    private static final Logger logger = LoggerFactory.getLogger(Debugger.class);
    private static final Debugger INSTANCE = new Debugger();
    private static DebugHandler debugger;

    private Debugger() {} // Prevent init

    public static Debugger getDebugger() {
        return INSTANCE;
    }

    public static void setDebugHandler(DebugHandler d) {
        debugger = d;
    }

    public void messageReceived(Message m) {
        if (debugger == null) {
            return;
        }
        waitForModel();
        //logger.debug("Received: " + m);
        debugger.messageReceived(m);
    }

    public void messageDelivered(Message m) {
        if (debugger == null) {
            return;
        }
        //logger.debug("Delivered: " + m);
        debugger.messageDelivered(m);
    }

    public void crash() {
        if (debugger == null) {
            return;
        }
    }

    public void block() {
        if (debugger == null) {
            return;
        }
        debugger.block();
    }

    public void groupChange(GroupView g) {
        if (debugger == null) {
            return;
        }
        waitForModel();
        debugger.groupChange(g);
    }

    public boolean holdMessage(Message m, Receiver r) {
        if (debugger == null) {
            return false;
        }
        return debugger.holdMessage(m, r);
    }

    public boolean isModelInitialized(int hack) {
        return debugger.isModelInitialized(1);
    }

    public void waitForModel() {
        if(!debugger.isModelInitialized(1)) {
            while(debugger.isModelInitialized(1) == false){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.err.println("Thread in debugger couldn't sleep.");
                }
            }
        }
    }

    public void updateVectorClock(VectorClock<UUID> vc) {
        if (debugger == null) {
            return;
        }
        debugger.updateVectorClock(vc);
    }

    public void updateOrderingHoldList(ArrayList<Message> holdList) {
        if (debugger == null) {
            return;
        }
        debugger.updateOrderingHoldList(holdList);
        
    }
}