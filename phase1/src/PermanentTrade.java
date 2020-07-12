import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a permanent trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-12
 */
public class PermanentTrade extends Trade implements Serializable {
    /**
     * PermanentTrade
     * Creates a PermanentTrade with given username array, item ID array, and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this TemporaryTrade
     * @param itemIDs       an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this TemporaryTrade's meeting
     * @param firstLocation the first location suggested for this TemporaryTrade's meeting
     */
    public PermanentTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
    }

    /**
     * Confirms transaction for a user
     * @param username the user's username
     */
    @Override
    public void confirmTransaction1(String username) {

        super.confirmTransaction1(username);

        if (getUserTransactionConfirmation1(username) &&
                getUserTransactionConfirmation1(getOtherUsername(username))) {
            closeTransaction();
        }
    }

    /**
     * Return the time the meeting has been chosen to take place
     * @return The time the meeting takes place
     */
    //should only be called on completed permanent trades
    @Override
    public LocalDateTime getFinalMeetingDateTime() {
        if (getIsComplete()) {
            return getMeetingDateTime1();
        }
        return null;
    }

    /**
     * Return string representation of the permanent trade
     * @param currentUsername the username of the current editor of the trade
     * @return a string representation of the trade
     */
    @Override
    public String toString(String currentUsername) {
        return "Permanent trade with < " + getOtherUsername(currentUsername) + " > - ";
    }
    
    @Override
    public int compareTo(Trade t) {
        if (t instanceof TemporaryTrade && ((TemporaryTrade) t).hasSecondMeeting()) {
            return getMeetingDateTime1().compareTo(t.getFinalMeetingDateTime());
        } else {
            return getMeetingDateTime1().compareTo(t.getMeetingDateTime1());
        }
    }
}

