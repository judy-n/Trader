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
 * Lets admin review request to be unfrozen sent by frozen normal users.
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-08-03
 * last modified 2020-08-04
 */
public class VacationViewer extends MenuItem {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <VacationViewer> with the given admin, item/user managers,
     * and notification system.
     * Lets the admin view which viewers are on vacation.
     *
     * @param username username of admin currently logged in
     * @param itemManager system's item manager
     * @param userManager system's user manager
     * @param notifSystem system's notification manager
     */
    public VacationViewer (String username, ItemManager itemManager,
                            UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<String> usernames = userManager.getUsernamesOnVacation();
        systemPresenter.usersOnVacation(usernames);

        close();
    }

    // allows admin to continue using their dashboard
    private void close() {
        new AdminDashboard(currUsername, itemManager, userManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return ("View users on vacation");
    }

}
