package AdminUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import Entities.AdminUser;
import SystemFunctions.SystemPresenter;
import SystemFunctions.SignUpSystem;

/**
 * Lets the initial admin create and add a new admin to the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-31
 */
public class AdminCreator {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <AdminCreator></AdminCreator> with the given admin, item/user managers, and notification system.
     * Lets the initial admin create other admins using <SignUpSystem></SignUpSystem>.
     *
     * @param user        the initial admin
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public AdminCreator(AdminUser user, ItemManager itemManager, UserManager userManager, NotificationSystem notifSystem) {
        currentAdmin = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        String username = "";
        String email = "";
        String password = "";

        SystemPresenter systemPresenter = new SystemPresenter();
        new SignUpSystem(userManager).createNewAdmin(username, email, password, notifSystem);
        systemPresenter.adminCreator();
        close();
    }

    private void close() {
        new AdminDashboard(currentAdmin, itemManager, userManager, notifSystem);
    }
}
