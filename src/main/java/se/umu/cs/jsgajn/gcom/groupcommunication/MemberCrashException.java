package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.RemoteException;

import se.umu.cs.jsgajn.gcom.groupmanagement.CrashList;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MemberCrashException extends Exception implements Serializable{
    private static final long serialVersionUID = 1L;
    private CrashList crashedMembers;
    
    public MemberCrashException(CrashList crashedMembers) {
        this.crashedMembers = crashedMembers;
    }

    public CrashList getCrashedMembers() {
        return this.crashedMembers;
    }
}
