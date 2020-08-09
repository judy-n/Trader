package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;
import SystemFunctions.SystemPresenter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Shows all items available for trade from all users' inventories except for the user who's currently logged in.
 * Allows users to initiate trades and add items to their wishlist.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-09
 */
public class CatalogViewer {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;
    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;

    /**
     * Creates an <CatalogViewer></CatalogViewer> with the given normal user username,
     * item/user/trade managers, and notification system.
     * Displays all items available for trade (excluding the current user's items).
     *
     * @param username     the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public CatalogViewer(String username, ItemManager itemManager, UserManager userManager,
                         TradeManager tradeManager, NotificationSystem notifSystem) {
        currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Item> allItemsOtherUsers = itemManager.getApprovedItems(currUsername);
        List<Item> catalogToView = filterItemsNotOnVacation(filterItemsHomeCity(allItemsOtherUsers));
        int maxIndex = catalogToView.size();

        systemPresenter.catalogViewer(catalogToView);

        int timesBorrowed = userManager.getNormalUserTimesBorrowed(currUsername) + tradeManager.getTimesBorrowed(currUsername);
        int timesLent = userManager.getNormalUserTimesLent(currUsername) + tradeManager.getTimesLent(currUsername);
        int lendMinimum = userManager.getThresholdSystem().getLendMinimum();

        systemPresenter.catalogViewer(1);
        try {
            String temp = bufferedReader.readLine();
            while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > maxIndex) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input != 0) {
                Item selectedItem = catalogToView.get(input - 1);
                long itemID = selectedItem.getID();

                systemPresenter.catalogViewer(selectedItem.getName(), selectedItem.getOwnerUsername(), 1);

                String temp2 = bufferedReader.readLine();
                while (!temp2.matches("[0-2]")) {
                    systemPresenter.invalidInput();
                    temp2 = bufferedReader.readLine();
                }
                int tradeOrWishlist = Integer.parseInt(temp2);

                if (tradeOrWishlist == 1 && (!selectedItem.getAvailability()) ||
                        userManager.getNormalUserIsFrozen(itemManager.getItemOwner(itemID))) {

                    if (!selectedItem.getAvailability()) {
                        systemPresenter.catalogViewer(3);
                    } else {
                        systemPresenter.catalogViewer(7);
                    }

                    //option to add unavailable item or item belonging to frozen user to wishlist
                    String confirmInput = bufferedReader.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        systemPresenter.invalidInput();
                        confirmInput = bufferedReader.readLine();
                    }

                    if (confirmInput.equalsIgnoreCase("Y")) {
                        tradeOrWishlist = 2;
                    }

                } else if (tradeOrWishlist == 1) {
                    if (userManager.getNormalUserIsFrozen(currUsername)) {
                        systemPresenter.catalogViewer(2);
                    } else if (userManager.isRequestedInTrade(currUsername, itemID)) {
                        systemPresenter.catalogViewer(6);
                    } else if (timesBorrowed > 0 && ((timesLent - timesBorrowed) < lendMinimum)) {
                        systemPresenter.catalogViewerLendWarning(lendMinimum);
                    } else {

                        /*
                         * If this code is reached, then the user is allowed to set up a trade since they've
                         * lent at least lendMinimum more items than they've borrowed, or it's the user's
                         * first time initiating a trade request/all their past requests were rejected.
                         *
                         * However, if the difference between the number of items they've lent and the number of
                         * items they've borrowed is exactly equal to lendMinimum, then the user must offer to
                         * lend an item in order to keep the balance.
                         *
                         * Additionally, if it's the user's first time initiating a trade request
                         * or all their past requests were rejected, then they must request a two-way trade.
                         */
                        boolean mustLend = false;
                        if (timesBorrowed == 0 || (timesLent - timesBorrowed) == lendMinimum) {
                            mustLend = true;
                        }
                        new TradeRequestSetup(currUsername, itemManager, userManager, mustLend).makeTradeRequest(selectedItem);
                    }
                }

                if (tradeOrWishlist == 2 && !userManager.isInNormalUserWishlist(itemID, currUsername)) {
                    userManager.addToNormalUserWishlist(itemID, currUsername);
                    systemPresenter.catalogViewer(4);
                } else if (tradeOrWishlist == 2 && userManager.isInNormalUserWishlist(itemID, currUsername)) {
                    systemPresenter.catalogViewer(5);
                }
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
        close();
    }

    /*
     * Returns a list of items that are owned by users with the same home city as the current user.
     */
    private List<Item> filterItemsHomeCity(List<Item> listToFilter) {
        List<Item> itemsSameCity = new ArrayList<>();
        for (Item i : listToFilter) {
            if (userManager.getNormalUserHomeCity(i.getOwnerUsername()).equals(userManager.getNormalUserHomeCity(currUsername))) {
                itemsSameCity.add(i);
            }
        }
        return itemsSameCity;
    }

    /*
     * Returns a list of items that are owned by users NOT on vacation.
     */
    private List<Item> filterItemsNotOnVacation(List<Item> listToFilter) {
        List<Item> itemsNotOnVacation = new ArrayList<>();
        for (Item i : listToFilter) {
            if (!userManager.getNormalUserOnVacation(i.getOwnerUsername())) {
                itemsNotOnVacation.add(i);
            }
        }
        return itemsNotOnVacation;

    }

    private void close() {
        new NormalDashboard(currUsername, itemManager, userManager, tradeManager, notifSystem);
    }
}