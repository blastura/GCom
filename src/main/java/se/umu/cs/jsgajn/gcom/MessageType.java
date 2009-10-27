package se.umu.cs.jsgajn.gcom;

/**
 * Enumeration for different message types for GCom. All client messages should
 * be sent with {@link #CLIENTMESSAGE}.
 * 
 * @author dit06ajn, dit06jsg
  */ 
public enum MessageType {
    /**
     * For client message.
     */
    CLIENTMESSAGE,
    
    /**
     * When a leader has detected a change in the group composition. 
     */
    GROUPCHANGE,
    
    
    /**
     * For sending a join message to the leader. 
     */
    JOIN,
    
    /**
     * When members detect that another member has crashed.
     */
    MEMBERCRASH;
}
