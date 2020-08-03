package SystemManagers;

import Entities.NormalUser;

/**
 * <UserNotificationHelper></UserNotificationHelper> helps create notifications
 * triggered by certain user actions.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-02
 * last modified 2020-08-02
 */
public class UserNotificationHelper extends UserManager {

    public void basicUpdate(String action, String usernameNotified, String otherParty) {
        String[] notifArg = {action, usernameNotified, otherParty};
        notifyUser(usernameNotified, notifArg);
    }

    public void itemUpdate(String action, String usernameNotified, String otherParty, String itemName) {
        String[] notifArg = {action, usernameNotified, otherParty, itemName};
        notifyUser(usernameNotified, notifArg);
    }

    public void itemUpdateWithID(String action, String usernameNotified, String otherParty,
                                 String itemName, long itemID) {
        String[] notifArg = {action, usernameNotified, otherParty, itemName, String.valueOf(itemID)};
        notifyUser(usernameNotified, notifArg);
    }

    public void thresholdUpdate(String action, String usernameNotified, String otherParty,
                                String thresholdType, int newValue) {
        String[] notifArg = {action, usernameNotified, otherParty, thresholdType, String.valueOf(newValue)};
        notifyUser(usernameNotified, notifArg);
    }

    public void extraUsernameUpdate(String action, String usernameNotified, String otherParty, String adminUsername) {
        String[] notifArg = {action, usernameNotified, otherParty, adminUsername};
        notifyUser(usernameNotified, notifArg);
    }

    private void notifyUser(String usernameNotified, String[] notifArg) {
        NormalUser userNotified = getNormalByUsername(usernameNotified);
        userNotified.setChangedNormal();
        userNotified.notifyObservers(notifArg);
    }
}
