import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Shows all the completed trades for the user, sorted by date.
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class CompletedTradesViewer {
    private HashMap<String[], long[]> completedTrades;
    private ItemManager im;
    private UserManager um;
    private NormalUser currentUser;

    public CompletedTradesViewer (NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        this.im = im;
        this.um = um;
        String[] a;
        NormalUser trader;
        completedTrades = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();

        // loop through all trades and collect the ones that are completed.

        // getter for completed trades
    }
}