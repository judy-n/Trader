package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an approved trade between two normal users.
 * <Trade></Trade> stores the usernames of both users in an array which is parallel to an array
 * containing the IDs of the items each user is lending in this trade.
 * Note that an ID of 0.0 means the associated user is not lending an item (aka a one-way trade).
 * Also note that in the username array, the username at index 0 is the user who accepted the trade request
 * while the username at index 1 is the sender of the trade request.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-12
 */

public abstract class Trade implements Serializable, Comparable<Trade> {
    private final String[] involvedUsernames = new String[2];
    private final long[] involvedItemIDs = new long[2];
    private boolean isComplete;
    private boolean isCancelled;

    private boolean hasAgreedMeeting1;
    private int[] numEdits1 = {0, 0};
    private String lastEditor;
    private LocalDateTime meetingDateTime1;
    private String meetingLocation1;
    private boolean[] transactionConfirmed1 = new boolean[2];

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

        meetingDateTime1 = firstDateTime;
        meetingLocation1 = firstLocation;
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
    public boolean getHasAgreedMeeting1() {
        return hasAgreedMeeting1;
    }

    /**
     * Confirms that both traders have agreed on a first meeting time and place.
     */
    public void confirmAgreedMeeting1() {
        hasAgreedMeeting1 = true;
    }

    /**
     * Getter for this trade's current first meeting date and time.
     *
     * @return a <LocalDateTime></LocalDateTime> object representing this trade's current first meeting date and time
     */
    public LocalDateTime getMeetingDateTime1() {
        return meetingDateTime1;
    }

    /**
     * Setter for this trade's first meeting date and time.
     *
     * @param dateTime the new date and time for the first meeting
     */
    public void setMeetingDateTime1(LocalDateTime dateTime) {
        meetingDateTime1 = dateTime;
    }

    /**
     * Getter for this trade's current first meeting location.
     *
     * @return a String representing this trade's current first meeting location
     */
    public String getMeetingLocation1() {
        return meetingLocation1;
    }

    /**
     * Setter for this trade's first meeting date and time.
     *
     * @param location the new location for the first meeting
     */
    public void setMeetingLocation1(String location) {
        meetingLocation1 = location;
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
    public boolean getUserTransactionConfirmation1(String username) {
        if (username.equals(involvedUsernames[0])) {
            return transactionConfirmed1[0];
        } else {
            return transactionConfirmed1[1];
        }
    }

    /**
     * Confirms this trade's first transaction for the given user.
     *
     * @param username the username of the user confirming the first transaction
     */
    public void confirmTransaction1(String username) {
        if (username.equals(involvedUsernames[0])) {
            transactionConfirmed1[0] = true;
        } else {
            transactionConfirmed1[1] = true;
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
    public void addUserEditCount1(String username) {
        if (username.equals(involvedUsernames[0])) {
            numEdits1[0]++;
        } else {
            numEdits1[1]++;
        }
        lastEditor = username;
    }

    /**
     * Gets the number of edits the given user has made on this trade's meeting.
     *
     * @param username the username of the user to query
     * @return the number of edits the given user has made
     */
    public int getUserEditCount1(String username) {
        if (username.equals(involvedUsernames[0])) {
            return numEdits1[0];
        } else {
            return numEdits1[1];
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
     * @param currentUsername the username of the user who's currently logged in
     * @return a String representation of this trade
     */
    public abstract String toString(String currentUsername);

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
