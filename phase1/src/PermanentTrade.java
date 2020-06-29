/**
 * PermanentTrade.java
 * Represents a permanent trade.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */
public class PermanentTrade extends Trade {

    /**
     * PermanentTrade
     * Creates a permanent trade with two users
     * @param u1 user 1
     * @param u2 user 2
     */
    public PermanentTrade(User u1, User u2) {
        super(u1, u2);
    }
}

