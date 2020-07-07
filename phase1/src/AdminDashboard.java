import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard once an administrative user logs in.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-05
 */

public class AdminDashboard {
    private AdminUser currentAdmin;
    private int input;
    private ItemManager itemManager;
    private UserManager userManager;

    /**
     * Creates an AdminDashboard that stores the given logged-in admin.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AdminDashboard(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        itemManager = im;
        userManager = um;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int maxChoice = 4;

        sp.showAdminID(currentAdmin);
        if (currentAdmin.getAdminID() != 1) {
            sp.adminDashboard(1);
        } else {
            maxChoice = 5;
            sp.adminDashboard(2);
        }

        try {
            input = Integer.parseInt(br.readLine());
            while (input < 0 || input > maxChoice) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            sp.exceptionMessage();
            System.exit(-1);
        }

        switch (input) {
            case 0:
                try {
                    br.close();
                } catch (IOException e) {
                    sp.exceptionMessage();
                }
                sp.normalDashboard(3);
                break;
            case 1:
                new CatalogEditor(currentAdmin, itemManager, userManager);
                break;
            case 2:
                new AccountFreezer(currentAdmin, itemManager, userManager);
                break;
            case 3:
                new AccountUnfreezer(currentAdmin, itemManager, userManager).reviewUnfreezeRequests();
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
