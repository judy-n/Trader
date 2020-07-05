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
    private ItemManager im;
    private UserManager um;

    /**
     * Creates an AdminDashboard that stores the given logged-in admin.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AdminDashboard(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        this.im = im;
        this.um = um;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int maxChoice = 4;

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

                    // TODO: write everything to file :(

                } catch (IOException e) {
                    sp.exceptionMessage();
                }
                sp.normalDashboard(3);
                System.exit(0);
            case 1:
                // view items awaiting approval
                // ItemManager's pendingItems list; print with index of item in the arraylist in SystemPresenter
                // option to approve or reject item by index:
                // use approveItem()/rejectItem() in ItemManager + approveUserItem()/rejectUserItem() in AdminUser
                break;
            case 2:
                // view accounts to freeze
                // usernamesToFreeze in UserManager
                // if admin chooses to freeze an account: remove from usernamesToFreeze and call freezeUser() in AdminUser
            case 3:
                // view requests to unfreeze account
                // new class or stuff it in UserManager too?
            case 4:
                // edit a user's threshold values
                // all the methods needed to set these values are in AdminUser
            case 5:
                // add new admin to system
                // enter username, email, password and create an AdminUser right away
                // addUser() in UserManager (handles the separation of admins from non-admins for you)
        }
    }

}
