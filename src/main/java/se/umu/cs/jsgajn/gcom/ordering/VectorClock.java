package se.umu.cs.jsgajn.gcom.ordering;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to represent vector clocks. Which is a map identifiers T mapped to 
 * counters.
 * 
 * @author dit06ajn, dit06jsg
 *
 * @param <T>
 */
public class VectorClock<T extends Serializable> implements Comparable<VectorClock<T>>, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(VectorClock.class);

    private Map<T, Integer> map;
    private T id;

    public VectorClock(VectorClock<T> v) {
        this.id = v.getID();
        this.map = new HashMap<T, Integer>(v.getMap());
    }

    /**
     * Create a vector clock with specified id. The counter for that id will be
     * 0.
     * 
     * @param id The id of this vector clock.
     */
    public VectorClock(T id) {
        this(id, 0);
    }

    /**
     * Create a new vector clock with a specified start value for id.
     * 
     * @param id The id of this vector clock.
     * @param startValue The start value of the id.
     */
    public VectorClock(T id, int startValue) {
        this.id = id;
        map = new HashMap<T, Integer>();
        map.put(id, startValue);
    }

    /**
     * @param newId Add a new process to this vector clock with counter 0.
     */
    public void newProcess(T newId) {
        newProcess(newId, 0);
    }

    public void newProcess(T newId, int startValue) {
        if (newId.equals(this.id)) {
            throw new IllegalArgumentException("Own process id added");
        }
        map.put(newId, startValue);
    }

    /**
     * Increment own counter by 1;
     */
    public void tick() {
        map.put(id, (map.get(id) + 1));
    }

    /**
     * Increment supplied ids counter by 1;
     */
    public void tick(T id) {
        if (!map.containsKey(id)) {
            throw new IllegalArgumentException("VectorClock does not contain id: " + id);
        }
        map.put(id, (map.get(id) + 1));
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

    public T getID() {
        return this.id;
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

    public boolean containsKey(T key) {
        return map.containsKey(key);
    }

    /**
     * Compare this VectorClock to another VectorClock.
     *
     * @return negative numbers if clock is smaller than every value in
     *         parameter, 0 if it's smaller or equal, possitive numbers if it is
     *         greater than the parameter clock.
     */
    public int compareTo(final VectorClock<T> o) {
        return compareTo(o, true);
    }

    public int compareToAllButOwnID(final VectorClock<T> o) {
        return compareTo(o, false);
    }

    // TODO: not consistent with equals() or anything? Can't be used in sets...
    private int compareTo(final VectorClock<T> o, final boolean compareOwnID) {
        //checkEqualKeySets(o);
        Map<T, Integer> oMap = o.getMap();
        int nrEqual = 0;
        int larger = 0;
        int smaller = 0;
        for (T id : map.keySet()) {
            // Skip own ID, used by causal-order
            if (!compareOwnID && id.equals(getID())) {
                logger.debug("Don't compare own id");
                continue;
            }

            if (oMap.containsKey(id)) {
                // TODO: Possible error for large or small values
                int diff = map.get(id) - oMap.get(id);
                if (diff < 0) {
                    smaller += diff;
                } else if (diff > 0) {
                    larger += diff;
                } else {
                    nrEqual++;
                }
            } else {
                logger.debug("================= Diff in keyset comparing VectorClocks");
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

    private Map<T, Integer> getMap() {
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

    @Override
    public String toString() {
        return this.map.toString();
    }

    @Override
    public VectorClock<T> clone() {
        try {
            @SuppressWarnings("unchecked")
                VectorClock<T> newVC = (VectorClock<T>) super.clone();
            newVC.setMap(new HashMap<T, Integer>(this.map));
            return newVC;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new Error("Object " + this.getClass().getName()
                            + " is not Cloneable");
        }
    }

    private void setMap(Map<T, Integer> map) {
        this.map = map;
    }

    private void checkEqualKeySets(final VectorClock<T> o) {
        if ((map.size() != o.getMap().size())
            && !(map.keySet().equals(o.getMap().keySet()))) {
            // TODO: what now? Fix this, so program don't crash.
            throw new AssertionError("Vectorclocks doesn't match sizes, and keySets");
        }
    }
}
