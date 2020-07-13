import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays a list of usernames that need to be frozen and lets an admin freeze them.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-12
 */
public class AccountFreezer {
    private AdminUser currentAdmin;
    private ItemManager im;
    private UserManager um;

    /**
     * Creates an <AccountFreezer></AccountFreezer> with the given admin and item/user managers.
     * Sets all normal users on the list's status to frozen.
     *
     * @param user the admin currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AccountFreezer(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        this.im = im;
        this.um = um;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input;
        List<String> usernames = um.getUsernamesToFreeze();
        List<NormalUser> users = new ArrayList<>();

        sp.accountFreezer(usernames);
        for (String username : usernames) {
            users.add(um.getNormalByUsername(username));
        }

        if (!usernames.isEmpty()) {
            try {
                input = br.readLine();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    sp.invalidInput();
                    input = br.readLine();
                    sp.accountFreezer();
                }
                if (input.equalsIgnoreCase("y")) {
                    for (NormalUser u : users) {
                        u.freeze();
                    }
                    um.clearUsernamesToFreeze();
                }
            } catch (IOException e) {
                sp.exceptionMessage();
            }
        }
        close();
    }

    private void close() {
        new AdminDashboard(currentAdmin, im, um);
    }

}
