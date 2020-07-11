/**
 * Confirms and closes permanent trades.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-11
 */
public class ConfirmAndClosePermTrade {

    //javadoc properly later
    //confirms the transaction for logged-in user
    //PermanentTrade automatically closes it if both users have confirmed the meeting (isComplete = true)
    //if item ID is not 0 and the item still exists then 
    //remove it from its owner's inventory and the ItemManager list + remove from the other user's wishlist
    public void confirmAndClosePermTransaction(String username, PermanentTrade a, ItemManager im, UserManager um) {

        a.confirmTransaction1(username);
        String[] usernames = a.getInvolvedUsernames();

        if (a.getIsComplete()) {
            NormalUser tempUser1 = um.getNormalByUsername(usernames[0]);
            NormalUser tempUser2 = um.getNormalByUsername(usernames[1]);
            assert tempUser1 != null && tempUser2 != null;

            long[] itemIDs = a.getInvolvedItemIDs();
            if (itemIDs[0] != 0) {
                tempUser1.removeInventory(itemIDs[0]);
                im.getApprovedItem(itemIDs[0]).setIsRemoved(true);
                if (tempUser2.getWishlist().contains(itemIDs[0])) {
                    tempUser2.removeWishlist(itemIDs[0]);
                }
            }
            if (itemIDs[1] != 0) {
                tempUser2.removeInventory(itemIDs[1]);
                im.getApprovedItem(itemIDs[1]).setIsRemoved(true);
                if (tempUser1.getWishlist().contains(itemIDs[1])) {
                    tempUser1.removeWishlist(itemIDs[1]);
                }
            }
        }
    }
}
