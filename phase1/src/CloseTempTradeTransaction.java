/**
 * Use Case of Temporary Trade; closes it.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Yiwei Chen
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-06
 */

public class CloseTempTradeTransaction {

    public void closeTransaction(Trade a) {
        if (a.getIsComplete()) {
            long[] itemIDs = a.getInvolvedItemIDs();
            Item tempItem1 = a.im.getApprovedItem(itemIDs[0]);
            Item tempItem2 = a.im.getApprovedItem(itemIDs[1]);
            assert tempItem1 != null;
            assert tempItem2 != null;
            tempItem1.setAvailability(true);
            tempItem2.setAvailability(true);
        }
    }
}