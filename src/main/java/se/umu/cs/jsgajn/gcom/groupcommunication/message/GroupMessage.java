package se.umu.cs.jsgajn.gcom.groupcommunication.message;

import se.umu.cs.jsgajn.gcom.groupcommunication.Header;
import se.umu.cs.jsgajn.gcom.groupcommunication.HeaderImpl;
import se.umu.cs.jsgajn.gcom.groupcommunication.Message;
import se.umu.cs.jsgajn.gcom.groupcommunication.MessageTypes;
import se.umu.cs.jsgajn.gcom.groupmanagement.Group;

public class GroupMessage implements Message<Group> {
    private Group msg;
    private Header header;
    
    public GroupMessage(Group msg) {
        this.msg = msg;
        this.header = new HeaderImpl(MessageTypes.GROUPCHANGE);
    }
    
    public Group getMessage() {
        return this.msg;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setHeader() {
        
    }

}
