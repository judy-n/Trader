import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * StartMenu.java
 * Lets the user choose to sign up, log in, or exit the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-04
 */
public class StartMenu {
    private int userInput;

    /**
     * Class constructor.
     * Creates a start menu that takes in user input.
     */
    public StartMenu() {
        //This does not need system presenter because this is a presenter
        userInput = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(" 1) Sign up \n 2) Log in \n 3) Exit the program");
        System.out.print("Please select an option by entering the number 1, 2, or 3: ");
        try {
            userInput = Integer.parseInt(br.readLine());

            while (!(userInput == 1) && !(userInput == 2) && !(userInput == 3)) {
                System.out.print("Invalid input. Please enter 1, 2, or 3: ");
                userInput = Integer.parseInt(br.readLine());
            }

        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        if (userInput == 3) {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream.");
            }
            System.out.println("Exiting the program. Hope to see you again soon!");
            System.exit(0);
        }
    }

    public int getChoice() {
        return userInput;
    }
}
