import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The presenter used for the entire program. Prints to text UI.
 *
 * @author Ning Zhang
 * @author Kushagra
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-11
 */
public class SystemPresenter {
    private final String choicePrompt = "\nPlease enter your choice here: ";

    /**
     * Opens menus for logging in, singing up, or closing the program based on user input
     * @param input The input either 1, 2, or 3.
     */
    public void startMenu(int input) {
        switch (input) {
            case 1:
                System.out.print("----- WELCOME -----\n 1) Sign up \n 2) Log in \n 3) Exit the program" + choicePrompt);
                break;
            case 2:
                System.out.println("Exiting the program. Hope to see you again soon!");
                break;
        }
    }

    /**
     * Prompt based system for signing up a user based on the user's input
     * @param input the user's input
     */
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


    /**
     * Prompt based system for logging in an already existing user based on userinput
     * @param input the user's input
     */
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
    private void presentInventory(List<Item> itemInventory, List<Item> pendingItems) {
        System.out.println("\n-- Your inventory --");
        if (itemInventory.isEmpty()) {
            emptyListMessage();
        }
        for (Item i : itemInventory) {
            System.out.println((itemInventory.indexOf(i) + 1) + ". " + i);
        }

        System.out.println("\n-- Items awaiting approval --");
        if (pendingItems.isEmpty()) {
            emptyListMessage();
        }
        for (Item i : pendingItems) {
            System.out.println("- " + i);
        }
    }

    //helper method that prints wishlist
    private void presentWishlist(List<Item> itemWishlist) {
        System.out.println("\n-- Your wishlist --");
        if (itemWishlist.isEmpty()) {
            emptyListMessage();
        }
        int index = 1;
        for (Item i : itemWishlist) {
            if (i != null) {
                System.out.println(index + ". " + i);
            } else {
                System.out.println(index + ". (item has been removed from system)");
            }
            index++;
        }
    }

    /**
     * Prompts for if a user would like to edit their inventory
     * @param itemInventory The approved inventory of the user
     * @param pendingItems THe pending inventory of the user
     */
    public void inventoryEditor(List<Item> itemInventory, List<Item> pendingItems) {
        presentInventory(itemInventory, pendingItems);
        System.out.println("\n   Choose one of the options: " +
                "\n   1 - Add an item to inventory" +
                "\n   2 - Remove item from inventory" +
                "\n   3 - Cancel");
        System.out.print(choicePrompt);
    }

    /**
     * Prompts user for an item they would like to add
     * @param input the user's input
     */
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

    /**
     * Prints details of an item to be added
     * @param name the name of the item
     * @param description the description of the item
     */
    public void inventoryAddItem(String name, String description) {
        System.out.println(name + " : " + description);
    }

    /**
     * Prompts for removing an item from a user's inventory
     * @param input the user's input
     */
    public void inventoryRemoveItem(int input) {
        switch (input) {
            case 1:
                System.out.println("\nNo items to remove.");
                break;
            case 2:
                System.out.print("\nEnter the index of the item you would like to remove: ");
                break;
            case 3:
                System.out.println("\nYou may not remove this item as it is currently involved in a trade.");
                break;
            case 4:
                System.out.println("\nYou may not remove this item as it has been requested by one or more other users." +
                        "\nPlease reject their requests before attempting to remove this item again.");
                break;
        }
    }

    /**
     * Prompts a user for if they want to remove an item from their inventory, and tells them if it has been removed
     * @param name the name of the item
     * @param index the index of the item
     * @param input the user's input
     */
    public void inventoryRemoveItem(String name, int index, int input) {
        if (input == 1) {
            System.out.print("\nRemove " + index + ". [" + name + "] from your inventory? (Y/N): ");
        } else {
            System.out.println("\n[" + name + "] has been removed from your inventory!");
        }
    }

    /**
     * Prompts for editing a user's wishlist
     * @param itemWishlist  the user's wishlist
     */
    public void wishlistEditor(List<Item> itemWishlist) {
        presentWishlist(itemWishlist);
        System.out.println("\n   Choose one of the options:" +
                "\n   1 - Remove item from wishlist" +
                "\n   2 - Cancel ");
        System.out.print(choicePrompt);
    }

    /**
     * Prompts user with whether they would like to choose an item to remove from their wishlist
     * @param input the the user's input
     */
    public void wishlistRemoveItem(int input) {
        switch (input) {
            case 1:
                System.out.println("\nYour wishlist is empty.");
                break;
            case 2:
                System.out.print("\nEnter the index of the item you would like to remove: ");
                break;
        }
    }

    /**
     * Prompts the user on whether to remove a specific item from their wishlist
     * @param name the name of the item
     * @param input the user's input
     */
    public void wishlistRemoveItem(String name, int input) {
        switch (input) {
            case 1:
                System.out.print("\nRemove [" + name + "] from your wishlist? (Y/N): ");
                break;
            case 2:
                System.out.println("\n[" + name + "] has been removed from your wishlist!");
                break;
        }
    }

    /**
     * Prints the available approved items that can be borrowed from the marketplace
     * @param approvedItems the approved items
     */
    public void catalogViewer(List<Item> approvedItems) {
        System.out.println("\nThese are all the items available for trade:");
        presenterAllItems(approvedItems);
    }

    /**
     * Tells a user that they are borrowing more than their threshold
     * @param user the user to be notified
     */
    public void catalogViewer(NormalUser user) {
        System.out.println("\nYou're borrowing too much! You need to lend AT LEAST " + user.getLendMinimum() + " more item(s) than you've borrowed.");
    }

    /**
     * Prompts user about items in the catalog
     * @param input the user's input
     */
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
                System.out.println("\nItem has been added to your wishlist!");
                break;
            case 5:
                System.out.println("\nThis item is already in your wishlist!");
                break;
            case 6:
                System.out.println("\nYou've already sent a request to borrow this item!");
                break;
        }
    }

    /**
     * Prompts the user about an item they have chosen from the catalog
     * @param item the item the user has chosen
     * @param input the user's input
     */
    public void catalogViewer(Item item, int input) {
        switch (input) {
            case 1:
                System.out.print("\nYou have chosen: [" + item + "]\n Would you like to 1) trade or 2) wishlist this item? (0 to cancel): ");
                break;
            case 2:
                System.out.print("\nAre you sure you want to trade for this item with user < " + item.getOwnerUsername() + " >? (Y/N): ");
                break;
            case 3:
                System.out.println("\nYour request to borrow [" + item + "] has been sent to < " + item.getOwnerUsername() + " >" +
                        "\nIf this item was not already in your wishlist, it has automatically been added.");
                break;
        }
    }

    /**
     * Presents pending items
     * @param pendingItems the pending items
     */
    public void catalogEditor(List<Item> pendingItems) {
        System.out.println("\nThese are all the items waiting for approval:");
        presenterAllItems(pendingItems);
    }

    /** takes input from user on which items to approve/reject iff items exist
     * @param input the user's input
     */
    public void catalogEditor(int input) {
        switch (input) {
            case 1:
                System.out.println("\nThere are no items waiting for approval!");
                break;
            case 2:
                System.out.print("\nIs there an item you would like to approve/reject? (0 to quit): ");
                break;
        }
    }

    /**
     * Prompts the user about whether to approve or reject a chosen item
     * @param item the chosen item
     */
    public void catalogEditor(Item item) {
        System.out.println("\nYou have chosen: [" + item + "]");
        System.out.print("Would you like to 1) approve or 2) reject this item? (0 to quit): ");
    }

    private void presenterAllItems(List<Item> items) {
        if (items.isEmpty()) {
            emptyListMessage();
        } else {
            int index = 1;
            for (Item i : items) {
                System.out.println(index + ". " + i);
                index++;
            }
        }
    }

    public void accountFreezer(List<String> usernames) {
        System.out.println("\nHere are the users that need to be frozen:");
        int index = 1;
        for (String username : usernames) {
            System.out.println(index + ". " + username);
            index++;
        }
        if (usernames.isEmpty()) {
            emptyListMessage();
        } else {
            System.out.print("\nWould you like to freeze all of the accounts above? (Y/N): ");
        }
    }

    public void accountFreezer() {
        System.out.println("\nAll frozen!");
    }

    public void requestUnfreeze(int input) {
        switch (input) {
            case 1:
                System.out.println("\nYou already sent an unfreeze request, please wait for an admin to review it.");
                break;
            case 2:
                System.out.println("\nYour request has been sent in! Please allow some time for an admin to review it.");
                break;
        }
    }

    public void adminGetUnfreezeRequests(List<NormalUser> unfreezeRequests) {
        System.out.println("\nHere are the users that requested to be unfrozen:");
        int index = 1;
        for (User u : unfreezeRequests) {
            System.out.println(index + ". " + u.getUsername());
            index++;
        }
        if (unfreezeRequests.isEmpty()) {
            emptyListMessage();
        } else {
            System.out.print("\nWould you like to unfreeze any of the accounts? (Y/N): ");
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
        }
    }

    public void tradeRequestViewer(int input) {
        switch (input) {
            case 1:
                System.out.print("\nOnce you're done viewing your initiated trades, enter 0 to quit: ");
                break;
            case 2:
                System.out.print("\nPlease suggest a place: ");
                break;
            case 3:
                System.out.println("\nSorry, you can't view any trade requests because your account is currently frozen.");
                break;
            case 4:
                System.out.print("\nPlease suggest a date and time using the given format (YYYY/MM/DD-hh:mm): ");
                break;
            case 5:
                System.out.print("Would you like any item in their inventory? Enter the item's index (0 if not): ");
                break;
            case 6:
                System.out.print("\nWould you like to make a 1) permanent or 2) temporary trade?: ");
                break;
            case 7:
                System.out.println("\nThe other user currently has no items available for trade, so you cannot borrow anything from them.");
                break;
            case 8:
                System.out.println("\nThe item that this user wants to borrow is currently being lent to someone else!");
                break;
            case 9:
                System.out.println("\nTrade request has been rejected.");
                break;
        }
    }

    public void tradeRequestViewer(List<Item> items) {
        //for viewing initiator's inventory
        System.out.println("\nTheir inventory:");
        presenterAllItems(items);
    }

    public void tradeRequestViewer(int input, String owner, String itemName) {
        switch (input) {
            case 1:
                System.out.print("\nAre you sure you want to lend [" + itemName + "] to < " + owner + " > in a one-way or two-way trade? (Y/N): ");
                break;
            case 2:
                System.out.println("\nInitiating trade with < " + owner + " >");
                break;
            case 3:
                System.out.print("\nWould you like to 1) accept, or 2) reject < " + owner + " >'s request?: ");
                break;
            case 4:
                System.out.print("\nAre you sure you want to reject < " + owner + " >'s request? (Y/N): ");
                break;
        }

    }

    public void tradeRequestViewer(int input, List<Item> items, List<String> owners) {
        switch (input) {
            case 1:
                System.out.println("\nHere are all the trade requests you sent:");
                presentInitiatedTradeRequests(items, owners);
                break;
            case 2:
                System.out.println("\nHere are all the trade requests you received:");
                presentReceivedTradeRequests(items, owners);

                break;
        }
    }

    private void presentInitiatedTradeRequests(List<Item> items, List<String> users) {
        int index = 1;
        for (Item i : items) {
            if (i.getAvailability()) {
                System.out.println(index + ". Trade for [" + i.getName() + "] sent to < " + users.get(index - 1) + " >");
            } else {
                System.out.println(index + ". Trade for [" + i.getName() + "] sent to < " + users.get(index - 1) + " >" +
                        "\n    (their item is currently lent out to someone else)");
            }
            index++;
        }
        if (items.isEmpty()) {
            System.out.println("You don't have any sent trade requests waiting for approval.");
        }
    }

    private void presentReceivedTradeRequests(List<Item> items, List<String> users) {
        int index = 1;
        for (Item i : items) {
            if (i.getAvailability()) {
                System.out.println(index + ". Trade for [" + i.getName() + "] from < " + users.get(index - 1) + " >");
            } else {
                System.out.println(index + ". Trade for [" + i.getName() + "] from < " + users.get(index - 1) + " >" +
                        "\n    (your item is currently lent out to someone else)");
            }
            index++;
        }
        if (items.isEmpty()) {
            System.out.println("You haven't received any trade requests yet.");
        } else {
            System.out.print("\nWould you like to accept/reject any of these requests? Enter the request's index (0 to quit): ");
        }
    }

    public void ongoingTrades(int input) {
        switch (input) {
            case 1:
                System.out.println("\nWhat would you like to do? " +
                        "\n 1. Edit meeting time and place" +
                        "\n 2. Confirm this trade's current meeting time and place" +
                        "\n 3. Confirm the latest meeting took place" +
                        "\n 4. Cancel this trade" +
                        "\n 5. Quit");
                System.out.print(choicePrompt);
                break;
            case 2:
                System.out.println("\nThis trade has been cancelled!");
                break;
            case 3:
                System.out.println("\nThe latest meeting has been confirmed! " +
                        "\nIf the other user doesn't also confirm within a day of the latest meeting, " +
                        "you're both at risk of having your accounts be frozen.");
                break;
            case 4:
                System.out.println("\nThe trade's meeting time and place has been confirmed!");
                break;
            case 5:
                System.out.println("\nYou and your trade partner have already agreed upon a meeting date/time/location.");
                break;
            case 6:
                System.out.println("\nYou were the last person to suggest the meeting details! " +
                        "\nPlease wait for the other user to agree or send a suggestion of their own.");
                break;
            case 7:
                System.out.print("\nPlease suggest a date and time using the given format (YYYY/MM/DD-hh:mm): ");
                break;
            case 8:
                System.out.print("\nPlease suggest a place: ");
                break;
            case 9:
                System.out.println("\nYou've reached your maximum number of edits!");
                break;
            case 10:
                System.out.println("\nSorry, you can't confirm this date and time because you've " +
                        "reached the maximum number of meetings allowed in the same week.");
                break;
            case 11:
                System.out.println("\nSuggestion for meeting details successfully set!");
                break;
            case 12:
                System.out.println("\nThis meeting has already been agreed upon, so you cannot edit it.");
                break;
            case 13:
                System.out.println("\nYour trade partner has also confirmed, so this transaction is now closed.");
                break;
            case 14:
                System.out.println("\nYour trade partner has also confirmed, " +
                        "so your second meeting has been set to exactly 30 days from the first meeting (same time, same place).");
                break;
            case 15:
                System.out.println("\nYou and your trade partner have not yet agreed upon a meeting date/time/location!");
                break;
            case 16:
                System.out.println("\nCannot confirm a meeting before it is scheduled to take place.");
                break;
            case 17:
                System.out.println("\nYou've already confirmed that the latest transaction took place!");
                break;
            case 18:
                System.out.println("\nYou may not cancel a trade after the meeting has already been scheduled.");
                break;
        }
    }

    public void ongoingTrades(int situation, LocalDateTime meeting, Trade trade) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String meetingStr = meeting.format(formatter);
        String date = meetingStr.substring(0, meetingStr.indexOf(" "));
        String time = meetingStr.substring(meetingStr.indexOf(" ") + 1);

        switch(situation) {
            case 1:
                System.out.println("\nMost recent meeting suggestion: " + date + " at " + time +
                        " - " + trade.getMeetingLocation1());
                break;
            case 2:
                if (trade instanceof TemporaryTrade) {
                    System.out.println("\nFirst meeting: " + date + " at " + time +
                            " - " + trade.getMeetingLocation1());
                } else {
                    System.out.println("\nMeeting: " + date + " at " + time +
                            " - " + trade.getMeetingLocation1());
                }
                break;
            case 3:
                System.out.println("\nSecond meeting: " + date + " at " + time +
                        " - " + ((TemporaryTrade) trade).getMeetingLocation2());
                break;
        }
    }

    public void ongoingTrades(int numEdits, boolean isFinalEdit) {
        System.out.println("\n# of edits you've made so far: " + numEdits);
        if (isFinalEdit) {
            System.out.println("Warning: This is the last time you can suggest a meeting.");
        }
    }

    public void ongoingTrades(List<Trade> ongoingTrades, List<Item[]> tradeItems, String username) {
        System.out.println("\nHere are all your ongoing trades:");
        int index = 1;
        for (Trade trade : ongoingTrades) {
            Item[] tempItems = tradeItems.get(index - 1);
            long[] tempItemIDs = new long[2];
            if (tempItems[0] != null) {
                tempItemIDs[0] = tempItems[0].getID();
            }
            if (tempItems[1] != null) {
                tempItemIDs[1] = tempItems[1].getID();
            }
            String tradePrint;
            if (tempItemIDs[0] == 0) {
                tradePrint = trade.toString(username) + "you're borrowing [" + tempItems[1].getName() + "]";
            } else if (tempItemIDs[1] == 0) {
                tradePrint = trade.toString(username) + "you're lending [" + tempItems[0].getName() + "]";
            } else {
                tradePrint = trade.toString(username) + "you're lending [" +
                        tempItems[0].getName() + "] for [" + tempItems[1].getName() + "]";
            }
            System.out.println(index + ". " + tradePrint);
            index++;
        }

        if (ongoingTrades.isEmpty()) {
            emptyListMessage();
        } else {
            System.out.print("\nEnter the index of the trade you wish to view (0 to quit): ");
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
                System.out.print(menuUnfrozen + logoutOption + choicePrompt);
                break;
            case 2:
                System.out.println(frozenWarning + menuUnfrozen + menuFrozen + logoutOption + choicePrompt);
                break;
            case 3:
                System.out.println("\nLogging out of the program now. See ya!");
                break;
        }
    }

    public void thresholdEditor(int input) {
        switch (input) {
            case 1:
                System.out.print("\nPlease enter the username of the user whose threshold you would like to change (0 to quit): ");
                break;
            case 2:
                System.out.print("\nWhich threshold would you like to change? (0 to quit) " +
                        "\n 1 - Weekly trade maximum " +
                        "\n 2 - Meeting edit maximum " +
                        "\n 3 - Lend minimum" +
                        "\n 4 - Incomplete trade maximum" + choicePrompt);
                break;
        }
    }

    public void thresholdEditor(int input, int oldThreshold) {
        switch (input) {
            case 1:
                System.out.print("\nThe current weekly trade max is " + oldThreshold +
                        "\n Change it to: ");
                break;
            case 2:
                System.out.print("\nThe current meeting edit max is " + oldThreshold +
                        "\n Change it to: ");
                break;
            case 3:
                System.out.print("\nThe current lend min is " + oldThreshold +
                        "\n Change it to: ");
                break;
            case 4:
                System.out.print("\nThe current incomplete trade max is " + oldThreshold +
                        "\n Change it to: ");
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

    public void exceptionMessage(int input, String process, String type) {
        switch (input) {
            case 1:
                System.out.println("\n" + process + " error for " + type + "!");
                break;
            case 2:
                System.out.println("\nMissing files for deserialization of " + type + "!");
                break;
        }
        System.exit(-1);
    }

    public void invalidInput() {
        System.out.print("\nInvalid input. Please try again: ");
    }

    public void failedSuggestion() {
        System.out.print("\nSorry, you can't suggest this date and time because you've " +
                "reached the maximum number of meetings allowed in the same week." +
                "\nPlease enter a different date and time (not within the same week): ");
    }

    private void emptyListMessage() {
        System.out.println("nothing here yet!");
    }
}
