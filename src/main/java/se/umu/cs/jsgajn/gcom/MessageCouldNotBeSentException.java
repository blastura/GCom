package se.umu.cs.jsgajn.gcom;

import se.umu.cs.jsgajn.gcom.management.CrashList;

public class MessageCouldNotBeSentException extends Exception {
    private static final long serialVersionUID = 1L;
    private CrashList cl;

    public MessageCouldNotBeSentException(CrashList cl) {
        this.cl = cl;
    }

    public CrashList getCrashedMembers() {
        return cl;
    }
}