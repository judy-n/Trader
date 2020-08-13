package NormalUserFunctions;

import Entities.PermanentTrade;
import SystemManagers.ItemManager;
import SystemManagers.UserManager;

/**
 * Confirms and closes permanent trades.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-09
 */
public class ConfirmAndClosePermTrade {

    /**
     * Confirms the transaction in a permanent trade for the logged-in normal user.
     * <PermanentTrade></PermanentTrade> automatically closes it if both users have confirmed the meeting (isComplete = true).
     * If item ID is not 0 then remove the item from its owner's inventory and from the other user's wishlist.
     *
     * @param username    the username of the normal user who's currently logged in
     * @param permTrade   the permanent trade transaction being confirmed
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     */
    public void confirmAndClosePermTransaction(String username, PermanentTrade permTrade, ItemManager itemManager, UserManager userManager) {

        permTrade.confirmFirstTransaction(username);
        String[] usernames = permTrade.getInvolvedUsernames();

        if (permTrade.getIsComplete()) {
            String username1 = usernames[0];
            String username2 = usernames[1];

            long[] itemIDs = permTrade.getInvolvedItemIDs();
            long itemID1 = itemIDs[0];
            long itemID2 = itemIDs[1];

            if (itemID1 != 0) {
                userManager.removeFromNormalUserInventory(itemID1, username1);
                itemManager.setItemIsRemoved(itemID1);

                if (userManager.isInNormalUserWishlist(itemID1, username2)) {
                    userManager.removeFromNormalUserWishlist(itemID1, username2);
                }
            }
            if (itemID2 != 0) {
                userManager.removeFromNormalUserInventory(itemID2, username2);
                itemManager.setItemIsRemoved(itemID2);

                if (userManager.isInNormalUserWishlist(itemID2, username1)) {
                    userManager.removeFromNormalUserWishlist(itemID2, username1);
                }
            }
        }
    }
}
