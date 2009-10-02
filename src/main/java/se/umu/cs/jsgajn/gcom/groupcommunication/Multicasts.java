package se.umu.cs.jsgajn.gcom.groupcommunication;

public class Multicasts {
    private Multicasts() {}; // Prevent instantiation

    private static final MulticastType DEFAULT_ORDERING = MulticastType.BASIC_MULTICAST; 

    public static Multicast newInstance() {
        return newInstance(DEFAULT_ORDERING);
    }

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