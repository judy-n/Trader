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
 * last modified 2020-07-08
 */
public class CompletedTradesViewer {
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;

    /**
     * Creates a completed trades viewer that lets an non admin user view their most recent
     * completed trades or trade partners
     * @param user non admin user
     * @param im the system's item manager
     * @param um the system's user manager
     * @param tm the system's trade manager
     */
    public CompletedTradesViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        //test v
//        Item i = new Item("4.0 gpa", "hi hi", "UofT");
//        Item i2 = new Item("your soul", "hi hi", "yingjia");
//        NormalUser u = um.getNormalByUsername("UofT");
//        NormalUser u2 = um.getNormalByUsername("yingjia");
//        im.addPendingItem(i);
//        u.addPendingInventory(i.getID());
//        im.approveItem(i);
//        u.addInventory(i.getID());
//        im.addPendingItem(i2);
//        u2.addPendingInventory(i2.getID());
//        im.approveItem(i2);
//        u2.addInventory(i2.getID());
//        String[] usernames = {"UofT", "yingjia"};
//        long[] IDs = {i.getID(), i2.getID()};
//        PermanentTrade t = new PermanentTrade(usernames, IDs, LocalDateTime.now(), "address");
//        tradeManager.addTrade(t);
//
//        Item i3 = new Item("doge", "hi hi", "Angelina");
//        NormalUser u3 = um.getNormalByUsername("Angelina");
//
//        String dateString = "2020-07-08T17:45:55.9483536";
//        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE_TIME;
//        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter1);
//        im.approveItem(i3);
//        u3.addInventory(i3.getID());
//        im.approveItem(i3);
//        u3.addInventory(i3.getID());
//        String[] usernames1 = {"Angelina", "yingjia"};
//        long[] IDs1 = {i3.getID(), 0};
//        TemporaryTrade t1 = new TemporaryTrade(usernames1, IDs1, dateTime, "address");
//        tradeManager.addTrade(t1);
    }

    /**
     * This method lets the non admin user viewer their three most recent trades
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

                    //index 0 - item (ID) lent by current user ; index 1 - item (ID) borrowed by current user
                    long[] tempItemIDs = {trade.getLentItemID(currUsername), trade.getLentItemID(otherUsername)};

                    if (tempItemIDs[0] == 0) {
                        Item itemBorrowed = itemManager.getApprovedItem(tempItemIDs[1]);
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currUsername) + "you borrowed [" + itemBorrowed.getName() + "]";
                    } else if (tempItemIDs[1] == 0) {
                        Item itemLent = itemManager.getApprovedItem(tempItemIDs[0]);
                        tradePrint = meeting.format(formatter) + "   " +
                                trade.toString(currUsername) + "you lent [" + itemLent.getName() + "]";
                    } else {
                        Item[] tempItems = {itemManager.getApprovedItem(tempItemIDs[0]), itemManager.getApprovedItem(tempItemIDs[1])};
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
     * This method lets the non admin user view their top three frequent trade partners
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
}