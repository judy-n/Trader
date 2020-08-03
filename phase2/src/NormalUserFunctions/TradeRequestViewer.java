package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;
import SystemFunctions.SystemPresenter;
import SystemFunctions.MenuItem;
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
 * last modified 2020-08-03
 */
public class TradeRequestViewer extends MenuItem {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    private LinkedHashMap<String[], long[]> initiatedTrades;
    private LinkedHashMap<String[], long[]> receivedTrades;
    private String[] keyToRemove;
    private long itemToBorrowID;
    private long itemToLendID;

    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;

    /**
     * Creates an <TradeRequestViewer></TradeRequestViewer> with the given normal user,
     * item/user/trade managers, and notification system.
     * Prints to the screen all trade requests received/sent by the given normal user and options on actions to take
     * using <SystemPresenter></SystemPresenter>.
     *
     * @param currUsername   the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public TradeRequestViewer(String currUsername, ItemManager itemManager, UserManager userManager,
                              TradeManager tradeManager, NotificationSystem notifSystem) {

        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        if (userManager.getNormalUserIsFrozen(currUsername)) {
            caseUserIsFrozen();
        } else {
            caseUserNotFrozen();
        }
        close();
    }

    private void caseUserIsFrozen() {
        systemPresenter.tradeRequestViewer(3);
    }

    private void caseUserNotFrozen() {
        initiatedTrades = new LinkedHashMap<>();
        receivedTrades = new LinkedHashMap<>();

        /* separate into initiated trades and received trades */
        for (String[] key : userManager.getNormalUserTradeRequests(currUsername).keySet()) {
            if (currUsername.equals(key[0])) {
                initiatedTrades.put(key, userManager.getNormalUserTradeRequests(currUsername).get(key));
            } else if (!userManager.getNormalUserIsFrozen(key[1])) {
                receivedTrades.put(key, userManager.getNormalUserTradeRequests(currUsername).get(key));
            }
        }

        /* just displays the trade requests sent by this user, no action required */
        List<String[]> initiatedItemNames = new ArrayList<>();
        List<String> initiatedOwners = new ArrayList<>();

        for (String[] key : initiatedTrades.keySet()) {

            String itemToBorrowName;
            String itemToLendName = "";

            if (initiatedTrades.get(key)[0] != 0) {
                Item itemToLend = itemManager.getItem(initiatedTrades.get(key)[0]);
                itemToLendName = itemToLend.getName();
            }
            Item itemToBorrow = itemManager.getItem(initiatedTrades.get(key)[1]);
            itemToBorrowName = itemToBorrow.getName();

            initiatedItemNames.add(new String[]{itemToLendName, itemToBorrowName});
            initiatedOwners.add(key[1]);
        }
        systemPresenter.tradeRequestViewer(1, initiatedItemNames, initiatedOwners);

        /* display received trade requests */
        List<String[]> receivedItemNames = new ArrayList<>();
        List<String> receivedOwners = new ArrayList<>();

        for (String[] key : receivedTrades.keySet()) {

            String itemToBorrowName = "";
            String itemToLendName;

            if (receivedTrades.get(key)[0] != 0) {
                Item itemToBorrow = itemManager.getItem(receivedTrades.get(key)[0]);
                itemToBorrowName = itemToBorrow.getName();
            }
            Item itemToLend = itemManager.getItem(receivedTrades.get(key)[1]);
            itemToLendName = itemToLend.getName();

            receivedItemNames.add(new String[]{itemToBorrowName, itemToLendName});
            receivedOwners.add(key[0]);
        }
        systemPresenter.tradeRequestViewer(2, receivedItemNames, receivedOwners);

        /* doesn't show trade requests from frozen users */
        try {
            if (receivedTrades.isEmpty() && !initiatedTrades.isEmpty()) {
                // let user quit with 0 after viewing their initiated trades
                systemPresenter.tradeRequestViewer(1);
                String quitInput = bufferedReader.readLine();
                while (!quitInput.equals("0")) {
                    systemPresenter.invalidInput();
                    quitInput = bufferedReader.readLine();
                }
            } else if (!receivedTrades.isEmpty()) {

                /* pick a request to accept/reject (0 to quit) */
                String temp = bufferedReader.readLine();
                while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > receivedTrades.size()) {
                    systemPresenter.invalidInput();
                    temp = bufferedReader.readLine();
                }
                int input = Integer.parseInt(temp);

                /* input is selected request index */
                if (input != 0) {
                    String[] senderAndItemNames = getTradeHelper(input);
                    String senderUsername = senderAndItemNames[0];
                    String[] tradeItemNames = {senderAndItemNames[1], senderAndItemNames[2]};

                    /* 1) accept, or 2) reject ? */
                    systemPresenter.tradeRequestViewer(3, senderUsername, tradeItemNames);

                    String temp2 = bufferedReader.readLine();
                    while (!temp2.matches("[1-2]")) {
                        systemPresenter.invalidInput();
                        temp2 = bufferedReader.readLine();
                    }
                    int acceptReject = Integer.parseInt(temp2);

                    if (acceptReject == 2) {        /* reject request */

                        /* sure you want to reject? */
                        systemPresenter.tradeRequestViewer(4, senderUsername, tradeItemNames);
                        String inputConfirm = bufferedReader.readLine();
                        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                            systemPresenter.invalidInput();
                            inputConfirm = bufferedReader.readLine();
                        }

                        /* confirm rejection */
                        if (inputConfirm.equalsIgnoreCase("y")) {
                            /* Notify sender of rejected trade request */
                            userManager.getNotifHelper().itemUpdate
                                    ("TRADE REQUEST REJECTED", senderUsername, currUsername,
                                            itemManager.getItemName(itemToLendID));

                            userManager.removeTradeRequests(getKeyToRemove(), currUsername);
                            userManager.removeTradeRequests(getKeyToRemove(), senderUsername);

                            systemPresenter.tradeRequestViewer(9);
                        } else {
                            systemPresenter.cancelled();
                        }
                    } else if (acceptReject == 1 &&
                            itemManager.getItem(itemToLendID).getAvailability() &&
                            itemManager.getItem(itemToBorrowID).getAvailability()) {

                        /*
                         * Accept request.
                         * Only allow trade if both items being requested or offered are available for trade.
                         */

                        /* trade with xx person? */
                        systemPresenter.tradeRequestViewer(1, senderUsername, tradeItemNames);
                        String inputConfirm = bufferedReader.readLine();
                        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                            systemPresenter.invalidInput();
                            inputConfirm = bufferedReader.readLine();
                        }

                        /* confirm trade */
                        if (inputConfirm.equalsIgnoreCase("y")) {

                            /* remove the trade request from both parties once its been accepted */
                            userManager.removeTradeRequests(getKeyToRemove(), currUsername);
                            userManager.removeTradeRequests(getKeyToRemove(), senderUsername);

                            /* permanent or temporary? */
                            systemPresenter.tradeRequestViewer(2, senderUsername, tradeItemNames);
                            systemPresenter.tradeRequestViewer(6);
                            String permOrTemp = bufferedReader.readLine();
                            while (!permOrTemp.equals("1") && !(permOrTemp.equals("2"))) {
                                systemPresenter.invalidInput();
                                permOrTemp = bufferedReader.readLine();
                            }

                            /* suggest time */
                            systemPresenter.tradeRequestViewer(4);
                            LocalDateTime time = new DateTimeSuggestion(currUsername, userManager, tradeManager).suggestDateTime();

                            /* suggest place */
                            systemPresenter.tradeRequestViewer(2);
                            String place = bufferedReader.readLine();
                            while (place.trim().isEmpty()) {
                                systemPresenter.invalidInput();
                                place = bufferedReader.readLine();
                            }

                            /* set item statuses to unavailable */
                            itemManager.getItem(itemToLendID).setAvailability(false);
                            if (itemToBorrowID != 0) {
                                itemManager.getItem(itemToBorrowID).setAvailability(false);
                            }

                            if (permOrTemp.equals("1")) {
                                tradeManager.createPermTrade(new String[]{currUsername, senderUsername},
                                        new long[]{itemToLendID, itemToBorrowID}, time, place);
                            } else {
                                tradeManager.createTempTrade(new String[]{currUsername, senderUsername},
                                        new long[]{itemToLendID, itemToBorrowID}, time, place);
                            }

                            /* Notify sender of accepted trade request */
                            userManager.getNotifHelper().itemUpdate
                                    ("TRADE REQUEST ACCEPTED", senderUsername, currUsername,
                                            itemManager.getItemName(itemToLendID));
                        } else {
                            systemPresenter.cancelled();
                        }
                    } else {
                        systemPresenter.tradeRequestViewer(8);
                    }
                }
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
    }

    private String[] getTradeHelper(int index) {
        List<String[]> traders = new ArrayList<>();
        List<long[]> itemIDs = new ArrayList<>();
        for (Map.Entry<String[], long[]> e : receivedTrades.entrySet()) {
            traders.add(e.getKey());
            itemIDs.add(e.getValue());
        }
        String[] keyToRemove = traders.get(index - 1);
        setKeyToRemove(keyToRemove);
        String trader = traders.get(index - 1)[0];

        itemToBorrowID = itemIDs.get(index - 1)[0];
        itemToLendID = itemIDs.get(index - 1)[1];

        Item itemToLend = itemManager.getItem(itemToLendID);
        String itemToLendName = itemToLend.getName();
        String itemToBorrowName = "";

        if (itemToBorrowID != 0) {
            Item itemToBorrow = itemManager.getItem(itemToBorrowID);
            itemToBorrowName = itemToBorrow.getName();
        }

        return new String[]{trader, itemToBorrowName, itemToLendName};
    }

    private void setKeyToRemove(String[] keyToRemove) {
        this.keyToRemove = keyToRemove;
    }

    private String[] getKeyToRemove() {
        return keyToRemove;
    }

    private void close() {
        new NormalDashboard(currUsername, itemManager, userManager, tradeManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return "Trade Request Viewer";
    }
}