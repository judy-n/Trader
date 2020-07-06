import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Shows the user their inventory and lets them edit it through user input.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-06
 */

public class InventoryEditor {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Class constructor.
     * Creates an InventoryEditor with the given logged-in user, item manager, and user manager.
     * Prints to the screen the given user's inventory and options to add/remove/cancel.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public InventoryEditor(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        // This allows the User to request adding Items to their inventory, or to remove an existing Item.
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        ArrayList<Item> itemInventory = im.getApprovedItemsByIDs(currentUser.getInventory());
        ArrayList<Item> pendingItems = im.getPendingItemsByIDs(currentUser.getPendingInventory());
        sp.inventoryEditor(itemInventory, pendingItems);
        try {
            input = Integer.parseInt(br.readLine());
            while (input < 1 || input > 3) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }

            if (input == 1) {   //add item
                String itemNameInput;
                String itemDescriptionInput;
                try {
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
                        im.addPendingItem(requestedItem);

                        sp.inventoryAddItem(4);
                    } else {
                        sp.cancelled();
                    }
                    close();

                } catch (IOException e) {
                    sp.exceptionMessage();
                    System.exit(-1);
                }

            } else if (input == 2) {    //remove item
                if (itemInventory.isEmpty()) {
                    sp.inventoryRemoveItem(1);
                    close();
                }

                sp.inventoryRemoveItem(2);
                int indexInput;
                try {
                    indexInput = Integer.parseInt(br.readLine());
                    Item selectedItem = itemInventory.get(indexInput - 1); //changed it so no need for ID

                    sp.inventoryRemoveItem(selectedItem.getName(), indexInput, 1);
                    String confirmInput = br.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        sp.invalidInput();
                        confirmInput = br.readLine();
                    }
                    if (confirmInput.equalsIgnoreCase("Y")) {

                        currentUser.removeInventory(selectedItem.getID());
                        im.removeApprovedItem(selectedItem);

                        sp.inventoryRemoveItem(selectedItem.getName(), 0, 2);
                    } else {
                        sp.cancelled();
                    }
                    close();

                } catch (IOException e) {
                    sp.exceptionMessage();
                    System.exit(-1);
                }
            } else {    //cancel
                close();
            }

        } catch (IOException e) {
            sp.exceptionMessage();
            System.exit(-1);
        }
    }
    private void close(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
