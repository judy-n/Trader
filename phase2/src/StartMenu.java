import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets the user choose to sign up, log in, or exit the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-20
 */
public class StartMenu {
    private int userInput;

    /**
     * Creates a <StartMenu></StartMenu> that lets the user choose their next course of action through user input.
     */
    public StartMenu() {
        userInput = 0;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sp.startMenu(1);
        try {
            String temp = br.readLine();
            while (!temp.matches("[0-3]")) {
                sp.invalidInput();
                temp = br.readLine();
            }
            userInput = Integer.parseInt(temp);
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        if (userInput == 0) {
            try {
                br.close();
            } catch (IOException e) {
                sp.streamExceptionMessage();
            }
        }
    }

    /**
     * Getter for the option input by the user.
     *
     * @return the option input by the user
     */
    public int getUserInput() {
        return userInput;
    }
}
