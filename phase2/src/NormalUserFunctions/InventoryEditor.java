package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;
import Entities.Item;
import SystemFunctions.SystemPresenter;
import SystemFunctions.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Shows the user their inventory and lets them edit it through user input.
 * If the user adds an item to their inventory, it will first be sent to an admin for approval.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-31
 */

public class InventoryEditor extends MenuItem {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    /**
     * Creates an <InventoryEditor></InventoryEditor> with the given normal user,
     * item/user/trade managers, and notification system.
     * Prints to the screen the given user's inventory and options to add/remove/cancel.
     *
     * @param user         the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public InventoryEditor(NormalUser user, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {
        currentUser = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Item> itemInventory = itemManager.getItemsByIDs(currentUser.getInventory());
        List<Item> pendingItems = itemManager.getItemsByIDs(currentUser.getPendingInventory());

        systemPresenter.inventoryEditor(itemInventory, pendingItems);
        try {
            String temp = bufferedReader.readLine();
            while (!temp.matches("[1-3]")) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            int input = Integer.parseInt(temp);

            if (input == 1) {           /* add item */
                String itemNameInput;
                String itemDescriptionInput;

                systemPresenter.inventoryAddItem(1);
                itemNameInput = bufferedReader.readLine().trim();
                while (itemNameInput.length() < 3) {
                    systemPresenter.invalidInput();
                    itemNameInput = bufferedReader.readLine().trim();
                }   // item name at least 3 char long

                systemPresenter.inventoryAddItem(2);
                itemDescriptionInput = bufferedReader.readLine().trim();
                while (!itemDescriptionInput.contains(" ")) {
                    systemPresenter.invalidInput();
                    itemDescriptionInput = bufferedReader.readLine().trim();
                }   // item description at least two words

                systemPresenter.inventoryAddItem(3);
                systemPresenter.inventoryAddItem(itemNameInput, itemDescriptionInput);

                String confirmInput = bufferedReader.readLine();
                while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                    systemPresenter.invalidInput();
                    confirmInput = bufferedReader.readLine();
                }
                if (confirmInput.equalsIgnoreCase("Y")) {
                    long newItemID = itemManager.createItem(itemNameInput, itemDescriptionInput, currentUser.getUsername());
                    currentUser.addPendingInventory(newItemID);

                    systemPresenter.inventoryAddItem(4);
                } else {
                    systemPresenter.cancelled();
                }
            } else if (input == 2) {    /* remove item */
                if (itemInventory.isEmpty()) {
                    systemPresenter.inventoryRemoveItem(1);
                } else {
                    systemPresenter.inventoryRemoveItem(2);
                    String temp2 = bufferedReader.readLine();

                    /* no (0 to quit) option */
                    while (!temp2.matches("[0-9]+") ||
                            Integer.parseInt(temp2) > itemInventory.size() || Integer.parseInt(temp2) < 1) {
                        systemPresenter.invalidInput();
                        temp2 = bufferedReader.readLine();
                    }
                    int indexInput = Integer.parseInt(temp2);
                    Item selectedItem = itemInventory.get(indexInput - 1);

                    /*
                     * Allow removal if item is available + not being asked for in a trade request
                     * OR if item is in a trade that's been cancelled due to users failing to confirm the transaction
                     */
                    if (currentUser.isRequestedInTrade(selectedItem.getID())) {
                        systemPresenter.inventoryRemoveItem(4);
                    } else if (selectedItem.getAvailability() ||
                            (!selectedItem.getAvailability() && tradeManager.getItemInCancelledTrade(selectedItem.getID()))) {
                        systemPresenter.inventoryRemoveItem(selectedItem.getName(), indexInput, 1);

                        String confirmInput = bufferedReader.readLine();
                        while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                            systemPresenter.invalidInput();
                            confirmInput = bufferedReader.readLine();
                        }
                        if (confirmInput.equalsIgnoreCase("Y")) {

                            currentUser.removeInventory(selectedItem.getID());
                            itemManager.getItem(selectedItem.getID()).setIsRemoved(true);
                            // don't remove from ItemManager

                            systemPresenter.inventoryRemoveItem(selectedItem.getName(), 0, 2);
                        } else {
                            systemPresenter.cancelled();
                        }
                    } else {
                        systemPresenter.inventoryRemoveItem(3);
                    }
                }
            }
            close();

        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return "Inventory Editor";
    }
}
