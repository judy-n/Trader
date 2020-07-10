import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Lets a frozen NormalUser request to be unfrozen, and lets an AdminUser accept/deny the requests.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-08
 */
public class AccountUnfreezer {
    private User currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter sp;

    /**
     * Creates an account unfreezer for a non admin user
     * @param user non admin user
     * @param im the system's item manager
     * @param um the system's user manager
     * @param tm the system's trade manager
     */
    public AccountUnfreezer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        sp = new SystemPresenter();

        requestUnfreeze((NormalUser) currentUser);
        closeNormal();
    }

    /**
     * Creates an account unfreezer for admin users
     * @param user admin user
     * @param im the system's item manager
     * @param um the system's user manager
     */
    public AccountUnfreezer(AdminUser user, ItemManager im, UserManager um) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        sp = new SystemPresenter();

        reviewUnfreezeRequests();
        closeAdmin();
    }

    // Sends a request to be unfrozen.
    private void requestUnfreeze(NormalUser currentUser) {
        if (userManager.containsUnfreezeRequest(currentUser.getUsername())) {
            sp.requestUnfreeze(1);
        } else {
            userManager.addUnfreezeRequest(currentUser.getUsername());
            sp.requestUnfreeze(2);
        }
    }

    // Displays the list of Users that requested to be unfrozen, and the admin can choose which ones to unfreeze (or none).
    private void reviewUnfreezeRequests() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int indexInput;
        ArrayList<NormalUser> users = userManager.getUnfreezeRequests();

        sp.adminGetUnfreezeRequests(users);
        sp.adminGetUnfreezeRequests(4);
        try {
            String input = br.readLine();
            while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                sp.invalidInput();
                input = br.readLine();
            }
            if (input.equalsIgnoreCase("y")) {
                do {

                    sp.adminGetUnfreezeRequests(1);

                    int max = userManager.getNumUnfreezeRequest();
                    String temp = br.readLine();
                    while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > max) {
                        sp.invalidInput();
                        temp = br.readLine();
                    }
                    indexInput = Integer.parseInt(temp);

                    if (indexInput != 0) {
                        NormalUser userToUnfreeze = userManager.getNormalByUsername(userManager.getUnfreezeRequest(indexInput));
                        userToUnfreeze.unfreeze();
                        userManager.removeUnfreezeRequest(userToUnfreeze.getUsername());

                        sp.adminGetUnfreezeRequests(2);

                        users = userManager.getUnfreezeRequests(); //update list
                        sp.adminGetUnfreezeRequests(users); //reprint the list to update indexes
                    }
                } while (indexInput != 0);
            }
            sp.adminGetUnfreezeRequests(3);
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    private void closeNormal() {
        new NormalDashboard((NormalUser) currentUser, itemManager, userManager, tradeManager);
    }

    private void closeAdmin() {
        new AdminDashboard((AdminUser) currentUser, itemManager, userManager);
    }

}
