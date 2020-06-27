/**
 * MeetingSystem.java
 * Represents a MeetingSystem
 * @author Judy Naamani
 * created 2020-06-27
 * last modified 2020-06-27
 */

public class MeetingSystem {
    public String location;
    public String date;
    public Trade trade;
    public User user1;
    public User user2;

    public MeetingSystem(Trade trade) {
        this.trade = trade;
        user1 = trade.trades[0];
        user2 = trade.trades[1];
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
