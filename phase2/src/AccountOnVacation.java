import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a list of usernames that are on vacation.
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-07-17
 * last modified 2020-07-19
 */
public class AccountOnVacation {
    private NormalUser currentAdmin;
    private ItemManager im;
    private UserManager um;
    private TradeManager tm;

    /**
     * Creates an instance of AccountOnVacation that sets a certain
     * user's status to on Vacation
     *
     * @param user the normal user
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public AccountOnVacation(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentAdmin = user;
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
                    sp.accountsOnVacation();
                }

                if (input.equalsIgnoreCase("y")) {
                    for (NormalUser u : users) {
                        u.onVacation();
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

    private void close() {
        new AdminDashboard(currentAdmin, im, um);
    }
}