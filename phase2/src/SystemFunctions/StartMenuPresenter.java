package SystemFunctions;

/**
 * The presenter used for the GUI StartMenu, returns strings to display
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-29
 * last modified 2020-08-05
 */
public class StartMenuPresenter {

    /**
     * Contains the labels for elements on the start menu screen of the program.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String startMenu(int input) {
        switch (input) {
            case 1:
                return ("----- WELCOME -----");
            case 2:
                return ("Exiting the program. Hope to see you again soon!");
            case 3:
                return ("back");
            case 4:
                return ("Demo our program");
            case 5:
                return ("Exit the program");
            default:
                return null;
        }
    }

    /**
     * Contains the labels for elements on the sign-up screen of the program.
     * Includes the error messages for invalid user credentials.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String signUpSystem(int input) {
        switch (input) {
            case 0:
                return ("Sign-up");
            case 1:
                return ("Please enter an email: ");
            case 2:
                return ("Email is already associated with an account! Please enter a different email.");
            case 3:
                return ("That's not an email address! Please enter a valid email.");
            case 4:
                return ("<html>Enter an username <br/> (min 3 characters, <br/> numbers/letters <br/> or _. in between)<html> ");
            case 5:
                return ("Username already exists! Please enter a different username.");
            case 6:
                return ("Invalid username.");
            case 7:
                return ("<html>Please enter a password <br/> (6-20 characters)<html>");
            case 8:
                return ("Invalid password.");
            case 9:
                return ("Please verify your password.");
            case 10:
                return ("Passwords do not match.");
            case 11:
                return ("Thank you for signing up! You are now logged in.");
            case 12:
                return ("Username can't be empty!");
            case 13:
                return ("Please enter a home city: ");
            case 14:
                return ("Show password");
            case 15:
                return ("Email can't be empty!");
            case 16:
                return ("Password can't be empty!");
            case 17:
                return ("Please verify your password!");
            default:
                return null;
        }
    }


    /**
     * Contains the labels for elements on the login screen of the program.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String loginSystem(int input) {
        switch (input) {
            case 0:
                return ("Login");
            case 1:
                return ("Enter username or email: ");
            case 2:
                return ("Username/email does not exist in our database!");
            case 3:
                return ("Please enter your password: ");
            case 4:
                return ("Password does not match username/email!");
            case 5:
                return ("Welcome, you are now logged in.");
            default:
                return null;
        }
    }
}
