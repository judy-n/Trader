package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;

/**
 * Helps show the user their inventory and let them edit it through user input.
 * If the user adds an item to their inventory, it will first be sent to an admin for approval.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-08-06
 */
public class InventoryEditor {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Creates an <InventoryEditor></InventoryEditor> with the given normal user and item/user/trade managers.
     *
     * @param currUsername the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     */
    public InventoryEditor(String currUsername, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager) {
        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;

//            if (input == 1) {           /* add item */
//                String itemNameInput;
//                String itemDescriptionInput;
//
//                systemPresenter.inventoryAddItem(1);
//                itemNameInput = bufferedReader.readLine().trim();
//                while (itemNameInput.length() < 3) {
//                    systemPresenter.invalidInput();
//                    itemNameInput = bufferedReader.readLine().trim();
//                }   // item name at least 3 char long
//
//                systemPresenter.inventoryAddItem(2);
//                itemDescriptionInput = bufferedReader.readLine().trim();
//                while (!itemDescriptionInput.contains(" ")) {
//                    systemPresenter.invalidInput();
//                    itemDescriptionInput = bufferedReader.readLine().trim();
//                }   // item description at least two words
//
//                systemPresenter.inventoryAddItem(3);
//                systemPresenter.inventoryAddItem(itemNameInput, itemDescriptionInput);
//
//                String confirmInput = bufferedReader.readLine();
//                while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
//                    systemPresenter.invalidInput();
//                    confirmInput = bufferedReader.readLine();
//                }
//                if (confirmInput.equalsIgnoreCase("Y")) {
//                    long newItemID = itemManager.createItem(itemNameInput, itemDescriptionInput, currUsername);
//                    userManager.addNormalUserPendingInventory(newItemID, currUsername);
//
//                    systemPresenter.inventoryAddItem(4);
//                } else {
//                    systemPresenter.cancelled();
//                }
//            } else if (input == 2) {    /* remove item */
//                if (itemInventory.isEmpty()) {
//                    systemPresenter.inventoryRemoveItem(1);
//                } else {
//                    systemPresenter.inventoryRemoveItem(2);
//                    String temp2 = bufferedReader.readLine();
//
//                    /* no (0 to quit) option */
//                    while (!temp2.matches("[0-9]+") ||
//                            Integer.parseInt(temp2) > itemInventory.size() || Integer.parseInt(temp2) < 1) {
//                        systemPresenter.invalidInput();
//                        temp2 = bufferedReader.readLine();
//                    }
//                    int indexInput = Integer.parseInt(temp2);
//                    Item selectedItem = itemInventory.get(indexInput - 1);
//                    long selectedItemID = selectedItem.getID();
//
//                    /*
//                     * Allow removal if item is available + not being asked for in a trade request
//                     * OR if item is in a trade that's been cancelled due to users failing to confirm the transaction
//                     */
//                    if (userManager.isRequestedInTrade(currUsername, selectedItemID)) {
//                        systemPresenter.inventoryRemoveItem(4);
//                    } else if (selectedItem.getAvailability() ||
//                            (!selectedItem.getAvailability() && tradeManager.getItemInCancelledTrade(selectedItemID))) {
//                        systemPresenter.inventoryRemoveItem(selectedItem.getName(), indexInput, 1);
//
//                        String confirmInput = bufferedReader.readLine();
//                        while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
//                            systemPresenter.invalidInput();
//                            confirmInput = bufferedReader.readLine();
//                        }
//                        if (confirmInput.equalsIgnoreCase("Y")) {
//
//                            userManager.removeNormalUserInventory(selectedItemID, currUsername);
//                            itemManager.getItem(selectedItemID).setIsRemoved(true);
//                            // don't remove from ItemManager
//
//                            systemPresenter.inventoryRemoveItem(selectedItem.getName(), 0, 2);
//                        } else {
//                            systemPresenter.cancelled();
//                        }
//                    } else {
//                        systemPresenter.inventoryRemoveItem(3);
//                    }
//                }
//            }
//
//        } catch (IOException e) {
//            systemPresenter.exceptionMessage();
//        }
    }

    /**
     * Converts the current user's inventory into an array of string representations and returns it.
     *
     * @return an array containing string representations of the current user's inventory
     */
    public String[] getInventory() {
        // Passing second arg as false means each item's owner username won't be included.
        return itemManager.getItemStringsID(userManager.getNormalUserInventory(currUsername), false);
    }

    /**
     * Converts the current user's pending inventory into an array of string representations and returns it.
     *
     * @return an array containing string representations of the current user's pending inventory
     */
    public String[] getPendingInventory() {
        // Passing second arg as false means each item's owner username won't be included.
        return itemManager.getItemStringsID(userManager.getNormalUserPending(currUsername), false);
    }

    /**
     * Checks the item name and description input by the user.
     *
     * @param itemNameInput        the item name input by the user
     * @param itemDescriptionInput the item description input by the user
     * @return true iff both the name and description are valid
     */
    public boolean validateInput(String itemNameInput, String itemDescriptionInput) {
        return itemNameInput.length() < 3 || !itemDescriptionInput.contains(" ");
    }

    /**
     * Check if the item corresponding to the item ID at the given index
     * in the current user's inventory can be removed.
     *
     * @param index the index of the item that the user wishes to remove
     * @return true iff the user is allowed to remove the item from their inventory
     */
    public boolean validateRemoval(int index) {
        /*
         * Allow removal if item is available + not being asked for in a trade request
         * OR if item is in a trade that's been cancelled due to users failing to confirm the transaction
         */
        long selectedItemID = userManager.getNormalUserInventory(currUsername).get(index);
        Item selectedItem = itemManager.getItem(selectedItemID);
        if (userManager.isRequestedInTrade(currUsername, selectedItemID)) {
            return false;
        } else {
            return selectedItem.getAvailability() ||
                    (!selectedItem.getAvailability() && tradeManager.getItemInCancelledTrade(selectedItemID));
        }
    }

    /**
     * Removes the item ID at the given index from the current user's inventory and changes the
     * status of the corresponding item accordingly.
     *
     * @param index the index of the item ID being removed from inventory
     */
    public void removeInventory(int index) {
        long selectedItemID = userManager.getNormalUserInventory(currUsername).get(index);
        Item selectedItem = itemManager.getItem(selectedItemID);
        userManager.removeFromNormalUserInventory(selectedItemID, currUsername);
        selectedItem.setIsRemoved(true);
    }

    /**
     * Creates a new item using the given name and description and sends it for admin approval.
     *
     * @param itemNameInput        the item name input by the user
     * @param itemDescriptionInput the item description input by the user
     */
    public void addInventory(String itemNameInput, String itemDescriptionInput) {
        long newItemID = itemManager.createItem(itemNameInput, itemDescriptionInput, currUsername);
        userManager.addToNormalUserPending(newItemID, currUsername);
    }
}
