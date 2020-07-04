import java.util.ArrayList;

/**
 * All the print stuff.
 *
 * @author Ning Zhang
 * @author Kushagra
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-04
 */

public class SystemPresenter {

    //helper method that prints inventory + pending
    private void presentInventory(ArrayList<Item> itemInventory, ArrayList<Item> pendingItems) {
        System.out.println("\n-- Your inventory --");
        if (itemInventory.isEmpty()) {
            System.out.println("nothing here yet!");
        }
        for (Item i : itemInventory) {
            System.out.println((itemInventory.indexOf(i) + 1) + ". " + i);
        }
        System.out.println("\n-- Items awaiting approval --");
        if (pendingItems.isEmpty()) {
            System.out.println("nothing here yet!");
        }
        for (Item i : pendingItems) {
            System.out.println("- " + i);
        }
    }

    //helper method that prints wishlist
    private void presentWishlist(ArrayList<Item> itemWishlist) {
        System.out.println("\n-- Your wishlist --");
        if (itemWishlist.isEmpty()) {
            System.out.println("nothing here yet!");
        }
        for (Item i : itemWishlist) {
            System.out.println((itemWishlist.indexOf(i) + 1) + ". " + i);
        }
    }

    public void inventoryEditor(ArrayList<Item> itemInventory, ArrayList<Item> pendingItems) {

        presentInventory(itemInventory, pendingItems);
        // show user their inventory right after selecting from dashboard?
        // (then edit option substitutes for a separate view inventory option)

        System.out.println("\n   Choose one of the options: " +
                "\n   1 - Add an item to inventory" +
                "\n   2 - Remove item from inventory" +
                "\n   3 - Cancel");
        System.out.print("\nPlease enter your choice here: ");
    }

    public void inventoryAddItem(int input) {
        switch (input) {
            case 1:
                System.out.println("\nEnter the name of the item to add (at least 3 letters):");
                break;

            case 2:
                System.out.println("\nEnter a description (at least 2 words):");
                break;

            case 3:
                System.out.print("\nAre you sure you want to add this item? (Y/N): ");
                break;

            case 4:
                System.out.println("\nYour item has been requested! Please wait for an admin to review it.");
                break;
        }
    }

    public void inventoryAddItem(String name, String description) {
        System.out.println(name + " : " + description);
    }

    public void inventoryRemoveItem(int input) {
        switch (input) {
            case 1:
                System.out.println("\nNo items to remove.");
                break;
            case 2:
                System.out.print("Enter the # of the item you would like to remove: ");
                break;
        }
    }

    public void inventoryRemoveItem(String name, int index, int input) {
        if (input == 1) {
            System.out.print("\nRemove " + index + ". " + name + " from your inventory? (Y/N): ");
        } else {
            System.out.println("\n" + name + " has been removed from your inventory!");
        }
    }

    public void wishlistEditor(ArrayList<Item> itemWishlist) {

        presentWishlist(itemWishlist); //same reasoning as in inventoryEditor

        System.out.println("\n   Choose one of the options:" +
                "\n   1 - Remove item from wish list" +
                "\n   2 - Cancel ");
        System.out.print("   Please enter your choice here: ");
    }

    public void wishlistRemoveItem(int input) {
        switch (input) {
            case 1:
                System.out.println("\nYour wish list is empty.");
                break;
            case 2:
                System.out.println("\nEnter the # of the item you would like to remove:");
                break;
        }
    }

    public void wishlistRemoveItem(String name, int input) {
        switch (input) {
            case 1:
                System.out.println("\nRemove " + name + " from your wishlist?(Y/N)");
                break;
            case 2:
                System.out.println("\n" + name + " is removed from your wishlist!");
                break;
        }
    }

    public void cancelled() {
        System.out.println("\nCancelled!");
    }

    public void exceptionMessage() {
        System.out.println("\nError reading user input!");
    }

    public void invalidInput() {
        System.out.print("\nInvalid input. Please try again: ");
    }

    // doesn't need user to work, (need another SystemPresenter contructor for other classes that need this too)
    public void itemPresenter(String input) {
        switch (input) {
            case "available Items":
                System.out.println("\nThese are all the items available for trade:");
                break;
            case "choose trade":
                System.out.print("\nIs there an item you would like to trade for? (0 to quit): ");
                break;
            case "try again":
                System.out.println("\nInvalid input try again.");
                break;
            case "cancelled":
                System.out.println("\nCancelled.");
                break;
            case "not available":
                System.out.println("\nSorry, this item is currently not available for trade. We suggest adding it to your wishlist!");
                break;
            case "error":
                System.out.println("Trade has resulted in error due to invalid input.");
                break;
        }
    }

    public void tradeRequestPresenter(String input) {
        switch (input) {
            case "sent requests":
                System.out.println("Here is all the trade request(s) you sent:");
                break;
            case "no requests found":
                System.out.println("None!");
                break;
            case "received requests":
                System.out.println("Here is all the trade request(s) you received:");
                break;
            case "to accept":
                System.out.println("Would you like to accept any of these requests?(0 to quit)");
                break;
            case "invalid":
                System.out.println("Invalid input! Please try again.");
                break;
            case "suggest time":
                System.out.println("Please suggest a time(YYYY/MM/DD-HH/MM): ");
                break;
            case "suggest place":
                System.out.println("Please suggest a place: ");
                break;
            case "error":
                System.out.println("Trade has resulted in error due to invalid input.");
                break;
        }
    }

    public void userDashboard(String input) {
        switch (input) {
            case "frozen":
                System.out.println("-- Your account is currently frozen due to you reaching the limit on incomplete trades --");
                break;
            case "menu":
                System.out.println("\nWhat would you like to do:" +
                        "\n 1 - see all items available for trade" +
                        "\n 2 - edit inventory " +
                        "\n 3 - edit wishlist " +
                        "\n 4 - view trade requests " +
                        "\n 5 - view latest trades ");
                break;
            case "unfreeze option":
                System.out.println(" 6 - request to unfreeze account");
                break;
            case "admin options":
                System.out.println(" 6 - view items waiting for approval " +
                        "\n 7 - view accounts to freeze " +
                        "\n 8 - view requests to unfreeze account " +
                        "\n 9 - edit a user's threshold values ");
                break;
            case "initial admin option":
                System.out.println(" 10 - request to unfreeze account");
                System.out.println(" 11 - add a new admin to the system");
                break;
            case "admin frozen option":
                System.out.println(" 10 - request to unfreeze account");
                break;
            case "new admin":
                System.out.println(" 10 - add a new admin to the system");
                break;
            case "logout":
                System.out.println(" 0 - logout ");
                break;
            case "action":
                System.out.print("Please enter your choice here: ");
                break;
            case "invalid":
                System.out.print("Invalid input. Try again: ");
                break;

        }
    }
}
