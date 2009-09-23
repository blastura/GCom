package se.umu.cs.jsgajn.gcom.messageordering;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class VectorClock<T extends Serializable> implements Comparable<VectorClock<T>> {
    private Map<T, Integer> map = new HashMap<T, Integer>();
    private T id;
    
    public VectorClock(T id) {
        this.id = id;
    }

    public void newProcess(T newId) {
        if (newId.equals(this.id)) {
            throw new AssertionError("Own process id added");
        }
        map.put(id, 0);
    }

    /**
     * Increment own value by 1;
     */
    public void tick() {
        map.put(id, map.get(id) + 1);
    }

    public int get() {
        return map.get(this.id);
    }

    public int get(T id) {
        return map.get(id);
    }

    public int compareTo(VectorClock<T> o) {
        if (!this.equals(o)) {
            // TODO: what now?
            throw new AssertionError("Vectorclocks doesn't match sizes");
        }
        Map<T, Integer> oMap = o.getMap();
        int result = 0;
        for (T id : map.keySet()) {
            result += map.get(id) - oMap.get(id);
        }
        return result;
    }

    public int size() {
        return map.size();
    }

    protected Map<T, Integer> getMap() {
        return this.map;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof VectorClock))
            return false;
        @SuppressWarnings("unchecked") // TODO: Unchecked cast?
        VectorClock<T> other = (VectorClock) o;
        return map.equals(other.getMap());
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
