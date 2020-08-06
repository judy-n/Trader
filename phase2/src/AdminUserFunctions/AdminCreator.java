package AdminUserFunctions;

import SystemManagers.UserManager;

/**
 * Helps let the initial admin add new admins to the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-05
 */
public class AdminCreator {
    private String currUsername;
    private UserManager userManager;

    /**
     * Creates an <AdminCreator></AdminCreator> with the given admin username and user manager.
     *
     * @param currUsername the initial admin's username
     * @param userManager  the system's user manager
     */
    public AdminCreator(String currUsername, UserManager userManager) {
        this.currUsername = currUsername;
        this.userManager = userManager;

        //systemPresenter.adminCreator() for successful admin creation message

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
        /* Record admin creation in activity log */
        // Notify through initial admin (the only observed admin)
        userManager.notifyUser(currUsername).recordAdminCreation(newAdminUsername);
    }
}
