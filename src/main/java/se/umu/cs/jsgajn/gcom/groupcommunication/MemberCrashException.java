package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.RemoteException;
import se.umu.cs.jsgajn.gcom.groupmanagement.GroupMember;
import java.util.Map;

public class MemberCrashException extends Exception {
    private static final long serialVersionUID = 1L;
    private Map<GroupMember, RemoteException> crashedMembers;
    
    public MemberCrashException(Map<GroupMember, RemoteException> crashedMembers) {
        this.crashedMembers = crashedMembers;
    }

    public Map<GroupMember, RemoteException> getCrashedMembers() {
        return this.crashedMembers;
    }

    //     @Override
    //     public Throwable getCause() {
    //         return this.re;
    //     }

    //     @Override
    //     public String getMessage() {
    //         return re.getMessage();
    //     }
}
