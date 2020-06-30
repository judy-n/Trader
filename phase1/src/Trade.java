import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Trade.java
 * Represents an approved trade between 2 Users.
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
     * Creates a Trade
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
        assert tempItem1 != null;
        assert tempItem2 != null;
        tempItem1.setAvailability(false);
        tempItem2.setAvailability(false);
    }

    /**
     * Getter for the usernames of the two Users involved in this Trade
     *
     * @return the
     */
    public String[] getInvolvedUsernames() {
        return involvedUsernames;
    }

    public int[] getInvolvedItemIDs() {
        return involvedItemIDs;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public boolean getHasAgreedMeeting() {
        return hasAgreedMeeting;
    }

    public void confirmAgreedMeeting() {
        hasAgreedMeeting = true;
    }

    public LocalDateTime getMeetingDateTime() {
        return meetingDateTime;
    }

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
