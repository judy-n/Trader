import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an approved trade between 2 Users.
 * Trade stores the usernames of both Users in an array which is parallel to an array
 * containing the IDs of the Items each User is lending in this trade.
 * Note that an ID of 0.0 means the associated User is not lending an Item (aka a one-way trade).
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */

public class Trade implements Serializable {
    private boolean hasAgreedMeeting;
    private boolean isComplete;
    private final String[] involvedUsernames = new String[2];
    private final long[] involvedItemIDs = new long[2];
    private LocalDateTime meetingDateTime;
    private String meetingLocation;
    private boolean[] transactionConfirmed = new boolean[2];
    private int[] numEdits = {0, 0};
    public ItemManager im;
    public UserManager um;

    /**
     * Class constructor.
     * Creates a Trade with given username array, item ID array, and first suggestions for meeting time/date/location.
     * Automatically sets status of all Items being traded to unavailable.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this Trade
     * @param itemIDs       an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this Trade's meeting
     * @param firstLocation the first location suggested for this Trade's meeting
     */
    public Trade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime,
                 String firstLocation, ItemManager im, UserManager um) {
        involvedUsernames[0] = usernames[0];
        involvedUsernames[1] = usernames[1];
        involvedItemIDs[0] = itemIDs[0];
        involvedItemIDs[1] = itemIDs[1];
        meetingDateTime = firstDateTime;
        meetingLocation = firstLocation;
        hasAgreedMeeting = false;
        this.im = im;
        this.um = um;

        Item tempItem1 = im.getApprovedItem(involvedItemIDs[0]);
        Item tempItem2 = im.getApprovedItem(involvedItemIDs[1]);
        if (tempItem1 != null) {
            tempItem1.setAvailability(false);
        }
        if (tempItem2 != null) {
            tempItem2.setAvailability(false);
        }
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
     * Takes in the username of a user and returns the ID of the item they lent in this Trade.
     *
     * @param username the username of the user
     * @return the ID of the item the given user lent in this Trade
     */
    public long getLentItemID (String username) {
        if (involvedUsernames[0].equals(username)) {
            return involvedItemIDs[0];
        } else {
            return involvedItemIDs[1];
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
     * Get whether or not this Trade has a meeting that both Users have agreed upon.
     *
     * @return a boolean representing wwhether or not this Trade has a meeting that both Users have agreed upon
     */
    public boolean getHasAgreedMeeting() {
        return hasAgreedMeeting;
    }

    /**
     * Confirms that both Users have agreed on a meeting time and place.
     */
    public void confirmAgreedMeeting() {
        hasAgreedMeeting = true;
    }

    /**
     * Getter for this Trade's current meeting date and time.
     *
     * @return a LocalDateTime object representing this Trade's current meeting date and time
     */
    public LocalDateTime getMeetingDateTime() {
        return meetingDateTime;
    }

    /**
     * Setter for this Trade's meeting date and time.
     *
     * @param dateTime the new date and time for the meeting
     */
    public void setMeetingDateTime(LocalDateTime dateTime) {
        meetingDateTime = dateTime;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String location) {
        meetingLocation = location;
    }

    public boolean getUserTransactionConfirmation(String username) {
        if (username.equals(involvedUsernames[0])) {
            return transactionConfirmed[0];
        } else {
            return transactionConfirmed[1];
        }
    }

    public void confirmTransaction(String username) {
        if (username.equals(involvedUsernames[0])) {
            transactionConfirmed[0] = true;
        } else {
            transactionConfirmed[1] = true;
        }

        if (transactionConfirmed[0] && transactionConfirmed[1]) {
            isComplete = true;
        }
    }

    public void addUserEditCount(String username) {
        if (username.equals(involvedUsernames[0])) {
            numEdits[0]++;
        } else {
            numEdits[1]++;
        }
    }

    public int getUserEditCount(String username) {
        if (username.equals(involvedUsernames[0])) {
            return numEdits[0];
        } else {
            return numEdits[1];
        }
    }
}
