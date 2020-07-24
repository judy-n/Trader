import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * Shows all trade requests received/sent by a user and lets them take actions through user input.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-29
 * last modified 2020-07-24
 */
public class TradeRequestViewer extends MenuItem{
    private LinkedHashMap<String[], long[]> initiatedTrades;
    private LinkedHashMap<String[], long[]> receivedTrades;

    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private String[] keyToRemove;

    private SystemPresenter sp;
    private BufferedReader br;

    /**
     * Creates an <TradeRequestViewer></TradeRequestViewer> with the given normal user and item/user/trade managers.
     * Prints to the screen all trade requests received/sent by the given normal user and options on actions to take
     * using <SystemPresenter></SystemPresenter>.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public TradeRequestViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        sp = new SystemPresenter();
        br = new BufferedReader(new InputStreamReader(System.in));
        if (currentUser.getIsFrozen()) {
            caseUserIsFrozen();
        } else {
            caseUserNotFrozen();
        }
        close();
    }

    private void caseUserIsFrozen() {
        sp.tradeRequestViewer(3);
    }

    private void caseUserNotFrozen() {
        initiatedTrades = new LinkedHashMap<>();
        receivedTrades = new LinkedHashMap<>();

        String[] a;
        NormalUser trader;
        long firstItem;
        long secondItem = 0;

        /* separate into initiated trades and received trades */
        for (String[] key : currentUser.getTradeRequests().keySet()) {
            if (currentUser.getUsername().equals(key[0])) {
                initiatedTrades.put(key, currentUser.getTradeRequests().get(key));
            } else if (!userManager.getNormalByUsername(key[1]).getIsFrozen()) {
                receivedTrades.put(key, currentUser.getTradeRequests().get(key));
            }
        }
        List<Item> initiatedItems = new ArrayList<>();
        List<String> initiatedOwners = new ArrayList<>();


        /* this just displays the initiated trades no action required */
        for (String[] key : initiatedTrades.keySet()) {
            Item i = itemManager.getApprovedItem(initiatedTrades.get(key)[1]);
            initiatedItems.add(i);
            initiatedOwners.add(key[1]);
        }
        sp.tradeRequestViewer(1, initiatedItems, initiatedOwners);

        /* display received trade requests */
        List<Item> receivedItems = new ArrayList<>();
        List<String> receivedOwners = new ArrayList<>();
        int index = 1;
        for (String[] key : receivedTrades.keySet()) {
            Item i = itemManager.getApprovedItem(receivedTrades.get(key)[1]);
            receivedItems.add(i);
            receivedOwners.add(key[0]);
            index++;
        }
        sp.tradeRequestViewer(2, receivedItems, receivedOwners);

        /* doesn't show trade requests from frozen users */
        try {
            if (receivedTrades.isEmpty() && !initiatedTrades.isEmpty()) {
                //let them quit with 0 after viewing their initiated trades
                sp.tradeRequestViewer(1);
                String quitInput = br.readLine();
                while (!quitInput.equals("0")) {
                    sp.invalidInput();
                    quitInput = br.readLine();
                }
            } else if (!receivedTrades.isEmpty()) {

                /* pick a request to accept/reject (0 to quit) */
                String temp = br.readLine();
                while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > index - 1) {
                    sp.invalidInput();
                    temp = br.readLine();
                }
                int input = Integer.parseInt(temp);

                /* input is selected request index */
                if (input != 0) {
                    a = getTradeHelper(input);
                    trader = userManager.getNormalByUsername(a[0]);
                    firstItem = receivedItems.get(input - 1).getID();

                    /* 1) accept, or 2) reject ? */
                    sp.tradeRequestViewer(3, a[0], a[1]);

                    String temp2 = br.readLine();
                    while (!temp2.matches("[1-2]")) {
                        sp.invalidInput();
                        temp2 = br.readLine();
                    }
                    int acceptReject = Integer.parseInt(temp2);

                    if (acceptReject == 2) {        /* reject request */

                        /* sure you want to reject? */
                        sp.tradeRequestViewer(4, a[0], a[1]);
                        String inputConfirm = br.readLine();
                        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                            sp.invalidInput();
                            inputConfirm = br.readLine();
                        }

                        /* confirm rejection */
                        if (inputConfirm.equalsIgnoreCase("y")) {

                            currentUser.removeTradeRequests(getKeyToRemove());
                            trader.removeTradeRequests(getKeyToRemove());

                            sp.tradeRequestViewer(9);
                        } else {
                            sp.cancelled();
                        }
                    } else if (acceptReject == 1 &&
                            itemManager.getApprovedItem(firstItem).getAvailability()) {

                        /*
                         * Accept request.
                         * Only allow trade if item being requested is available for trade.
                         */

                        /* trade with xx person? */
                        sp.tradeRequestViewer(1, a[0], a[1]);
                        String inputConfirm = br.readLine();
                        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                            sp.invalidInput();
                            inputConfirm = br.readLine();
                        }

                        /* confirm trade */
                        if (inputConfirm.equalsIgnoreCase("y")) {

                            /* remove the trade request from both parties once its been accepted */
                            currentUser.removeTradeRequests(getKeyToRemove());
                            trader.removeTradeRequests(getKeyToRemove());

                            sp.tradeRequestViewer(2, a[0], a[1]);   /* initiating trade */
                            int twoWayItem;
                            List<Item> tempItems = itemManager.getApprovedItemsByIDs(trader.getInventory());
                            List<Item> availableInventory = itemManager.getAvailableItems(tempItems);

                            /* if other user's available inventory isn't empty, allow this user to choose what item they want */
                            if (!availableInventory.isEmpty()) {
                                sp.tradeRequestViewer(availableInventory);
                                sp.tradeRequestViewer(5);
                                String temp3 = br.readLine();
                                while (!temp3.matches("[0-9]+") || Integer.parseInt(temp3) > availableInventory.size()) {
                                    sp.invalidInput();
                                    temp3 = br.readLine();
                                }
                                twoWayItem = Integer.parseInt(temp3);
                                if (twoWayItem != 0) {
                                    secondItem = availableInventory.get(twoWayItem - 1).getID();
                                }
                            } else {
                                sp.tradeRequestViewer(7);
                            }
                            sp.tradeRequestViewer(6);

                            String permOrTemp = br.readLine();
                            while (!permOrTemp.equals("1") && !(permOrTemp.equals("2"))) {
                                sp.invalidInput();
                                permOrTemp = br.readLine();
                            }

                            /* suggest time */
                            sp.tradeRequestViewer(4);
                            LocalDateTime time = new DateTimeSuggestion(currentUser, tradeManager).suggestDateTime();

                            /* suggest place */
                            sp.tradeRequestViewer(2);
                            String place = br.readLine();
                            while (place.trim().isEmpty()) {
                                sp.invalidInput();
                                place = br.readLine();
                            }

                            /* set item status to unavailable */
                            itemManager.getApprovedItem(firstItem).setAvailability(false);
                            if (secondItem != 0) {
                                itemManager.getApprovedItem(secondItem).setAvailability(false);
                            }

                            if (permOrTemp.equals("1")) {
                                tradeManager.createPermTrade(new String[]{currentUser.getUsername(), a[0]},
                                        new long[]{firstItem, secondItem}, time, place);
                            } else {
                                tradeManager.createTempTrade(new String[]{currentUser.getUsername(), a[0]},
                                        new long[]{firstItem, secondItem}, time, place);
                            }
                        } else {
                            sp.cancelled();
                        }
                    } else {
                        sp.tradeRequestViewer(8);
                    }
                }
            }
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }

    private String[] getTradeHelper(int index) {
        List<String[]> traders = new ArrayList<>();
        List<long[]> itemIds = new ArrayList<>();
        for (Map.Entry<String[], long[]> e : receivedTrades.entrySet()) {
            traders.add(e.getKey());
            itemIds.add(e.getValue());
        }
        String[] keyToRemove = traders.get(index - 1);
        setKeyToRemove(keyToRemove);
        String trader = traders.get(index - 1)[0];
        long itemId = itemIds.get(index - 1)[1];
        Item firstItem = itemManager.getApprovedItem(itemId);
        String itemName = firstItem.getName();
        return new String[]{trader, itemName};
    }

    private void setKeyToRemove(String[] keyToRemove) {
        this.keyToRemove = keyToRemove;
    }

    private String[] getKeyToRemove() {
        return keyToRemove;
    }

    @Override
    String getTitle() {
        return "Trade Request Viewer";
    }
}

