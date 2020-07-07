import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Shows all the completed trades for the user, sorted by date.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-07
 */
public class CompletedTradesViewer {
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;

    public CompletedTradesViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        //this doesn't need SystemPresent since it is a presenter
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
    }

    public void viewRecentThreeTrades() {

        String currUsername = currentUser.getUsername();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        Trade[] recentThree = tradeManager.getRecentThreeTrades(currUsername);

        System.out.println("Here are your three most recent completed trades:");
        if (recentThree[0] == null) {
            System.out.println("Nothing here yet!");
        } else {
            int index = 0;
            while (index <=2 && recentThree[index] != null) {
                Trade trade = recentThree[index];
                LocalDateTime meeting = trade.getFinalMeetingDateTime();
                String otherUsername = trade.getOtherUsername(currUsername);
                long[] tempItemIDs = {trade.getLentItemID(currUsername), trade.getLentItemID(otherUsername)};
                Item[] tempItems = {itemManager.getApprovedItem(tempItemIDs[0]), itemManager.getApprovedItem(tempItemIDs[1])};

                String tradePrint = (index + 1) + ") " + meeting.format(formatter) + "   " +
                        trade.toString(currUsername) + "you lent " +
                        tempItems[0].getName() + " for " + tempItems[1].getName();

                System.out.println(tradePrint);
                index++;
            }
            //jesus
        }
        close();
    }

    public void viewFrequentTrader() {
        String[] frequentTrader = tradeManager.getFrequentTradePartners(currentUser.getUsername());
        System.out.println("Here are your top most frequent trade partners:");
        for (int i = 0; i < frequentTrader.length; i++) {
            System.out.println((i + 1) + ". " + frequentTrader[i]);
        }
        close();
    }


    public void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}