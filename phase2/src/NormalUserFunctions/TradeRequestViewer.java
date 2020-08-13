package NormalUserFunctions;

import SystemFunctions.DateTimeHandler;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;
import SystemFunctions.SystemPresenter;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * Shows all trade requests received/sent by a user and lets them take actions through user input.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 2.0
 * @since 2020-06-29
 * last modified 2020-08-11
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

    private DateTimeHandler dateTimeHandler;
    private SystemPresenter systemPresenter;

    /**
     * Creates an <TradeRequestViewer></TradeRequestViewer> with the given normal user,
     * item/user/trade managers, and notification system.
     *
     * @param currUsername the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem the system's notification manager
     */
    public TradeRequestViewer(String currUsername, ItemManager itemManager, UserManager userManager,
                              TradeManager tradeManager, NotificationSystem notifSystem) {

        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        dateTimeHandler = new DateTimeHandler();
        systemPresenter = new SystemPresenter();

        initiatedTrades = new LinkedHashMap<>();
        initiatedItemNames = new ArrayList<>();
        initiatedOwners = new ArrayList<>();

        receivedTrades = new LinkedHashMap<>();
        receivedItemNames = new ArrayList<>();
        receivedOwners = new ArrayList<>();
    }

    /**
     * Returns an array of string representations of the trade requests initiated by the current user.
     *
     * @return an array of string representations of the trade requests initiated by the current user
     */
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
                itemToLendName = itemManager.getItemName(initiatedTrades.get(key)[0]);
            }
            itemToBorrowName = itemManager.getItemName(initiatedTrades.get(key)[1]);

            initiatedItemNames.add(new String[]{itemToLendName, itemToBorrowName});
            initiatedOwners.add(key[1]);
        }
        return systemPresenter.presentInitiatedTradeRequests(initiatedItemNames, initiatedOwners);
    }

    /**
     * Returns an array of string representations of the trade requests received by the current user.
     *
     * @return an array of string representations of the trade requests received by the current user
     */
    public String[] getReceivedTrades() {

        receivedTrades.clear();
        receivedItemNames.clear();
        receivedOwners.clear();

        for (String[] key : userManager.getNormalUserTradeRequests(currUsername).keySet()) {
            if (!userManager.getNormalUserIsFrozen(key[0]) && !userManager.getNormalUserOnVacation(key[0])
                    && currUsername.equals(key[1])) {
                receivedTrades.put(key, userManager.getNormalUserTradeRequests(currUsername).get(key));
            }
        }
        for (String[] key : receivedTrades.keySet()) {

            String itemToBorrowName = "";
            String itemToLendName;

            if (receivedTrades.get(key)[0] != 0) {
                itemToBorrowName = itemManager.getItemName(receivedTrades.get(key)[0]);
            }
            itemToLendName = itemManager.getItemName(receivedTrades.get(key)[1]);

            receivedItemNames.add(new String[]{itemToBorrowName, itemToLendName});
            receivedOwners.add(key[0]);
        }
        return systemPresenter.presentReceivedTradeRequests(receivedItemNames, receivedOwners);
    }

    /**
     * Checks if the trade request at the given index can be accepted.
     *
     * @param index the index of the trade request selected by the user
     * @return an error message iff the trade request cannot be accepted, an empty string otherwise
     */
    public boolean canAcceptRequest(int index) {
        getTradeHelper(index);
        if (itemToBorrowID != 0 && !itemManager.getItemAvailability(itemToBorrowID)) {
            return false;
        } else return itemManager.getItemAvailability(itemToLendID);
    }

    /**
     * Checks if the date/time and location suggested by the user are valid.
     *
     * @param suggestedDateTime the date and time suggested by the user
     * @param suggestedLocation the location suggested by the user
     * @return an error message iff the date/time or location is not valid, an empty string otherwise
     */
    public int validateSuggestion(String suggestedDateTime, String suggestedLocation) {
        return new TradeMeetingSuggestionValidator
                (currUsername, userManager, tradeManager, dateTimeHandler)
                .validateSuggestion(suggestedDateTime, suggestedLocation);
    }

    /**
     * Accepts the trade request at the given index and creates a new <Trade></Trade> based on
     * the details given by the user.
     * Notifies the sender of the acceptance.
     *
     * @param index         the index of the trade request being accepted
     * @param isPerm        whether or not the user chose to make it a permanent trade
     * @param validDateTime a valid date/time suggested by the user
     * @param validLocation a valid location suggested by the user
     */
    public void acceptTradeRequest(int index, boolean isPerm, String validDateTime, String validLocation) {
        String[] senderAndItemNames = getTradeHelper(index);
        String senderUsername = senderAndItemNames[0];
        String[] tradeItemNames = {senderAndItemNames[1], senderAndItemNames[2]};

        /* set item statuses to unavailable */
        itemManager.setItemAvailability(itemToLendID, false);
        if (itemToBorrowID != 0) {
            itemManager.setItemAvailability(itemToBorrowID, false);
        }

        if (isPerm) {
            tradeManager.createPermTrade(new String[]{currUsername, senderUsername},
                    new long[]{itemToLendID, itemToBorrowID},
                    dateTimeHandler.getLocalDateTime(validDateTime), validLocation);
        } else {
            tradeManager.createTempTrade(new String[]{currUsername, senderUsername},
                    new long[]{itemToLendID, itemToBorrowID},
                    dateTimeHandler.getLocalDateTime(validDateTime), validLocation);
        }

        /* Notify sender of accepted trade request */
        userManager.notifyUser(senderUsername).itemUpdate
                ("TRADE REQUEST ACCEPTED", senderUsername, currUsername, tradeItemNames[1]);

        // Remove trade request from both user's accounts and
        // get rid of the revertible notif associated with the trade request being removed
        userManager.removeTradeRequestBothUsers(getKeyToRemove());
        notifSystem.removeRevertibleNotif(senderUsername, currUsername);

        // Also get rid of the revertible notifs associated with the approval of all items involved in this trade request
        notifSystem.removeRevertibleNotif(itemToBorrowID);
        notifSystem.removeRevertibleNotif(itemToLendID);
    }

    /**
     * Rejects the trade request at the given index.
     * Notifies the sender of the rejection.
     *
     * @param index the index of the trade request being rejected
     */
    public void rejectTradeRequest(int index) {
        String[] senderAndItemNames = getTradeHelper(index);
        String senderUsername = senderAndItemNames[0];
        String[] tradeItemNames = {senderAndItemNames[1], senderAndItemNames[2]};

        /* Notify sender of rejected trade request */
        userManager.notifyUser(senderUsername).itemUpdate
                ("TRADE REQUEST REJECTED", senderUsername, currUsername, tradeItemNames[1]);

        // Remove trade request from both user's accounts and
        // get rid of the revertible notif associated with the trade request being removed
        userManager.removeTradeRequestBothUsers(getKeyToRemove());
        notifSystem.removeRevertibleNotif(senderUsername, currUsername);
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