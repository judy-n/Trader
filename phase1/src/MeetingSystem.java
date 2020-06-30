import java.time.LocalDateTime;

/**
 * MeetingSystem.java
 * A meeting system for a trade that allows users to edit/confirm.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */

public class MeetingSystem {
    private final Trade trade;


    public MeetingSystem(Trade trade) {
        this.trade = trade;
    }

    public LocalDateTime getSuggestedDateTime() {
        return trade.getMeetingDateTime();
    }

    public void editDateTimeLocation (LocalDateTime newDateTime, String newLocation, String editorUsername) {
        trade.setMeetingDateTime(newDateTime);
        trade.setMeetingLocation(newLocation);
        trade.addUserEditCount(editorUsername);
    }

    public String getSuggestedLocation() {
        return trade.getMeetingLocation();
    }

    public void confirmMeeting() {
        trade.confirmAgreedMeeting();
    }

    public int getUserEditCount(String username) {
        return trade.getUserEditCount(username);
    }
}
