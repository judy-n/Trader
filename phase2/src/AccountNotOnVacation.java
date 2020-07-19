import java.util.ArrayList;
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
 * last modified 2020-07-19
 */
public class AccountNotOnVacation {
    private NormalUser currentUser;
    private ItemManager im;
    private UserManager um;
    private TradeManager tm;

    /**
     * Creates an instance of AccountNotOnVacation for a normal user
     *
     * @param user the admin user
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public AccountNotOnVacation(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        this.im = im;
        this.um = um;
        this.tm = tm;

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
                    sp.accountsNotOnVacation();
                }

                if (input.equalsIgnoreCase("y")) {
                    for (NormalUser u : users) {
                        u.notOnVacation();
                    }
                    um.clearUsernamesOnVacation();
                }
            }
            catch (IOException e) {
                sp.exceptionMessage();
            }
        }
        close();
    }

    // closes normally as done by non-admin
    private void close() {
        new NormalDashboard((NormalUser) currentUser, im, um, tm);
    }
}