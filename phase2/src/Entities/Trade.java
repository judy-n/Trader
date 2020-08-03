package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an approved trade between two normal users.
 * <Trade></Trade> stores the usernames of both users in an array which is parallel to an array
 * containing the IDs of the items each user is lending in this trade.
 * Note that an ID of 0 means the associated user is not lending an item (aka a one-way trade).
 * Also note that in the username array, the username at index 0 is the user who accepted the trade request
 * while the username at index 1 is the sender of the trade request.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-03
 */

public abstract class Trade implements Serializable, Comparable<Trade> {
    private final String[] involvedUsernames = new String[2];
    private final long[] involvedItemIDs = new long[2];
    private boolean isComplete;
    private boolean isCancelled;

    private boolean hasAgreedMeeting;
    private int[] numEdits = {0, 0};
    private String lastEditor;
    private LocalDateTime firstMeetingDateTime;
    private String firstMeetingLocation;
    private boolean[] firstTransactionConfirmed = new boolean[2];

    /**
     * Creates a <Trade></Trade> with given username array, item ID array,
     * and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two users involved in this <Trade></Trade>
     * @param itemIDs       an array containing the IDs of the items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this <Trade></Trade>'s meeting
     * @param firstLocation the first location suggested for this <Trade></Trade>'s meeting
     */
    public Trade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        involvedUsernames[0] = usernames[0];
        involvedUsernames[1] = usernames[1];
        involvedItemIDs[0] = itemIDs[0];
        involvedItemIDs[1] = itemIDs[1];

        firstMeetingDateTime = firstDateTime;
        firstMeetingLocation = firstLocation;
        lastEditor = involvedUsernames[0];

        isCancelled = false;
        isComplete = false;
    }

    /**
     * Getter for the usernames of the two users involved in this trade.
     *
     * @return an array containing the usernames of the two users involved in this trade
     */
    public String[] getInvolvedUsernames() {
        return involvedUsernames;
    }

    /**
     * Cancels this trade by setting <isCancelled></isCancelled> to true.
     */
    public void setIsCancelled() {
        isCancelled = true;
    }

    /**
     * Getter for the IDs of the items involved in this trade.
     *
     * @return an array containing the IDs of the items involved in this trade
     */
    public long[] getInvolvedItemIDs() {
        return involvedItemIDs;
    }

    /**
     * Takes in a username and returns whether or not they're a part of this trade.
     *
     * @param username the username whose involvement in this trade is being determined
     * @return true if the given user is involved in this trade, false otherwise
     */
    public boolean isInvolved(String username) {
        return involvedUsernames[0].equals(username) || involvedUsernames[1].equals(username);
    }

    /**
     * Takes in the username of a user who's a part of this trade and returns the ID of the item they lent in this trade.
     *
     * @param username the username of the user whose lent item ID is being retrieved
     * @return the ID of the item the given user lent in this trade
     */
    public long getLentItemID(String username) {
        if (involvedUsernames[0].equals(username)) {
            return involvedItemIDs[0];
        } else {
            return involvedItemIDs[1];
        }
    }

    /**
     * Takes in the username of a user who's part of this trade and return the username of their trade partner.
     *
     * @param username the username of the user who's trade partner is being retrieved
     * @return the username of the given user's trade partner
     */
    public String getOtherUsername(String username) {
        if (involvedUsernames[0].equals(username)) {
            return involvedUsernames[1];
        } else {
            return involvedUsernames[0];
        }
    }

    /**
     * Get whether or not this trade is complete.
     *
     * @return true if this trade is complete, false otherwise
     */
    public boolean getIsComplete() {
        return isComplete;
    }


    /**
     * Get whether or not this trade is cancelled.
     *
     * @return true if this trade is cancelled, false otherwise
     */
    public boolean getIsCancelled() {
        return isCancelled;
    }

    /**
     * Get whether or not this trade has a first meeting that both traders have agreed upon.
     *
     * @return true if this trade has an agreed first meeting, false otherwise
     */
    public boolean getHasAgreedMeeting() {
        return hasAgreedMeeting;
    }

    /**
     * Confirms that both traders have agreed on a first meeting time and place.
     */
    public void confirmAgreedMeeting() {
        hasAgreedMeeting = true;
    }

    /**
     * Getter for this trade's current first meeting date and time.
     *
     * @return a <LocalDateTime></LocalDateTime> object representing this trade's current first meeting date and time
     */
    public LocalDateTime getFirstMeetingDateTime() {
        return firstMeetingDateTime;
    }

    /**
     * Setter for this trade's first meeting date and time.
     *
     * @param dateTime the new date and time for the first meeting
     */
    public void setFirstMeetingDateTime(LocalDateTime dateTime) {
        firstMeetingDateTime = dateTime;
    }

    /**
     * Getter for this trade's current first meeting location.
     *
     * @return a String representing this trade's current first meeting location
     */
    public String getFirstMeetingLocation() {
        return firstMeetingLocation;
    }

    /**
     * Setter for this trade's first meeting date and time.
     *
     * @param location the new location for the first meeting
     */
    public void setFirstMeetingLocation(String location) {
        firstMeetingLocation = location;
    }

    /**
     * Getter for the username of the user who last edited this trade's meeting details.
     *
     * @return the username of the user who last edited this trade's meeting details
     */
    public String getLastEditor() {
        return lastEditor;
    }

    /**
     * Returns whether or not the given user has confirmed this trade's first transaction.
     *
     * @param username the username of the user to query
     * @return true if the given user has confirmed the first transaction, false otherwise
     */
    public boolean getUserFirstTransactionConfirmation(String username) {
        if (username.equals(involvedUsernames[0])) {
            return firstTransactionConfirmed[0];
        } else {
            return firstTransactionConfirmed[1];
        }
    }

    /**
     * Confirms this trade's first transaction for the given user.
     *
     * @param username the username of the user confirming the first transaction
     */
    public void confirmFirstTransaction(String username) {
        if (username.equals(involvedUsernames[0])) {
            firstTransactionConfirmed[0] = true;
        } else {
            firstTransactionConfirmed[1] = true;
        }
    }

    /**
     * Closes this trade's transaction (completes the trade).
     */
    public void closeTransaction() {
        isComplete = true;
    }

    /**
     * Adds one to the number of edits made by the given user.
     * Automatically sets the last editor of this trade's meeting to the given user.
     *
     * @param username the username of the user whose edit count is being incremented
     */
    public void addUserEditCount(String username) {
        if (username.equals(involvedUsernames[0])) {
            numEdits[0]++;
        } else {
            numEdits[1]++;
        }
        lastEditor = username;
    }

    /**
     * Gets the number of edits the given user has made on this trade's meeting.
     *
     * @param username the username of the user to query
     * @return the number of edits the given user has made
     */
    public int getUserEditCount(String username) {
        if (username.equals(involvedUsernames[0])) {
            return numEdits[0];
        } else {
            return numEdits[1];
        }
    }

    /**
     * Getter for final meeting date and time of this trade.
     * Abstract method implemented by subclasses of <Trade></Trade>.
     *
     * @return the final meeting date and time of this trade
     */
    public abstract LocalDateTime getFinalMeetingDateTime();

    /**
     * Returns a String representation of this trade.
     * Abstract method implemented by subclasses of <Trade></Trade>.
     *
     * @param currUsername the username of the user who's currently logged in
     * @return a String representation of this trade
     */
    public abstract String toString(String currUsername);

    /**
     * Allows trades to be compared to each other based on their latest meeting time.
     * Abstract method implemented by subclasses of <Trade></Trade>.
     *
     * @param trade the trade being compared to this trade
     * @return a negative value if this trade comes before the given trade,
     * 0 if exactly the same, and a positive value if it comes after the given trade
     */
    public abstract int compareTo(Trade trade);

}
