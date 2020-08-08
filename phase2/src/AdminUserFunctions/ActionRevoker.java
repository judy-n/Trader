package AdminUserFunctions;

import Entities.RevertibleNotification;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;

import java.util.List;

/**
 * Lets an admin user revoke a normal user's actions in the program
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-07
 */

public class ActionRevoker {
    private String currUsername;
    private UserManager userManager;
    private NotificationSystem notifSystem;
    private List<RevertibleNotification> revertibleNotificationList;

    public ActionRevoker(String currUsername, UserManager userManager, NotificationSystem notifSystem){
        this.currUsername = currUsername;
        this.userManager = userManager;
        this.notifSystem = notifSystem;
    }

}
