package se.umu.cs.jsgajn.gcom.ordering;


/**
 * Singleton factory class to create instances of orderings.
 * 
 * @author dit06ajn, dit06jsg
  */
public class Orderings {
    private Orderings() {}; // Prevent instantiation

    private static final OrderingType DEFAULT_ORDERING = OrderingType.FIFO; 

    public static Ordering newInstance() {
        return newInstance(DEFAULT_ORDERING);
    }

    /**
     * Create and return a new instance of the specified type.
     * 
     * @param type The type of the ordering to create.
     * @return The newly created instance of the specified ordering.
     */
    public static Ordering newInstance(OrderingType type) {
        switch (type) {
        case FIFO:
            return new FIFOOrdering();
        case TOTAL_ORDER:
            return new TotalOrdering();
        case NO_ORDERING:
            return new NoOrdering();
        case CASUAL_ORDERING:
            return new CasualOrdering();
        case CASUALTOTAL_ORDERING:
            return new CasualTotalOrdering();
        default:
            throw new UnsupportedOperationException("OrderingType: " + type 
                    + " is not supported, yet."); 
        }
    }
}
