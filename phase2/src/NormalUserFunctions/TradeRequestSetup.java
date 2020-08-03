package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import Entities.Item;
import SystemFunctions.SystemPresenter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lets the user set up a trade request to another user and sends it once completed.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-28
 * last modified 2020-07-31
 */
public class TradeRequestSetup {
    private String currUsername;
    private UserManager userManager;
    private ItemManager itemManager;
    boolean mustLend;

    /**
     * Creates a <TradeRequestSetup></TradeRequestSetup> with the given username, item/user managers,
     * and boolean indicating whether or not the user must lend an item in the trade request.
     *
     * @param username    the username of the normal user who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param mustLend    true iff the user must lend an item in the trade request
     */
    public TradeRequestSetup(String username, ItemManager itemManager, UserManager userManager, boolean mustLend) {
        currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.mustLend = mustLend;
    }

    /**
     * Allows the user to set up a trade request through user input.
     *
     * @param selectedItem the item selected by the user to borrow in the trade request
     * @throws IOException when there's an error reading user input
     */
    public void makeTradeRequest(Item selectedItem) throws IOException {
        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        systemPresenter.catalogViewer(selectedItem.getName(), selectedItem.getOwnerUsername(), 2);

        String inputConfirm = bufferedReader.readLine();
        while (!inputConfirm.matches("[nNyY]")) {
            systemPresenter.invalidInput();
            inputConfirm = bufferedReader.readLine();
        }
        if (inputConfirm.equalsIgnoreCase("Y")) {

            String traderUsername = selectedItem.getOwnerUsername();
            String[] traders = {currUsername, traderUsername};
            long[] items = {0, selectedItem.getID()};

            systemPresenter.tradeRequestSetup(1);
            String suggestionChoice = bufferedReader.readLine();
            while (!suggestionChoice.matches("[nNyY]")) {
                systemPresenter.invalidInput();
                suggestionChoice = bufferedReader.readLine();
            }

            List<Item> fullInventory = itemManager.getItemsByIDs(userManager.getNormalUserInventory(currUsername));
            List<Item> availableInventory = itemManager.getAvailableItems(fullInventory);

            /* show suggested items */
            if (suggestionChoice.equalsIgnoreCase("Y")) {
                List<Item> otherUserWishlist = itemManager.getItemsByIDs(userManager.getNormalUserWishlist(traderUsername));
                List<Item> suggestedItems = new ArrayList<>();

                // the questionable item suggestion algorithm
                for (Item i : otherUserWishlist) {
                    for (Item j : availableInventory) {
                        if (i == j || i.getName().equalsIgnoreCase(j.getName())) {
                            suggestedItems.add(i);
                        }
                    }
                }

                if (suggestedItems.isEmpty()) {
                    systemPresenter.tradeRequestSetup(2);
                } else {
                    systemPresenter.tradeRequestSetup(suggestedItems, 1);
                }
            }

            /* show all items available for trade in the current user's inventory and allow them to select one */
            boolean selectionSuccessful;
            do {
                systemPresenter.tradeRequestSetup(availableInventory, 2);

                String tempInput = bufferedReader.readLine();
                while (!tempInput.matches("[0-9]+") || Integer.parseInt(tempInput) > availableInventory.size()) {
                    systemPresenter.invalidInput();
                    tempInput = bufferedReader.readLine();
                }
                int indexInput = Integer.parseInt(tempInput);
                if (indexInput == 0 && mustLend) {
                    selectionSuccessful = false;
                    systemPresenter.tradeRequestSetup(3);
                } else {
                    if (indexInput != 0) {
                        Item lendingItem = availableInventory.get(indexInput - 1);
                        items[0] = lendingItem.getID();
                    }
                    selectionSuccessful = true;
                }
            } while (!selectionSuccessful);

            userManager.addTradeRequestBothUsers(traders, items);
            if (!userManager.getNormalUserWishlist(currUsername).contains(selectedItem.getID())) {
                userManager.addNormalUserWishlist(selectedItem.getID(), currUsername);
            }

            /* Notify other user of new trade request */
            userManager.getNotifHelper().basicUpdate("TRADE REQUEST RECEIVED", traderUsername, currUsername);

            systemPresenter.tradeRequestSetup(traderUsername);

        } else {
            systemPresenter.cancelled();
        }
    }
}
