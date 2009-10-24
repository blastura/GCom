package se.umu.cs.jsgajn.gcom;

import java.io.Serializable;

import se.umu.cs.jsgajn.gcom.management.CrashList;

public class MemberCrashException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    private CrashList crashedMembers;
    
    public MemberCrashException(CrashList crashedMembers) {
        this.crashedMembers = crashedMembers;
    }

    public CrashList getCrashedMembers() {
        return this.crashedMembers;
    }
}