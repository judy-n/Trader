package NormalUserFunctions;

import SystemManagers.NotificationSystem;
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
 * last modified 2020-08-03
 */
public class CompletedTradesViewer extends MenuItem {
    private String currentUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    /**
     * Creates a <CompletedTradesViewer></CompletedTradesViewer> with the given normal user,
     * item/user/trade managers, and notification system.
     * Lets a normal user view their three most recent completed trades or three most frequent trade partners.
     *
     * @param currentUsername   the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public CompletedTradesViewer(String currentUsername, ItemManager itemManager, UserManager userManager,
                                 TradeManager tradeManager, NotificationSystem notifSystem) {
        this.currentUsername = currentUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;
    }

    /**
     * Displays the three most recent trades for a normal user.
     */
    public void viewRecentThreeTrades() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Trade[] recentThree = tradeManager.getRecentThreeTrades(currentUsername);

        System.out.println("\nHere are your three most recently completed trades:");
        if (recentThree[0] == null) {
            System.out.println("Nothing here yet!");
        } else {
            for (int i = 0; i < 3; i++) {

                String tradePrint = "empty";

                if (recentThree[i] != null) {
                    Trade trade = recentThree[i];
                    LocalDateTime meeting = trade.getFinalMeetingDateTime();
                    String otherUsername = trade.getOtherUsername(currentUsername);

                    /*
                     * tempItemIDs[0] - ID of item lent by current user
                     * tempItemIDs[1] - ID of item borrowed by current user
                     */
                    long[] tempItemIDs = {trade.getLentItemID(currentUsername), trade.getLentItemID(otherUsername)};

                    if (tempItemIDs[0] == 0) {
                        Item itemBorrowed = itemManager.getItem(tempItemIDs[1]);
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currentUsername) + "you borrowed [" + itemBorrowed.getName() + "]";
                    } else if (tempItemIDs[1] == 0) {
                        Item itemLent = itemManager.getItem(tempItemIDs[0]);
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currentUsername) + "you lent [" + itemLent.getName() + "]";
                    } else {
                        Item[] tempItems = {itemManager.getItem(tempItemIDs[0]), itemManager.getItem(tempItemIDs[1])};
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currentUsername) + "you lent [" +
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
        String[] topTraders = tradeManager.getFrequentTradePartners(currentUsername);
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
        new NormalDashboard(currentUsername, itemManager, userManager, tradeManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return "Completed Trades Viewer";
    }
}