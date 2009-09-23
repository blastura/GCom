package se.umu.cs.jsgajn.gcom.groupmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GroupViewImpl implements GroupView {

    private Collection<Receiver> group;
    private String name;
    private Receiver groupLeader;
    private Collection<Receiver> members;
    
    public GroupViewImpl(String name, Receiver groupLeader) {
	this.group = new ArrayList<Receiver>();
        this.name = name;
        this.groupLeader = groupLeader;
    }

    public String getName() {
        return name;
    }

    public Receiver getGroupLeaderReceiver() {
        return groupLeader;
    }
    
    public void closeGroup() {
    }

    public boolean add(Receiver member) {
        return group.add(member);
    }

    public boolean addAll(Collection<? extends Receiver> members) {
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
