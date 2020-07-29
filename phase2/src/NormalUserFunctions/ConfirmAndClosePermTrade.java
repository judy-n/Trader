package NormalUserFunctions;
import SystemManagers.*;
import Entities.*;
import SystemFunctions.*;
/**
 * Confirms and closes permanent trades.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-12
 */
public class ConfirmAndClosePermTrade {

    /**
     * Confirms the transaction in a permanent trade for the logged-in normal user.
     * <PermanentTrade></PermanentTrade> automatically closes it if both users have confirmed the meeting (isComplete = true).
     * If item ID is not 0 then remove the item from its owner's inventory and from the other user's wishlist.
     *
     * @param username  the username of the normal user who's currently logged in
     * @param permTrade the permanent trade transaction being confirmed
     * @param im        the system's item manager
     * @param um        the system's user manager
     */
    public void confirmAndClosePermTransaction(String username, PermanentTrade permTrade, ItemManager im, UserManager um) {

        permTrade.confirmTransaction1(username);
        String[] usernames = permTrade.getInvolvedUsernames();

        if (permTrade.getIsComplete()) {
            NormalUser tempUser1 = um.getNormalByUsername(usernames[0]);
            NormalUser tempUser2 = um.getNormalByUsername(usernames[1]);
            assert tempUser1 != null && tempUser2 != null;

            long[] itemIDs = permTrade.getInvolvedItemIDs();
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
