package se.umu.cs.jsgajn.gcom.groupmanagement;

import se.umu.cs.jsgajn.gcom.groupcommunication.Receiver;

public interface GroupLeader {
    public void removeFromGroup(Receiver member);
    public void addMemberToGroup(Receiver r);
}
