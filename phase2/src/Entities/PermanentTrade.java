package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a permanent trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-30
 */
public class PermanentTrade extends Trade implements Serializable, Comparable<Trade> {
    /**
     * Creates a <PermanentTrade></PermanentTrade> with given username array, item ID array,
     * and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this <PermanentTrade></PermanentTrade>
     * @param itemIDs       an array containing the IDs of the items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this <PermanentTrade></PermanentTrade>'s meeting
     * @param firstLocation the first location suggested for this <PermanentTrade></PermanentTrade>'s meeting
     */
    public PermanentTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
    }

    /**
     * Confirms this permanent trade's transaction for the given user.
     * Automatically closes the transaction if both traders have confirmed.
     *
     * @param username the username of the user confirming the transaction
     */
    @Override
    public void confirmFirstTransaction(String username) {

        super.confirmFirstTransaction(username);

        if (getUserFirstTransactionConfirmation(username) &&
                getUserFirstTransactionConfirmation(getOtherUsername(username))) {
            closeTransaction();
        }
    }

    /**
     * Return the scheduled meeting time.
     * Should only be called on a completed permanent trade.
     *
     * @return the scheduled meeting time
     */
    @Override
    public LocalDateTime getFinalMeetingDateTime() {
        if (getIsComplete()) {
            return getFirstMeetingDateTime();
        }
        return null;
    }

    /**
     * Return a String representation of this permanent trade.
     *
     * @param currentUsername the username of the user currently logged in
     * @return the trade type and the given user's trade partner in a string
     */
    @Override
    public String toString(String currentUsername) {
        return "Permanent trade with < " + getOtherUsername(currentUsername) + " > - ";
    }

    /**
     * Allows this permanent trade to be compared to both types of trade base on the date and time of their meetings.
     *
     * @param trade the trade being compared with this permanent trade
     * @return a negative value if this permanent trade comes before the given trade,
     * 0 if exactly the same, and a positive value if it comes after the given trade
     */
    @Override
    public int compareTo(Trade trade) {
        if (trade instanceof TemporaryTrade && ((TemporaryTrade) trade).hasSecondMeeting()) {
            return getFirstMeetingDateTime().compareTo(trade.getFinalMeetingDateTime());
        } else {
            return getFirstMeetingDateTime().compareTo(trade.getFirstMeetingDateTime());
        }
    }
}

