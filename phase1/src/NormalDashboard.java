import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.sql.Array;
import java.sql.SQLOutput;

/**
 * Displays a dashboard once a non-administrative user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-04
 */


public class NormalDashboard {
    private NormalUser currentUser;
    private int input;
    private int maxChoice = 5;
    private ItemManager im;
    private UserManager um;

    /**
     * Creates a NormalDashboard that stores the given logged-in user.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public NormalDashboard(NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        this.im = im;
        this.um = um;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        if (currentUser.getIsFrozen()) {
            sp.normalDashboard(2);
        } else {
            sp.normalDashboard(1);
        }

//        } else if (currentUser instanceof AdminUser) {    //admin
//            maxChoice = 9;
//            specialCase = 2;
//            sp.userDashboard("admin options");
//
//            if (currentUser.getIsFrozen() && (((AdminUser) currentUser).getAdminID() == 1)) {
//                //initial admin allowed to add subsequent admins
//                maxChoice = 11;
//                specialCase = 3;
//                sp.userDashboard("initial admin");
//            }
//
//            else if (((AdminUser) currentUser).getAdminID() == 1) {
//                maxChoice = 10;
//                specialCase = 5;
//                sp.userDashboard("new admin");
//            }
//        }

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
                new ItemPresenter(currentUser, im, um);
                break;

            case 2:
                new InventoryEditor(currentUser, im, um);
                break;

            case 3:
                new WishlistEditor(currentUser, im, um);
                break;

            case 4:
                new TradeRequestViewer(currentUser, im, um);
                break;

            case 5:

                break;

            case 6: {
                // unfreeze request option for frozen non-admin
                break;
            }
        }
    }
}
