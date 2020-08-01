package SystemFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import Entities.NormalUser;
import java.util.ArrayList;

/**
 * Signs a new user up in the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-30
 */
public class SignUpSystem {
    private UserManager userManager;

    /**
     * Creates a <SignUpSystem></SignUpSystem> with the given user manager.
     *
     * @param userManager the system's user manager
     */
    public SignUpSystem(UserManager userManager) {
        this.userManager = userManager;
    }

    public ArrayList<Integer> validateInput(String username, String email, String password, String validatePassword){
        ArrayList<Integer> invalidInput = new ArrayList<>();
        if(username.isEmpty()){
            invalidInput.add(12);
        }else {
            if (userManager.usernameExists(username)) {
                invalidInput.add(5);
            } else if (!username.matches("[a-zA-Z0-9]+([_.][a-zA-Z0-9]+)*") || username.length() < 3) {
                invalidInput.add(6);
            }
        }
        if(email.isEmpty()){
            invalidInput.add(15);
        }else {
            if (userManager.emailExists(email)) {
                invalidInput.add(2);
            } else if (!email.matches("[\\w]+(\\.[\\w]+)*@([a-zA-Z]+\\.)+[a-z]{2,}")) {
                invalidInput.add(3);
            }
        }
        if(password.isEmpty()){
            invalidInput.add(16);
        }else {
            if (!password.matches("[\\S]{6,20}")) {
                invalidInput.add(8);
            }
        }
        if(validatePassword.isEmpty()){
            invalidInput.add(17);
        }else {
            if (!validatePassword.equals(password)) {
                invalidInput.add(10);
            }
        }
        return invalidInput;
    }

    /**
     * Creates a new <NormalUser></NormalUser> based on input from <inputProcess()></inputProcess()>.
     *
     * @return the normal user that was just created
     */
    public NormalUser createNewNormal(String username, String email, String password, String homeCity, NotificationSystem notifSystem) {
        userManager.createNormalUser(username, email, password, homeCity);
        NormalUser newNormalUser = userManager.getNormalByUsername(username);
        newNormalUser.addObserver(notifSystem);
        return newNormalUser;
    }

    /**
     * Creates a new <AdminUser></AdminUser> based on input from <inputProcess()></inputProcess()>.
     */
    public void createNewAdmin(String username, String email, String password, NotificationSystem notifSystem) {
        userManager.createAdminUser(username, email, password);
        userManager.getAdminByUsername(username).addObserver(notifSystem);
    }
}