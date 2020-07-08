import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets the user choose to sign up, log in, or exit the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-08
 */
public class StartMenu {
    private int userInput;

    /**
     * Class constructor.
     * Creates a start menu that takes in user input.
     */
    public StartMenu() {
        userInput = 0;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        sp.startMenu(1);
        try {
            String temp = br.readLine();
            while (!temp.matches("[1-3]")){
                sp.invalidInput();
                temp = br.readLine();
            }
            userInput = Integer.parseInt(temp);
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    public int getUserInput() {
        return userInput;
    }
}
