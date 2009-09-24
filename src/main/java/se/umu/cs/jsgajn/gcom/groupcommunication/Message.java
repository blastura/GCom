package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;
import java.rmi.server.UID;

public interface Message extends Serializable {
    public final static UID ID = new UID();
    public Object getMessage();
    public Header getHeader();
    public void setHeader(Header h);

}
