package se.umu.cs.jsgajn.gcom;

public class MessageDebugException extends Exception {
    private static final long serialVersionUID = 1L;
    private Message m;

    public MessageDebugException(Message m) {
        this.m = m;
    }

    public Message getMessag() {
        return m;
    }
}
