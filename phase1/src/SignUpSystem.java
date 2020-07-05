import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets the user sign up.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-04
 */
public class SignUpSystem {
    private String username;
    private String email;
    private String password;
    private UserManager um;
    private NormalUser newUser;
    private boolean isSignedUp;

    /**
     * Class constructor.
     * Creates a SignUpSystem with the given user manager.
     * Lets the user sign up through user input.
     *
     * @param um the system's user manager
     */
    public SignUpSystem(UserManager um) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        this.um = um;
        isSignedUp = false;
        sp.signUpSystem(1);
        try {
            String emailInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (um.emailExists(emailInput)) {
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
                if (um.usernameExists(usernameInput)) {
                    invalidInput = true;
                    sp.signUpSystem(5);
                    usernameInput = br.readLine();
                } else if (usernameInput.trim().isEmpty()) {
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
            while (pwInput1.trim().isEmpty()) {
                sp.signUpSystem(8);
                pwInput1 = br.readLine();
            }
            sp.signUpSystem(9);
            String pwInput2 = br.readLine();
            while (!pwInput1.equals(pwInput2)) {
                sp.signUpSystem(10);
                pwInput2 = br.readLine();
            }
            password = pwInput1;
        } catch (IOException e) {
            sp.exceptionMessage();
        }
        newUser = new NormalUser(username, email, password);
        um.addUser(newUser);
        sp.signUpSystem(11);
        isSignedUp = true;
    }

    public NormalUser getNewUser() {
        return newUser;
    }

    public boolean getSignedUp() {
        return isSignedUp;
    }

}