package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemFunctions.SystemPresenter;
import SystemFunctions.SystemController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Shows all items available for trade from all users' inventories.
 * Allows user demo-ing the program to browse items, and prompts them to make an account if they choose an item.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-19
 * last modified 2020-07-31
 */
public class DemoCatalogViewer {
    private ItemManager itemManager;
    private UserManager userManager;
    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;

    /**
     * Creates a <DemoCatalogViewer></DemoCatalogViewer> with the given user/item managers.
     *
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     */
    public DemoCatalogViewer(ItemManager itemManager, UserManager userManager) {
        this.itemManager = itemManager;
        this.userManager = userManager;

        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        systemPresenter.catalogViewer(itemManager.getApprovedItems());
        systemPresenter.demoCatalogViewer();

        try {
            String temp = bufferedReader.readLine();
            while (!temp.matches("[0-1]")) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input == 1) {
                new SystemController();
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
        close();
    }

    private void close() {
        new DemoDashboard(itemManager, userManager);
    }

}
