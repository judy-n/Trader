package SystemManagers;

import Entities.User;

import java.io.Serializable;

/**
 * <UserNotificationHelper></UserNotificationHelper> helps create notifications
 * triggered by certain user actions.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-02
 * last modified 2020-08-12
 */
public class UserNotificationHelper implements Serializable {
    private User currUserToNotify;

    /**
     * Sets the current user to notify to the given user.
     *
     * @param newUserToNotify the new current user to notify
     */
    public void setCurrUserToNotify(User newUserToNotify) {
        currUserToNotify = newUserToNotify;
    }

    /**
     * Helps build the argument array for a notification involving two usernames.
     *
     * @param action           the action generating a notification
     * @param usernameNotified the username of the user being notified
     * @param otherParty       the username of the other user involved in the notification
     */
    public void basicUpdate(String action, String usernameNotified, String otherParty) {
        String[] notifArg = {action, usernameNotified, otherParty};
        notifyUser(notifArg);
    }

    /**
     * Helps build the argument array for a notification involving two usernames and an item name.
     *
     * @param action           the action generating a notification
     * @param usernameNotified the username of the user being notified
     * @param otherParty       the username of the other user involved in the notification
     * @param itemName         the name of the item involved in the notification
     */
    public void itemUpdate(String action, String usernameNotified, String otherParty, String itemName) {
        String[] notifArg = {action, usernameNotified, otherParty, itemName};
        notifyUser(notifArg);
    }

    /**
     * Helps build the argument array for a notification involving two usernames, an item name, and the item's ID.
     *
     * @param action           the action generating a notification
     * @param usernameNotified the username of the user being notified
     * @param otherParty       the username of the other user involved in the notification
     * @param itemName         the name of the item involved in the notification
     * @param itemID           the item ID of the item involved in the notification
     */
    public void itemUpdateWithID(String action, String usernameNotified, String otherParty,
                                 String itemName, long itemID) {
        String[] notifArg = {action, usernameNotified, otherParty, itemName, String.valueOf(itemID)};
        notifyUser(notifArg);
    }

    /**
     * Helps build the argument array for a notification regarding a change in threshold values.
     *
     * @param action           the action generating a notification
     * @param usernameNotified the username of the user being notified
     * @param otherParty       the username of the other user involved in the notification
     * @param thresholdTypeInt the number corresponding to the threshold type that was changed
     * @param newValue         the new value of the threshold type that was changed
     */
    public void thresholdUpdate(String action, String usernameNotified, String otherParty,
                                int thresholdTypeInt, int newValue) {
        String thresholdType;
        switch (thresholdTypeInt) {
            case 0:
                thresholdType = "maximum number of weekly trade meetings";
                break;
            case 1:
                thresholdType = "maximum number of edits you can make to a trade's meeting details";
                break;
            case 2:
                thresholdType = "minimum number of items more you must lend than items borrowed";
                break;
            case 3:
                thresholdType = "maximum number of incomplete trades you can have " +
                        "before being at risk of getting frozen by an admin";
                break;
            default:
                thresholdType = "unknown threshold type!";
                break;
        }
        String[] notifArg = {action, usernameNotified, otherParty, thresholdType, String.valueOf(newValue)};
        notifyUser(notifArg);
    }

    /**
     * Helps build the argument array for a notification involving three usernames.
     *
     * @param action           the action generating a notification
     * @param usernameNotified the username of the user being notified
     * @param otherParty       the username of the other user involved in the notification
     * @param thirdParty       the username of a third party user
     */
    public void threeUsernameUpdate(String action, String usernameNotified, String otherParty, String thirdParty) {
        String[] notifArg = {action, usernameNotified, otherParty, thirdParty};
        notifyUser(notifArg);
    }

    /**
     * Helps record the creation of a new admin in the system.
     *
     * @param newUsername the username of the new admin
     */
    public void recordAdminCreation(String newUsername) {
        String[] notifArg = {newUsername};
        notifyUser(notifArg);
    }

    private void notifyUser(String[] notifArg) {
        currUserToNotify.updateUser(notifArg);
    }
}
