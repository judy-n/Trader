import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * InventoryEditor.java
 * Lets the user editor their inventory
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-01
 */

public class InventoryEditor {
    private NormalUser currentUser;
    private String itemNameInput;
    private String itemDescriptionInput;
    private ItemManager im;
    private UserManager um;

    public InventoryEditor(NormalUser user, ItemManager im, UserManager um) {
        // This allows the User to request adding Items to their inventory, or to remove an existing Item.
        currentUser = user;
        this.im = im;
        this.um = um;
        SystemPresenter sp = new SystemPresenter(currentUser);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        sp.inventoryEditor();
        try {
            input = Integer.parseInt(br.readLine());

            while (input < 1 || input > 3) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }

            if (input == 3) {
                new UserDashboard(currentUser, im, um);
            }

            if (input == 1) {

                try {
                    do {
                        sp.inventoryAddItem(1);
                        itemNameInput = br.readLine();
                    }while (itemNameInput.length() < 3);

                    do {
                        sp.inventoryAddItem(2);
                        itemDescriptionInput = br.readLine();
                    }while(!itemDescriptionInput.contains(" "));
                    sp.inventoryAddItem(3);
                    sp.inventoryAddItem(itemNameInput, itemDescriptionInput);
                    String confirmInput = br.readLine();

                    while(!confirmInput.equalsIgnoreCase("Y")&&!confirmInput.equalsIgnoreCase("N")){
                        sp.invalidInput();
                        confirmInput = br.readLine();
                    }

                    if(confirmInput.equalsIgnoreCase("Y")) {
                        Item requestedItem = new Item(itemNameInput, itemDescriptionInput, currentUser.getUsername());
                        currentUser.addPendingInventory(requestedItem.getId());
                        sp.inventoryAddItem(4);
                    }else{
                        sp.cancelled();
                    }
                    new UserDashboard(currentUser, im, um);

                } catch (IOException e) {
                    sp.exceptionMessage();
                }

            } else {
                if (currentUser.getInventory().isEmpty()) {
                    sp.inventoryRemoveItem(1);
                    new UserDashboard(currentUser, im, um);
                }

                int index = 1;
                sp.inventoryRemoveItem(2);
                for (Long id : currentUser.getInventory()) {
                    Item tempItem = im.getApprovedItem(id);
                    System.out.println(index + tempItem.toString());
                    index++;
                }

                int indexInput;

                try {
                    indexInput = Integer.parseInt(br.readLine());
                    long idSelected = currentUser.getInventory().get(indexInput - 1);
                    Item selected = im.getApprovedItem(idSelected);
                    sp.inventoryRemoveItem(selected.getName(), indexInput, 1);
                    String confirmInput = br.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        sp.invalidInput();
                        confirmInput = br.readLine();
                    }
                    if (confirmInput.equalsIgnoreCase("y")) {
                        currentUser.removeInventory(selected.getId());
                        sp.inventoryRemoveItem(selected.getName(),0,2);
                    } else {
                        sp.cancelled();
                    }
                    new UserDashboard(currentUser, im, um);

                } catch (IOException e) {
                    sp.exceptionMessage();
                }
            }

        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }
}
