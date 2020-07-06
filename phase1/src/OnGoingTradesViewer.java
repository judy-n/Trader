import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Shows all the ongoing trades for the user.
 *
 * @author Kushagra Mehta
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class OnGoingTradesViewer {
    private HashMap<String[], long[]> allTrades;
    private ItemManager im;
    private UserManager um;
    private NormalUser currentUser;

    public OnGoingTradesViewer (NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        this.im = im;
        this.um = um;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        sp.ongoingTrades(currentUser);
        // should add stuff to view the meeting time (if confirmed) of each trade and
        // option to suggest a meeting time
    }
}
