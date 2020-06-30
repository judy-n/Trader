import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * UserDashboard.java
 * Displays a dashboard once the user logs in.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-29
 */


public class UserDashboard {
    public User user;
    private int input;

    /**
     * UserDashboard
     * Creates a userDashboard with a user
     * @param u user
     */
    public UserDashboard(User u) {
        user = u;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What would you like to do: ");
        System.out.println(" 1 - see all items available for trade" +
                "\n 2 - edit inventory " +
                "\n 3 - edit wishlist " +
                "\n 4 - view trade requests " +
                "\n 5 - view latest trades " +
                "\n 0 - log out ");
        try {
            input = Integer.parseInt(br.readLine());
            while(input<0 || input>5){
                System.out.println("Invalid input try again.");
                input = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Plz try again.");
        }
        switch (input) {
            case 0:
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing input stream.");
                }
                System.out.println("Logging out of the program. Bye!");
                System.exit(0);

            case 1:
                new ItemPresenter(user);
                break;

            case 2:

                break;
            case 3:

                break;

            case 4:
                new TradeRequestPresenter(user);
                break;
            case 5:

                break;

        }

    }
}
