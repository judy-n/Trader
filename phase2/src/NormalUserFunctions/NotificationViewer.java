package NormalUserFunctions;

import SystemManagers.NotificationSystem;

/**
 * Lets normal users review their notifications and mark them as read.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-11
 * last modified 2020-08-11
 */
public class NotificationViewer {
    private String currUsername;
    private NotificationSystem notifSystem;

    public NotificationViewer(String username, NotificationSystem notifSystem) {
        currUsername = username;
        this.notifSystem = notifSystem;
    }

    /**
     * Returns an array of string representations of the current user's notification list.
     *
     * @return an array of string representations of the current user's notification list
     */
    public String[] getNotifStrings() {
        return notifSystem.getUserNotifStrings(currUsername);
    }

    /**
     * Marks the notification at the given index as read by removing it from the current
     * user's list of notifications.
     *
     * @param index the index of the notification marked as read
     */
    public void markNotifAsRead(int index) {
        notifSystem.removeUserNotif(currUsername, index);
    }
}
