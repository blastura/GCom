package se.umu.cs.jsgajn.gcom.messageordering;

import java.rmi.server.UID;
import java.util.HashMap;
import java.util.Map;

public class VectorClock implements Comparable<VectorClock> {
    private Map<UID, Integer> map = new HashMap<UID, Integer>();

    public VectorClock() {
    }

    public int compareTo(VectorClock o) {
        if (!this.equals(o)) {
            // TODO: what now?
            throw new AssertionError("Vectorclocks doesn't match sizes");
        }
        Map<UID, Integer> oMap = o.getMap();
        int result = 0;
        for (UID id : map.keySet()) {
            result += map.get(id) - oMap.get(id);
        }
        return result;
    }

    public int size() {
        return map.size();
    }

    protected Map<UID, Integer> getMap() {
        return this.map;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof VectorClock))
            return false;
        VectorClock other = (VectorClock) o;
        return map.equals(other.getMap());
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
