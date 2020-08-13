package NormalUserFunctions;

import Entities.Item;
import SystemFunctions.SystemPresenter;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import SystemManagers.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Helps show all items available for trade from all users' inventories except for the user who's currently logged in.
 * Allows users to initiate trades and add items to their wishlist.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-12
 */
public class CatalogViewer {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter systemPresenter;

    private List<Item> allItemsOtherUsers;
    private List<Item> catalogToView;
    private List<Long> fullInventory;
    private List<Long> availableInventory;
    private int timesBorrowed;
    private int timesLent;
    private int lendMinimum;
    private int indexOfItemRequested;

    /**
     * Creates an <CatalogViewer></CatalogViewer> with the given normal user username and item/user/trade managers.
     *
     * @param username     the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     */
    public CatalogViewer(String username, ItemManager itemManager, UserManager userManager, TradeManager tradeManager) {
        currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        systemPresenter = new SystemPresenter();
    }

    /**
     * Filters the catalog to only show items that:
     * a) belong to other users,
     * b) belong to users located in the same home city as the user currently viewing the catalog, and
     * c) belong to users that aren't currently on vacation.
     * Returns an array of string representations of the catalog items.
     *
     * @return an array of string representations of the catalog items
     */
    public String[] getCatalogStrings() {
        allItemsOtherUsers = itemManager.getApprovedItems(currUsername);
        catalogToView = filterItemsNotOnVacation(filterItemsHomeCity(allItemsOtherUsers));

        return itemManager.getItemStrings(catalogToView, true);
    }

    /**
     * Adds the catalog item at the given index to the current user's wishlist iff
     * it isn't already in their wishlist.
     * Returns whether or not the item was successfully added to the user's wishlist.
     *
     * @param index the index of the catalog item being added to wishlist
     * @return true iff the given item isn't already in the current user's wishlist
     */
    public boolean addToWishlist(int index) {

        long itemToWishlistID = catalogToView.get(index).getID();

        if (!userManager.isInNormalUserWishlist(itemToWishlistID, currUsername)) {
            userManager.addToNormalUserWishlist(itemToWishlistID, currUsername);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the catalog item at the given index can be requested in a trade.
     * An item cannot be requested in a trade if:
     * - the user requesting is currently frozen
     * - the owner of the item is currently frozen
     * - the item is currently involved in a trade
     * - the user already has sent a trade request for it that the recipient hasn't yet responded to
     *
     * @param index the index of the catalog item being checked
     * @return an error message iff item is not available for trade or owner is frozen, an empty string otherwise
     */
    public int canTradeRequestItem(int index) {

        long itemToRequestID = catalogToView.get(index).getID();

        timesBorrowed = userManager.getNormalUserTimesBorrowed(currUsername) + tradeManager.getTimesBorrowed(currUsername);
        timesLent = userManager.getNormalUserTimesLent(currUsername) + tradeManager.getTimesLent(currUsername);

        if (userManager.getNormalUserIsFrozen(currUsername)) {
            return 27;
        } else if (userManager.getNormalUserIsFrozen(itemManager.getItemOwner(itemToRequestID))) {
            return 28;
        } else if (!itemManager.getItemAvailability(itemToRequestID)) {
            return 29;
        } else if (userManager.isRequestedInTrade(currUsername, itemToRequestID)) {
            return 31;
        } else {
            return 0;
        }
    }

    /**
     * Checks if the catalog item at the given index can be requested in a trade.
     * - the user hasn't lent enough items more than they've borrowed
     *
     * @return the lend minimum if the user violated this rule
     */
    public int canTradeRequestItem() {
        lendMinimum = userManager.getThresholdSystem().getLendMinimum();
        if (timesBorrowed > 0 && ((timesLent - timesBorrowed) < lendMinimum)) {
            return lendMinimum;
        }
        return 0;
    }

    /**
     * Returns an array of string representations for all the items in the current user's inventory
     * that are available for trade.
     *
     * @return an array of string representations for the current user's available inventory
     */
    public String[] getCurrUserInventory() {
        fullInventory = userManager.getNormalUserInventory(currUsername);
        availableInventory = itemManager.getAvailableItems(fullInventory);
        return itemManager.getItemStringsID(availableInventory, false);
    }

    /**
     * Returns an array of string representations for the
     * suggested items to lend for the current user's trade request.
     *
     * @param index the index of the selected catalog item for which a trade request is being set up
     * @return an array of string representations for the suggested items
     */
    public String[] getSuggestedItems(int index) {

        long itemToRequest = catalogToView.get(index).getID();

        fullInventory = userManager.getNormalUserInventory(currUsername);
        availableInventory = itemManager.getAvailableItems(fullInventory);

        String traderUsername = itemManager.getItemOwner(itemToRequest);
        List<Long> otherUserWishlist = userManager.getNormalUserWishlist(traderUsername);
        List<Long> suggestedItems = new ArrayList<>();

        // the questionable item suggestion algorithm
        for (long wishlistID : otherUserWishlist) {
            for (long availableItemID : availableInventory) {
                if ((availableItemID == wishlistID ||
                        itemManager.getItemName(wishlistID)
                                .equalsIgnoreCase(itemManager.getItemName(availableItemID)))
                        && !suggestedItems.contains(availableItemID)) {
                    suggestedItems.add(availableItemID);
                }
            }
        }

        String[] suggestedItemStrings;

        if (suggestedItems.isEmpty()) {
            suggestedItemStrings = new String[1];
            suggestedItemStrings[0] = systemPresenter.tradeRequestSetup(1);
        } else {
            suggestedItemStrings = itemManager.getItemStringsID(suggestedItems, false);
        }

        return suggestedItemStrings;
    }

    /**
     * Sets the index of the item requested
     *
     * @param indexOfItemRequested the index of the item requested
     */
    public void setIndexOfItemRequested(int indexOfItemRequested) {
        this.indexOfItemRequested = indexOfItemRequested;
    }

    /**
     * Creates a trade request for a two-way trade using the given index of a catalog item
     * and an item from the current user's inventory.
     *
     * @param indexOfItemToLend the index of the inventory item that the user is choosing to lend
     */
    public void requestItemInTwoWayTrade(int indexOfItemToLend) {

        long itemToLendID = availableInventory.get(indexOfItemToLend);
        long itemToBorrowID = catalogToView.get(indexOfItemRequested).getID();

        sendTradeRequest(itemToLendID, itemToBorrowID);
    }

    /**
     * Checks if the current user is allowed to initiate a one-way trade.
     * If allowed, creates a trade request for a one-way trade using the given index of a catalog item.
     *
     * @return an error message iff the user is not allowed to initiate a one-way trade, an empty string otherwise
     */
    public int requestItemInOneWayTrade() {
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
        if (timesBorrowed == 0) {
            return 32;
        } else if ((timesLent - timesBorrowed) == lendMinimum) {
            return 33;
        } else {
            long itemToBorrowID = catalogToView.get(indexOfItemRequested).getID();
            sendTradeRequest(0, itemToBorrowID);
            return 0;
        }
    }

    private void sendTradeRequest(long itemToLendID, long itemToBorrowID) {
        String traderUsername = itemManager.getItemOwner(itemToBorrowID);
        long[] tradeItems = {itemToLendID, itemToBorrowID};
        String[] traderUsernames = {currUsername, traderUsername};

        userManager.addTradeRequestBothUsers(traderUsernames, tradeItems);

        /* Notify other user of new trade request */
        userManager.notifyUser(traderUsername).basicUpdate
                ("TRADE REQUEST RECEIVED", traderUsername, currUsername);
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
}