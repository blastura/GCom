package se.umu.cs.jsgajn.gcom.messageordering;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VectorClock<T extends Serializable> implements Comparable<VectorClock<T>> {
    private Map<T, Integer> map = new HashMap<T, Integer>();
    private T id;
    
    public VectorClock(T id) {
        this.id = id;
        map.put(id, 0);
    }

    public void newProcess(T newId) {
        if (newId.equals(this.id)) {
            throw new AssertionError("Own process id added");
        }
        map.put(newId, 0);
    }

    /**
     * Increment own value by 1;
     */
    public void tick() {
        map.put(id, map.get(id) + 1);
    }

    /**
     * Merge this vector clock with supplied v-clock. For every process in both
     * other and this vectorclock, compare counters and change own counter to
     * the maximum value of the two.
     *
     * @param other The vector clock to merge with.
     */
    public void merge(final VectorClock<T> other) {
        checkEqualKeySets(other);
	for (T id : keySet()) {
	    int max = Math.max(get(id), other.get(id));
	    map.put(id, max);
	}
    }
    
    public int get() {
        return map.get(this.id);
    }

    public int get(T id) {
        return map.get(id);
    }

    public Set<T> keySet() {
        return map.keySet();
    }

    /**
     * Compare this VectorClock to another VectorClock.
     *
     * @return negative numbers if clock is smaller than parameter, 0 if it's
     *         smaller or equal, possitive numbers if it is greater than the
     *         parameter clock.
     */
    public int compareTo(final VectorClock<T> o) {
        checkEqualKeySets(o);
        // TODO: use this or somehting else
        // =, <=, <
        Map<T, Integer> oMap = o.getMap();
        System.out.println("myMap: " + map.toString());
        System.out.println("oMap: " + oMap.toString());
        System.out.println("===========");
        int nrEqual = 0;
        int larger = 0;
        int smaller = 0;
        for (T id : map.keySet()) {
            // TODO: Possible error for large or small values
            int diff = map.get(id) - oMap.get(id);
            if (diff < 0) {
                smaller =+ diff;
            } else if (diff > 0) {
                larger =+ diff;
            } else {
                nrEqual++;
            }
        }
        
        if (nrEqual > 0 && larger == 0) {
            return 0;
        } else if (nrEqual == 0 && larger == 0) {
            return smaller; // Will be negative
        } else {
            return larger; // Will be positive
        }
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

    private void checkEqualKeySets(final VectorClock<T> o) {
        if ((map.size() != o.getMap().size())
            && !(map.keySet().equals(o.getMap().keySet()))) {
            // TODO: what now? Fix this, so program don't crash.
            throw new AssertionError("Vectorclocks doesn't match sizes, and keySets");
        }
    }
}
