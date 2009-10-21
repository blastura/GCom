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
        case TOTAL_ORDER:
            return new Total();
        case NO_ORDERING:
            return new NoOrdering();
        case CASUAL_ORDERING:
            return new CasualOrdering();
        case CASUALTOTAL_ORDERING:
            return new CasualTotal();
        default:
            throw new UnsupportedOperationException("OrderingType: " + type 
                    + " is not supported, yet."); 
        }
    }
}
