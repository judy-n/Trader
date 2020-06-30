import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an approved trade between 2 Users.
 * Trade stores the usernames of both Users in an array which is parallel to an array
 * containing the IDs of the Items each User is lending in this trade.
 * Note that an ID of 0 means the associated User is not lending an Item (aka a one-way trade).
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
    private final int[] involvedItemIDs = new int[2];
    private LocalDateTime meetingDateTime;
    private String meetingLocation;
    private boolean[] transactionConfirmed = new boolean[2];
    private int[] numEdits = {0, 0};

    /**
     * Class constructor.
     * Creates a Trade with given username array, item ID array, and first suggestions for meeting time/date/location.
     * Automatically sets status of all Items being traded to unavailable.
     *
     * @param usernames an array containing the usernames of the two Users involved in this Trade
     * @param itemIDs an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this Trade's meeting
     * @param firstLocation the first location suggested for this Trade's meeting
     */
    public Trade(String[] usernames, int[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        involvedUsernames[0] = usernames[0];
        involvedUsernames[1] = usernames[1];
        involvedItemIDs[0] = itemIDs[0];
        involvedItemIDs[1] = itemIDs[1];
        meetingDateTime = firstDateTime;
        meetingLocation = firstLocation;

        hasAgreedMeeting = false;
        Item tempItem1 = ItemDatabase.getItem(involvedItemIDs[0]);
        Item tempItem2 = ItemDatabase.getItem(involvedItemIDs[1]);
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
    public int[] getInvolvedItemIDs() {
        return involvedItemIDs;
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
     *
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
     * @param dateTime the given date and time
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
