package se.umu.cs.jsgajn.gcom;

/**
 * Clients of GCom should implement this interface and register an instance of 
 * it in ManagementModule. This will make the ManagementModule deliver messages
 * to the client. 
 * 
 * @author dit06ajn, dit06jsg
 */
public interface Client {
    /**
     * Used by GCom to deliver messages to a client.
     * 
     * @param message The message to deliver.
     */
    public void deliver(Object message);
}