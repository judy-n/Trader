package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;

/**
 * Helps show the user their inventory and let them edit it through user input.
 * If the user adds an item to their inventory, it will first be sent to an admin for approval.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-08-11
 */
public class InventoryEditor {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <InventoryEditor></InventoryEditor> with the given normal user,
     * item/user/trade managers, and notification system.
     *
     * @param currUsername the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem the system's notification manager
     */
    public InventoryEditor(String currUsername, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {
        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;
    }

    /**
     * Converts the current user's inventory into an array of string representations and returns it.
     *
     * @return an array containing string representations of the current user's inventory
     */
    public String[] getInventory() {
        // Passing second arg as false means each item's owner username won't be included.
        return itemManager.getItemStringsID(userManager.getNormalUserInventory(currUsername), false);
    }

    /**
     * Converts the current user's pending inventory into an array of string representations and returns it.
     *
     * @return an array containing string representations of the current user's pending inventory
     */
    public String[] getPendingInventory() {
        // Passing second arg as false means each item's owner username won't be included.
        return itemManager.getItemStringsID(userManager.getNormalUserPending(currUsername), false);
    }

    /**
     * Checks the item name and description input by the user.
     *
     * @param itemNameInput        the item name input by the user
     * @param itemDescriptionInput the item description input by the user
     * @return true iff both the name and description are valid
     */
    public boolean validateInput(String itemNameInput, String itemDescriptionInput) {
        return itemNameInput.length() >= 3 && itemNameInput.matches("[\\w]+([\\s][\\w]+)*")
                && itemDescriptionInput.trim().contains(" ");
    }

    /**
     * Check if the item corresponding to the item ID at the given index
     * in the current user's inventory can be removed.
     *
     * @param index the index of the item that the user wishes to remove
     * @return true iff the user is allowed to remove the item from their inventory
     */
    public boolean validateRemoval(int index) {
        /*
         * Allow removal if item is available + not being asked for in a trade request
         * OR if item is in a trade that's been cancelled due to users failing to confirm the transaction
         */
        long selectedItemID = userManager.getNormalUserInventory(currUsername).get(index);
        if (userManager.isRequestedInTrade(currUsername, selectedItemID)) {
            return false;
        } else {
            return itemManager.getItemAvailability(selectedItemID) ||
                    (!itemManager.getItemAvailability(selectedItemID) && tradeManager.getItemInCancelledTrade(selectedItemID));
        }
    }

    /**
     * Removes the item ID at the given index from the current user's inventory and changes the
     * status of the corresponding item accordingly.
     *
     * @param index the index of the item ID being removed from inventory
     */
    public void removeInventory(int index) {
        long selectedItemID = userManager.getNormalUserInventory(currUsername).get(index);
        userManager.removeFromNormalUserInventory(selectedItemID, currUsername);
        itemManager.setItemIsRemoved(selectedItemID);

        // Get rid of the revertible notif associated with the approval of the item being removed
        notifSystem.removeRevertibleNotif(selectedItemID);
    }

    /**
     * Creates a new item using the given name and description and sends it for admin approval.
     *
     * @param itemNameInput        the item name input by the user
     * @param itemDescriptionInput the item description input by the user
     */
    public void addInventory(String itemNameInput, String itemDescriptionInput) {
        long newItemID = itemManager.createItem(itemNameInput, itemDescriptionInput, currUsername);
        userManager.addToNormalUserPending(newItemID, currUsername);
    }
}
