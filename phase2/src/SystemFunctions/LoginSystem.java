package SystemFunctions;

import SystemManagers.UserManager;
import Entities.User;
import java.util.ArrayList;

/**
 * Logs the user into the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-30
 */

public class LoginSystem {
    private String validPw;
    private UserManager userManager;

    /**
     * Creates a <LoginSystem></LoginSystem> that lets the user log in through user input.
     * Checks if username/email and password match using <UserManager></UserManager>.
     */
    public LoginSystem(UserManager userManager) {
        this.userManager = userManager;
    }

    public ArrayList<Integer> validateInput(String usernameOrEmail, String password){
        ArrayList<Integer> invalidInput = new ArrayList<>();
        if(!userManager.usernameExists(usernameOrEmail) && !userManager.emailExists(usernameOrEmail)){
            invalidInput.add(2);
        }
        User currentUser = userManager.getUserByUsernameOrEmail(usernameOrEmail);
        validPw = currentUser.getPassword();
        if(!password.equals(validPw)){
            invalidInput.add(4);
        }
        return invalidInput;
    }

    /**
     * Getter for this <LoginSystem></LoginSystem>'s logged-in user.
     *
     * @return the user who just logged in
     */
    public User getUser(String usernameOrEmail) {
        return userManager.getUserByUsernameOrEmail(usernameOrEmail);
    }
}
