import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Logs the user into the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-08
 */

public class LoginSystem {
    private String username;
    private String email;
    private String validPw;
    private User user;
    private UserManager userManager;
    private boolean isAdmin;
    private String optionInput;

    /**
     * Class constructor.
     * Creates a login system that takes in user input.
     */
    public LoginSystem(UserManager um) {
        userManager = um;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sp.loginSystem(1);
        try {
            optionInput = br.readLine();
            while (!optionInput.equalsIgnoreCase("y") && !optionInput.equalsIgnoreCase("n")) {
                sp.invalidInput();
                optionInput = br.readLine();
            }
            isAdmin = optionInput.equalsIgnoreCase("y");
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        sp.loginSystem(9);

        try {
            optionInput = br.readLine();
            while (!optionInput.equals("1") && !optionInput.equals("2")) {
                sp.invalidInput();
                optionInput = br.readLine();
            }
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        if (optionInput.equals("1")) {
            sp.loginSystem(2);
            try {
                username = br.readLine();
                while (!userManager.usernameExists(username, isAdmin)) {
                    sp.loginSystem(3);
                    username = br.readLine();
                }
                if (isAdmin) {
                    user = userManager.getAdminByUsername(username);
                    validPw = userManager.adminUsernamePassword(username);
                } else {
                    user = userManager.getNormalByUsername(username);
                    validPw = userManager.normalUsernamePassword(username);
                }
            } catch (IOException e) {
                sp.exceptionMessage();
            }
        } else {
            sp.loginSystem(4);
            try {
                email = br.readLine();
                while (!userManager.emailExists(email, isAdmin)) {
                    sp.loginSystem(5);
                    email = br.readLine();
                }
            } catch (IOException e) {
                sp.exceptionMessage();
            }
            if (isAdmin) {
                user = userManager.getAdminByEmail(email);
                validPw = userManager.adminEmailPassword(email);
            } else {
                user = userManager.getNormalByEmail(email);
                validPw = userManager.normalEmailPassword(email);
            }
        }

        sp.loginSystem(6);
        try {
            String password = br.readLine();
            while (!password.equals(validPw)) {
                sp.loginSystem(7);
                password = br.readLine();
            }
        } catch (IOException e) {
            sp.exceptionMessage();
        }
        sp.loginSystem(8);
    }

    public User getUser() {
        return user;
    }
}
