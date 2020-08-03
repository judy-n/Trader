package AdminUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import Entities.AdminUser;
import SystemFunctions.SystemPresenter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets an admin approve or reject pending items.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-31
 */
public class CatalogEditor {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;

    /**
     * Creates a <CatalogEditor></CatalogEditor> with the given admin,
     * item/user managers, and notification system.
     * Lets an admin user approve or reject items added to the system by normal users.
     *
     * @param user        the admin who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public CatalogEditor(AdminUser user, ItemManager itemManager,
                         UserManager userManager, NotificationSystem notifSystem) {
        currentAdmin = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int input;
        try {
            do {
                int max = itemManager.getNumPendingItems();

                if (itemManager.getPendingItems().isEmpty()) {
                    systemPresenter.catalogEditor(1);
                    break;
                } else {
                    systemPresenter.catalogEditor(itemManager.getPendingItems());
                }
                systemPresenter.catalogEditor(2);
                String temp = bufferedReader.readLine();
                while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > max) {
                    systemPresenter.invalidInput();
                    temp = bufferedReader.readLine();
                }
                input = Integer.parseInt(temp);

                if (input != 0) {
                    long pendingItemID = itemManager.getPendingItem(input);
                    String itemOwnerUsername = itemManager.getItemOwner(pendingItemID);
                    systemPresenter.catalogEditor(itemManager.getItemName(pendingItemID));
                    String temp2 = bufferedReader.readLine();
                    while (!temp2.matches("[0-2]")) {
                        systemPresenter.invalidInput();
                        temp2 = bufferedReader.readLine();
                    }
                    int actionInput = Integer.parseInt(temp2);
                    if (actionInput == 1) {
                        itemManager.approveItem(pendingItemID);
                        userManager.addNormalUserInventory(pendingItemID, itemOwnerUsername);
                    } else if (actionInput == 2) {
                        itemManager.rejectItem(pendingItemID);
                        userManager.removeNormalUserPending(pendingItemID, itemOwnerUsername);
                    } else {
                        input = 0;
                    }
                }
            } while (input != 0);
            close();

        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
    }

    private void close() {
        new AdminDashboard(currentAdmin.getUsername(), itemManager, userManager, notifSystem);
    }
}
