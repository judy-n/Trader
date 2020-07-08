import java.time.LocalDateTime;

/**
 * Represents a permanent trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-07
 */
public class PermanentTrade extends Trade {
    /**
     * PermanentTrade
     * Creates a PermanentTrade with given username array, item ID array, and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this TemporaryTrade
     * @param itemIDs       an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this TemporaryTrade's meeting
     * @param firstLocation the first location suggested for this TemporaryTrade's meeting
     */
    public PermanentTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
    }

    @Override
    public void confirmTransaction1(String username) {

        super.confirmTransaction1(username);

        if (getUserTransactionConfirmation1(username) &&
                getUserTransactionConfirmation1(getOtherUsername(username))) {
            closeTransaction();
        }
    }

    //should only be called on completed permanent trades
    @Override
    public LocalDateTime getFinalMeetingDateTime() {
        if (getIsComplete()) {
            return getMeetingDateTime1();
        }
        return null;
    }

    @Override
    public String toString(String currentUsername) {
        return "Permanent trade with " + getOtherUsername(currentUsername) + " - ";
    }

    @Override
    public int compareTo(Trade t) {
        if (t instanceof TemporaryTrade) {
            return getMeetingDateTime1().compareTo(((TemporaryTrade) t).getMeetingDateTime2());
        } else {
            return getMeetingDateTime1().compareTo(t.getMeetingDateTime1());
        }
    }
}

