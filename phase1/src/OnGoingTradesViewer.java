import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Shows all the ongoing trades for the user.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class OnGoingTradesViewer {
    private ItemManager im;
    private UserManager um;
    private TradeManager tm;
    private NormalUser currentUser;

    public OnGoingTradesViewer (NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        this.im = im;
        this.um = um;
        this.tm = tm;

        ArrayList<Trade> onGoingTrades = tm.getOngoingTrades(currentUser);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        sp.onGoingTrades(onGoingTrades);

    }
}
