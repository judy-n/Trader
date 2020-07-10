import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Displays a list of usernames that need to be frozen
 * and lets admin user freeze them
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-08
 */
public class AccountFreezer {
    private AdminUser currentAdmin;
    private ItemManager im;
    private UserManager um;

    /**
     * Creates an AccountFreezer that sets a certain user's status to frozen
     * @param user the admin user
     * @param im the system's item manager
     * @param um the system's user manager
     */
    public AccountFreezer(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        this.im = im;
        this.um = um;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input;
        ArrayList<String> usernames = um.getUsernamesToFreeze();
        ArrayList<NormalUser> users = new ArrayList<>();

        sp.accountFreezer(usernames);
        for (String username : usernames) {
            users.add(um.getNormalByUsername(username));
        }
        try {
            input = br.readLine();
            while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                sp.invalidInput();
                input = br.readLine();
            }
            if (input.equalsIgnoreCase("y")) {
                for (NormalUser u : users) {
                    u.freeze();
                }
                um.clearUsernamesToFreeze();
            }
            sp.accountFreezer();
            close();
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    private void close() {
        new AdminDashboard(currentAdmin, im, um);
    }

}
