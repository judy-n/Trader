/**
 * Trade.java
 * Represents a confirmed trade between 2 Users.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */

public class Trade {
    public Boolean isCancelled;
    public Boolean isComplete;
    public User[] trades;

    /**
     * Trade
     * Creates a Trade with two users
     * @param u1 user 1
     * @param u2 user 2
     */
    public Trade(User u1, User u2) {
        trades = new User[]{u1, u2};
    }

}
