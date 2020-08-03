package AdminUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemFunctions.SystemPresenter;
import SystemFunctions.SignUpSystem;

/**
 * Lets the initial admin create and add a new admin to the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-03
 */
public class AdminCreator {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <AdminCreator></AdminCreator> with the given admin, item/user managers, and notification system.
     * Lets the initial admin create other admins using <SignUpSystem></SignUpSystem>.
     *
     * @param currUsername the initial admin's username
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public AdminCreator(String currUsername, ItemManager itemManager, UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        String newUsername = "";
        String email = "";
        String password = "";

        SystemPresenter systemPresenter = new SystemPresenter();
        new SignUpSystem(userManager).createNewAdmin(newUsername, email, password);
        systemPresenter.adminCreator();
        close();
    }

    private void close() {
        new AdminDashboard(currUsername, itemManager, userManager, notifSystem);
    }
}
