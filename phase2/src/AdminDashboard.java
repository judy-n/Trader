import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard once an administrative user logs in.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-28
 */

public class AdminDashboard {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;
    private int input;

    /**
     * Creates an <AdminDashboard></AdminDashboard> with the given admin and item/user managers.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AdminDashboard(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        itemManager = im;
        userManager = um;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String regex = "[0-4]";

        systemPresenter.showAdminID(currentAdmin);
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
                new CatalogEditor(currentAdmin, itemManager, userManager);
                break;
            case 2:
                new AccountFreezer(currentAdmin, itemManager, userManager);
                break;
            case 3:
                new AccountUnfreezer(currentAdmin, itemManager, userManager);
                break;

            case 4:
                new ThresholdEditor(currentAdmin, itemManager, userManager);
                break;
            case 5:
                new AdminCreator(currentAdmin, itemManager, userManager);
                break;
        }
    }
}
