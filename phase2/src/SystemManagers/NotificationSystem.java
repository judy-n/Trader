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
 * Observes all <User></User>s in order to create notifications based on actions taken by each user.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-08-01
 */
public class NotificationSystem implements Observer {

    private List<String> fullActivityLog;
    private List<RevertibleNotification> revertibleActivityLog;
    private Map<String, List<Notification>> userNotifMap;

    public NotificationSystem() {
        fullActivityLog = new ArrayList<>();
        revertibleActivityLog = new ArrayList<>();
        userNotifMap = new HashMap<>();
    }

    public List<Notification> getUserNotifs(String username) {
        return userNotifMap.get(username);
    }

    @Override
    public void update(Observable user, Object arg) {

    }

    public void createUserNotif(String usernameNotified, String otherParty, String notifType) {
        Notification newNotif = new Notification("test");

        switch (notifType) {
            case "TEST1":
                break;
            case "TEST2":
                break;
        }
        userNotifMap.get(usernameNotified).add(newNotif);
    }

    public void createItemNotif(String usernameNotified, String otherParty, long itemID, String notifType) {
        Notification newNotif = new Notification("test");

        switch (notifType) {
            case "TEST1":
                break;
            case "TEST2":
                break;
        }
        userNotifMap.get(usernameNotified).add(newNotif);
    }

    public void addUser(String username) {
        userNotifMap.put(username, new ArrayList<>());
    }
}
