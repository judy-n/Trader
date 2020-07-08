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

public class TemporaryTrade extends Trade {
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

        //test v
        //meetingDateTime2 = firstDateTime;
    }

    public LocalDateTime getMeetingDateTime2() {
        return meetingDateTime2;
    }

    public String getMeetingLocation2() {
        return meetingLocation2;
    }

    //should only be called on completed temporary trades
    @Override
    public LocalDateTime getFinalMeetingDateTime() {
        if (getIsComplete()) {
            return meetingDateTime2;
        }
        return null;
    }

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

    @Override
    public String toString(String currentUsername) {
        return "Temporary trade with " + getOtherUsername(currentUsername) + " - ";
    }

    @Override
    public int compareTo(Trade t) {
        if (t instanceof TemporaryTrade) {
            return meetingDateTime2.compareTo(((TemporaryTrade) t).getMeetingDateTime2());
        } else {
            return meetingDateTime2.compareTo(t.getMeetingDateTime1());
        }
    }
}
