package se.umu.cs.jsgajn.gcom.management;

public interface GroupLeader {
    
    /**
     * Add GroupMember member to this process instance of GroupView
     * 
     * @param member
     */
    public void removeFromGroup(GroupMember member);
    
    /**
     * Removes GroupMember member to this process instance of GroupView
     * 
     * @param r
     */
    public void addMemberToGroup(GroupMember r);
}
