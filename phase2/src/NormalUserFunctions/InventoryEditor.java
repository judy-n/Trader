package NormalUserFunctions;
import SystemManagers.*;
import Entities.*;
import SystemFunctions.*;
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
 * last modified 2020-07-28
 */

public class InventoryEditor extends MenuItem {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Creates an <InventoryEditor></InventoryEditor> with the given normal user and item/user/trade managers.
     * Prints to the screen the given user's inventory and options to add/remove/cancel.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public InventoryEditor(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Item> itemInventory = itemManager.getApprovedItemsByIDs(currentUser.getInventory());
        List<Item> pendingItems = itemManager.getPendingItemsByIDs(currentUser.getPendingInventory());

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
                            (!selectedItem.getAvailability() && tradeManager.getItemInCancelledTrade(selectedItem))) {
                        systemPresenter.inventoryRemoveItem(selectedItem.getName(), indexInput, 1);

                        String confirmInput = bufferedReader.readLine();
                        while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                            systemPresenter.invalidInput();
                            confirmInput = bufferedReader.readLine();
                        }
                        if (confirmInput.equalsIgnoreCase("Y")) {

                            currentUser.removeInventory(selectedItem.getID());
                            itemManager.getApprovedItem(selectedItem.getID()).setIsRemoved(true);
                            //don't remove from ItemManager

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
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }

    @Override
    public String getTitle() {
        return "Inventory Editor";
    }
}
