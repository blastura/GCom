package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.util.Collection;
import java.util.Iterator;

import se.umu.cs.jsgajn.gcom.testapp.Server;

public class GroupImpl implements Group {

    private Collection<GroupMember> group;
    private String name;
    
    public GroupImpl(String name) {
        this.name = name;
    }
    
    public void closeGroup() {
    }

    public boolean add(GroupMember member) {
        return group.add(member);
    }

    public boolean addAll(Collection<? extends GroupMember> members) {
        return group.addAll(members);
    }

    public void clear() {
        group.clear();
    }

    public boolean contains(Object member) {
        return group.contains(member);
    }

    public boolean containsAll(Collection<?> members) {
        return group.containsAll(members);
    }

    public boolean isEmpty() {
        return group.isEmpty();
    }

    public Iterator iterator() {
        return group.iterator();
    }

    public boolean remove(Object member) {
        return group.remove(member);
    }

    public boolean removeAll(Collection<?> members) {
        return group.removeAll(members);
    }

    public boolean retainAll(Collection<?> members) {
        return group.retainAll(members);
    }

    public int size() {
        return group.size();
    }

    public Object[] toArray() {
        return group.toArray();
    }

    public Object[] toArray(Object[] arg0) {
        return group.toArray(arg0);
    }

}
