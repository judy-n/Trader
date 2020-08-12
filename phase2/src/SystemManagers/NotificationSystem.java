package SystemManagers;

import Entities.Notification;
import Entities.RevertibleNotification;
import SystemFunctions.DateTimeHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Stores and manages all <Notification></Notification>s in the system.
 * Observes all <NormalUser></NormalUser>s and the initial admin in order to create notifications
 * based on actions taken by each user.
 * Notifications are stored in chronological order from most recently generated to oldest.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-08-11
 */
public class NotificationSystem extends Manager implements Observer, Serializable {

    // Record of important activities that generated a notification
    private List<Notification> fullActivityLog;

    // List of actions that can be undone by an admin
    private List<RevertibleNotification> revertibleActivityLog;

    // Stores notifications for all normal users (admins don't have a list of their own)
    private Map<String, List<Notification>> userToNotifMap;

    /**
     * Creates a <NotificationSystem></NotificationSystem>.
     * Initializes activity logs and user notification map as empty.
     */
    public NotificationSystem() {
        fullActivityLog = new ArrayList<>();
        revertibleActivityLog = new ArrayList<>();
        userToNotifMap = new HashMap<>();
    }

    private String getNotifString(Notification notifToConvert) {
        DateTimeHandler dateTimeHandler = new DateTimeHandler();
        String dateTimeStr = dateTimeHandler.getDateTimeString(notifToConvert.getDateTime());
        return String.format("%1$s  %2$s", dateTimeStr, notifToConvert.getMessage());
    }

    /**
     * Takes in the username of a normal user and returns an array of strings representing
     * all their notifications, from most recent to oldest.
     *
     * @param username the username of the user whose notifications are being retrieved
     * @return an array of string representations of all the given user's notifications
     */
    public String[] getUserNotifStrings(String username) {
        List<String> notifStrings = new ArrayList<>();

        for (Notification n : userToNotifMap.get(username)) {
            notifStrings.add(getNotifString(n));
        }
        return notifStrings.toArray(new String[0]);
    }

    /**
     * Takes in the username of a normal user and the index of one of the notifications in
     * their list, then removes that notification (marked as read by the user).
     *
     * @param username the username of the user who marked a notification as read
     * @param index    the index of the notification being removed
     */
    public void removeUserNotif(String username, int index) {
        userToNotifMap.get(username).remove(index);
    }

    /**
     * Returns an array of string representations of the list of revertible notifications.
     *
     * @return an array of string representations of the list of revertible notifications
     */
    public String[] getRevertibleNotifStrings() {
        List<String> revertibleNotifStrings = new ArrayList<>();
        for (RevertibleNotification rn : revertibleActivityLog) {
            revertibleNotifStrings.add(getNotifString(rn));
        }
        return revertibleNotifStrings.toArray(new String[0]);
    }

    /**
     * Takes in the index of the revertible notification and returns an array combining the username
     * of the user who will be affected by the revert and the subject of the activity being reverted.
     *
     * @param index the index of the revertible notification
     * @return an array combining the affected username and subject of the activity being reverted
     */
    public String[] getUndoDetails(int index) {
        return new String[]{revertibleActivityLog.get(index).getRevertedUsername(),
                revertibleActivityLog.get(index).getSubject()};
    }

    /**
     * Takes in the index of the revertible notification and returns its type.
     *
     * @param index the index of the revertible notification
     * @return the revertible notification type
     */
    public String getUndoType(int index) {
        return revertibleActivityLog.get(index).getType();
    }

    /**
     * Removes the revertible notification at the given index.
     *
     * @param index the index of the revertible notification being removed
     */
    public void removeRevertibleNotif(int index) {
        revertibleActivityLog.remove(index);
    }

    /**
     * Removes the revertible notification of type "ITEM APPROVAL" involving the given item ID.
     *
     * @param itemID the ID of the item involved in the revertible notification being removed
     */
    public void removeRevertibleNotif(long itemID) {
        revertibleActivityLog.removeIf(rn -> rn.getType().equals("ITEM APPROVAL") &&
                rn.getSubject().equals(String.valueOf(itemID)));
    }

    /**
     * Returns an array of string representations of the full activity log.
     *
     * @return an array of string representations of the full activity log
     */
    public String[] getFullActivityLogStrings() {
        List<String> activityLogStrings = new ArrayList<>();

        for (Notification n : fullActivityLog) {
            activityLogStrings.add(getNotifString(n));
        }
        return activityLogStrings.toArray(new String[0]);
    }

    /**
     * Adds a normal user's username to the user notification map.
     * Called when a normal user creates their account.
     *
     * @param username the username being added to the notification map
     */
    public void addUser(String username) {
        userToNotifMap.put(username, new ArrayList<>());
    }

    /**
     * Updates this notification system with a new notification.
     * Called when a change is observed in an instance of <NormalUser></NormalUser>.
     *
     * @param user the user object in which a change is observed
     * @param arg  the String array containing all the necessary info to create a notification of specific type
     */
    @Override
    public void update(Observable user, Object arg) {
        String[] notifArg = (String[]) arg;
        String usernameNotified;
        String mainMessage = "";

        if (notifArg.length == 1) {
            usernameNotified = notifArg[0];
        } else {
            usernameNotified = notifArg[1];
        }

        if (notifArg.length == 1) {
            recordAdminCreation(notifArg[0]);
        } else if (notifArg.length == 3) {
            mainMessage = createNotif(notifArg[0], notifArg[1], notifArg[2]);
        } else if (notifArg.length == 4) {
            mainMessage = createNotif(notifArg[0], notifArg[1], notifArg[2], notifArg[3]);
        } else if (notifArg.length == 5) {
            mainMessage = createNotif(notifArg[0], notifArg[1], notifArg[2], notifArg[3], notifArg[4]);
        } else {
            mainMessage = "Unknown notification type! :(";
        }

        if (!mainMessage.equals("")) {
            Notification mainNotif = new Notification(mainMessage);

            // TODO: delete this code after notifs can be displayed
            System.out.println(getNotifString(mainNotif));
            System.out.println("    notif for user: " + usernameNotified);


            userToNotifMap.get(usernameNotified).add(0, mainNotif);
        }
    }

    private void recordAdminCreation(String newUsername) {
        recordActivity(null, "Initial admin created new admin " + newUsername + " .");
    }

    private String createNotif(String notifType, String usernameNotified, String otherParty) {
        Notification activityToRecord = null;
        String mainMessage;
        String recordMessage = "";

        switch (notifType) {
            case "FROZEN":
                mainMessage = "Your account was frozen by an admin due to it exceeding the " +
                        "maximum allowed number of incomplete trades.";
                recordMessage = "Admin " + otherParty + " froze " + usernameNotified + "'s account.";
                break;
            case "UNFROZEN":
                mainMessage = "Your request to unfreeze your account was accepted by an admin. " +
                        "Your account is now unfrozen and your incomplete trade count has been reset to zero.";
                recordMessage = "Admin " + otherParty + " unfroze " + usernameNotified + "'s account.";
                break;
            case "TRADE REQUEST RECEIVED":
                mainMessage = "You received a trade request from " + otherParty + " .";
                recordMessage = otherParty + " sent a trade request to " + usernameNotified + " .";

                // Trade requests can be undone by an admin.
                activityToRecord = new RevertibleNotification
                        (recordMessage, otherParty, usernameNotified, "TRADE REQUEST");

                break;
            case "NEW SUGGESTION":
                mainMessage = "A new trade meeting suggestion has been made by " + otherParty + " .";
                // no need to record this
                break;
            case "MEETING AGREED":
                mainMessage = otherParty + " agreed to the trade meeting you suggested!";
                // no need to record this
                break;
            case "CONFIRM BEFORE":
                mainMessage = otherParty + " has confirmed your transaction with them! " +
                        "Please remember to confirm on your end within 24 hours of the transaction's scheduled time.";
                // no need to record this
                break;
            case "CONFIRM PERM TRADE AFTER":
                mainMessage = otherParty + " has also confirmed your transaction with them! This trade is now closed. " +
                        "The item you lent in this permanent trade has automatically been removed from your inventory, " +
                        "and if the other user's item was in your wishlist it has been removed.";
                recordMessage = "Permanent trade between users " + usernameNotified + " and " + otherParty + " completed.";
                break;
            case "CONFIRM TEMP TRADE FIRST TRANSACTION AFTER":
                mainMessage = otherParty + " has also confirmed your transaction with them! " +
                        "The second meeting for this temporary trade has been set to " +
                        "exactly 30 days from the first meeting (same time, same place).";
                // no need to record this
                break;
            case "CONFIRM TEMP TRADE SECOND TRANSACTION AFTER":
                mainMessage = otherParty + " has also confirmed your transaction with them! This trade is now closed. " +
                        "The item you lent is now available for trade again.";
                recordMessage = "Temporary trade between users " + usernameNotified + " and " + otherParty + " completed.";
                break;
            case "INCOMPLETE TRADE":
                mainMessage = "You and your trade partner " + otherParty + " failed to both confirm a transaction within " +
                        "24 hours of its scheduled time. Your trade with them has been marked as incomplete.";
                recordMessage = "Incomplete trade between users " + usernameNotified + " and " + otherParty + " .";

                // Notify the other user as well.
                String mirrorMessage = "You and your trade partner " + usernameNotified + " failed to both confirm a transaction " +
                        "within 24 hours of its scheduled time. Your trade with them has been marked as incomplete.";
                userToNotifMap.get(otherParty).add(0, new Notification(mirrorMessage));
                break;
            case "FREEZE WARNING":
                mainMessage = "You've exceeded the maximum allowed number of incomplete trades. " +
                        "Your account is now at risk of being frozen by an admin.";
                recordMessage = "User " + usernameNotified + " exceeded their maximum allowed number of incomplete trades.";
                break;
            case "ON VACATION":
                mainMessage = "Status changed to: on vacation. " +
                        "Your account is now invisible to other users and functionality is limited.";
                recordMessage = "User " + usernameNotified + " is now on vacation.";
                break;
            case "OFF VACATION":
                mainMessage = "Welcome back! Your account has now regained full functionality.";
                recordMessage = "User " + usernameNotified + " is back from vacation.";
                break;
            default:
                mainMessage = "Unknown notification type! :(";
                break;
        }

        if (!recordMessage.equals("")) {
            recordActivity(activityToRecord, recordMessage);
        }
        return mainMessage;
    }

    private String createNotif(String notifType, String usernameNotified, String otherParty, String subjectName) {
        Notification activityToRecord = null;
        String mainMessage;
        String recordMessage = "";

        switch (notifType) {
            case "ITEM REJECTED":
                mainMessage = "Your item [" + subjectName + "] was rejected by an admin.";
                recordMessage = "Admin " + otherParty + " rejected the item [" + subjectName + "] for user " +
                        usernameNotified + " .";
                break;
            case "APPROVAL UNDONE":
                mainMessage = "An admin retracted the approval for your item [" + subjectName + "].";
                recordMessage = "Admin " + otherParty + " undid the approval of item [" + subjectName + "] " +
                        "for user " + usernameNotified + " .";
                break;
            case "TRADE REQUEST ACCEPTED":
                mainMessage = otherParty + " accepted your trade request for [" + subjectName + "]! " +
                        "Check your ongoing trades to see the meeting details they suggested.";
                recordMessage = "Trade initiated between users " + usernameNotified + " and " + otherParty + " .";
                break;
            case "TRADE REQUEST REJECTED":
                mainMessage = otherParty + " rejected your trade request for [" + subjectName + "].";
                // no need to record
                break;
            case "TRADE CANCELLED":
                mainMessage = otherParty + " has cancelled their trade with you for [" + subjectName + "].";
                recordMessage = otherParty + " cancelled their trade with " + usernameNotified + " .";
                break;
            case "TRADE REQUEST UNSENT":
                mainMessage = "Your trade request to " + otherParty + " was removed by an admin.";
                recordMessage = "Admin " + subjectName + " undid a trade request sent by " + usernameNotified +
                        " to " + otherParty + " .";

                // Notify the other user as well.
                String mirrorMessage = "A trade request sent to you from " + usernameNotified + " was removed by an admin.";
                userToNotifMap.get(otherParty).add(0, new Notification(mirrorMessage));
                break;
            default:
                mainMessage = "Unknown notification type! :(";
                break;
        }

        if (!recordMessage.equals("")) {
            recordActivity(activityToRecord, recordMessage);
        }
        return mainMessage;
    }

    private String createNotif(String notifType, String usernameNotified, String otherParty,
                               String subjectName, String subjectValue) {
        Notification activityToRecord = null;
        String mainMessage;
        String recordMessage = "";

        switch (notifType) {
            case "ITEM APPROVED":
                mainMessage = "Your item [" + subjectName + "] was approved by an admin.";
                recordMessage = "Admin " + otherParty + " approved the item [" + subjectName + "] for user " +
                        usernameNotified + " .";

                // Item approval can be undone by an admin.
                activityToRecord = new RevertibleNotification
                        (recordMessage, usernameNotified, subjectValue, "ITEM APPROVAL");

                break;
            case "THRESHOLD ALL USERS":
                mainMessage = "The default " + subjectName + " has been changed to " + subjectValue + " for all users.";
                recordMessage = "Admin " + otherParty + " changed the default " + subjectName +
                        " to " + subjectValue + " for all users.";
                break;
            default:
                mainMessage = "Unknown notification type! :(";
                break;
        }

        if (!recordMessage.equals("")) {
            recordActivity(activityToRecord, recordMessage);
        }
        return mainMessage;
    }

    private void recordActivity(Notification activityToRecord, String recordMessage) {

        if (activityToRecord instanceof RevertibleNotification) {
            revertibleActivityLog.add(0, (RevertibleNotification) activityToRecord);
        } else {
            activityToRecord = new Notification(recordMessage);
        }
        fullActivityLog.add(0, activityToRecord);
    }

}
