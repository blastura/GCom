package se.umu.cs.jsgajn.gcom;

import se.umu.cs.jsgajn.gcom.management.CrashList;

/**
 * This exception is thrown when the sequencer doesn't answer when its called
 * from TotalOrder or CasualTotalOrder
 * 
 * @author dit06jsg, dit06ajn
 *
 */

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
