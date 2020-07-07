import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Arrays;

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
    private LocalDate[] endDateTime;
//    private String status;

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
//        this.status = "Ok";
    }

    // might use getEndDateTime in CloseTempTradeTransaction
    public LocalDate[] getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDate dueDateItem1, LocalDate dueDateItem2){
        endDateTime = new LocalDate[]{dueDateItem1, dueDateItem2};
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void updateStatus(){
//        LocalDate Today = LocalDate.now();
//        if (endDateTime[0] != null && Today.compareTo(endDateTime[0]) > 0){
//            this.status = "Overdue";
//        }
//        if (endDateTime[1] != null && Today.compareTo(endDateTime[1]) > 0){
//            this.status = "Overdue";
//        }
//    }

    //Going to have to change the confirmAgreedMeeting() below - Yiwei

//    @Override
//    public void confirmAgreedMeeting() {
//        super.confirmAgreedMeeting();
//        endDateTime[0] = getMeetingDateTime().toLocalDate().plusDays(31); //wow that's satisfying
//        endDateTime[1] = getMeetingDateTime().toLocalDate().plusDays(31);
//    }

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

    @Override
    public String toString(String currentUsername) {
        return "Temporary trade with " + getOtherUsername(currentUsername) + " - ";
    }
}
