import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Lets frozen normal users request to be unfrozen, and lets admins accept the requests.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-12
 */
public class AccountUnfreezer {
    private User currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter sp;

    /**
     * Creates an <AccountUnfreezer></AccountUnfreezer> with the given normal user and item/user/trade managers.
     * Calls a private method that adds the user's username
     * to a list of accounts that requested to be unfrozen (only if they haven't already requested).
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
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
     * Creates an <AccountUnfreezer></AccountUnfreezer> with the given admin and item/user managers.
     * Calls a private method that lets the admin view requests to be unfrozen and choose which ones to accept.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AccountUnfreezer(AdminUser user, ItemManager im, UserManager um) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        sp = new SystemPresenter();

        reviewUnfreezeRequests();
        closeAdmin();
    }

    /* Sends a request to be unfrozen. */
    private void requestUnfreeze(NormalUser currentUser) {
        if (userManager.getUnfreezeRequests().contains(currentUser.getUsername())) {
            sp.requestUnfreeze(1);
        } else {
            userManager.addUnfreezeRequest(currentUser.getUsername());
            sp.requestUnfreeze(2);
        }
    }

    /*
     * Displays the list of Users that requested to be unfrozen,
     * and the admin can choose which ones to unfreeze (or none).
     */
    private void reviewUnfreezeRequests() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int indexInput;
        List<String> usernames = userManager.getUnfreezeRequests();

        sp.adminGetUnfreezeRequests(usernames);

        if (!usernames.isEmpty()) {
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
                            userManager.removeUnfreezeRequest(indexInput);

                            sp.adminGetUnfreezeRequests(2);

                            usernames = userManager.getUnfreezeRequests(); // update list
                            sp.adminGetUnfreezeRequests(usernames); // reprint the list to update indexes
                        }
                    } while (indexInput != 0);
                }
                sp.adminGetUnfreezeRequests(3);
            } catch (IOException e) {
                sp.exceptionMessage();
            }
        }
    }

    private void closeNormal() {
        new NormalDashboard((NormalUser) currentUser, itemManager, userManager, tradeManager);
    }

    private void closeAdmin() {
        new AdminDashboard((AdminUser) currentUser, itemManager, userManager);
    }
}
