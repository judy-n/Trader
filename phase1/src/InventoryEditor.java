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
    public User currentUser;
    public String itemNameInput;
    public String itemDescriptionInput;

    public InventoryEditor(User user) {
        // This allows the User to request adding Items to their inventory, or to remove an existing Item.
        currentUser = user;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        System.out.println("Choose one of the options: ");
        System.out.println("1 - Add an item to inventory" +
                "\n2 - Remove item from inventory " +
                "\n3 - Cancel ");
        try {
            input = Integer.parseInt(br.readLine());

            while (input < 1 || input > 3) {
                System.out.println("Invalid input try again.");
                input = Integer.parseInt(br.readLine());
            }

            if (input == 3) {
                new UserDashboard(currentUser);
            }

            if (input == 1) {

                try {
                    do {
                        System.out.println("Enter the name of the item to add(at least 3 letters):");
                        itemNameInput = br.readLine();
                    }while (itemNameInput.length() < 3);

                    do {
                        System.out.println("Enter a description(at least 2 words):");
                        itemDescriptionInput = br.readLine();
                    }while(!itemDescriptionInput.contains(" "));

                    System.out.println("Are you sure you want to add this item?(Y/N)");
                    System.out.println(itemNameInput + " : "+itemDescriptionInput);

                    String confirmInput = br.readLine();

                    while(!confirmInput.equalsIgnoreCase("Y")&&!confirmInput.equalsIgnoreCase("N")){
                        System.out.println("Invalid Input try again.");
                        confirmInput = br.readLine();
                    }

                    if(confirmInput.equalsIgnoreCase("Y")) {
                        Item requestedItem = new Item(itemNameInput, itemDescriptionInput);
                        currentUser.addPendingInventory(requestedItem);
                        System.out.println("Your item has been requested! Please wait for an admin to review it.");
                    }else{
                        System.out.println("Cancelled!");
                    }
                    new UserDashboard(currentUser);

                } catch (IOException e) {
                    System.out.println("Plz try again.");
                }

            } else {
                if (currentUser.inventory.isEmpty()) {
                    System.out.println("No items to remove.");
                    new UserDashboard(currentUser);
                }

                int index = 1;
                System.out.println("Enter the ID of the item you would like to remove:");

                for (Item i : currentUser.inventory) {
                    System.out.println(index + i.toString());
                    index++;
                }

                int indexInput;

                try {
                    indexInput = Integer.parseInt(br.readLine());
                    Item selected = currentUser.inventory.get(indexInput - 1);
                    System.out.println("Remove " + indexInput + ". " + selected.name + " from your inventory?(Y/N)");
                    String confirmInput = br.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        System.out.println("Invalid input try again.");
                        confirmInput = br.readLine();
                    }
                    if (confirmInput.equalsIgnoreCase("y")) {
                        currentUser.removeInventory(selected);
                        System.out.println(selected.name + " is removed from your inventory!");
                    } else {
                        System.out.println("Cancelled.");
                    }
                    new UserDashboard(currentUser);

                } catch (IOException e) {
                    System.out.println("Plz try again.");
                }
            }

        } catch (IOException e) {
            System.out.println("Plz try again.");
        }
    }
}
