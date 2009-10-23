package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.util.UUID;
import se.umu.cs.jsgajn.gcom.groupcommunication.MemberCrashException;
import se.umu.cs.jsgajn.gcom.Module;

public interface GroupModule extends Module {
    /** The stubname to bind in registry */
    public static final String STUB_NAME = "ballen";

    /** Unique identifier for every groupmember/process */
    public static final UUID PID = UUID.randomUUID();
    
    /** Used by clients to send messages to group */
    public void send(Object m);

    /** Current groupview, used by communicationsmodel... */
    public GroupView getGroupView();
}