package SystemManagers;

import Entities.Notification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class NotificationSystem implements Observer {

    private List<String> activityLog;
    private Map<String, List<Notification>> userNotifMap;

    public NotificationSystem() {
        activityLog = new ArrayList<>();
        userNotifMap = new HashMap<>();
    }

    public List<Notification> getUserNotifs(String username) {
        return userNotifMap.get(username);
    }

    @Override
    public void update(Observable user, Object arg) {

    }

    public void createNotif(String usernameNotified, String otherParty, String notifType) {
        Notification newNotif = new Notification("test");

        switch (notifType) {
            case "TEST":
                break;
        }
        userNotifMap.get(usernameNotified).add(newNotif);
    }

    public void addUser(String username) {
        userNotifMap.put(username, new ArrayList<>());
    }
}
