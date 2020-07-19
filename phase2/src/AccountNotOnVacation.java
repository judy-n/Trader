import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets a User on Vacation request to be not on Vacation, and lets an AdminUser
 * accept/deny the requests.
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-07-17
 * last modified 2020-07-17
 */
public class AccountNotOnVacation {
    private SystemPresenter sp;
    private User currentUser;
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;

    /**
     * Creates an instance of AccountNotOnVacation for a normal user
     *
     * @param user normal user
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public AccountNotOnVacation(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        sp = new SystemPresenter();
        closeNormal();
    }

    // closes normally as done by non admin
    private void closeNormal() {
        new NormalDashboard((NormalUser) currentUser, itemManager, userManager, tradeManager);
    }

    // closed by an admin
    private void closeAdmin() {
        new AdminDashboard((AdminUser) currentUser, itemManager, userManager);
    }

    // Sends a request to be not on Vacation.
//    private void requestNotOnVacation (NormalUser currentUser) {
//        if (userManager.containsNotOnVacationRequest(currentUser.getUsername())) {
//            sp.requestNotOnVacation(1);
//        } else {
//            userManager.addNotOnVacationRequest(currentUser.getUsername());
//            sp.requestNotOnVacation(2);
//        }
//    }
}