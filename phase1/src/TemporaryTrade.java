import java.time.LocalDateTime;

/**
 * TemporaryTrade.java
 * Represents a temporary trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-02
 */

public class TemporaryTrade extends Trade {
    private LocalDateTime endDateTime;

    /**
     * Class constructor.
     * Creates a TemporaryTrade
     * @param usernames an array containing the usernames of the two Users involved in this TemporaryTrade
     * @param itemIDs an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this TemporaryTrade's meeting
     * @param firstLocation the first location suggested for this TemporaryTrade's meeting
     */
    public TemporaryTrade(String[] usernames, double[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
    }

    @Override
    public void confirmAgreedMeeting() {
        super.confirmAgreedMeeting();
        endDateTime = getMeetingDateTime().plusMonths(1); //wow that's satisfying
    }

    public void closeTransaction() {
        if (getIsComplete()) {
            double[] itemIDs = getInvolvedItemIDs();
            Item tempItem1 = ItemDatabase.getItem(itemIDs[0]);
            Item tempItem2 = ItemDatabase.getItem(itemIDs[1]);
            assert tempItem1 != null;
            assert tempItem2 != null;
            tempItem1.setAvailability(true);
            tempItem2.setAvailability(true);
        }
    }
}
