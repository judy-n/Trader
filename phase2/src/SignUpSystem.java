import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Signs a new user up in the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-28
 */
public class SignUpSystem {
    //private String username;
    //private String email;
    //private String password;
    //private String homeCity;
    private UserManager userManager;
    private SystemPresenter systemPresenter;

    /**
     * Creates a <SignUpSystem></SignUpSystem> with the given user manager.
     *
     * @param um the system's user manager
     */
    public SignUpSystem(UserManager um) {
        userManager = um;
        systemPresenter = new SystemPresenter();
    }


    public ArrayList<Integer> validateInput(String username, String email, String password, String validatePassword){
        ArrayList<Integer> invalidInput = new ArrayList<>();
        if(!validatePassword.equals(password)){
            invalidInput.add(10);
        }
        if (!password.matches("[\\S]{6,20}")){
            invalidInput.add(8);
        }
        if (userManager.usernameExists(username)) {
            invalidInput.add(5);
        } else if (!username.matches("[a-zA-Z0-9]+([_.][a-zA-Z0-9]+)*") || username.length() < 3) {
            invalidInput.add(6);
        }
        if (userManager.emailExists(email)) {
            invalidInput.add(2);
        } else if (!email.matches("[\\w]+(\\.[\\w]+)*@([a-zA-Z]+\\.)+[a-z]{2,}")) {
            invalidInput.add(3);
        }
        return invalidInput;
    }

    /*
     * Allows a new user to be created through user input.
     * Runs checks to ensure that usernames and emails are unique, and that login credentials
     * follow certain rules (e.g. no spaces, at least x characters long, etc).
     */
    private void inputProcess() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // email
        //systemPresenter.signUpSystem(1);
        try {
            String emailInput = bufferedReader.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (userManager.emailExists(emailInput)) {
                    invalidInput = true;
                    //systemPresenter.signUpSystem(2);
                    emailInput = bufferedReader.readLine();
                } else if (!emailInput.matches("[\\w]+(\\.[\\w]+)*@([a-zA-Z]+\\.)+[a-z]{2,}")) {
                    invalidInput = true;
                    //systemPresenter.signUpSystem(3);
                    emailInput = bufferedReader.readLine();
                }
            } while (invalidInput);
            //email = emailInput;
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        // username
        //systemPresenter.signUpSystem(4);
        try {
            String usernameInput = bufferedReader.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (userManager.usernameExists(usernameInput)) {
                    invalidInput = true;
                    //systemPresenter.signUpSystem(5);
                    usernameInput = bufferedReader.readLine();
                } else if (!usernameInput.matches("[a-zA-Z0-9]+([_.][a-zA-Z0-9]+)*") || usernameInput.length() < 3) {
                    invalidInput = true;
                    //systemPresenter.signUpSystem(6);
                    usernameInput = bufferedReader.readLine();
                }
            } while (invalidInput);
            //username = usernameInput;
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        // password + password confirmation
        //systemPresenter.signUpSystem(7);
        try {
            String pwInput1 = bufferedReader.readLine();
            while (!pwInput1.matches("[\\S]{6,20}")) {
                //systemPresenter.signUpSystem(8);
                pwInput1 = bufferedReader.readLine();
            }
            //systemPresenter.signUpSystem(9);
            String pwInput2 = bufferedReader.readLine();
            while (!pwInput2.equals(pwInput1)) {
                //systemPresenter.signUpSystem(10);
                pwInput2 = bufferedReader.readLine();
            }
            //password = pwInput1;
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        // home city
        //systemPresenter.signUpSystem(13);
        //try {
            //this.homeCity = bufferedReader.readLine();
        //} catch (IOException e) {
            //systemPresenter.exceptionMessage();
        //}
    }

    /**
     * Creates a new <NormalUser></NormalUser> based on input from <inputProcess()></inputProcess()>.
     *
     * @return the normal user that was just created
     */
    public NormalUser createNewNormal(String username, String email, String password, String homeCity) {
        userManager.createNormalUser(username, email, password, homeCity);
        return userManager.getNormalByUsername(username);
    }

    /**
     * Creates a new <AdminUser></AdminUser> based on input from <inputProcess()></inputProcess()>.
     */
    public void createNewAdmin(String username, String email, String password) {
        //systemPresenter.signUpSystem(12);
        //inputProcess();
        userManager.createAdminUser(username, email, password);
    }
}