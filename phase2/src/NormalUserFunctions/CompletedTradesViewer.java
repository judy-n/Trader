package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;
import Entities.Item;
import Entities.Trade;
import SystemFunctions.MenuItem;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Shows the user their three most recently completed trades and their top three most frequent trading partners.
 * A presenter separate from SystemPresenter because reading user input is not required in this class.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-30
 */
public class CompletedTradesViewer extends MenuItem {
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;

    /**
     * Creates a <CompletedTradesViewer></CompletedTradesViewer> that lets a normal user view their three most recent
     * completed trades or three most frequent trade partners.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public CompletedTradesViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
    }

    /**
     * Displays the three most recent trades for a normal user.
     */
    public void viewRecentThreeTrades() {

        String currUsername = currentUser.getUsername();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Trade[] recentThree = tradeManager.getRecentThreeTrades(currUsername);

        System.out.println("\nHere are your three most recently completed trades:");
        if (recentThree[0] == null) {
            System.out.println("Nothing here yet!");
        } else {
            for (int i = 0; i < 3; i++) {

                String tradePrint = "empty";

                if (recentThree[i] != null) {
                    Trade trade = recentThree[i];
                    LocalDateTime meeting = trade.getFinalMeetingDateTime();
                    String otherUsername = trade.getOtherUsername(currUsername);

                    /*
                     * tempItemIDs[0] - ID of item lent by current user
                     * tempItemIDs[1] - ID of item borrowed by current user
                     */
                    long[] tempItemIDs = {trade.getLentItemID(currUsername), trade.getLentItemID(otherUsername)};

                    if (tempItemIDs[0] == 0) {
                        Item itemBorrowed = itemManager.getItem(tempItemIDs[1]);
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currUsername) + "you borrowed [" + itemBorrowed.getName() + "]";
                    } else if (tempItemIDs[1] == 0) {
                        Item itemLent = itemManager.getItem(tempItemIDs[0]);
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currUsername) + "you lent [" + itemLent.getName() + "]";
                    } else {
                        Item[] tempItems = {itemManager.getItem(tempItemIDs[0]), itemManager.getItem(tempItemIDs[1])};
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currUsername) + "you lent [" +
                                tempItems[0].getName() + "] for [" + tempItems[1].getName() + "]";
                    }
                }
                System.out.println((i + 1) + ". " + tradePrint);
            }
        }
        close();
    }

    /**
     * Displays the top three frequent trade partners for a normal user.
     */
    public void viewTopThreeTrader() {
        String[] topTraders = tradeManager.getFrequentTradePartners(currentUser.getUsername());
        System.out.println("\nHere are your top 3 most frequent trade partners:");
        int index = 1;
        if (topTraders[0].equals("empty")) {
            System.out.println("No one yet!");
        } else {
            for (String s : topTraders) {
                System.out.println(index + ". " + s);
                index++;
            }
        }
        close();
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }

    @Override
    public String getTitle() {
        return "Completed Trades Viewer";
    }
}