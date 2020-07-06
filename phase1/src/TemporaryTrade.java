import java.time.LocalDateTime;

/**
 * Represents a temporary trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-06
 */

public class TemporaryTrade extends Trade {
    private Object[] endDateTime;

    /**
     * Class constructor.
     * Creates a TemporaryTrade.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this TemporaryTrade
     * @param itemIDs       an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this TemporaryTrade's meeting
     * @param firstLocation the first location suggested for this TemporaryTrade's meeting
     */
    public TemporaryTrade(String[] usernames, long[] itemIDs,
                          LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
    }

    // might use getEndDateTime in CloseTempTradeTransaction
    public Object[] getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Object dueDateItem1, Object dueDateItem2){
        endDateTime = new Object[]{dueDateItem1, dueDateItem2};
    }

    //Going to have to change the confirmAgreedMeeting() below - Yiwei

    //@Override
    //public void confirmAgreedMeeting() {
    //    super.confirmAgreedMeeting();
    //    endDateTime = getMeetingDateTime().plusDays(31); //wow that's satisfying
    //}

    //This was put in a use case class since Temporary Trade is an entity
    //public void closeTransaction() {
    //    if (getIsComplete()) {
    //        long[] itemIDs = getInvolvedItemIDs();
    //        Item tempItem1 = im.getApprovedItem(itemIDs[0]);
    //        Item tempItem2 = im.getApprovedItem(itemIDs[1]);
    //        assert tempItem1 != null;
    //        assert tempItem2 != null;
    //        tempItem1.setAvailability(true);
    //        tempItem2.setAvailability(true);
    //    }
    //}
}
