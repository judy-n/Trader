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

public class OngoingTradesViewer {
    private ItemManager im;
    private UserManager um;
    private TradeManager tm;
    private NormalUser currentUser;

    public OngoingTradesViewer (NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        this.im = im;
        this.um = um;
        this.tm = tm;

        ArrayList<Trade> ongoingTrades = tm.getOngoingTrades(currentUser);
        ArrayList<Item[]> tradeItems = new ArrayList<>();
        for (Trade t : ongoingTrades) {
            String otherUsername = t.getOtherUsername(currentUser.getUsername());
            long[] tempItemIDs = {t.getLentItemID(currentUser.getUsername()), t.getLentItemID(otherUsername)};
            Item[] tempItems = {im.getApprovedItem(tempItemIDs[0]), im.getApprovedItem(tempItemIDs[1])};
            tradeItems.add(tempItems);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        sp.ongoingTrades(ongoingTrades, tradeItems, currentUser.getUsername());

    }
}
