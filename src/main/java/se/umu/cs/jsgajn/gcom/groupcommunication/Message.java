package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;

public interface Message extends Serializable {

    public Object getMessage();
    public Header getHeader();
    public void setHeader();

}
