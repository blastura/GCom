package se.umu.cs.jsgajn.gcom.groupcommunication;

import java.rmi.server.UID;

public class MessageImpl implements Message {
    
    Object m;
    Header h;
    
    public MessageImpl(Object m, Header h){
        this.m = m;
        this.h = h;
    }
    
    public Header getHeader() {
        return h;
    }

    public Object getMessage() {
        return m;
    }

    public void setHeader(Header h) {
        this.h = h;
    }

}
