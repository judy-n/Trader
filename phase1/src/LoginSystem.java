import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * LoginSystem.java
 * Logs the user into the system.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */

public class LoginSystem {
    public String username;
    private String email;
    private String validPw;
    private User user;

    /**
     * LoginSystem
     * Creates a log in system that takes in user input
     */

    public LoginSystem() {
        String optionInput = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n--- Login ---");
        do {
            System.out.println("Would you like to login with username or email?");
            try {
                optionInput = br.readLine();
            } catch (IOException e) {
                System.out.println("Error reading user input.");
            }
            if (!optionInput.equals("username") && !optionInput.equals("email")) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!optionInput.equals("username") && !optionInput.equals("email"));

        if (optionInput.equals("username")) {
            System.out.print("Please enter your username: ");
            try {
                username = br.readLine();
                while (!UserDatabase.usernameExists(username)) {
                    System.out.print("Username does not exist in our database!! Would you like to sign up instead? (Y/N): ");
                    String optionInput2;
                    do {
                        optionInput2 = br.readLine();
                        if (optionInput2.equalsIgnoreCase("Y")) {
                            new SignUpSystem();
                        } else if (optionInput2.equalsIgnoreCase("N")) {
                            System.out.print("Please enter the correct username: ");
                        } else {
                            System.out.print("Invalid input. Please enter Y or N: ");
                        }
                    } while (!optionInput2.equalsIgnoreCase("Y") &&
                            !optionInput2.equalsIgnoreCase("N"));
                    username = br.readLine();
                }
                validPw = UserDatabase.usernamePassword(username);
                user = UserDatabase.getUserByUsername(username);
            } catch (IOException e) {
                System.out.println("Error reading user input.");
            }
        } else {
            System.out.print("Please enter your email: ");
            try {
                email = br.readLine();
                while (!UserDatabase.emailExists(email)) {
                    System.out.print("Email does not exist in our database! Would you like to sign up instead? (Y/N): ");
                    String optionInput2;
                    do {
                        optionInput2 = br.readLine();
                        if (optionInput2.equalsIgnoreCase("Y")) {
                            new SignUpSystem();
                        } else if (optionInput2.equalsIgnoreCase("N")) {
                            System.out.print("Please enter the correct email: ");
                        } else {
                            System.out.print("Invalid input. Please enter Y or N: ");
                        }
                    } while (!optionInput2.equalsIgnoreCase("Y") &&
                            !optionInput2.equalsIgnoreCase("N"));
                    email = br.readLine();
                }
            } catch (IOException e) {
                System.out.println("Error reading user input.");
            }
            validPw = UserDatabase.emailPassword(email);
            user = UserDatabase.getUserByEmail(email);
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
        new UserDashboard(user);
    }
}
