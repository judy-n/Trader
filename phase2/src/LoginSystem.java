import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Logs the user into the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-22
 */

public class LoginSystem {
    private String usernameOrEmail;
    private String validPw;
    private User currentUser;
    private UserManager userManager;

    /**
     * Creates a <LoginSystem></LoginSystem> that lets the user log in through user input.
     * Checks if username/email and password match using <UserManager></UserManager>.
     */
    public LoginSystem(UserManager um) {
        userManager = um;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        //systemPresenter.loginSystem(1);
        try {
            usernameOrEmail = bufferedReader.readLine();

            while (!userManager.usernameExists(usernameOrEmail) && !userManager.emailExists(usernameOrEmail)) {
                //systemPresenter.loginSystem(2);
                usernameOrEmail = bufferedReader.readLine();
            }
            if (usernameOrEmail.contains("@")) {
                validPw = userManager.emailPassword(usernameOrEmail);
                currentUser = userManager.getUserByEmail(usernameOrEmail);
            } else {
                validPw = userManager.usernamePassword(usernameOrEmail);
                currentUser = userManager.getUserByUsername(usernameOrEmail);
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        //systemPresenter.loginSystem(3);
        try {
            String password = bufferedReader.readLine();
            while (!password.equals(validPw)) {
               // systemPresenter.loginSystem(4);
                password = bufferedReader.readLine();
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
       // systemPresenter.loginSystem(5);
    }

    /**
     * Getter for this <LoginSystem></LoginSystem>'s logged-in user.
     *
     * @return the user who just logged in
     */
    public User getUser() {
        return currentUser;
    }
}
