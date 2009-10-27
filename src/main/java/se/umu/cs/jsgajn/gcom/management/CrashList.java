package se.umu.cs.jsgajn.gcom.management;

import java.io.Serializable;
import java.util.List;

public interface CrashList extends Iterable<GroupMember>, Serializable {

    /**
     * Adds member to list of crashed members.
     * 
     * @param r
     * @return
     */
    public boolean add(GroupMember r);
    
    /**
     * Return true if the list is emtyp.
     * 
     * @return
     */
    public boolean isEmpty();
    
    /**
     * Returns all crashed members from the list.
     * 
     * @return
     */
    public List<GroupMember> getAll();
    
    /**
     * Returns true if the the list of crashed members contains 
     * GroupMember gm
     * 
     * @param gm
     * @return
     */
    public boolean contains(GroupMember gm);
    
}
