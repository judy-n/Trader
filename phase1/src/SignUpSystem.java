import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SignUpSystem.java
 * Lets the user sign up.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * created 2020-06-26
 * last modified 2020-06-28
 */
public class SignUpSystem {
    public String username;
    public String email;
    public String password;

    public SignUpSystem() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n--- Signup ---");
        System.out.print("Please enter an email: ");
        try {
            String emailInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (UserDatabase.emailExists(emailInput)) {
                    invalidInput = true;
                    System.out.print("Email is already associated with an account! Would you like to log in instead? (Y/N): ");
                    String optionInput;
                    do {
                        optionInput = br.readLine();
                        if (optionInput.equalsIgnoreCase("Y")) {
                            new LoginSystem();
                        } else if (optionInput.equalsIgnoreCase("N")) {
                            System.out.print("Please enter a different email: ");
                        } else {
                            System.out.print("Invalid input. Please enter Y or N: ");
                        }
                    } while (!optionInput.equalsIgnoreCase("Y") &&
                            !optionInput.equalsIgnoreCase("N"));

                    emailInput = br.readLine();
                }
//              valid email check commented out for quicker testing
//                else if (!(emailInput.contains("@") && emailInput.contains(".")) || emailInput.contains(" ")) {
//                    invalidInput = true;
//                    System.out.print("That's not an email address! Please enter a valid email: ");
//                    emailInput = br.readLine();
//                }
            } while (invalidInput);
            email = emailInput;
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        System.out.print("Please enter an username: ");
        try {
            String usernameInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (UserDatabase.usernameExists(usernameInput)) {
                    invalidInput = true;
                    System.out.print("Username already exists! Would you like to log in instead? (Y/N): ");
                    String optionInput;
                    do {
                        optionInput = br.readLine();
                        if (optionInput.equalsIgnoreCase("Y")) {
                            new LoginSystem();
                        } else if (optionInput.equalsIgnoreCase("N")) {
                            System.out.print("Please enter a different username: ");
                        } else {
                            System.out.print("Invalid input. Please enter Y or N: ");
                        }
                    } while (!optionInput.equalsIgnoreCase("Y") &&
                            !optionInput.equalsIgnoreCase("N"));
                    usernameInput = br.readLine();
                } else if (usernameInput.trim().isEmpty()) {
                    invalidInput = true;
                    System.out.print("Invalid username. Please try again: ");
                    usernameInput = br.readLine();
                }
            } while (invalidInput);
            username = usernameInput;
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        System.out.print("Please enter a password: ");
        try {
            String pwInput1 = br.readLine();
            while (pwInput1.trim().isEmpty()) {
                System.out.print("Invalid password. Please try again: ");
                pwInput1 = br.readLine();
            }
            System.out.print("Please verify your password: ");
            String pwInput2 = br.readLine();
            while (!pwInput1.equals(pwInput2)) {
                System.out.print("Passwords do not match. Please try again: ");
                pwInput2 = br.readLine();
            }
            password = pwInput1;
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        System.out.println("\n Thank you for signing up! \n You are now logged in. \n");
        User newUser = new User(username, email, password);
        UserDatabase.addUser(newUser);
        new UserDashboard(newUser);
    }
}