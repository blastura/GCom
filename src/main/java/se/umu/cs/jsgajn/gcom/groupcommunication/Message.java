package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.io.Serializable;

public interface Message<T> extends Serializable {

    public T getMessage();
    public Header getHeader();
    public void setHeader();

}
