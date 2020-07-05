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
 * last modified 2020-07-05
 */

public class LoginSystem {
    private String username;
    private String email;
    private String validPw;
    private User user;
    private UserManager um;
    private boolean isLoggedIn;
    private boolean isAdmin;
    private String optionInput;

    /**
     * LoginSystem
     * Creates a log in system that takes in user input
     */
    public LoginSystem(UserManager um) {
        isLoggedIn = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        this.um = um;

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
                while (!um.usernameExists(username)) {
                    sp.loginSystem(3);
                    username = br.readLine();
                }
                validPw = um.usernamePassword(username);
                if (isAdmin){
                    user = um.getAdminByUsername(username);
                } else {
                    user = um.getNormalByUsername(username);
                }
            } catch (IOException e) {
                sp.exceptionMessage();
            }
        } else {
            sp.loginSystem(4);
            try {
                email = br.readLine();
                while (!um.emailExists(email)) {
                    sp.loginSystem(5);
                    email = br.readLine();
                }
            } catch (IOException e) {
                sp.exceptionMessage();
            }
            validPw = um.emailPassword(email);
            if (isAdmin) {
                user = um.getAdminByEmail(email);
            } else {
                user = um.getNormalByEmail(email);
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
        isLoggedIn = true;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public User getUser() {
        return user;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
}
