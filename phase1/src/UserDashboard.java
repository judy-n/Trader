import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.sql.Array;
import java.sql.SQLOutput;

/**
 * UserDashboard.java
 * Displays a dashboard once the user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-01
 */


public class UserDashboard {
    private User currentUser;
    private int input;
    private int maxChoice = 5;
    private int specialCase = 0;

    /**
     * Creates a UserDashboard that stores the given user who is currently logged in.
     *
     * @param user the given User
     */
    public UserDashboard(User user) {
        currentUser = user;
        SystemPresenter sp = new SystemPresenter(User currentUser);
        if (currentUser.getIsFrozen()) {
            sp.userDashboard("frozen");
        }

        sp.userDashboard("menu");
        if (!(currentUser instanceof AdminUser) && currentUser.getIsFrozen()) {     //frozen non-admin
            maxChoice = 6;
            specialCase = 1;
            sp.userDashboard("unfreeze option");
        } else if (currentUser instanceof AdminUser) {    //admin
            maxChoice = 9;
            specialCase = 2;
            sp.userDashboard("admin options");
            if (currentUser.getIsFrozen() && (((AdminUser) currentUser).getAdminID() == 1)) {
                //initial admin allowed to add subsequent admins
                maxChoice = 11;
                specialCase = 3;
                sp.userDashboard("initial admin");
            } else if (currentUser.getIsFrozen()) {
                maxChoice = 10;
                specialCase = 4;
                sp.userDashboard("admin frozen option");
            } else if (((AdminUser) currentUser).getAdminID() == 1) {
                maxChoice = 10;
                specialCase = 5;
                sp.userDashboard("new admin");
            }
        }
        sp.userDashboard("logout");
        sp.userDashboard("action");

        selectChoice();

    }

    // no SystemPresenter as private
    private void selectChoice() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(br.readLine());
            while (input < 0 || input > maxChoice) {
                System.out.print("Invalid input. Try again: ");
                input = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        switch (input) {
            case 0:
                try {
                    br.close();

                    // TODO: write everything to file :(

                } catch (IOException e) {
                    System.out.println("Error closing input stream.");
                }
                System.out.println("Logging out of the program. Bye!");
                System.exit(0);

            case 1:
                new ItemPresenter(currentUser);
                break;

            case 2:
                new InventoryEditor(currentUser);
                break;

            case 3:
                new WishlistEditor(currentUser);
                break;

            case 4:
                new TradeRequestPresenter(currentUser);
                break;

            case 5:

                break;

            case 6:
                if (specialCase == 1) {
                    // unfreeze request option for frozen non-admin
                    break;
                } else {
                    // view items waiting for approval option for admin
                    break;
                }

            case 7:
                //view account to freeze option for admin
                break;

            case 8:
                // view requests to unfreeze account option for admin
                break;

            case 9:
                // edit a user's threshold values option for admin
                break;

            case 10:
                if (specialCase == 3 || specialCase == 4) {
                    // request to unfreeze account option for frozen admin
                    break;
                } else {
                    // add a new admin to the system option for frozen init admin
                    break;
                }

            case 11:
                // add a new admin to the system option for NON-frozen init admin
                break;
        }
    }
}
