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
 * Shows the user their inventory and lets them edit it through user input.
 * If the user adds an item to their inventory, it will first be sent to an admin for approval.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-08-03
 */

public class InventoryEditor {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;
    private List<Item> itemInventory;
    /**
     * Creates an <InventoryEditor></InventoryEditor> with the given normal user,
     * item/user/trade managers, and notification system.
     * Prints to the screen the given user's inventory and options to add/remove/cancel.
     *
     * @param currUsername  the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public InventoryEditor(String currUsername, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {
        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        itemInventory = itemManager.getItemsByIDs(userManager.getNormalUserInventory(currUsername));
        List<Item> pendingItems = itemManager.getItemsByIDs(userManager.getNormalUserPending(currUsername));

//        systemPresenter.inventoryEditor(itemInventory, pendingItems);
//        try {
//            String temp = bufferedReader.readLine();
//            while (!temp.matches("[1-3]")) {
//                systemPresenter.invalidInput();
//                temp = bufferedReader.readLine();
//            }
//            int input = Integer.parseInt(temp);
//
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

    public String[] getInventory(){
        ArrayList<String> stringInventory = new ArrayList<>();
        itemInventory = itemManager.getItemsByIDs(userManager.getNormalUserInventory(currUsername));
        int index = 1;
        for(Item item :itemInventory){
            stringInventory.add(index + ". "+ item.toString());
            index++;
        }
        return stringInventory.toArray(new String[itemInventory.size()]);
    }

    public String[] getPendingInventory(){
        ArrayList<String> stringInventory = new ArrayList<>();
        List<Item> pendingItems = itemManager.getItemsByIDs(userManager.getNormalUserPending(currUsername));
        int index = 1;
        for(Item item : pendingItems){
            stringInventory.add(index + ". " + item.toString() + "(pending)");
            index ++;
        }
        return stringInventory.toArray(new String[pendingItems.size()]);
    }


    public boolean validateInput(String itemNameInput, String itemDescriptionInput){
        return itemNameInput.length() < 3 || !itemDescriptionInput.contains(" ");
    }

    public boolean validateRemoval(int index){
        /*
        *Allow removal if item is available + not being asked for in a trade request
        * OR if item is in a trade that's been cancelled due to users failing to confirm the transaction
        */
        Item selectedItem = itemInventory.get(index);
        long selectedItemID = selectedItem.getID();
        if (userManager.isRequestedInTrade(currUsername, selectedItemID)) {
            return false;
        }else return selectedItem.getAvailability() ||
                (!selectedItem.getAvailability() && tradeManager.getItemInCancelledTrade(selectedItemID));
    }

    public void removeInventory(int index){
        Item selectedItem = itemInventory.get(index);
        long selectedItemID = selectedItem.getID();
        userManager.removeFromNormalUserInventory(selectedItemID, currUsername);
        itemManager.getItem(selectedItemID).setIsRemoved(true);
    }

    public void addInventory(String itemNameInput, String itemDescriptionInput){
        long newItemID = itemManager.createItem(itemNameInput, itemDescriptionInput, currUsername);
        userManager.addToNormalUserPending(newItemID, currUsername);
    }
}
