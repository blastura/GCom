package se.umu.cs.jsgajn.gcom.communication;

/**
 * A factory class to create different instances of different multicast types
 * from an enumeration MulticastType.
 * 
 * @author dit06ajn, dit06jsg
  */
public class Multicasts {
    private Multicasts() {}; // Prevent instantiation

    private static final MulticastType DEFAULT_ORDERING = MulticastType.BASIC_MULTICAST; 

    /**
     * Returns a new default ordering.
     * @return
     */
    public static Multicast newInstance() {
        return newInstance(DEFAULT_ORDERING);
    }

    /**
     * Returns a new instance of the specified ordering type.
     * 
     * @param type The type of the ordering to create.
     * @return The newly created ordering instance.
     */
    public static Multicast newInstance(MulticastType type) {
        switch (type) {
        case BASIC_MULTICAST:
            return new BasicMulticast();
        case RELIABLE_MULTICAST:
            return new ReliableMulticast();
        default:
            throw new UnsupportedOperationException("OrderingType: " + type 
                    + " is not supported, yet."); 
        }
    }
}