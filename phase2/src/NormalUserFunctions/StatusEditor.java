package NormalUserFunctions;
import SystemManagers.UserManager;

/**
 * Lets a normal user change vacation status.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-03
 * last modified 2020-08-04
 */
public class StatusEditor {
    private UserManager userManager;
    private String currUsername;

    /**
     * Allows change to the normal user's vacation status with given notification
     * system and item/user/trade managers.
     *
     * @param username     the username of the normal user who is currently logged in
     * @param userManager  the system's user manager
     */
    public StatusEditor(String username, UserManager userManager) {
        currUsername = username;
        this.userManager = userManager;
        switchVacationStatus();
    }

    /* Switches the vacation status of the normal user. */
    private void switchVacationStatus() {
        if (userManager.getNormalUserOnVacation(currUsername)) {
            userManager.removeUsernamesOnVacation(currUsername);

            /* Notify user of vacation status OFF */
            userManager.getNotifHelper(currUsername).basicUpdate("OFF VACATION", currUsername, "");
        } else {
            userManager.addUsernamesOnVacation(currUsername);

            /* Notify user of vacation status ON */
            userManager.getNotifHelper(currUsername).basicUpdate("ON VACATION", currUsername, "");
        }
    }
}
