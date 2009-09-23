package se.umu.cs.jsgajn.gcom.groupcommunication;

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
        // TODO Auto-generated method stub
        return m;
    }

    public void setHeader(Header h) {
        this.h = h;
    }

}
