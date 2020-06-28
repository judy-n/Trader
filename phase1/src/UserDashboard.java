import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * UserDashboard.java
 * Displays a dashboard once the user logs in.
 *
 * @author Ning Zhang
 * created 2020-06-27
 * last modified 2020-06-28
 */


public class UserDashboard {
    public User user;
    private int input;

    public UserDashboard(User u) {
        user = u;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What would you like to do: ");
        System.out.println(" 1 - see all items available for trade" +
                " \n 2 - edit inventory " +
                "\n 3 - edit wishlist " +
                "\n 4 - view trade requests " +
                "\n 5 - view latest trades");
        try {
            input = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("Plz try again.");
        }
        switch (input) {
            case 1:
                new ItemPresenter();
                break;

            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;

        }

    }
}
