/**
 * Trade.java
 * Represents a Trade between 2 Users
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-26
 */

public class Trade {
    public Boolean isCancelled;
    public Boolean isComplete;
    public User[] trades;

    public Trade (User u1, User u2){
        trades = new User [] {u1, u2};
    }

}
