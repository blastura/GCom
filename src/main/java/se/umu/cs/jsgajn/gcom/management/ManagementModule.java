package se.umu.cs.jsgajn.gcom.management;

import java.util.UUID;
import se.umu.cs.jsgajn.gcom.Module;

/**
 * Interface do define behavior of the management module used for
 * clients to communicate with the group.
 *
 * TODO: Hide or differentiate methods used by other Modules and
 *       method used by Clients (send(Object))
 *
 * @author dit06ajn, dit06jsg
 * @version 1.0
 */
public interface ManagementModule extends Module {
    
    /**
     * The stubname to bind in registry.
     *
     * @return A name to bind and locate the stub used for
     *         communication with this process.
     */
    public static final String STUB_NAME = "magementModule";

    /**
     * Unique identifier for every groupmember/process. This i
     * generated on first call and is consistent after that.
     *
     * @return The globally unique identifier for this current process.
     */
    public static final UUID PID = UUID.randomUUID();
    
    /**
     * Used by clients to send messages to group
     *
     * @param m The object to send to group.
     */
    public void send(Object m);

    /**
     * Current groupview, used by communicationsmodel, in reliable
     * multicast for resending messages
     * 
     * @return The current GroupView
     */
    public GroupView getGroupView();
}