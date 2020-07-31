package AdminUserFunctions;

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
 * Lets admins review request to be unfrozen sent by frozen normal users.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-30
 */
public class AccountUnfreezer extends MenuItem {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;
    private SystemPresenter systemPresenter;

    /**
     * Creates an <AccountUnfreezer></AccountUnfreezer> with the given admin and item/user managers.
     * Lets the admin view requests to be unfrozen and choose which ones to accept.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AccountUnfreezer(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        itemManager = im;
        userManager = um;
        systemPresenter = new SystemPresenter();

        reviewUnfreezeRequests();
        close();
    }

    /*
     * Displays the list of Users that requested to be unfrozen,
     * and the admin can choose which ones to unfreeze (or none).
     */
    private void reviewUnfreezeRequests() {
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
                            userManager.removeUnfreezeRequest(indexInput);

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
    }

    private void close() {
        new AdminDashboard(currentAdmin, itemManager, userManager);
    }

    @Override
    public String getTitle(){
        return ("Review Unfreeze Requests");
    }

}
