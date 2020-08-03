package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import Entities.NormalUser;
import Entities.PermanentTrade;

/**
 * Confirms and closes permanent trades.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-03
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
            String tempUsername1 = usernames[0];
            String tempUsername2 = usernames[1];
            NormalUser tempUser1 = userManager.getNormalByUsername(usernames[0]);
            NormalUser tempUser2 = userManager.getNormalByUsername(usernames[1]);

            // could also use usermanager.doesUserExist(username), but I won't change that because I'm not sure if  works in the same way
            assert tempUser1 != null && tempUser2 != null;

            long[] itemIDs = permTrade.getInvolvedItemIDs();
            long itemID1 = itemIDs[0];
            long itemID2 = itemIDs[1];

            if (itemID1!= 0) {
                userManager.removeNormalUserinventory(itemID1, tempUsername1);
                tempUser1.removeInventory(itemID1);
                itemManager.getItem(itemID1).setIsRemoved(true);
                if (tempUser2.getWishlist().contains(itemID1)) {
                    tempUser2.removeWishlist(itemID1);
                }
            }
            if (itemID2 != 0) {
                userManager.removeNormalUserinventory(itemID2, tempUsername2);
                itemManager.getItem(itemID2).setIsRemoved(true);
                if (tempUser1.getWishlist().contains(itemID2)) {
                    tempUser1.removeWishlist(itemID2);
                }
            }
        }
    }
}
