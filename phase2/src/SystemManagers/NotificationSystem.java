package SystemManagers;

import Entities.Notification;
import Entities.RevertibleNotification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Stores and manages all <Notification></Notification>s in the system.
 * Observes all <NormalUser></NormalUser>s in order to create notifications based on actions taken by each user.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-08-03
 */
public class NotificationSystem implements Observer {

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

    /**
     * Takes in the username of a normal user and returns a list of all their notifications.
     *
     * @param username the username of the user whose notifications are being retrieved
     * @return a list of all the given user's notifications
     */
    public List<Notification> getUserNotifs(String username) {
        return userToNotifMap.get(username);
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
     * @param arg the String array containing all the necessary info to create a notification of specific type
     */
    @Override
    public void update(Observable user, Object arg) {
        String[] notifArg = (String[]) arg;

        if (notifArg.length == 3) {
            createNotif(notifArg[0], notifArg[1], notifArg[2]);
        } else if (notifArg.length == 4) {
            createNotif(notifArg[0], notifArg[1], notifArg[2], notifArg[3]);
        } else if (notifArg.length == 5) {
            createNotif(notifArg[0], notifArg[1], notifArg[2], notifArg[3], notifArg[4]);
        }
    }

    private void createNotif(String notifType, String usernameNotified, String otherParty) {
        Notification mainNotif;
        Notification activityToRecord = null;
        String mainMessage = "";
        String recordMessage = "";

        switch (notifType) {
            case "FROZEN":
                mainMessage = "Your account was frozen by an admin due to it exceeding the " +
                        "maximum allowed number of incomplete trades.";
                recordMessage = "Admin " + otherParty + " froze " + usernameNotified + "'s account.";
                break;
            case "UNFROZEN":
                mainMessage = "Your request to unfreeze your account was accepted by an admin. " +
                        "Your account is now unfrozen.";
                recordMessage = "Admin " + otherParty + " unfroze " + usernameNotified + "'s account.";
                break;
            case "TRADE REQUEST RECEIVED":
                mainMessage = "You received a trade request from " + otherParty + " .";
                recordMessage = otherParty + " sent a trade request to " + usernameNotified + " .";

                // Trade requests can be undone by an admin.
                activityToRecord = new RevertibleNotification(recordMessage, otherParty, usernameNotified);

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
                userToNotifMap.get(otherParty).add(new Notification(mirrorMessage));
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
        }

        mainNotif = new Notification(mainMessage);
        userToNotifMap.get(usernameNotified).add(mainNotif);

        if (!recordMessage.equals("")) {
            if (activityToRecord == null) {
                activityToRecord = new Notification(recordMessage);
            }
            recordActivity(activityToRecord);
        }
    }

    private void createNotif(String notifType, String usernameNotified, String otherParty, String subjectName) {
        Notification mainNotif;
        Notification activityToRecord;
        String mainMessage = "";
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
                recordMessage = otherParty + " cancelled their trade with " + usernameNotified;
                break;
            case "TRADE REQUEST UNSENT":
                mainMessage = "Your trade request to " + otherParty + " was removed by an admin.";
                recordMessage = "Admin " + subjectName + " undid a trade request sent by " + usernameNotified +
                        " to " + otherParty + " .";

                // Notify the other user as well.
                String mirrorMessage = "A trade request sent to you from " + usernameNotified + " was removed by an admin.";
                userToNotifMap.get(otherParty).add(new Notification(mirrorMessage));
                break;
        }

        mainNotif = new Notification(mainMessage);
        userToNotifMap.get(usernameNotified).add(mainNotif);

        if (!recordMessage.equals("")) {
            activityToRecord = new Notification(recordMessage);
            recordActivity(activityToRecord);
        }
    }

    private void createNotif(String notifType, String usernameNotified, String otherParty,
                            String subjectName, String subjectValue) {
        Notification mainNotif;
        Notification activityToRecord = null;
        String mainMessage = "";
        String recordMessage = "";

        switch (notifType) {
            case "ITEM APPROVED":
                mainMessage = "Your item [" + subjectName + "] was approved by an admin.";
                recordMessage = "Admin " + otherParty + " approved the item [" + subjectName + "] for user " +
                        usernameNotified + " .";

                // Item approval can be undone by an admin.
                activityToRecord = new RevertibleNotification(recordMessage, usernameNotified, subjectValue);
                
                break;
            case "THRESHOLD SINGLE USER":
                mainMessage = "An admin changed your " + subjectName + " to " + subjectValue + ".";
                recordMessage = "Admin " + otherParty + " changed " + usernameNotified + "'s " 
                        + subjectName + " to " + subjectValue + ".";
                break;
            case "THRESHOLD ALL USERS":
                mainMessage = "The default " + subjectName + " has been changed to " + subjectValue + " for all users.";
                recordMessage = "Admin " + otherParty + " changed the default " + subjectName + 
                        " to " + subjectValue + " for all users.";
                break;
        }

        mainNotif = new Notification(mainMessage);
        userToNotifMap.get(usernameNotified).add(mainNotif);
        
        if (!recordMessage.equals("")) {
            if (activityToRecord == null) {
                activityToRecord = new Notification(recordMessage);
            }
            recordActivity(activityToRecord);
        }
    }

    private void recordActivity(Notification activityToRecord) {
        if (activityToRecord instanceof RevertibleNotification) {
            revertibleActivityLog.add((RevertibleNotification) activityToRecord);
        }
        fullActivityLog.add(activityToRecord);
    }

}
