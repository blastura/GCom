package se.umu.cs.jsgajn.gcom.messageordering;


public class Orderings {
    private Orderings() {}; // Prevent instantiation

    private static final OrderingType DEFAULT_ORDERING = OrderingType.FIFO; 

    public static Ordering newInstance() {
        return newInstance(DEFAULT_ORDERING);
    }

    public static Ordering newInstance(OrderingType type) {
        switch (type) {
        case FIFO:
            return new FIFO();
        default:
            throw new UnsupportedOperationException("OrderingType: " + type 
                    + " is not supported, yet."); 
        }
    }
}