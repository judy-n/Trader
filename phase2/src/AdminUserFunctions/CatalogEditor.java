package AdminUserFunctions;

import SystemManagers.ItemManager;
import SystemManagers.UserManager;

/**
 * Helps show admins all items waiting for approval and let them approve or reject pending items.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-10
 */
public class CatalogEditor {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;

    /**
     * Creates a <CatalogEditor></CatalogEditor> with the given admin username and item/user managers.
     *
     * @param username    the username of the admin who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     */
    public CatalogEditor(String username, ItemManager itemManager, UserManager userManager) {
        this.currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;

        // Header: systemPresenter.catalogEditor() for "These are all the items waiting for approval:"
        // 1. Display items waiting for approval: getPendingItemStrings()
        //      > if empty: systemPresenter.emptyListMessage()
        // 2. Approve or reject items
        //      > approve: pass index into approveItem()
        //      > reject: pass index into rejectItem()
    }

    /**
     * Converts the list of pending items into an array of string representations and returns it.
     *
     * @return an array containing string representations of all items waiting for approval
     */
    public String[] getPendingItemStrings() {
        // Passing second arg as true means each string representation will include the item's owner.
        return itemManager.getItemStrings(itemManager.getPendingItems(), true);
    }

    /**
     * Approves the pending item at the given index in the list of pending items.
     * Notifies the item owner of the approval.
     *
     * @param index the index of the item being approved
     */
    public void approveItem(int index) {
        long pendingItemID = itemManager.getPendingItem(index);
        String itemOwnerUsername = itemManager.getItemOwner(pendingItemID);

        /* Notify item owner of approval */
        userManager.notifyUser(itemOwnerUsername).itemUpdateWithID
                ("ITEM APPROVED", itemOwnerUsername, currUsername,
                        itemManager.getItemName(pendingItemID), pendingItemID);

        itemManager.approveItem(pendingItemID);
        userManager.addToNormalUserInventory(pendingItemID, itemOwnerUsername);
    }

    /**
     * Rejects the pending item at the given index in the list of pending items.
     * Notifies the item owner of the rejection.
     *
     * @param index the index of the item being rejected
     */
    public void rejectItem(int index) {
        long pendingItemID = itemManager.getPendingItem(index);
        String itemOwnerUsername = itemManager.getItemOwner(pendingItemID);

        /* Notify item owner of rejection */
        userManager.notifyUser(itemOwnerUsername).itemUpdate
                ("ITEM REJECTED", itemOwnerUsername, currUsername,
                        itemManager.getItemName(pendingItemID));

        itemManager.rejectItem(pendingItemID);
        userManager.removeFromNormalUserPending(pendingItemID, itemOwnerUsername);
    }
}
