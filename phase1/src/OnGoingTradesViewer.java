import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Shows all trade requests received/sent by a user and lets them take actions through user input.
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class OnGoingTradesViewer {
    private HashMap<String[], long[]> allTrades
    private ItemManager im;
    private UserManager um;
    private NormalUser currentUser;

    public OnGoingTradesViewer (NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        this.im = im;
        this.um = um;
        String[] a;
        NormalUser trader;
        allTrades = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();

        //loop through all trades and collect the ones that aren't completed.

        // getter for ongoing trades
    }
}
