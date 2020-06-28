import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * StartMenu.java
 * Lets the user choose to sign up, log in, or exit the program.
 * @author Ning Zhang
 * @author Yingjia Liu
 * created 2020-06-26
 * last modified 2020-06-27
 */
public class StartMenu {
    public StartMenu() {
        String userInput = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("---------- Welcome ---------- \nWould you like to:");
        do {
            System.out.println(" 1) Sign up \n 2) Log in \n 3) Exit the program" +
                    "\nPlease select an option by entering the number 1, 2, or 3.");
            try {

                userInput = br.readLine();

                if (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3")) {
                    System.out.println("! Please enter a valid option.");
                }

            } catch (IOException e) {
                System.out.println("Error reading user input.");
            }
        } while (!userInput.equals("1") && !userInput.equals("2") && !userInput.equals("3"));

        if (userInput.equals("1")) {
            new SignUpSystem();
        } else if (userInput.equals("2")) {
            new LoginSystem();
        } else {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream.");
            }
            System.out.println("Exiting the program. Goodbye!");
            System.exit(0);
        }
    }
}
