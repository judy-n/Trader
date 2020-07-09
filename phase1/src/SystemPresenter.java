import java.util.ArrayList;

/**
 * The presenter used for the entire program. Prints to text UI.
 *
 * @author Ning Zhang
 * @author Kushagra
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-08
 */
public class SystemPresenter {
    private final String choicePrompt = "\nPlease enter your choice here: ";

    public void startMenu(int input) {
        switch(input) {
            case 1:
                System.out.print("----- WELCOME -----\n 1) Sign up \n 2) Log in \n 3) Exit the program" + choicePrompt);
                break;
            case 2:
                System.out.println("Exiting the program. Hope to see you again soon!");
                break;
        }
    }

    public void signUpSystem(int input) {
        switch (input) {
            case 0:
                System.out.println("\n--- Sign-up ---");
                break;
            case 1:
                System.out.print("\nPlease enter an email: ");
                break;
            case 2:
                System.out.print("\nEmail is already associated with an account! Please enter a different email: ");
                break;
            case 3:
                System.out.print("\nThat's not an email address! Please enter a valid email: ");
                break;
            case 4:
                System.out.print("\nPlease enter an username (at least 3 characters): ");
                break;
            case 5:
                System.out.print("\nUsername already exists! Please enter a different username: ");
                break;
            case 6:
                System.out.print("\nInvalid username. Please try again: ");
                break;
            case 7:
                System.out.print("\nPlease enter a password: ");
                break;
            case 8:
                System.out.print("\nInvalid password. Please try again: ");
                break;
            case 9:
                System.out.print("\nPlease verify your password: ");
                break;
            case 10:
                System.out.print("\nPasswords do not match. Please try again: ");
                break;
            case 11:
                System.out.println("\n Thank you for signing up! \n You are now logged in.");
                break;
            case 12:
                System.out.println("\n--- New Admin ---");
                break;
        }
    }


    public void loginSystem(int input) {
        switch (input) {
            case 1:
                System.out.println("\n--- Login ---");
                System.out.print("\nAre you an admin? (Y/N): ");
                break;
            case 2:
                System.out.print("\nPlease enter your username: ");
                break;
            case 3:
                System.out.print("\nUsername does not exist in our database! Please try again: ");
                break;
            case 4:
                System.out.print("\nPlease enter your email: ");
                break;
            case 5:
                System.out.print("\nEmail does not exist in our database! Please try again: ");
                break;
            case 6:
                System.out.print("\nPlease enter your password: ");
                break;
            case 7:
                System.out.print("\nPassword does not match email/username! Please try again: ");
                break;
            case 8:
                System.out.println("\n Logged in!");
                break;
            case 9:
                System.out.print("\nWould you like to login with 1) username or 2) email?: ");
                break;
        }
    }

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
        System.out.println("\n   Choose one of the options: " +
                "\n   1 - Add an item to inventory" +
                "\n   2 - Remove item from inventory" +
                "\n   3 - Cancel");
        System.out.print(choicePrompt);
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
                System.out.print("Enter the index of the item you would like to remove: ");
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
        presentWishlist(itemWishlist);
        System.out.println("\n   Choose one of the options:" +
                "\n   1 - Remove item from wish list" +
                "\n   2 - Cancel ");
        System.out.print(choicePrompt);
    }

    public void wishlistRemoveItem(int input) {
        switch (input) {
            case 1:
                System.out.println("\nYour wish list is empty.");
                break;
            case 2:
                System.out.println("\nEnter the index of the item you would like to remove:");
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

    public void catalogViewer(ArrayList<Item> approvedItems) {
        System.out.println("\nThese are all the items available for trade:");
        presenterAllItems(approvedItems);

    }

    public void catalogViewer(int input) {
        switch (input) {
            case 1:
                System.out.print("\nIs there an item you would like to trade for or add to your wishlist?" +
                        "\nPlease enter your choice here (0 to quit): ");
                break;
            case 2:
                System.out.println("\nYou cannot initiate any trades at the moment due to your account being frozen." +
                        "\nHowever, you may still add items to your wishlist.");
                break;
            case 3:
                System.out.print("\nSorry, this item is currently not available for trade." +
                        "\nWould you like to add it to your wishlist? (Y/N): ");
                break;
            case 4:
                System.out.println("\n Item has been added to your wishlist!");
                break;
            case 5:
                System.out.println("\n You've borrowed more than your lend minimum");
                break;
        }
    }

    public void catalogViewer(Item item, int input) {
        switch (input) {
            case 1:
                System.out.print("\nYou have chosen: [" + item + "]\n Would you like to 1) trade or 2) wishlist this item? (0 to cancel): ");
                break;
            case 2:
                System.out.print("\nAre you sure you want to trade for this item with user, " + item.getOwnerUsername() + " ? (Y/N): ");
                break;
            case 3:
                System.out.println("\nYour request to borrow [" + item + "] has been sent to " + item.getOwnerUsername() +
                        "\nIf this item was not already in your wishlist, it has automatically been added.");
                break;
        }
    }

    public void catalogEditor(ArrayList<Item> pendingItems) {
        System.out.println("\nThese are all the items waiting for approval:");
        presenterAllItems(pendingItems);
    }

    public void catalogEditor(int input) {
        switch (input) {
            case 1:
                System.out.println("\nThere are no items waiting for approval!");
                break;
            case 2:
                System.out.print("\nIs there an item you would like to approve/deny? (0 to quit): ");
                break;
        }
    }

    public void catalogEditor(Item item) {
        System.out.println("You have chosen: " + item);
        System.out.println("Would you like to 1) approve or 2) reject this item?");
    }

    private void presenterAllItems(ArrayList<Item> items) {
        if (items.isEmpty()) {
            System.out.println("Nothing here yet!");
        } else {
            int index = 1;
            for (Item i : items) {
                System.out.println(index + ". " + i);
                index++;
            }
        }
    }

    public void accountFreezer(ArrayList<String> usernames) {
        System.out.println("Here are the users that need to be frozen:");
        int index = 1;
        for (String username : usernames) {
            System.out.println(index + ". " + username);
            index++;
        }
        System.out.println("Would you like to freeze all of the accounts?(Y/N)");
    }

    public void accountFreezer() {
        System.out.println("All frozen!");
    }

    public void requestUnfreeze(int input) {
        switch (input){
            case 1:
                System.out.println("\nYou already sent an unfreeze request, please wait for an admin to review it.");
                break;
            case 2:
                System.out.println("\nYour request has been sent in! Please allow some time for an admin to review it.");
                break;
        }
    }

    public void adminGetUnfreezeRequests(ArrayList<NormalUser> unfreezeRequests) {
        System.out.println("\nHere are the users that requested to be unfrozen:");
        int index = 1;
        for (User u : unfreezeRequests) {
            System.out.println(index + ". " + u.getUsername());
            index++;
        }
    }

    public void adminGetUnfreezeRequests(int input) {
        switch (input) {
            case 1:
                System.out.print("\nEnter the index of the user you would like to unfreeze (0 to quit): ");
                break;
            case 2:
                System.out.println("\nThe user has been unfrozen!");
                break;
            case 3:
                System.out.println("\nFinished!");
                break;
            case 4:
                System.out.print("Would you like to unfreeze any of the accounts? (Y/N): ");
                break;
        }
    }

    public void tradeRequestViewer(int input) {
        switch (input) {
            case 1:
                System.out.println("You did not receive any trade requests.");
                break;
            case 2:
                System.out.println("You did not initiate any trade requests.");
                break;
            case 3:
                System.out.print("\nPlease suggest a place: ");
                break;
            case 4:
                System.out.print("\nWould you like to accept any of these requests? (0 to quit): ");
                break;
            case 5:
                System.out.println("\nSorry, you can't view any trade requests because your account is frozen!");
                break;
            case 6:
                System.out.print("\nPlease suggest a time (DD/MM/YYYY-HH/MM): ");
                break;
            case 7:
                System.out.print("\nWould you like any item in their inventory? (0 to quit): ");
                break;
            case 8:
                System.out.print("\nWould you like to make a 1) permanent or 2) temporary trade?: ");
                break;
        }
    }

    public void tradeRequestViewer(ArrayList<Item> items){
        int index = 1;
        for (Item i : items) {
            System.out.println(index+ ". " + i);
            index ++;
        }
    }

    public void tradeRequestViewer(int input, String owner, String itemName) {
        switch (input) {
            case 1:
                System.out.println("Are you sure you want to trade " + itemName + " with " + owner + "?(Y/N)");
                break;
            case 2:
                System.out.println("Initiating Trade with " + owner + ".");
                break;
            case 3:
                System.out.println("Sorry but " + owner + " is currently frozen. " +
                        "\nPlease pick another request: ");
                break;

        }

    }

    public void tradeRequestViewer(int input, ArrayList<Item> items, ArrayList<String> owners) {
        switch (input) {
            case 1:
                System.out.println("Here is all the trade request(s) you sent:");
                presentInitiatedTradeRequests(items, owners);
                break;
            case 2:
                System.out.println("Here is all the trade request(s) you received:");
                presentReceivedTradeRequests(items, owners);

                break;
        }
    }

    private void presentInitiatedTradeRequests(ArrayList<Item> items, ArrayList<String> users) {
        if (items.isEmpty()) {
            System.out.println("You did not initiate any trades.");
        } else {
            int index = 0;
            for (Item i : items) {
                System.out.println("Trade for " + i.getName() + " from user " + users.get(index));
                index++;
            }
        }
    }

    private void presentReceivedTradeRequests(ArrayList<Item> items, ArrayList<String> users) {
        int index = 1;
        for (Item i : items) {
            System.out.println(index + ". Trade for " + i.getName() + " from user " + users.get(index - 1));
            index ++;
        }
    }

    public void ongoingTrades(int input){
        switch (input) {
            case 1 :
                System.out.println("What would you like to?");
                System.out.println("1. Edit meeting time and/or place " +
                        "\n 2. Confirm this trade's current meeting time and place " +
                        "\n 3. Confirm this trade took place " +
                        "\n 4. Cancel this trade (Penalties will apply) " +
                        "\n 5. Quit");
                break;
            case 2:
                System.out.println("The trade has been cancelled!");
                break;
            case 3:
                System.out.println("The trade has been confirmed!");
                break;
            case 4:
                System.out.println("The trade's meeting time and place has been confirmed!");
                break;
            case 5:
                System.out.println("These meeting details are set!");
                break;
            case 6:
                System.out.println("You were the person who suggested this details!");
                break;
            case 7:
                System.out.println("Please suggest a time (DD/MM/YYYY-HH/MM):");
                break;
            case 8:
                System.out.println("Please suggest a place:");
                break;
            case 9:
                System.out.println("You've reached your edit max!");
                break;
            case 10:
                System.out.println("You can't comfirm this time since you've reach your weekly trade max!");
                break;
        }
    }

    public void ongoingTrades(ArrayList<Trade> ongoingTrades, ArrayList<Item[]> tradeItems, String username) {
        System.out.println("\nHere are all your ongoing trades:");
        int index = 1;
        for (Trade trade : ongoingTrades) {
            Item[] tempItems = tradeItems.get(index - 1);
            long[] tempItemIDs = {tempItems[0].getID(), tempItems[1].getID()};
            String tradePrint;
            if (tempItemIDs[0] == 0) {
                tradePrint = trade.toString(username) + "you're borrowing " + tempItems[1].getName();
            } else if (tempItemIDs[1] == 0) {
                tradePrint = trade.toString(username) + "you're lending " + tempItems[0].getName();
            } else {
                tradePrint = trade.toString(username) + "you're lending " +
                        tempItems[0].getName() + " for " + tempItems[1].getName();
            }
            System.out.println(index + ". " + tradePrint);
            index++;
        }
    }

    public void normalDashboard(int input) {
        String frozenWarning = "\n-- Your account is currently frozen due to you reaching the limit on incomplete trades --";
        String menuUnfrozen = "\nWhat would you like to do:" +
                "\n 1 - see all items available for trade" +
                "\n 2 - edit inventory" +
                "\n 3 - edit wishlist" +
                "\n 4 - view trade requests" +
                "\n 5 - view ongoing trades" +
                "\n 6 - view most recent 3 trades" +
                "\n 7 - view top 3 trade partners";
        String menuFrozen = "\n 8 - request to unfreeze account";
        String logoutOption = "\n 0 - logout ";

        switch (input) {
            case 1:
                System.out.println(menuUnfrozen + logoutOption);
                System.out.print(choicePrompt);
                break;
            case 2:
                System.out.println(frozenWarning + menuUnfrozen + menuFrozen + logoutOption);
                System.out.print(choicePrompt);
                break;
            case 3:
                System.out.println("\nLogging out of the program now. See ya!");
                break;
        }
    }

    public void thresholdEditor(int input) {
        switch (input) {
            case 1:
                System.out.println("\nPlease enter the username of the user whose threshold you would like to change:");
                break;
            case 2:
                System.out.println("\nWhich threshold would you like to change? (0 to quit) " +
                        "\n 1 - Weekly trade maximum " +
                        "\n 2 - Meeting edit maximum " +
                        "\n 3 - Lend minimum" +
                        "\n 4 - Incomplete trade maximum");
                System.out.print(choicePrompt);
                break;
        }
    }

    public void thresholdEditor(int input, int oldThreshold) {
        switch (input) {
            case 1:
                System.out.println("The current weekly trade max is " + oldThreshold +
                        "\n Change it to :");
                break;
            case 2:
                System.out.println("The current meeting edit max is " + oldThreshold +
                        "\n Change it to :");
                break;
            case 3:
                System.out.println("The current lend min is " + oldThreshold +
                        "\n Change it to :");
                break;
            case 4:
                System.out.println("The current incomplete trade max is " + oldThreshold +
                        "\n Change it to :");
                break;
        }
    }

    public void adminCreator() {
        System.out.println("\n New admin created!");
    }

    public void showAdminID(AdminUser user) {
        System.out.println("\nYour admin ID: " + user.getAdminID());
    }

    public void adminDashboard(int input) {
        String menuNotInitAdmin = "What would you like to do:" +
                "\n 1 - view items awaiting approval" +
                "\n 2 - view accounts to freeze" +
                "\n 3 - view requests to unfreeze account" +
                "\n 4 - edit a user's threshold values";
        String menuInitAdmin = "\n 5 - add new admin to the system";
        String logoutOption = "\n 0 - logout ";

        switch (input) {
            case 1:
                System.out.print(menuNotInitAdmin + logoutOption + choicePrompt);
                break;
            case 2:
                System.out.print(menuNotInitAdmin + menuInitAdmin + logoutOption + choicePrompt);
                break;
        }
    }

    public void cancelled() {
        System.out.println("\nCancelled!");
    }

    public void exceptionMessage() {
        System.out.println("\nError reading user input!");
        System.exit(-1);
    }

    public void exceptionMessage(int input, String process, String type)  {
        switch(input) {
            case 1:
                System.out.println(process + " error for " + type + "!");
                break;
            case 2:
                System.out.println("Missing files for serialization of " + type + "!");
                break;
        }
        System.exit(-1);
    }

    public void invalidInput() {
        System.out.print("\nInvalid input. Please try again: ");
    }
}
