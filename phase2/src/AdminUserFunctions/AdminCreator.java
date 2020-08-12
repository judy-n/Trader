package AdminUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;

/**
 * Helps let the initial admin add new admins to the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-11
 */
public class AdminCreator {
    private String currUsername;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <AdminCreator></AdminCreator> with the given admin username, user manager, and notification system.
     *
     * @param currUsername the initial admin's username
     * @param userManager  the system's user manager
     * @param notifSystem  the system's notification manager
     */
    public AdminCreator(String currUsername, UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = currUsername;
        this.userManager = userManager;
        this.notifSystem = notifSystem;
    }

    /**
     * Adds a new admin to the system with the given login credentials
     * and records the admin creation in the activity log.
     *
     * @param newAdminUsername the new admin's username
     * @param newAdminEmail    the new admin's email address
     * @param newAdminPass     the new admin's password
     */
    public void createNewAdmin(String newAdminUsername, String newAdminEmail, String newAdminPass) {
        userManager.createAdminUser(newAdminUsername, newAdminEmail, newAdminPass);
        userManager.getAdminByUsername(newAdminUsername).addObserver(notifSystem);

        /* Record admin creation in activity log */
        userManager.notifyUser(currUsername).recordAdminCreation(newAdminUsername);
    }
}
