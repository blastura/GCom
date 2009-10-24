package se.umu.cs.jsgajn.gcom.management;

import java.util.UUID;
import se.umu.cs.jsgajn.gcom.Module;

public interface ManagementModule extends Module {
    /** The stubname to bind in registry */
    public static final String STUB_NAME = "ballen";

    /** Unique identifier for every groupmember/process */
    public static final UUID PID = UUID.randomUUID();
    
    /** Used by clients to send messages to group */
    public void send(Object m);

    /** Current groupview, used by communicationsmodel... */
    public GroupView getGroupView();
}