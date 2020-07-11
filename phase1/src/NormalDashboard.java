import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Displays a dashboard once a normal user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-11
 */


public class NormalDashboard {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private int input;

    /**
     * Creates a NormalDashboard with the given logged-in user and item/user/trade managers.
     *
     * @param user current normal user that is logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public NormalDashboard(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String regex = "[0-7]";

        if (currentUser.getIsFrozen()) {
            regex = "[0-8]";
            sp.normalDashboard(2);
        } else {
            sp.normalDashboard(1);
        }
        try {
            String temp = br.readLine();
            while (!temp.matches(regex)) {
                sp.invalidInput();
                temp = br.readLine();
            }
            input = Integer.parseInt(temp);
        } catch (IOException e) {
            sp.exceptionMessage();
        }

        switch (input) {
            case 0:
                try {
                    br.close();
                } catch (IOException e) {
                    sp.exceptionMessage();
                }
                sp.normalDashboard(3);
                break;
            case 1:
                new CatalogViewer(currentUser, itemManager, userManager, tradeManager);
                break;

            case 2:
                new InventoryEditor(currentUser, itemManager, userManager, tradeManager);
                break;

            case 3:
                new WishlistEditor(currentUser, itemManager, userManager, tradeManager);
                break;

            case 4:
                new TradeRequestViewer(currentUser, itemManager, userManager, tradeManager);
                break;

            case 5:
                // view ongoing trades
                // use getOngoingTrades in TradeManager
                // in the new class created for this option, handle the whole meeting suggestion thing?
                // make sure you check if the meeting is a suggestion or if it's already agreed upon
                // before editing a meeting check if they've reached the max number of times they can edit (meetingEditMax in NormalUser)
                // if both users have reached their max, the recipient of the last suggestion either accepts the suggestion or cancels the trade

                // btw what to do about cancelled trades? (when both users have reached their max edits and the last suggestion is rejected)
                // >> have to let both users know it got cancelled
                new OngoingTradesViewer(currentUser, itemManager, userManager, tradeManager);
                break;

            case 6:
                // view most recent three *completed* trades
                new CompletedTradesViewer(currentUser, itemManager, userManager, tradeManager).viewRecentThreeTrades();
                break;

            case 7:
                // view top three most frequent trading partners (only counts if trades are completed)
                new CompletedTradesViewer(currentUser, itemManager, userManager, tradeManager).viewTopThreeTrader();
                break;

            case 8:
                // unfreeze request option for frozen account
                new AccountUnfreezer(currentUser, itemManager, userManager, tradeManager);
                break;
        }
    }
}
