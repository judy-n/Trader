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
    private ItemManager itemManager;

    public void closeTransaction(Trade a, ItemManager im) {

        itemManager = im;
        
        if (a.getIsComplete()) {
            long[] itemIDs = a.getInvolvedItemIDs();
            Item tempItem1 = itemManager.getApprovedItem(itemIDs[0]);
            Item tempItem2 = itemManager.getApprovedItem(itemIDs[1]);
            assert tempItem1 != null;
            assert tempItem2 != null;
            if (itemIDs[0] != 0){
            tempItem1.setAvailability(true);}
            if (itemIDs[1] != 0){
            tempItem2.setAvailability(true);}
        }
    }
}
