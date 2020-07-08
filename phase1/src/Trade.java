import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an approved trade between 2 Users.
 * Trade stores the usernames of both Users in an array which is parallel to an array
 * containing the IDs of the Items each User is lending in this trade.
 * Note that an ID of 0.0 means the associated User is not lending an Item (aka a one-way trade).
 * Also note that in the username array,
 * the username at index 0 is the initiator while the username at index 1 was the recipient of the trade request.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Yiwei Chen
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-07
 */

public abstract class Trade implements Serializable, Comparable<Trade> {
    private final String[] involvedUsernames = new String[2];
    private final long[] involvedItemIDs = new long[2];
    private boolean isComplete;

    private boolean hasAgreedMeeting1;
    private int[] numEdits1 = {0, 0};
    private String lastEditor;
    private LocalDateTime meetingDateTime1;
    private String meetingLocation1;
    private boolean[] transactionConfirmed1 = new boolean[2];

    /**
     * Class constructor.
     * Creates a Trade with given username array, item ID array, and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this Trade
     * @param itemIDs       an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this Trade's meeting
     * @param firstLocation the first location suggested for this Trade's meeting
     */
    public Trade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        involvedUsernames[0] = usernames[0];
        involvedUsernames[1] = usernames[1];
        involvedItemIDs[0] = itemIDs[0];
        involvedItemIDs[1] = itemIDs[1];

        meetingDateTime1 = firstDateTime;
        meetingLocation1 = firstLocation;
        lastEditor = involvedUsernames[1];
        //since the recipient of the trade request always makes the first suggestion

        //test v
        isComplete = true;
    }

    /**
     * Getter for the usernames of the two Users involved in this Trade.
     *
     * @return an array containing the usernames of the two Users involved in this Trade
     */
    public String[] getInvolvedUsernames() {
        return involvedUsernames;
    }

    /**
     * Getter for the IDs of the Items involved in this Trade.
     *
     * @return an array containing the IDs of the Items involved in this Trade
     */
    public long[] getInvolvedItemIDs() {
        return involvedItemIDs;
    }

    /**
     * Takes in a username and returns whether or not they're a part of this Trade.
     *
     * @param username the username whose involvement in this Trade is being determined
     * @return whether or not the given user is part of this Trade
     */
    public boolean isInvolved(String username) {
        return involvedUsernames[0].equals(username) || involvedUsernames[1].equals(username);
    }

    /**
     * Takes in the username of a user who's a part of this Trade and returns the ID of the item they lent in this Trade.
     *
     * @param username the username of the user whose lent item ID is being retrieved
     * @return the ID of the item the given user lent in this Trade
     */
    public long getLentItemID(String username) {
        if (involvedUsernames[0].equals(username)) {
            return involvedItemIDs[0];
        } else {
            return involvedItemIDs[1];
        }
    }

    /**
     * Takes in the username of a user who's part of this Trade and return the username of their trade partner.
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
     * Get whether or not this Trade is complete.
     *
     * @return a boolean representing whether or not this Trade is complete
     */
    public boolean getIsComplete() {
        return isComplete;
    }

    /**
     * Get whether or not this Trade has a first meeting that both traders have agreed upon.
     *
     * @return a boolean representing whether or not this Trade has a first meeting that both traders have agreed upon
     */
    public boolean getHasAgreedMeeting1() {
        return hasAgreedMeeting1;
    }

    /**
     * Confirms that both traders have agreed on a first meeting time and place.
     *
     */
    public void confirmAgreedMeeting1() {
        hasAgreedMeeting1 = true;
    }

    /**
     * Getter for this Trade's current first meeting date and time.
     *
     * @return a LocalDateTime object representing this Trade's current first meeting date and time
     */
    public LocalDateTime getMeetingDateTime1() {
        return meetingDateTime1;
    }

    /**
     * Setter for this Trade's first meeting date and time.
     *
     * @param dateTime the new date and time for the first meeting
     */
    public void setMeetingDateTime1(LocalDateTime dateTime) {
        meetingDateTime1 = dateTime;
    }

    /**
     * Getter for this Trade's current first meeting location.
     *
     * @return a String representing this Trade's current first meeting location
     */
    public String getMeetingLocation1() {
        return meetingLocation1;
    }

    /**
     * Setter for this Trade's first meeting date and time.
     *
     * @param location the new location for the first meeting
     */
    public void setMeetingLocation1(String location) {
        meetingLocation1 = location;
    }

    /**
     * Getter for the username of the user who last edited this Trade's meeting details.
     *
     * @return the username of the user who last edited this Trade's meeting details
     */
    public String getLastEditor() {
        return lastEditor;
    }

    public boolean getUserTransactionConfirmation1(String username) {
        if (username.equals(involvedUsernames[0])) {
            return transactionConfirmed1[0];
        } else {
            return transactionConfirmed1[1];
        }
    }

    public void confirmTransaction1(String username) {
        if (username.equals(involvedUsernames[0])) {
            transactionConfirmed1[0] = true;
        } else {
            transactionConfirmed1[1] = true;
        }
    }

    public void closeTransaction() {
        isComplete = true;
    }

    public void addUserEditCount1(String username) {
        if (username.equals(involvedUsernames[0])) {
            numEdits1[0]++;
        } else {
            numEdits1[1]++;
        }
        lastEditor = username;
    }

    public int getUserEditCount1(String username) {
        if (username.equals(involvedUsernames[0])) {
            return numEdits1[0];
        } else {
            return numEdits1[1];
        }
    }

    public abstract LocalDateTime getFinalMeetingDateTime();

    public abstract String toString(String currentUsername);

    public abstract int compareTo(Trade t);
    //returns -ve if this trade comes before t, 0 if exactly the same, and +ve if it comes after t
}
