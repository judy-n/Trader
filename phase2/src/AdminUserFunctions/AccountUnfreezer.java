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
 * Lets admins review request to be unfrozen sent by frozen normal users.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-03
 */
public class AccountUnfreezer extends MenuItem {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <AccountUnfreezer></AccountUnfreezer> with the given admin,
     * item/user managers, and notification system.
     * Lets the admin view requests to be unfrozen and choose which ones to accept.
     *
     * @param username the username of the admin who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public AccountUnfreezer(String username, ItemManager itemManager,
                            UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int indexInput;
        List<String> usernames = userManager.getUnfreezeRequests();

        systemPresenter.adminGetUnfreezeRequests(usernames);
        if (!usernames.isEmpty()) {
            try {
                String input = bufferedReader.readLine();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    systemPresenter.invalidInput();
                    input = bufferedReader.readLine();
                }
                if (input.equalsIgnoreCase("y")) {
                    do {

                        systemPresenter.adminGetUnfreezeRequests(1);

                        int max = userManager.getNumUnfreezeRequest();
                        String temp = bufferedReader.readLine();
                        while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > max) {
                            systemPresenter.invalidInput();
                            temp = bufferedReader.readLine();
                        }
                        indexInput = Integer.parseInt(temp);

                        if (indexInput != 0) {
                            String unfreezeUsername = userManager.getUnfreezeUsername(indexInput - 1);
                            userManager.removeUnfreezeRequest(indexInput - 1);

                            /* Notify normal user of account being unfrozen */
                            userManager.getNotifHelper(unfreezeUsername).basicUpdate
                                    ("UNFROZEN", unfreezeUsername, currUsername);

                            systemPresenter.adminGetUnfreezeRequests(2);

                            usernames = userManager.getUnfreezeRequests(); // update list
                            systemPresenter.adminGetUnfreezeRequests(usernames); // reprint the list to update indexes
                        }
                    } while (indexInput != 0);
                }
                systemPresenter.adminGetUnfreezeRequests(3);
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
        return ("Review Unfreeze Requests");
    }

}
