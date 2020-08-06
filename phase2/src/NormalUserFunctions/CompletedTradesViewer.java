package NormalUserFunctions;

import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;
import Entities.Trade;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Helps show the user their three most recently completed trades and their top three most frequent trading partners
 * by formatting the information into easy-to-read statements and putting them in order.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-05
 */
public class CompletedTradesViewer {
    private String currUsername;
    private ItemManager itemManager;
    private TradeManager tradeManager;

    /**
     * Creates a <CompletedTradesViewer></CompletedTradesViewer> with the given normal username and item/trade managers.
     *
     * @param currUsername   the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param tradeManager the system's trade manager
     */
    public CompletedTradesViewer(String currUsername, ItemManager itemManager, TradeManager tradeManager) {
        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
    }

    /**
     * Formats a normal user's three most recent trades into string representations to be displayed.
     *
     * @return string representations of the three most recent trades for a normal user
     */
    public String[] getRecentThreeTradesStrings() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Trade[] recentThree = tradeManager.getRecentThreeTrades(currUsername);
        String[] tradeStrings = new String[3];

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
                tradeStrings[i] = ((i + 1) + ". " + tradePrint);
            }
        }
        return tradeStrings;
    }

    /**
     * Formats a normal user's top three frequent trade partners into string representations to be displayed.
     *
     * @return string representations of the top three frequent trade partners for a normal user
     */
    public String[] getTopThreeTraderStrings() {
        String[] topTraders = tradeManager.getFrequentTradePartners(currUsername);
        System.out.println("\nHere are your top 3 most frequent trade partners:");
        for (int i = 0; i < 3; i++) {
            topTraders[i] = ((i + 1) + ". " + topTraders[0]);
        }
        return topTraders;
    }
}