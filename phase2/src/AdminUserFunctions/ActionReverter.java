package AdminUserFunctions;

import SystemManagers.ItemManager;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;

import java.util.List;

/**
 * Helps let admins undo a normal user's actions in the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-11
 */
public class ActionReverter {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <ActionReverter></ActionReverter> with the given admin username,
     * item/user managers, and notification system.
     *
     * @param currUsername the username of the admin who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param notifSystem  the system's notification manager
     */
    public ActionReverter(String currUsername, ItemManager itemManager, UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;
    }

    /**
     * Returns an array of string representations of the revertible actions done by normal users.
     *
     * @return the revertible activity log
     */
    public String[] getRevertibleNotifs() {
        return notifSystem.getRevertibleNotifStrings();
    }

    /**
     * Reverts the action at the given index and removes it from the revertible activity log.
     * Notifies the affected user(s) about their action being undone by an admin.
     *
     * @param index the index of the action being reverted
     */
    public void revertAction(int index) {
        String actionType = notifSystem.getUndoType(index);
        if (actionType.equals("TRADE REQUEST")) {

            // Sender of request at index 0, recipient at index 1
            String[] usernamesInvolved = notifSystem.getUndoDetails(index);
            String sender = usernamesInvolved[0];
            String recipient = usernamesInvolved[1];

            // Remove trade request from both users' accounts
            userManager.removeTradeRequestBothUsers(usernamesInvolved);

            /* Notify both users of trade request revert (NotificationSystem handles notif to recipient) */
            userManager.notifyUser(sender).threeUsernameUpdate
                    ("TRADE REQUEST UNSENT", sender, recipient, currUsername);

        } else if (actionType.equals("ITEM APPROVAL")) {

            // Owner of item at index 0, item ID at index 1
            String[] usernameAndItem = notifSystem.getUndoDetails(index);
            String itemOwner = usernameAndItem[0];
            long itemID = Long.parseLong(usernameAndItem[1]);

            // Remove item from its owner's inventory (but not from the system)
            userManager.removeFromNormalUserInventory(itemID, itemOwner);
            itemManager.setItemIsRemoved(itemID);

            // Remove all trade requests involving the item
            List<String[]> involvedUsernames = userManager.removeTradeRequestByItemID(itemOwner, itemID);
            for (String[] usernames : involvedUsernames) {
                /* Notify both users of trade request revert due to item disapproval */
                userManager.notifyUser(usernames[0]).threeUsernameUpdate
                        ("TRADE REQUEST ITEM DISAPPROVAL", usernames[0], usernames[1], currUsername);
            }

            /* Notify the item owner of the item approval being retracted */
            userManager.notifyUser(itemOwner).itemUpdate
                    ("APPROVAL UNDONE", itemOwner, currUsername, itemManager.getItemName(itemID));
        }

        // Remove the selected revertible notif from the revertible activity log
        notifSystem.removeRevertibleNotif(index);
    }
}
