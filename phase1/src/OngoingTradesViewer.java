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
 * last modified 2020-07-08
 */
public class OngoingTradesViewer {
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;

    public OngoingTradesViewer (NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String currUsername = currentUser.getUsername();
        ArrayList<Trade> ongoingTrades = tradeManager.getOngoingTrades(currUsername);
        ArrayList<Item[]> tradeItems = new ArrayList<>();

        for (Trade t : ongoingTrades) {
            String otherUsername = t.getOtherUsername(currUsername);
            long[] tempItemIDs = {t.getLentItemID(currUsername), t.getLentItemID(otherUsername)};
            Item[] tempItems = {itemManager.getApprovedItem(tempItemIDs[0]), itemManager.getApprovedItem(tempItemIDs[1])};
            tradeItems.add(tempItems);
        }

        sp.ongoingTrades(ongoingTrades, tradeItems, currUsername);

    }
}
