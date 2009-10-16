package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.io.Serializable;
import java.util.List;

public interface CrashList extends Iterable<GroupMember>, Serializable {

    public boolean add(GroupMember r);
    public boolean isEmpty();
    public List<GroupMember> getAll();
    
}
