import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Signs a new user up in the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-12
 */
public class SignUpSystem {
    private String username;
    private String email;
    private String password;
    private UserManager userManager;
    private SystemPresenter sp;

    /**
     * Creates a <SignUpSystem></SignUpSystem> with the given user manager.
     *
     * @param um the system's user manager
     */
    public SignUpSystem(UserManager um) {
        userManager = um;
        sp = new SystemPresenter();
    }

    /*
     * Allows a new user to be created through user input.
     * Runs checks to ensure that usernames and emails are unique, and that login credentials
     * follow certain rules (e.g. no spaces, at least x characters long, etc).
     */
    private void inputProcess() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sp.signUpSystem(1);
        try {
            String emailInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (userManager.emailExists(emailInput)) {
                    invalidInput = true;
                    sp.signUpSystem(2);
                    emailInput = br.readLine();
                } else if (!(emailInput.contains("@") && emailInput.contains(".")) || emailInput.contains(" ")) {
                    invalidInput = true;
                    sp.signUpSystem(3);
                    emailInput = br.readLine();
                }
            } while (invalidInput);
            email = emailInput;
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        sp.signUpSystem(4);
        try {
            String usernameInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (userManager.usernameExists(usernameInput)) {
                    invalidInput = true;
                    sp.signUpSystem(5);
                    usernameInput = br.readLine();
                } else if (usernameInput.isEmpty() || usernameInput.contains(" ") || usernameInput.length() < 3) {
                    invalidInput = true;
                    sp.signUpSystem(6);
                    usernameInput = br.readLine();
                }
            } while (invalidInput);
            username = usernameInput;
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        sp.signUpSystem(7);
        try {
            String pwInput1 = br.readLine();
            while (pwInput1.isEmpty() || pwInput1.contains(" ") ||
                    pwInput1.length() < 6 || pwInput1.length() > 20) {
                sp.signUpSystem(8);
                pwInput1 = br.readLine();
            }
            sp.signUpSystem(9);
            String pwInput2 = br.readLine();
            while (!pwInput2.equals(pwInput1)) {
                sp.signUpSystem(10);
                pwInput2 = br.readLine();
            }
            password = pwInput1;
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    /**
     * Creates a new <NormalUser></NormalUser> based on input from <inputProcess()></inputProcess()>.
     *
     * @return the normal user that was just created
     */
    public NormalUser createNewNormal() {
        sp.signUpSystem(0);
        inputProcess();
        userManager.createNormalUser(username, email, password);
        sp.signUpSystem(11);
        return userManager.getNormalByUsername(username);
    }

    /**
     * Creates a new <AdminUser></AdminUser> based on input from <inputProcess()></inputProcess()>.
     */
    public void createNewAdmin() {
        sp.signUpSystem(12);
        inputProcess();
        userManager.createAdminUser(username, email, password);
    }
}