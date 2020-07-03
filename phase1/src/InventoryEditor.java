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
    private User currentUser;
    private String itemNameInput;
    private String itemDescriptionInput;

    public InventoryEditor(User user) {
        // This allows the User to request adding Items to their inventory, or to remove an existing Item.
        currentUser = user;
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
                new UserDashboard(currentUser);
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
                        Item requestedItem = new Item(itemNameInput, itemDescriptionInput);
                        currentUser.addPendingInventory(requestedItem);
                        sp.inventoryAddItem(4);
                    }else{
                        sp.cancelled();
                    }
                    new UserDashboard(currentUser);

                } catch (IOException e) {
                    sp.exceptionMessage();
                }

            } else {
                if (currentUser.getInventory().isEmpty()) {
                    sp.inventoryRemoveItem(1);
                    new UserDashboard(currentUser);
                }

                int index = 1;
                sp.inventoryRemoveItem(2);
                for (Item i : currentUser.getInventory()) {
                    System.out.println(index + i.toString());
                    index++;
                }

                int indexInput;

                try {
                    indexInput = Integer.parseInt(br.readLine());
                    Item selected = currentUser.getInventory().get(indexInput - 1);
                    sp.inventoryRemoveItem(selected.getName(), indexInput, 1);
                    String confirmInput = br.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        sp.invalidInput();
                        confirmInput = br.readLine();
                    }
                    if (confirmInput.equalsIgnoreCase("y")) {
                        currentUser.removeInventory(selected);
                        sp.inventoryRemoveItem(selected.getName(),0,2);
                    } else {
                        sp.cancelled();
                    }
                    new UserDashboard(currentUser);

                } catch (IOException e) {
                    sp.exceptionMessage();
                }
            }

        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }
}
