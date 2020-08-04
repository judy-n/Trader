package AdminUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemFunctions.SystemPresenter;
import SystemFunctions.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Displays a list of usernames that need to be frozen and lets an admin freeze them.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-03
 */
public class AccountFreezer extends MenuItem {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <AccountFreezer></AccountFreezer> with the given admin,
     * item/user managers, and notification system.
     * Sets all normal users on the list's status to frozen.
     *
     * @param username    the username of the admin currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public AccountFreezer(String username, ItemManager itemManager,
                          UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String input;
        List<String> usernames = userManager.getUsernamesToFreeze();

        systemPresenter.accountFreezer(usernames);

        if (!usernames.isEmpty()) {
            try {
                input = bufferedReader.readLine();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    systemPresenter.invalidInput();
                    input = bufferedReader.readLine();
                    systemPresenter.accountFreezer();
                }
                if (input.equalsIgnoreCase("y")) {
                    for (String usernameToFreeze : usernames) {
                        userManager.freezeNormalUser(usernameToFreeze);

                        /* Notify normal user of account being frozen */
                        userManager.getNotifHelper(usernameToFreeze).basicUpdate
                                ("FROZEN", usernameToFreeze, currUsername);
                    }
                    userManager.clearUsernamesToFreeze();
                }
            } catch (IOException e) {
                systemPresenter.exceptionMessage();
            }
        }
        close();
    }


    private void close() {
        new AdminDashboard(currUsername, itemManager, userManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return ("Freeze Users");
    }
}
