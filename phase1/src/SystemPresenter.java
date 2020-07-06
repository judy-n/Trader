import java.util.ArrayList;

/**
 * The presenter used for the entire program. Prints to text UI.
 *
 * @author Ning Zhang
 * @author Kushagra
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-05
 */

public class SystemPresenter {
    private final String choicePrompt = "\nPlease enter your choice here: ";

    public void signUpSystem(int input){
        switch (input) {
            case 1:
                System.out.println("\n--- Signup ---");
                System.out.print("Please enter an email: ");
                break;
            case 2:
                System.out.print("Email is already associated with an account! Please enter a different email: ");
                break;
            case 3:
                System.out.print("That's not an email address! Please enter a valid email: ");
                break;
            case 4:
                System.out.print("Please enter an username: ");
                break;
            case 5:
                System.out.print("Username already exists! Please enter a different username: ");
                break;
            case 6:
                System.out.print("Invalid username. Please try again: ");
                break;
            case 7:
                System.out.print("Please enter a password: ");
                break;
            case 8:
                System.out.print("Invalid password. Please try again: ");
                break;
            case 9:
                System.out.print("Please verify your password: ");
                break;
            case 10:
                System.out.print("Passwords do not match. Please try again: ");
                break;
            case 11:
                System.out.println("\n Thank you for signing up! \n You are now logged in.");
                break;
        }
    }


    public void loginSystem(int input){
        switch (input){
            case 1:
                System.out.println("\n--- Login ---");
                System.out.print("Are you an admin? (Y/N): ");
                break;
            case 2:
                System.out.print("Please enter your username: ");
                break;
            case 3:
                System.out.print("Username does not exist in our database! Please try again: ");
                break;
            case 4:
                System.out.print("Please enter your email: ");
                break;
            case 5:
                System.out.print("Email does not exist in our database! Please try again: ");
                break;
            case 6:
                System.out.print("Please enter your password: ");
                break;
            case 7:
                System.out.print("Password does not match email/username! Please try again: ");
                break;
            case 8:
                System.out.println("\n Logged in!");
                break;
            case 9:
                System.out.print("Would you like to login with 1) username or 2) email?: ");
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

    public void catalogViewer(ArrayList<Item> approvedItems){
        System.out.println("\nThese are all the items available for trade:");
        presenterAllItems(approvedItems);

    }
    public void catalogViewer(int input){
        switch (input){
            case 1:
                System.out.print("\nIs there an item you would like to trade for? (0 to quit): ");
                break;
            case 2:
                System.out.println("\nYour account is frozen!");
                break;
        }
    }

    public void catalogViewer(Item item, int input){
        switch (input){
            case 1:
                System.out.println("You have chosen: " + item);
                break;
            case 2:
                System.out.println("Are you sure you want to trade for this item with user, " + item.getOwnerUsername() + " ?(Y/N)");
                break;
            case 3:
                System.out.println("Contacting user, " + item.getOwnerUsername());
                break;
            case 4:
                System.out.println("\nSorry, this item is currently not available for trade. We suggest adding it to your wishlist!");
                break;

        }
    }

    public void catalogEditor(ArrayList<Item> pendingItems){
        System.out.println("\nThese are all the items waiting for approval:");
        presenterAllItems(pendingItems);

    }
    public void catalogEditor(int input){
        switch (input){
            case 1:
                System.out.println("\nThere are no items waiting for approval!");
                break;
            case 2:
                System.out.println("\nIs there an item you would like to approve/deny? (0 to quit)");
                break;

        }

    }

    public void catalogEditor(Item item){
        System.out.println("You have chosen: " + item);
        System.out.println("Would you like to 1) approve or 2) deny this item?");

    }

    private void presenterAllItems(ArrayList<Item> items){
        int index = 1;
        for (Item i : items) {
            System.out.println(index + ". " + i);
            index++;
        }
    }

    public void accountFreezer(ArrayList<String> usernames){
        System.out.println("Here are the users that need to be frozen:");
        int index = 1;
        for(String username : usernames){
            System.out.println(index + ". "+ username);
            index ++;
        }
        System.out.println("Would you like to freeze all of the accounts?(Y/N)");
    }

    public void accountFreezer(){
        System.out.println("All frozen!");
    }

    public void tradeRequestViewer(int input){
        switch (input){
            case 1:
                System.out.println("You did not receive any trade requests.");
                break;
            case 2:
                System.out.println("You did not initiate any trade requests.");
                break;
            case 3:
                System.out.println("Please suggest a place: ");
                break;
            case 4:
                System.out.println("Would you like to accept any of these requests?(0 to quit)");
                break;
            case 5:
                System.out.println("Your account is frozen!");
                break;
        }
    }

    public void tradeRequestViewer(int input, String owner, String itemName){
        switch (input){
            case 1:
                System.out.println("Are you sure you want to trade " + itemName + " with " + owner + "?(Y/N)");
                break;
            case 2:
                System.out.println("Initiating Trade with " + owner);
                System.out.println("Please suggest a time(YYYY/MM/DD-HH/MM): ");
                break;
            case 3:
                System.out.println("Sorry but "+owner+ " is currently frozen.");
                break;
        }

    }

    public void tradeRequestViewer(int input,ArrayList<Item> items, ArrayList<String> owners ){
        switch (input){
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

    private void presentInitiatedTradeRequests(ArrayList<Item> items, ArrayList<String> users){
        if (items.isEmpty()) {
            System.out.println("You did not initiate any trades.");
        } else {
            int index = 0;
            for(Item i : items) {
                System.out.println("Trade for " + i.getName() + " from user " + users.get(index));
                index ++;
            }
        }
    }

    private void presentReceivedTradeRequests(ArrayList<Item> items, ArrayList<String> users){
        int index = 1;
        for (Item i : items)
            System.out.println(index + ". Trade for " + i.getName() + " from user " + users.get(index-1));
    }


    public void normalDashboard(int input){
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

        switch (input){
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

    public void adminDashboard(int input) {
        String menuNotInitAdmin = "\nWhat would you like to do:" +
                "\n 1 - view items awaiting approval" +
                "\n 2 - view accounts to freeze" +
                "\n 3 - view requests to unfreeze account" +
                "\n 4 - edit a user's threshold values";
        String menuInitAdmin = "\n 5 - add new admin to the system";
        String logoutOption = "\n 0 - logout ";

        switch (input) {
            case 1:
                System.out.println(menuNotInitAdmin + logoutOption);
                break;
            case 2:
                System.out.println(menuNotInitAdmin + menuInitAdmin + logoutOption);
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
}
