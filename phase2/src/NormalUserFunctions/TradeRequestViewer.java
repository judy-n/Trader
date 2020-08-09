package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;
import SystemFunctions.SystemPresenter;

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
 * last modified 2020-08-09
 */
public class TradeRequestViewer {
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

    private List<String[]> initiatedItemNames;
    private List<String> initiatedOwners;

    List<String[]> receivedItemNames;
    List<String> receivedOwners;

    private SystemPresenter systemPresenter;


    /**
     * Creates an <TradeRequestViewer></TradeRequestViewer> with the given normal user,
     * item/user/trade managers, and notification system.
     * Prints to the screen all trade requests received/sent by the given normal user and options on actions to take
     * using <SystemPresenter></SystemPresenter>.
     *
     * @param currUsername the username of the normal user who's currently logged in
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

        initiatedTrades = new LinkedHashMap<>();
        initiatedItemNames = new ArrayList<>();
        initiatedOwners = new ArrayList<>();

        receivedTrades = new LinkedHashMap<>();
        receivedItemNames = new ArrayList<>();
        receivedOwners = new ArrayList<>();
    }
//
//    private void caseUserIsFrozen() {
//        systemPresenter.tradeRequestViewer(3);
//    }

    private void caseUserNotFrozen() {


        //systemPresenter.tradeRequestViewer(2, receivedItemNames, receivedOwners);

        /* doesn't show trade requests from frozen users */
//        try {
//             if (!receivedTrades.isEmpty()) {
//
//                /* input is selected request index */
//                if (input != 0) {
//                    String[] senderAndItemNames = getTradeHelper(input);
//                    String senderUsername = senderAndItemNames[0];
//                    String[] tradeItemNames = {senderAndItemNames[1], senderAndItemNames[2]};
//
//                    if (acceptReject == 2) {        /* reject request */
//
//                        /* sure you want to reject? */
//                        systemPresenter.tradeRequestViewer(4, senderUsername, tradeItemNames);
//                        String inputConfirm = bufferedReader.readLine();
//                        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
//                            systemPresenter.invalidInput();
//                            inputConfirm = bufferedReader.readLine();
//                        }
//
//                        /* confirm rejection */
//                        if (inputConfirm.equalsIgnoreCase("y")) {
//                            /* Notify sender of rejected trade request */
//                            userManager.notifyUser(senderUsername).itemUpdate
//                                    ("TRADE REQUEST REJECTED", senderUsername, currUsername,
//                                            itemManager.getItemName(itemToLendID));
//
//                            userManager.removeTradeRequests(getKeyToRemove(), currUsername);
//                            userManager.removeTradeRequests(getKeyToRemove(), senderUsername);
//
//                            systemPresenter.tradeRequestViewer(9);
//                        } else {
//                            systemPresenter.cancelled();
//                        }
//                    } else if (acceptReject == 1 &&
//                            itemManager.getItem(itemToLendID).getAvailability() &&
//                            itemManager.getItem(itemToBorrowID).getAvailability()) {
//
//                        /*
//                         * Accept request.
//                         * Only allow trade if both items being requested or offered are available for trade.
//                         */
//
//                        /* trade with xx person? */
//                        systemPresenter.tradeRequestViewer(1, senderUsername, tradeItemNames);
//                        String inputConfirm = bufferedReader.readLine();
//                        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
//                            systemPresenter.invalidInput();
//                            inputConfirm = bufferedReader.readLine();
//                        }
//
//                        /* confirm trade */
//                        if (inputConfirm.equalsIgnoreCase("y")) {
//
//                            /* remove the trade request from both parties once its been accepted */
//                            userManager.removeTradeRequests(getKeyToRemove(), currUsername);
//                            userManager.removeTradeRequests(getKeyToRemove(), senderUsername);
//
//                            /* permanent or temporary? */
//                            systemPresenter.tradeRequestViewer(2, senderUsername, tradeItemNames);
//                            systemPresenter.tradeRequestViewer(6);
//                            String permOrTemp = bufferedReader.readLine();
//                            while (!permOrTemp.equals("1") && !(permOrTemp.equals("2"))) {
//                                systemPresenter.invalidInput();
//                                permOrTemp = bufferedReader.readLine();
//                            }
//
//                            /* suggest time */
//                            systemPresenter.tradeRequestViewer(4);
//                            LocalDateTime time = new DateTimeSuggestion(currUsername, userManager, tradeManager).suggestDateTime();
//
//                            /* suggest place */
//                            systemPresenter.tradeRequestViewer(2);
//                            String place = bufferedReader.readLine();
//                            while (place.trim().isEmpty()) {
//                                systemPresenter.invalidInput();
//                                place = bufferedReader.readLine();
//                            }
//
//                            /* set item statuses to unavailable */
//                            itemManager.setItemAvailability(itemToLendID, false);
//                            if (itemToBorrowID != 0) {
//                                itemManager.setItemAvailability(itemToBorrowID, false);
//                            }
//
//                            if (permOrTemp.equals("1")) {
//                                tradeManager.createPermTrade(new String[]{currUsername, senderUsername},
//                                        new long[]{itemToLendID, itemToBorrowID}, time, place);
//                            } else {
//                                tradeManager.createTempTrade(new String[]{currUsername, senderUsername},
//                                        new long[]{itemToLendID, itemToBorrowID}, time, place);
//                            }
//
//                            /* Notify sender of accepted trade request */
//                            userManager.notifyUser(senderUsername).itemUpdate
//                                    ("TRADE REQUEST ACCEPTED", senderUsername, currUsername,
//                                            itemManager.getItemName(itemToLendID));
//                        } else {
//                            systemPresenter.cancelled();
//                        }
//                    } else {
//                        systemPresenter.tradeRequestViewer(8);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            systemPresenter.exceptionMessage();
//        }
    }


    public String[] getInitiatedTrades() {

        initiatedTrades.clear();
        initiatedItemNames.clear();
        initiatedOwners.clear();

        for (String[] key : userManager.getNormalUserTradeRequests(currUsername).keySet()) {
            if (currUsername.equals(key[0])) {
                initiatedTrades.put(key, userManager.getNormalUserTradeRequests(currUsername).get(key));
            }
        }
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
        return systemPresenter.presentInitiatedTradeRequests(initiatedItemNames, initiatedOwners);
    }

    public String[] getReceivedTrades() {

        receivedTrades.clear();
        receivedItemNames.clear();
        receivedOwners.clear();

        for (String[] key : userManager.getNormalUserTradeRequests(currUsername).keySet()) {
            if (!userManager.getNormalUserIsFrozen(key[1])) {
                receivedTrades.put(key, userManager.getNormalUserTradeRequests(currUsername).get(key));
            }
        }
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
        return systemPresenter.presentReceivedTradeRequests(receivedItemNames, receivedOwners);
    }

    //isPerm not needed, unless you wanna create the trade in this method?
    public void acceptTradeRequest(int index, boolean isPerm) {
        String[] senderAndItemNames = getTradeHelper(index);
        String senderUsername = senderAndItemNames[0];
        String[] tradeItemNames = {senderAndItemNames[1], senderAndItemNames[2]};
        userManager.removeTradeRequests(getKeyToRemove(), currUsername);
        userManager.removeTradeRequests(getKeyToRemove(), senderUsername);

        /* Notify sender of accepted trade request */
        userManager.notifyUser(senderUsername).itemUpdate
                ("TRADE REQUEST ACCEPTED", senderUsername, currUsername,
                        itemManager.getItemName(itemToLendID));
    }


    public void rejectTradeRequest(int index) {
        String[] senderAndItemNames = getTradeHelper(index);
        String senderUsername = senderAndItemNames[0];

        /* Notify sender of rejected trade request */
        userManager.notifyUser(senderUsername).itemUpdate
                ("TRADE REQUEST REJECTED", senderUsername, currUsername,
                        itemManager.getItemName(itemToLendID));

        userManager.removeTradeRequests(getKeyToRemove(), currUsername);
        userManager.removeTradeRequests(getKeyToRemove(), senderUsername);
    }


    private String[] getTradeHelper(int index) {
        List<String[]> traders = new ArrayList<>();
        List<long[]> itemIDs = new ArrayList<>();
        for (Map.Entry<String[], long[]> e : receivedTrades.entrySet()) {
            traders.add(e.getKey());
            itemIDs.add(e.getValue());
        }
        String[] keyToRemove = traders.get(index);
        setKeyToRemove(keyToRemove);
        String trader = traders.get(index)[0];

        itemToBorrowID = itemIDs.get(index)[0];
        itemToLendID = itemIDs.get(index)[1];

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
}