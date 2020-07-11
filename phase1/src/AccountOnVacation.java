import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a list of usernames that need to be put on
 * vacation status and lets admin user do so
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-07-11
 * last modified 2020-07-11
 */
public class AccountOnVacation {
    private AdminUser currentAdmin;
    private ItemManager im;
    private UserManager um;

    /**
     * Creates an instance of AccountOnVacation that sets a certain
     * user's status to on Vacation
     *
     * @param user the admin user
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public AccountOnVacation(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        this.im = im;
        this.um = um;

        List<String> usernames = um.getUsernamesOnVacation();
        List<NormalUser> users = new ArrayList<>();
        String input;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sp.accountsOnVacation(usernames);

        for (String username : usernames) {
            users.add(um.getNormalByUsername(username));
        }

        if (!usernames.isEmpty()) {
            try {
                input = br.readLine();
                while ((!input.equalsIgnoreCase("y")) && (!input.equalsIgnoreCase("n"))) {
                    sp.invalidInput();
                    input = br.readLine();
                    sp.accountsOnVacation();
                }

                if (input.equalsIgnoreCase("y")) {
                    for (NormalUser u : users) {
                        u.freeze();
                    }
                    um.clearUsernamesToFreeze();
                }
            }
            catch (IOException e) {
                sp.exceptionMessage();
            }
        }
        close();
    }

    private void close() {
        new AdminDashboard(currentAdmin, im, um);
    }
}
