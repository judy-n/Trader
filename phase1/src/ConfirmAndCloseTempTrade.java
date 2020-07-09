/**
 * Confirms and closes temporary trades.
 *
 * @author Yiwei Chen
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-09
 */

public class ConfirmAndCloseTempTrade {

    //javadoc properly later
    //confirms the transaction for logged-in user
    //TemporaryTrade automatically closes it if both users have confirmed the second meeting (isComplete = true)
    //if item ID is not 0 and the item still exists then set it's availability to true
    public void confirmAndCloseTempTransaction(String username, TemporaryTrade a, ItemManager im) {

        a.confirmTransaction2(username);
        
        if (a.getIsComplete()) {
            long[] itemIDs = a.getInvolvedItemIDs();
            Item tempItem1 = im.getApprovedItem(itemIDs[0]);
            Item tempItem2 = im.getApprovedItem(itemIDs[1]);

            if (itemIDs[0] != 0 && im.getApprovedItem(itemIDs[0]) != null){
                tempItem1.setAvailability(true);}
            if (itemIDs[1] != 0 && im.getApprovedItem(itemIDs[1]) != null){
                tempItem2.setAvailability(true);}
        }
    }
}
