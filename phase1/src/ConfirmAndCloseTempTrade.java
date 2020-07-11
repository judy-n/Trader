/**
 * Confirms and closes temporary trades.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-10
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

            if (itemIDs[0] != 0){
                Item tempItem1 = im.getApprovedItem(itemIDs[0]);
                tempItem1.setAvailability(true);}
            if (itemIDs[1] != 0){
                Item tempItem2 = im.getApprovedItem(itemIDs[1]);
                tempItem2.setAvailability(true);}
        }
    }
}
