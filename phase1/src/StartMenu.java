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
 * last modified 2020-07-06
 */
public class StartMenu {
    private int userInput;

    /**
     * Class constructor.
     * Creates a start menu that takes in user input.
     */
    public StartMenu() {
        userInput = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();

        sp.startMenu(1);
        try {
            userInput = Integer.parseInt(br.readLine());

            while (!(userInput == 1) && !(userInput == 2) && !(userInput == 3)) {
                sp.invalidInput();
                userInput = Integer.parseInt(br.readLine());
            }

        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    public int getUserInput() {
        return userInput;
    }
}
