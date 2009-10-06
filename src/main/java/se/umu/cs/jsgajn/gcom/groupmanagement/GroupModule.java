package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.server.UID;

import se.umu.cs.jsgajn.gcom.Module;
import se.umu.cs.jsgajn.gcom.debug.Debuggable;

public interface GroupModule extends Module, Debuggable {
    /** The stubname to bind in registry */
    public static final String STUB_NAME = "ballen";

    /** Unique identifier for every groupmember/process */
    public static final UID PID = new UID();
    
    /** Used by clients to send messages to group */
    public void send(Object m);

    /** Current groupview, used by communicationsmodel... */
    public GroupView getGroupView();
}