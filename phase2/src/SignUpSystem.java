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
 * last modified 2020-07-22
 */
public class SignUpSystem {
    private String username;
    private String email;
    private String password;
    private String homeCity;
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

    /*
     * Allows a new user to be created through user input.
     * Runs checks to ensure that usernames and emails are unique, and that login credentials
     * follow certain rules (e.g. no spaces, at least x characters long, etc).
     */
    private void inputProcess() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        systemPresenter.signUpSystem(1);
        try {
            String emailInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (userManager.emailExists(emailInput)) {
                    invalidInput = true;
                    systemPresenter.signUpSystem(2);
                    emailInput = br.readLine();
                } else if (!emailInput.matches("[\\w]+(\\.[\\w]+)*@([a-zA-Z]+\\.)+[a-z]{2,}")) {
                    invalidInput = true;
                    systemPresenter.signUpSystem(3);
                    emailInput = br.readLine();
                }
            } while (invalidInput);
            email = emailInput;
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        systemPresenter.signUpSystem(4);
        try {
            String usernameInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (userManager.usernameExists(usernameInput)) {
                    invalidInput = true;
                    systemPresenter.signUpSystem(5);
                    usernameInput = br.readLine();
                } else if (!usernameInput.matches("[a-zA-Z0-9]+([_.][a-zA-Z0-9]+)*") || usernameInput.length() < 3) {
                    invalidInput = true;
                    systemPresenter.signUpSystem(6);
                    usernameInput = br.readLine();
                }
            } while (invalidInput);
            username = usernameInput;
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

        systemPresenter.signUpSystem(7);
        try {
            String pwInput1 = br.readLine();
            while (!pwInput1.matches("[\\S]{6,20}")) {
                systemPresenter.signUpSystem(8);
                pwInput1 = br.readLine();
            }
            systemPresenter.signUpSystem(9);
            String pwInput2 = br.readLine();
            while (!pwInput2.equals(pwInput1)) {
                systemPresenter.signUpSystem(10);
                pwInput2 = br.readLine();
            }
            password = pwInput1;
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
        homeCityInputProcess(br);
    }

    /**
     * Creates a new <NormalUser></NormalUser> based on input from <inputProcess()></inputProcess()>.
     *
     * @return the normal user that was just created
     */
    public NormalUser createNewNormal() {
        systemPresenter.signUpSystem(0);
        inputProcess();
        userManager.createNormalUser(username, email, password, homeCity);
        systemPresenter.signUpSystem(11);
        return userManager.getNormalByUsername(username);
    }

    /**
     * Creates a new <AdminUser></AdminUser> based on input from <inputProcess()></inputProcess()>.
     */
    public void createNewAdmin() {
        systemPresenter.signUpSystem(12);
        inputProcess();
        userManager.createAdminUser(username, email, password);
    }

    private void homeCityInputProcess(BufferedReader bufferedReader) {
        systemPresenter.signUpSystem(13);
        try {
             this.homeCity = bufferedReader.readLine();
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
    }

}