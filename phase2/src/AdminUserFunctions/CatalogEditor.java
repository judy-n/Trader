package AdminUserFunctions;
import SystemManagers.*;
import Entities.*;
import SystemFunctions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets an admin approve or reject pending items.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-28
 */
public class CatalogEditor {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;

    /**
     * Creates a <CatalogEditor></CatalogEditor> with the given admin and item/user managers.
     * Lets an admin user approve or reject items added to the system by normal users.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public CatalogEditor(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        itemManager = im;
        userManager = um;

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
                    Item i = itemManager.getPendingItem(input);
                    NormalUser itemOwner = userManager.getNormalByUsername(i.getOwnerUsername());
                    systemPresenter.catalogEditor(i);
                    String temp2 = bufferedReader.readLine();
                    while (!temp2.matches("[0-2]")) {
                        systemPresenter.invalidInput();
                        temp2 = bufferedReader.readLine();
                    }
                    int actionInput = Integer.parseInt(temp2);
                    if (actionInput == 1) {
                        itemManager.approveItem(i);
                        itemOwner.addInventory(i.getID());
                    } else if (actionInput == 2) {
                        itemManager.rejectItem(i);
                        itemOwner.removePendingInventory(i.getID());
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
        new AdminDashboard(currentAdmin, itemManager, userManager);
    }
}
