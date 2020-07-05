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
 * last modified 2020-07-04
 */

public class LoginSystem {
    private String username;
    private String email;
    private String validPw;
    private User user;
    private UserManager um;
    private boolean isLoggedIn;
    private int optionInput;
    /**
     * LoginSystem
     * Creates a log in system that takes in user input
     */
    public LoginSystem(UserManager um) {
        isLoggedIn = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.um = um;
        System.out.println("\n--- Login ---");
        System.out.println("Would you like to login with 1) username or 2) email?");
        try {
            optionInput = Integer.parseInt(br.readLine());
        if (!(optionInput == 1) && !(optionInput == 2)) {
            System.out.println("Invalid input. Please try again.");
            optionInput = Integer.parseInt(br.readLine());
        }
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        if (optionInput==1) {
            System.out.print("Please enter your username: ");
            try {
                username = br.readLine();
                while (!um.usernameExists(username)) {
                    System.out.print("Username does not exist in our database! Please try again: ");
//                    System.out.print("Username does not exist in our database!! Would you like to sign up instead? (Y/N): ");
//                    String optionInput2;
//                    do {
//                        optionInput2 = br.readLine();
//                        if (optionInput2.equalsIgnoreCase("Y")) {
//                            new SignUpSystem(um);
//                        } else if (optionInput2.equalsIgnoreCase("N")) {
//                            System.out.print("Please enter the correct username: ");
//                        } else {
//                            System.out.print("Invalid input. Please enter Y or N: ");
//                        }
//                    } while (!optionInput2.equalsIgnoreCase("Y") &&
//                            !optionInput2.equalsIgnoreCase("N"));
                    username = br.readLine();
                }
                validPw = um.usernamePassword(username);
                user = um.getUserByUsername(username);
            } catch (IOException e) {
                System.out.println("Error reading user input.");
            }
        } else {
            System.out.print("Please enter your email: ");
            try {
                email = br.readLine();
                while (!um.emailExists(email)) {
                    System.out.print("Email does not exist in our database! Please try again: ");
//                    System.out.print("Email does not exist in our database! Would you like to sign up instead? (Y/N): ");
//                    String optionInput2;
//                    do {
//                        optionInput2 = br.readLine();
//                        if (optionInput2.equalsIgnoreCase("Y")) {
//                            new SignUpSystem(um);
//                        } else if (optionInput2.equalsIgnoreCase("N")) {
//                            System.out.print("Please enter the correct email: ");
//                        } else {
//                            System.out.print("Invalid input. Please enter Y or N: ");
//                        }
//                    } while (!optionInput2.equalsIgnoreCase("Y") &&
//                            !optionInput2.equalsIgnoreCase("N"));
                    email = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("Error reading user input.");
            }
            validPw = um.emailPassword(email);
            user = um.getUserByEmail(email);
        }

        System.out.print("Please enter your password: ");
        try {
            String password = br.readLine();
            while (!password.equals(validPw)) {
                System.out.print("Password does not match email/username! Please try again: ");
                password = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }
        System.out.println("\n Logged in! \n");
        isLoggedIn = true;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public User getUser() {
        return user;
    }
}
