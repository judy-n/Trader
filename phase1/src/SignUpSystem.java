import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SignUpSystem.java
 * Lets the user sign up.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */
public class SignUpSystem {
    private String username;
    private String email;
    private String password;
    private UserManager um;
    private NormalUser newUser;
    private boolean isSignedUp;
    /**
     * SignUpSystem
     * Creates a sign up system that takes in user input
     */
    public SignUpSystem(UserManager um) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.um = um;
        isSignedUp = false;
        System.out.println("\n--- Signup ---");
        System.out.print("Please enter an email: ");
        try {
            String emailInput = br.readLine();
            boolean invalidInput;
            do {
                invalidInput = false;
                if (um.emailExists(emailInput)) {
                    invalidInput = true;
                    System.out.print("Email is already associated with an account! Please enter a different email: ");
                    emailInput = br.readLine();
                }
//              valid email check commented out for quicker testing
               else if (!(emailInput.contains("@") && emailInput.contains(".")) || emailInput.contains(" ")) {
                    invalidInput = true;
                    System.out.print("That's not an email address! Please enter a valid email: ");
                    emailInput = br.readLine();
                }
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
                if (um.usernameExists(usernameInput)) {
                    invalidInput = true;
                    System.out.print("Username already exists! Please enter a different username: ");
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
        newUser = new NormalUser(username, email, password);
        um.addUser(newUser);
    }
    public NormalUser getNewUser(){
        return newUser;
    }

    public boolean getSignedUp(){
        return isSignedUp;
    }

}