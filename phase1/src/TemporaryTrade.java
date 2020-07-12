import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a temporary trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-07
 */

public class TemporaryTrade extends Trade implements Serializable {
    private LocalDateTime meetingDateTime2;
    private String meetingLocation2;
    private boolean[] transactionConfirmed2 = new boolean[2];
    private final int TIME_LIMIT = 30;

    /**
     * Class constructor.
     * Creates a TemporaryTrade with given username array, item ID array, and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this TemporaryTrade
     * @param itemIDs       an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this TemporaryTrade's meeting
     * @param firstLocation the first location suggested for this TemporaryTrade's meeting
     */
    public TemporaryTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
        meetingLocation2 = "";

        //test v
        //meetingDateTime2 = firstDateTime;

    }

    public LocalDateTime getMeetingDateTime2() {
        return meetingDateTime2;
    }

    /**
     * Getter for the meeting location of the second transaction
     * @return the second meeting time
     */
    public String getMeetingLocation2() {
        return meetingLocation2;
    }

    /**
     * Getter for the meeting time of the second transaction
     * @return The second meeting time
     */
    //should only be called on temp trades with a second meeting in place
    @Override
    public LocalDateTime getFinalMeetingDateTime() {
        return meetingDateTime2;
    }

    /**
     * Confirms the second transaction for a user
     * @param username the user's username
     */
    @Override
    public void confirmTransaction1(String username) {

        super.confirmTransaction1(username);

        if (getUserTransactionConfirmation1(username) &&
                getUserTransactionConfirmation1(getOtherUsername(username))) {

            meetingDateTime2 = getMeetingDateTime1().plusDays(TIME_LIMIT);
            //second meeting automatically set to 30 days after the first

            meetingLocation2 = getMeetingLocation1();
        }
    }

    /**
     * Confirms second transaction for a user
     * @param username the user's username
     */
    public void confirmTransaction2(String username) {
        if (username.equals(getInvolvedUsernames()[0])) {
            transactionConfirmed2[0] = true;
        } else {
            transactionConfirmed2[1] = true;
        }

        if (transactionConfirmed2[0] && transactionConfirmed2[1]) {
            closeTransaction();
        }
    }


    /**
     * Return true iff user has confirmed the meeting
     * @param username the user's username
     * @return boolean true iff confirmed
     */
    public boolean getUserTransactionConfirmation2(String username) {
        if (username.equals(getInvolvedUsernames()[0])) {
            return transactionConfirmed2[0];
        } else {
            return transactionConfirmed2[1];
        }
    }

    /**
     * Return whether or not second meeting time exists
     * @return true iff the second meeting time exists
     */
    public boolean hasSecondMeeting() {
        return meetingDateTime2 != null;
    }

    /**
     * Return string representation of the temporary trade, so that the current user can confirm or reject it
     * @param currentUsername the current user that is confirming the trade
     * @return String representation of the temporary trade
     */
    @Override
    public String toString(String currentUsername) {
        return "Temporary trade with < " + getOtherUsername(currentUsername) + " > - ";
    }

    @Override
    public int compareTo(Trade t) {
        if (t instanceof TemporaryTrade) {
            if (((TemporaryTrade) t).hasSecondMeeting() && this.hasSecondMeeting()) {
                return meetingDateTime2.compareTo(t.getFinalMeetingDateTime());
            } else if (((TemporaryTrade) t).hasSecondMeeting()) {
                return getMeetingDateTime1().compareTo(t.getFinalMeetingDateTime());
            } else if (this.hasSecondMeeting()) {
                return meetingDateTime2.compareTo(t.getMeetingDateTime1());
            } else {
                return getMeetingDateTime1().compareTo(t.getMeetingDateTime1());
            }
        } else {
            if (this.hasSecondMeeting()) {
                return meetingDateTime2.compareTo(t.getMeetingDateTime1());
            } else {
                return getMeetingDateTime1().compareTo(t.getMeetingDateTime1());
            }
        }
    }
}
