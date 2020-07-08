import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Shows the user their inventory and lets them edit it through user input.
 * If the user adds an item to their inventory, it will first be sent to an admin for approval.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-08
 */

public class InventoryEditor {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Class constructor.
     * Creates an InventoryEditor with the given logged-in user and item/user/trade managers.
     * Prints to the screen the given user's inventory and options to add/remove/cancel.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public InventoryEditor(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Item> itemInventory = itemManager.getApprovedItemsByIDs(currentUser.getInventory());
        ArrayList<Item> pendingItems = itemManager.getPendingItemsByIDs(currentUser.getPendingInventory());

        sp.inventoryEditor(itemInventory, pendingItems);
        try {
            String temp = br.readLine();
            while (!temp.matches("[1-3]+") || Integer.parseInt(temp) > 3) {
                sp.invalidInput();
                temp = br.readLine();
            }
            int input = Integer.parseInt(temp);

            if (input == 1) {   //add item
                String itemNameInput;
                String itemDescriptionInput;

                do {
                    sp.inventoryAddItem(1);
                    itemNameInput = br.readLine();
                } while (itemNameInput.length() < 3);   //name at least 3 char long

                do {
                    sp.inventoryAddItem(2);
                    itemDescriptionInput = br.readLine();
                } while (!itemDescriptionInput.contains(" "));  //description at least two words

                sp.inventoryAddItem(3);
                sp.inventoryAddItem(itemNameInput, itemDescriptionInput);

                String confirmInput = br.readLine();
                while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                    sp.invalidInput();
                    confirmInput = br.readLine();
                }
                if (confirmInput.equalsIgnoreCase("Y")) {
                    Item requestedItem = new Item(itemNameInput, itemDescriptionInput, currentUser.getUsername());
                    currentUser.addPendingInventory(requestedItem.getID());
                    itemManager.addPendingItem(requestedItem);
                    sp.inventoryAddItem(4);
                } else {
                    sp.cancelled();
                }
            } else if (input == 2) {    //remove item
                if (itemInventory.isEmpty()) {
                    sp.inventoryRemoveItem(1);
                } else {
                    sp.inventoryRemoveItem(2);
                    String temp2 = br.readLine();
                    while (!temp2.matches("[0-9]+") || Integer.parseInt(temp2) > itemInventory.size()) {
                        sp.invalidInput();
                        temp2 = br.readLine();
                    }
                    int indexInput = Integer.parseInt(br.readLine());
                    Item selectedItem = itemInventory.get(indexInput - 1);

                    sp.inventoryRemoveItem(selectedItem.getName(), indexInput, 1);

                    String confirmInput = br.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        sp.invalidInput();
                        confirmInput = br.readLine();
                    }
                    if (confirmInput.equalsIgnoreCase("Y")) {

                        currentUser.removeInventory(selectedItem.getID());
                        itemManager.removeApprovedItem(selectedItem);

                        sp.inventoryRemoveItem(selectedItem.getName(), 0, 2);
                    } else {
                        sp.cancelled();
                    }
                }
            }
            close();

        } catch (IOException e) {
            sp.exceptionMessage();
            System.exit(-1);
        }
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
