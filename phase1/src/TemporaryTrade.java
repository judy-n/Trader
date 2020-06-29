import java.util.Date;

/**
 * TemporaryTrade.java
 * Represents a TemporaryTrade
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */

public class TemporaryTrade extends Trade {
    public Date endDate;

    /**
     * TemporaryTrade
     * Creates a temporary trade with two users
     * @param u1 user 1
     * @param u2 user 2
     */
    public TemporaryTrade(User u1, User u2) {
        super(u1, u2);
        //endDate =
    }
}
