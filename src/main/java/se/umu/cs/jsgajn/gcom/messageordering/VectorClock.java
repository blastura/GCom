package se.umu.cs.jsgajn.gcom.messageordering;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class VectorClock<T extends Serializable> implements Comparable<VectorClock<T>> {
    private Map<T, Integer> map = new HashMap<T, Integer>();
    private T id;
    private enum diff {STRICTLY_LESS, STRICTLY_LESS_OR_EQUAL, EQUAL, LARGER}
    
    public VectorClock(T id) {
        this.id = id;
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

    public int get() {
        return map.get(this.id);
    }

    public int get(T id) {
        return map.get(id);
    }

    /**
     * Compare this VectorClock to another VectorClock.
     *
     * @return negative numbers if clock is smaller than parameter, 0 if it's
     *         smaller or equal, possitive numbers if it is greater than the
     *         parameter clock.
     */
    public int compareTo(VectorClock<T> o) {
        if (map.size() != o.getMap().size()
            && map.keySet().equals(o.getMap().keySet())) {
            // TODO: what now?
            throw new AssertionError("Vectorclocks doesn't match sizes, and keySets");
        }
        // TODO: use this or somehting else
        // =, <=, <
        Map<T, Integer> oMap = o.getMap();
        System.out.println("myMap: " + map.toString());
        System.out.println("===========");
        System.out.println("oMap: " + oMap.toString());
        int nrEqual = 0;
        int larger = 0;
        int smaller = 0;
        for (T id : map.keySet()) {
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
            return smaller;
        } else {
            return larger;
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
}
