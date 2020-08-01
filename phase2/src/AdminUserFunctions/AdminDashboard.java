package AdminUserFunctions;

import Entities.User;
import SystemFunctions.Dashboard;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import Entities.AdminUser;
import SystemFunctions.SystemPresenter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard once an administrative user logs in.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-31
 */

public class AdminDashboard extends Dashboard {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;
    private int input;

    /**
     * Creates an <AdminDashboard></AdminDashboard> with the given admin,
     * item/user managers, and notification system.
     *
     * @param user        the admin who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public AdminDashboard(AdminUser user, ItemManager itemManager,
                          UserManager userManager, NotificationSystem notifSystem) {
        currentAdmin = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String regex = "[0-4]";

        systemPresenter.showAdminID(currentAdmin.getAdminID());
        if (currentAdmin.getAdminID() != 1) {
            systemPresenter.adminDashboard(1);
        } else {
            regex = "[0-5]";
            systemPresenter.adminDashboard(2);
        }
        try {
            String temp;
            temp = bufferedReader.readLine();
            while (!temp.matches(regex)) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            input = Integer.parseInt(temp);
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        switch (input) {
            case 0:
                systemPresenter.logoutMessage();
                break;
            case 1:
                new CatalogEditor(currentAdmin, itemManager, userManager, notifSystem);
                break;
            case 2:
                new AccountFreezer(currentAdmin, itemManager, userManager, notifSystem);
                break;
            case 3:
                new AccountUnfreezer(currentAdmin, itemManager, userManager, notifSystem);
                break;

            case 4:
                new ThresholdEditor(currentAdmin, itemManager, userManager, notifSystem);
                break;
            case 5:
                new AdminCreator(currentAdmin, itemManager, userManager, notifSystem);
                break;
        }
    }

    @Override
    public User getUser() {
        return currentAdmin;
    }
}
