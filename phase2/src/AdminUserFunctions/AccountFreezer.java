package AdminUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import Entities.AdminUser;
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
 * last modified 2020-07-31
 */
public class AccountFreezer extends MenuItem {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <AccountFreezer></AccountFreezer> with the given admin,
     * item/user managers, and notification system.
     * Sets all normal users on the list's status to frozen.
     *
     * @param user        the admin currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public AccountFreezer(AdminUser user, ItemManager itemManager,
                          UserManager userManager, NotificationSystem notifSystem) {
        currentAdmin = user;
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
                    for (String username : usernames) {
                        userManager.freezeNormalUser(username);
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
        new AdminDashboard(currentAdmin, itemManager, userManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return ("Freeze Users");
    }
}
