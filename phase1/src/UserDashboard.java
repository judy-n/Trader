import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.sql.Array;
import java.sql.SQLOutput;

/**
 * UserDashboard.java
 * Displays a dashboard once the user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-01
 */


public class UserDashboard {
    public User currentUser;
    private int input;
    int maxChoice = 5;
    int specialCase = 0;

    /**
     * Creates a UserDashboard that stores the given user who is currently logged in.
     *
     * @param user the given User
     */
    public UserDashboard(User user) {
        currentUser = user;

        System.out.println("What would you like to do: ");
        if (currentUser.getIsFrozen()) {
            System.out.println("-- Your account is currently frozen due to you reaching the limit on incomplete trades --");
        }
        System.out.println(" 1 - see all items available for trade" +
                "\n 2 - edit inventory " +
                "\n 3 - edit wishlist " +
                "\n 4 - view trade requests " +
                "\n 5 - view latest trades ");
        if (!(currentUser instanceof AdminUser) && currentUser.getIsFrozen()) {     //frozen non-admin
            maxChoice = 6;
            specialCase = 1;
            System.out.println(" 6 - request to unfreeze account");
        } else if (currentUser instanceof AdminUser) {    //admin
            maxChoice = 9;
            specialCase = 2;
            System.out.println(" 6 - view items waiting for approval " +
                    "\n 7 - view accounts to freeze " +
                    "\n 8 - view requests to unfreeze account " +
                    "\n 9 - edit a user's threshold values ");
            if (currentUser.getIsFrozen() && (((AdminUser) currentUser).getAdminID() == 1)) {   //initial admin allowed to add subsequent admins
                maxChoice = 11;
                specialCase = 3;
                System.out.println(" 10 - request to unfreeze account");
                System.out.println(" 11 - add a new admin to the system");
            } else if (currentUser.getIsFrozen()) {
                maxChoice = 10;
                specialCase = 4;
                System.out.println(" 10 - request to unfreeze account");
            } else if (((AdminUser) currentUser).getAdminID() == 1) {
                maxChoice = 10;
                specialCase = 5;
                System.out.println(" 10 - add a new admin to the system");
            }
        }
        System.out.println(" 0 - log out ");
        System.out.print("Please enter the number of the action you wish to take: ");

        selectChoice();

    }

    private void selectChoice() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = Integer.parseInt(br.readLine());
            while (input < 0 || input > maxChoice) {
                System.out.print("Invalid input. Try again: ");
                input = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading user input.");
        }

        switch (input) {
            case 0:
                try {
                    br.close();

                    // TODO: write everything to file :(

                } catch (IOException e) {
                    System.out.println("Error closing input stream.");
                }
                System.out.println("Logging out of the program. Bye!");
                System.exit(0);

            case 1:
                new ItemPresenter(currentUser);
                break;

            case 2:
                // This allows the User to request adding Items to their inventory, or to remove an existing Item.

                System.out.println("Choose one of the options: ");
                System.out.println("1 - Add an item to inventory" +
                                "\n2 - Remove item from inventory " +
                                "\n3 - Cancel ");
                try {
                    input = Integer.parseInt(br.readLine());
                    while (input <0 || input >3){
                        System.out.println("Invalid input try again.");
                        input = Integer.parseInt(br.readLine());
                    }
                } catch (IOException e) {
                    System.out.println("Plz try again.");
                }
                switch (input) {
                    case 1:
                        System.out.println("Enter the name of the item to add:");
                        String itemNameInput = null;
                        try {
                            itemNameInput = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Plz try again.");
                        }
                        System.out.println("Enter a description:");
                        String itemDescriptionInput = null;
                        try {
                            itemDescriptionInput = br.readLine();
                        } catch (IOException e) {
                            System.out.println("Plz try again.");
                        }
                        Item requestedItem = new Item(itemNameInput, itemDescriptionInput);
                        if (itemNameInput != null && itemDescriptionInput != null){
                            currentUser.addPendingInventory(requestedItem);
                            System.out.println("Your item has been requested! Please wait for an admin to review it.");
                        }
                        else {
                            System.out.println("Request failed.");
                        }
                        new UserDashboard(currentUser);

                    case 2:
                        if (currentUser.inventory.isEmpty()){
                            System.out.println("No items to remove.");
                            break;
                        }
                        System.out.println("Enter the ID of the item you would like to remove:");
                        for(Item i: currentUser.inventory){
                            System.out.println(i);
                        }
                        int itemIdInput = 0;
                        try {
                            itemIdInput = Integer.parseInt(br.readLine());
                        } catch (IOException e) {
                            System.out.println("Plz try again.");
                        }
                        boolean removed = false;
                        for(Item i: currentUser.inventory){
                            if (i.id == itemIdInput){
                                currentUser.removeInventory(i);
                                removed = true;
                            }
                        }
                        if (removed) {
                            System.out.println("Item removed successfully!");
                        }
                        else {
                            System.out.println("Invalid ID. Please try again.");
                        }
                        new UserDashboard(currentUser);
                    case 3:
                        new UserDashboard(currentUser);

                }
            case 3:
                // This lets the User remove Items from the wish list. Assuming that they only add Items to
                // the wishlist when browsing items available for trade.

                System.out.println("Choose one of the options: ");
                System.out.println("1 - Remove item from wish list" +
                        "\n2 - Cancel ");
                try {
                    input = Integer.parseInt(br.readLine());
                    while (input <0 || input >2){
                        System.out.println("Invalid input try again.");
                        input = Integer.parseInt(br.readLine());
                    }
                } catch (IOException e) {
                    System.out.println("Plz try again.");
                }
                if (input == 2){ new UserDashboard(currentUser);}
                if (currentUser.wishlist.isEmpty()) {
                    System.out.println("Your wish list is empty.");
                }
                else{
                    System.out.println("Enter the ID of the item you would like to remove:");
                    ArrayList<Item> wishlistItems = currentUser.getItemWishlist();
                    for (Item i: wishlistItems){
                        System.out.println(i);
                    }
                    int itemIdInput = 0;
                    try {
                        itemIdInput = Integer.parseInt(br.readLine());
                    } catch (IOException e) {
                        System.out.println("Plz try again.");
                    }
                    boolean removed = false;
                    for (Long id: currentUser.wishlist){
                        if (id == itemIdInput) {
                            Item itemToRemove = ItemDatabase.getItem(id);
                            assert itemToRemove != null;
                            currentUser.removeWishlist(itemToRemove);
                            removed = true;
                        }
                    }
                    if (removed) {
                        System.out.println("Item removed successfully!");
                    }
                    else {
                        System.out.println("Invalid ID. Please try again.");
                    }

                }
                new UserDashboard(currentUser);

            case 4:
                new TradeRequestPresenter(currentUser);
                new UserDashboard(currentUser);
            case 5:

                break;

            case 6:
                if (specialCase == 1) {
                    // unfreeze request option for frozen non-admin
                    break;
                } else {
                    // view items waiting for approval option for admin
                    break;
                }

            case 7:
                //view account to freeze option for admin
                break;

            case 8:
                // view requests to unfreeze account option for admin
                break;

            case 9:
                // edit a user's threshold values option for admin
                break;

            case 10:
                if (specialCase == 3 || specialCase == 4) {
                    // request to unfreeze account option for frozen admin
                    break;
                } else {
                    // add a new admin to the system option for frozen init admin
                    break;
                }

            case 11:
                // add a new admin to the system option for NON-frozen init admin
                break;
        }
    }
}
