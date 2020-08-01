package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemFunctions.SystemPresenter;
import SystemFunctions.SystemController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard for a user choosing to demo the program.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-19
 * last modified 2020-07-31
 */

public class DemoDashboard {

    private ItemManager itemManager;
    private UserManager userManager;
    private int input;

    /**
     * Creates a <DemoDashboard></DemoDashboard> with the given item/user managers.
     *
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     */
    public DemoDashboard(ItemManager itemManager, UserManager userManager) {
        this.itemManager = itemManager;
        this.userManager = userManager;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        systemPresenter.demoDashboard();

        try {
            String temp;
            temp = bufferedReader.readLine();
            while (!temp.matches("[0-2]")) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            input = Integer.parseInt(temp);
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        switch (input) {
            case 0:
                systemPresenter.exitProgram();
                System.exit(0);
            case 1:
                new DemoCatalogViewer(itemManager, userManager);
                break;
            case 2:
                new SystemController();
        }
    }
}
