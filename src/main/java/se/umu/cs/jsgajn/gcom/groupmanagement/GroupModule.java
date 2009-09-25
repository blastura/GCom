package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.rmi.server.UID;

public interface GroupModule {    
    public static final String STUB_NAME = "ballen";

    /** Unique identifier for every groupmember */
    public static final UID PID = new UID();
    
    public void send(Object m);

    public GroupView getGroupView();
}
