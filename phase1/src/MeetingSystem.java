import java.time.LocalDateTime;

/**
 * A meeting system for a Trade that allows users to edit/confirm the time and place of their transaction.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */

public class MeetingSystem {
    private final Trade trade;

    /**
     * Class constructor.
     * Creates a MeetingSystem storing the given Trade.
     *
     * @param trade the Trade to store in this MeetingSystem
     */
    public MeetingSystem(Trade trade) {
        this.trade = trade;
    }

    /**
     * Getter for the last suggested meeting date and time for the Trade stored in this MeetingSystem.
     *
     * @return a LocalDateTime object representing the last suggested meeting date and time
     */
    public LocalDateTime getSuggestedDateTime() {
        return trade.getMeetingDateTime();
    }

    /**
     * Getter for the last suggested meeting location for the Trade stored in this MeetingSystem.
     *
     * @return the last suggested meeting location
     */
    public String getSuggestedLocation() {
        return trade.getMeetingLocation();
    }

    /**
     * Sets the date, time, and location of the trade's meeting to the given values.
     * Then increments the number of edits made by the given user.
     *
     * @param newDateTime the new date and time for the meeting
     * @param newLocation the new location for the meeting
     * @param editorUsername the username of the User making this edit
     */
    public void editDateTimeLocation (LocalDateTime newDateTime, String newLocation, String editorUsername) {
        trade.setMeetingDateTime(newDateTime);
        trade.setMeetingLocation(newLocation);
        trade.addUserEditCount(editorUsername);
    }

    /**
     * Confirms the meeting of the Trade stored in this MeetingSystem.
     *
     */
    public void confirmMeeting() {
        trade.confirmAgreedMeeting();
    }

    /**
     * Getter for the number of times the given user has edited the meeting details.
     *
     * @param username the username of the User whose number of edits is being searched for
     * @return the number of times the user has edited the meeting details
     */
    public int getUserEditCount(String username) {
        return trade.getUserEditCount(username);
    }
}
