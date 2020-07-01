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
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-01
 */


public class UserDashboard {
    public User user;
    private int input;

    /**
     * UserDashboard
     * Creates a userDashboard with a user
     * @param u user
     */
    public UserDashboard(User u) {
        user = u;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("What would you like to do: ");
        System.out.println(" 1 - see all items available for trade" +
                "\n 2 - edit inventory " +
                "\n 3 - edit wishlist " +
                "\n 4 - view trade requests " +
                "\n 5 - view latest trades " +
                "\n 0 - log out ");
        try {
            input = Integer.parseInt(br.readLine());
            while(input<0 || input>5){
                System.out.println("Invalid input try again.");
                input = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Plz try again.");
        }
        switch (input) {
            case 0:
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing input stream.");
                }
                System.out.println("Logging out of the program. Bye!");
                System.exit(0);

            case 1:
                new ItemPresenter(user);
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
                            ItemRequestDatabase.add(requestedItem);
                            System.out.println("Your item has been requested! Please wait for an admin to review it.");
                        }
                        else {
                            System.out.println("Request failed.");
                        }
                        new UserDashboard(user);

                    case 2:
                        if (user.inventory.isEmpty()){
                            System.out.println("No items to remove.");
                            break;
                        }
                        System.out.println("Enter the ID of the item you would like to remove:");
                        for(Item i: user.inventory){
                            System.out.println(i);
                        }
                        int itemIdInput = 0;
                        try {
                            itemIdInput = Integer.parseInt(br.readLine());
                        } catch (IOException e) {
                            System.out.println("Plz try again.");
                        }
                        boolean removed = false;
                        for(Item i: user.inventory){
                            if (i.id == itemIdInput){
                                user.removeInventory(i);
                                removed = true;
                            }
                        }
                        if (removed) {
                            System.out.println("Item removed successfully!");
                        }
                        else {
                            System.out.println("Invalid ID. Please try again.");
                        }
                        new UserDashboard(user);
                    case 3:
                        new UserDashboard(user);

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
                if (input == 2){ new UserDashboard(user);}
                if (user.wishlist.isEmpty()) {
                    System.out.println("Your wish list is empty.");
                }
                else{
                    System.out.println("Enter the ID of the item you would like to remove:");
                    ArrayList<Item> wishlistItems = user.getItemWishlist();
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
                    for (Integer id: user.wishlist){
                        if (id == itemIdInput) {
                            Item itemToRemove = ItemDatabase.getItem(id);
                            assert itemToRemove != null;
                            user.removeWishlist(itemToRemove);
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
                new UserDashboard(user);

            case 4:
                new TradeRequestPresenter(user);
                new UserDashboard(user);
            case 5:

                break;

        }

    }
}
