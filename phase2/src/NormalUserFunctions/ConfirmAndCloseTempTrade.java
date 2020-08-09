package NormalUserFunctions;

import SystemManagers.ItemManager;
import Entities.TemporaryTrade;

/**
 * Confirms and closes temporary trades.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-31
 */
public class ConfirmAndCloseTempTrade {

    /**
     * Confirms the final transaction in a temporary trade for the logged-in normal user.
     * <TemporaryTrade></TemporaryTrade> automatically closes it if both users have confirmed the second meeting (isComplete = true)
     * If item ID is not 0 then set the item's availability to true.
     *
     * @param username    the username of the normal user who's currently logged in
     * @param tempTrade   the temporary trade transaction being confirmed
     * @param itemManager the system's item manager
     */
    public void confirmAndCloseTempTransaction(String username, TemporaryTrade tempTrade, ItemManager itemManager) {

        tempTrade.confirmSecondTransaction(username);

        if (tempTrade.getIsComplete()) {
            long[] itemIDs = tempTrade.getInvolvedItemIDs();

            if (itemIDs[0] != 0) {
                itemManager.setItemAvailability(itemIDs[0], true);
            }
            if (itemIDs[1] != 0) {
                itemManager.setItemAvailability(itemIDs[1], true);
            }
        }
    }
}
