package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.server.UID;


public interface GroupMember {    
    public static final String STUB_NAME = "ballen";

    // TODO: Kommer detta fungera? där nere new UID static?
    /** Unique identifier for every groupmember */
    public static final UID ID = new UID();
    
    public void send(Object m);
}
