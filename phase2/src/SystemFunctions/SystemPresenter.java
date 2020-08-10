package SystemFunctions;

import Entities.Item;
import Entities.Trade;
import Entities.TemporaryTrade;
import java.util.List;

/**
 * The presenter used for the entire program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @author Kushagra
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-08-09
 */
public class SystemPresenter {
    private final StartMenuPresenter startMenuPresenter;
    private final NormalDashPresenter normalDashPresenter;
    private final AdminDashPresenter adminDashPresenter;

    private final String choicePrompt = "\nPlease enter your choice here: ";

    public SystemPresenter() {
        startMenuPresenter = new StartMenuPresenter();
        normalDashPresenter = new NormalDashPresenter();
        adminDashPresenter = new AdminDashPresenter();
    }

    public String startMenu(int input) {
        return startMenuPresenter.startMenu(input);
    }

    public String signUpSystem(int input) {
        return startMenuPresenter.signUpSystem(input);
    }

    public String loginSystem(int input) {
        return startMenuPresenter.loginSystem(input);
    }

    public String getNormalPopUpMessage(int type) {
        return normalDashPresenter.getPopUpMessage(type);
    }

    public String setUpNormalDash(int type) {
        return normalDashPresenter.setUpDash(type);
    }

    public String setUpAdminDash(int type) {
        return adminDashPresenter.setUpDash(type);
    }

    public String getAdminPopUpMessage(int type) {
        return adminDashPresenter.getPopUpMessage(type);
    }

    /**
     * Prompts for if a user would like to edit their inventory
     *
     * @param itemInventory The approved inventory of the user
     * @param pendingItems  THe pending inventory of the user
     */
    public void inventoryEditor(List<Item> itemInventory, List<Item> pendingItems) {
        System.out.println("\n-- Your inventory --");
        presentAllItems(itemInventory, false);
        System.out.println("\n-- Items awaiting approval --");
        presentAllItems(pendingItems, false);
        System.out.println("\n   Choose one of the options: " +
                "\n   1 - Add an item to inventory" +
                "\n   2 - Remove item from inventory" +
                "\n   3 - Cancel");
        System.out.print(choicePrompt);
    }

    /**
     * Returns a message telling the user they've successfully sent an item for admin approval.
     *
     * @return the string to display
     */
    public String inventoryAddItem() {
        return ("Your item has been requested! Please wait for an admin to review it.");
    }

    /**
     * Prompts for removing an item from a user's inventory
     *
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
                System.out.println("\nYou may not remove this item as it is involved in one or more trade requests." +
                        "\nPlease make sure these requests have been rejected before attempting to remove this item again.");
                break;
        }
    }

    /**
     * Prompts a user for if they want to remove an item from their inventory, and tells them if it has been removed
     *
     * @param name  the name of the item
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
     * Returns message telling user they've successfully removed an item from their wishlist.
     *
     * @param name the name of the item removed
     */
    public String wishlistRemoveItem(String name) {
        return ("[" + name + "] has been removed from your wishlist!");
    }

    /**
     * Prints the available approved items that can be borrowed from the marketplace
     *
     * @param approvedItems the approved items
     */
    public void catalogViewer(List<Item> approvedItems) {
        System.out.println("\nThese are all the items available for trade:");
        presentAllItems(approvedItems, true);
    }

    /**
     * Tells a user that they are borrowing more than their threshold.
     *
     * @param lendMinimum the lending minimum of the current user
     */
    public void catalogViewerLendWarning(int lendMinimum) {
        System.out.println("\nYou're borrowing too much! You need to lend AT LEAST " + lendMinimum + " more item(s) than you've borrowed.");
    }

    /**
     * Prompts user about items in the catalog.
     *
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
            case 7:
                System.out.println("\nThis item's owner is currently frozen!" +
                        "\nWould you like to add it to your wishlist instead? (Y/N): ");
                break;
        }
    }

    /**
     * Prompts the user about an item they have chosen from the catalog
     *
     * @param itemName the name of the selected item
     * @param owner    the owner of the selected item
     * @param input    the user's input
     */
    public void catalogViewer(String itemName, String owner, int input) {
        switch (input) {
            case 1:
                System.out.print("\nYou have chosen: [" + itemName + "]\n Would you like to 1) trade or 2) wishlist this item? (0 to cancel): ");
                break;
            case 2:
                System.out.print("\nAre you sure you want to trade for this item with user " + owner + "? (Y/N): ");
                break;
        }
    }

    /**
     * Returns the labels for elements on the screen while reviewing pending items.
     *
     * @param input the case corresponding the label being retrieved
     * @return the string to display
     */
    public String catalogEditor(int input) {
        switch (input) {
            case 1:
                return "These are all the items waiting for approval:";
            default:
                return null;
        }
    }

    /*
     * Replaced by method getItemStrings() in ItemManager!
     *
     * Presents all given items in a numbered list.
     * Also presents all items with their owner's username iff withOwner is true.
     */
    private void presentAllItems(List<Item> items, boolean withOwner) {
        if (items.isEmpty()) {
            emptyListMessage();
        } else {
            int index = 1;
            for (Item i : items) {
                System.out.println(index + ". " + i);
                if (withOwner) {
                    System.out.println("     added by " + i.getOwnerUsername());
                }
                index++;
            }
        }
    }

    /**
     * Returns the labels for elements on the account freezer screen of the program.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String accountFreezer(int input) {
        switch (input) {
            case 1:
                return "Here are the users that need to be frozen:";
            case 2:
                return "Would you like to freeze all of the accounts above? (Y/N): ";
            case 3:
                return "All frozen!";
            default:
                return null;
        }
    }

    /**
     * Presents whether or not a request to unfreeze their account has been confirmed, based off an input
     *
     * @param input the input
     */
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

    /**
     * Returns the labels for elements on the screen for viewing and accepting unfreeze requests.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String accountUnfreezer(int input) {
        switch (input) {
            case 1:
                return "Here are the users that requested to be unfrozen:";
            case 2:
                return "Select the unfreeze request you'd like to accept:";
            case 3:
                return "The selected user has been unfrozen!";
            default:
                return null;
        }
    }

    /**
     * Returns the labels for elements on the trade request viewer screen.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String tradeRequestViewer(int input) {
        switch (input) {
            case 1:
                return ("Once you're done viewing your initiated trades, enter 0 to quit: ");
            case 2:
                return ("Please suggest a place: ");
            case 3:
                return ("Sorry, you can't view any trade requests because your account is currently frozen.");
            case 4:
                return ("Please suggest a date and time using the given format (YYYY/MM/DD-hh:mm): ");
            case 6:
                return ("Would you like to make this a 1) permanent or 2) temporary trade?: ");
            case 8:
                return ("One of the items involved in this request is currently being lent to someone else!");
            case 9:
                return ("Trade request has been rejected.");
            default:
                return null;
        }
    }

    /**
     * Presents text based on input relating to specifics about trades
     *
     * @param input     the input
     * @param sender    the user who sent the trade request
     * @param itemNames the name of the items to be traded;
     *                  index 0 - item owned by the sender, index 1 - item owned by the recipient
     */
    public void tradeRequestViewer(int input, String sender, String[] itemNames) {
        switch (input) {
            case 1:
                if (itemNames[0].equals("")) {
                    System.out.print("\nAre you sure you want to lend [" + itemNames[1] + "] to " + sender +
                            " in a one-way trade? (Y/N): ");
                } else {
                    System.out.print("\nAre you sure you want to trade [" + itemNames[1] + "] for [" +
                            itemNames[0] + "] with" + sender + " in a two-way? (Y/N): ");
                }
                break;
            case 2:
                System.out.println("\nInitiating trade with " + sender + "...");
                break;
            case 3:
                System.out.print("\nWould you like to 1) accept, or 2) reject " + sender + "'s request?: ");
                break;
            case 4:
                System.out.print("\nAre you sure you want to reject " + sender + "'s request? (Y/N): ");
                break;
        }

    }

    /**
     * Presents requested and received trades to a user, based off an input
     *
     * @param input     the input
     * @param itemNames the names of items in the requests
     * @param users     the senders or recipients of the requests
     */
    public void tradeRequestViewer(int input, List<String[]> itemNames, List<String> users) {
        switch (input) {
            case 1:
                System.out.println("\nHere are all the trade requests you sent:");
                //presentInitiatedTradeRequests(itemNames, users);
                break;
            case 2:
                System.out.println("\nHere are all the trade requests you received:");
                //presentReceivedTradeRequests(itemNames, users);
                break;
        }
    }

    /**
     * Presents trade requests user has initiated
     *
     * @param itemNames  the names of the items in the initiated trade requests
     * @param recipients the recipients of the initiated trade requests
     */
    public String[] presentInitiatedTradeRequests(List<String[]> itemNames, List<String> recipients) {
        return normalDashPresenter.presentInitiatedTradeRequests(itemNames, recipients);
    }

    /**
     * Presents trade requests user has received
     *
     * @param itemNames the names of the items in the received trade requests
     * @param senders   the senders the received trade requests
     */
    public String[] presentReceivedTradeRequests(List<String[]> itemNames, List<String> senders) {
        return normalDashPresenter.presentReceivedTradeRequests(itemNames, senders);
    }

    /**
     * Returns the labels for elements on the ongoing trades screen of the program.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String ongoingTrades(int input) {
        switch (input) {
            case 1:
                return ("Edit meeting details");
            case 2:
                return ("Confirm suggested meeting");
            case 3:
                return ("Confirm the latest meeting took place");
            case 4:
                return ("Cancel trade");
            case 5:
                return ("This trade has been cancelled!");
            case 6:
                return ("The latest meeting has been confirmed! " +
                        "\nIf the other user doesn't also confirm within a day of the latest meeting, " +
                        "you're both at risk of having your accounts be frozen.");
            case 7:
                return ("The trade's meeting time and place has been confirmed!");
            case 8:
                return ("You and your trade partner have already agreed upon a meeting.");
            case 9:
                return ("You were the last person to suggest the meeting details! " +
                        "\nPlease wait for the other user to agree or send a suggestion of their own.");
            case 10:
                return ("Please suggest a date and time using the given format (YYYY/MM/DD-hh:mm): ");
            case 11:
                return ("Please suggest a place: ");
            case 12:
                return ("You've reached your maximum number of edits!");
            case 13:
                return ("Sorry, you can't confirm this date and time because you've " +
                        "reached the maximum number of meetings allowed in the same week.");
            case 14:
                return ("Suggestion for meeting details successfully set!");
            case 15:
                return ("This meeting has already been agreed upon, so you cannot edit it.");
            case 16:
                return ("Your trade partner has also confirmed the transaction, so this permanent trade is now closed." +
                        "\n The item you lent in this permanent trade has automatically been removed from your inventory," +
                        "\n and if the other user's item was in your wishlist it has been removed.\"");
            case 17:
                return ("Your trade partner has also confirmed the transaction, so this temporary trade is now closed." +
                        "\nThe item you lent is now available for trade again.");
            case 18:
                return ("Your trade partner has also confirmed the transaction, " +
                        "so your second meeting has been set to exactly 30 days from the first meeting (same time, same place).");
            case 19:
                return ("You and your trade partner have not yet agreed upon a meeting!");
            case 20:
                return ("Cannot confirm a meeting before it is scheduled to take place.");
            case 21:
                return ("You've already confirmed that the latest transaction took place!");
            case 22:
                return ("You may not cancel a trade after the meeting has already been scheduled.");
            case 23:
                return ("The suggested meeting time has already passed! You'll have to suggest a new time and place.");
            default:
                return null;
        }
    }

    /**
     * Presents the number of edits a user has made, and if they are on their final edit
     *
     * @param numEdits    the number of edits the user has made
     * @param isFinalEdit true iff this is the user's final edit
     */
    public String ongoingTrades(int numEdits, boolean isFinalEdit) {
        if (isFinalEdit) {
            return ("<html># of edits you've made so far: " + numEdits +
                    "<br/> Warning: This is the last time you can suggest a meeting.<html>");
        } else {
            return ("# of edits you've made so far: " + numEdits);
        }
    }

    /**
     * Formats all ongoing trades for a user into an array of string representations and returns it.
     *
     * @param ongoingTrades   the list of ongoing trades
     * @param tradeItemIDs    the IDs of the items involved in the trades
     * @param tradeItemNames  the names of the items involved in the trades
     * @param username        the username of the current user
     * @param dateTimeHandler a <DateTimeHandler></DateTimeHandler> for formatting the dates and times to be displayed
     */
    public String[] getOngoingTradeStrings(List<Trade> ongoingTrades,
                                           List<long[]> tradeItemIDs, List<String[]> tradeItemNames,
                                           String username, DateTimeHandler dateTimeHandler) {

        String[] ongoingTradeStrings = new String[ongoingTrades.size()];
        int index = 0;

        for (Trade trade : ongoingTrades) {
            String[] tempItemNames = tradeItemNames.get(index);
            long[] tempItemIDs = tradeItemIDs.get(index);

            StringBuilder tradePrint = new StringBuilder("<html>");
            if (tempItemIDs[0] == 0) {
                tradePrint.append(trade.toString(username)).append("you're borrowing [")
                        .append(tempItemNames[1]).append("]");
            } else if (tempItemIDs[1] == 0) {
                tradePrint.append(trade.toString(username)).append("you're lending [")
                        .append(tempItemNames[0]).append("]");
            } else {
                tradePrint.append(trade.toString(username)).append("you're lending [")
                        .append(tempItemNames[0]).append("] for [")
                        .append(tempItemNames[1]).append("]");
            }
            // New line
            tradePrint.append("<br/> ");

            String meetingStr = dateTimeHandler.getDateTimeString(trade.getFirstMeetingDateTime());
            String date = meetingStr.substring(0, meetingStr.indexOf("-"));
            String time = meetingStr.substring(meetingStr.indexOf("-") + 1);
            String location = trade.getFirstMeetingLocation();

            if (!trade.getHasAgreedMeeting()) {

                /* display latest meeting suggestion */
                tradePrint.append("Most recent meeting suggestion: ");

            } else {

                /* display first meeting details */
                if (trade instanceof TemporaryTrade) {
                    tradePrint.append("First meeting: ");
                } else {
                    tradePrint.append("Meeting: ");
                }
            }
            tradePrint.append(date).append(" at ").append(time).append(" - ").append(location);

            if (trade instanceof TemporaryTrade) {
                TemporaryTrade tempTrade = (TemporaryTrade) trade;
                if (tempTrade.hasSecondMeeting()) {

                    /* display second meeting details */
                    meetingStr = dateTimeHandler.getDateTimeString(tempTrade.getSecondMeetingDateTime());
                    date = meetingStr.substring(0, meetingStr.indexOf("-"));
                    time = meetingStr.substring(meetingStr.indexOf("-") + 1);

                    tradePrint.append("<br/> ").append("Second meeting: ").append(date).append(" at ")
                            .append(time).append(" - ").append(tempTrade.getSecondMeetingLocation());
                }
            }
            tradePrint.append("<html>");
            ongoingTradeStrings[index] = tradePrint.toString();
            index++;
        }
        return ongoingTradeStrings;
    }

    /**
     * Presents a dashboard to a user, and prints information based on an input
     *
     * @param input the input
     */
    public void normalDashboard(int input) {
        String frozenWarning = "\n-- Your account is currently frozen due to you reaching the " +
                "limit on incomplete trades --";
        String vacationWarning = "\n-- Your account status is currently On Vacation.";
        String menuUnfrozen = "\nWhat would you like to do:" +
                "\n 1 - see all items available for trade" +
                "\n 2 - edit inventory" +
                "\n 3 - edit wishlist" +
                "\n 4 - view trade requests" +
                "\n 5 - view ongoing trades" +
                "\n 6 - view most recent 3 trades" +
                "\n 7 - view top 3 trade partners";
        String menuFrozen = "\n 8 - request to unfreeze account";
        String menuOnVacation = "\n 9 - set status to On vacation";
        String menuNotOnVacation = "\n 10 - set status to Not On vacation";
        String logoutOption = "\n 0 - log out";

        switch (input) {
            // for a user that is not frozen and not on vacation
            case 1:
                System.out.print(menuUnfrozen + menuOnVacation + logoutOption + choicePrompt);
                break;
            // for a user that is frozen and on vacation
            case 2:
                System.out.println(frozenWarning + vacationWarning + menuUnfrozen + menuFrozen +
                        menuNotOnVacation + logoutOption + choicePrompt);
                break;
            // for a user that is frozen and not on vacation
            case 3:
                System.out.println(frozenWarning + menuUnfrozen + menuFrozen + menuOnVacation +
                        logoutOption + choicePrompt);
                break;
            // for a user that is not frozen and on vacation
            case 4:
                System.out.println(vacationWarning + menuUnfrozen + menuFrozen +
                        menuNotOnVacation + logoutOption + choicePrompt);
                break;
            case 9:
                System.out.println("\nStatus set to On vacation.");
                break;
            case 10:
                System.out.println("\nStatus set to Not On vacation.");
                break;
        }
    }

    /**
     * Returns the labels for elements on the threshold editor screen.
     *
     * @param input the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String thresholdEditor(int input) {
        switch (input) {
            case 1:
                return ("Set thresholds");
            case 2:
                return ("Invalid threshold value in one or more fields.");
            default:
                return null;
        }
    }

    /**
     * Returns the labels of the thresholds in an array
     *
     * @param currThresholds the current threshold values of the system
     */
    public String[] getThresholdStrings(int[] currThresholds) {

        String[] thresholdStrings = new String[4];

        thresholdStrings[0] = ("The current weekly trade max is " + currThresholds[0]);
        thresholdStrings[1] = ("The current meeting edit max is " + currThresholds[1]);
        thresholdStrings[2] = ("The current lend min is " + currThresholds[2]);
        thresholdStrings[3] = ("The current incomplete trade max is " + currThresholds[3]);

        return thresholdStrings;
    }

    /**
     * Returns a message telling the user that a new admin was successfully created.
     *
     * @return a string to display
     */
    public String adminCreator() {
        return "New admin created!";
    }

    /**
     * Presents an admin user's ID,
     *
     * @param adminID the ID of the current admin
     */
    public void showAdminID(int adminID) {
        System.out.println("\nYour admin ID: " + adminID);
    }

    /**
     * Presents the AdminDashboard, based off an input
     *
     * @param input the input
     */
    public void adminDashboard(int input) {
        String menuNotInitAdmin = "What would you like to do:" +
                "\n 1 - view items awaiting approval" +
                "\n 2 - view accounts to freeze" +
                "\n 3 - view requests to unfreeze account" +
                "\n 4 - edit threshold values";
        String menuInitAdmin = "\n 5 - add new admin to the system";
        String logoutOption = "\n 0 - log out";

        switch (input) {
            case 1:
                System.out.print(menuNotInitAdmin + logoutOption + choicePrompt);
                break;
            case 2:
                System.out.print(menuNotInitAdmin + menuInitAdmin + logoutOption + choicePrompt);
                break;
        }
    }

    /**
     * Presents the demo dashboard.
     */
    public void demoDashboard() {
        System.out.println(("\nWhat would you like to do:" +
                "\n 1 - see all items available for trade" +
                "\n 2 - create an account" +
                "\n 0 - exit") + choicePrompt);
    }

    public void demoCatalogViewer() {
        System.out.print("\nTrading is unavailable for non-registered users. Enter 1 to sign up, or 0 to return to dashboard: ");
    }

    /**
     * Presents prompts and other info for <TradeRequestSetup></TradeRequestSetup>.
     *
     * @param input the case associated with the string to display
     */
    public void tradeRequestSetup(int input) {
        switch (input) {
            case 1:
                System.out.print("\nWould you like us to suggest items to lend to the other user? (Y/N): ");
                break;
            case 2:
                System.out.println("\nUh oh! We couldn't find any items that the other user might want to borrow from you :(");
                break;
            case 3:
                System.out.println("\nSorry, to maintain the balance of how many more items you've lent than you've borrowed," +
                        "you must choose an item to lend to the other user.");
                break;
        }
    }

    /**
     * Presents the given list of items when making a trade request.
     *
     * @param itemList the list of items to be displayed
     * @param input    the case associated with which list to display
     */
    public void tradeRequestSetup(List<Item> itemList, int input) {
        switch (input) {
            case 1:
                System.out.println("\nThese are all the items you can currently lend out that the other user might want to borrow:");
                presentAllItems(itemList, false);
                break;
            case 2:
                System.out.println("\nHere are all your items currently available for trade:");
                presentAllItems(itemList, false);
                System.out.print("Enter the index of the item you'd like to lend (0 to not lend anything): ");
                break;
        }
    }

    /**
     * Presents a message that lets the user know their trade request was successfully created and sent out.
     *
     * @param username the username of recipient of the trade request
     */
    public void tradeRequestSetup(String username) {
        System.out.println("\nYour request to trade has been sent to " + username + "!" +
                "\nIf this item was not already in your wishlist, it has automatically been added.");
    }

    /**
     * Presents that a trade has been cancelled
     */
    public void cancelled() {
        System.out.println("\nCancelled!");
    }

    /**
     * Presents an error reading user input
     */
    public void exceptionMessage() {
        System.out.println("\nError reading user input!");
        System.exit(-1);
    }

    /**
     * Presents errors based on a type and a process, based off an input
     *
     * @param input   the input
     * @param process the process
     * @param type    the type of error
     */
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

    /**
     * Returns message telling user that an invalid input was made.
     */
    public String invalidInput() {
        return ("Invalid input. Please try again.");
    }

    /**
     * Returns message telling user that their date time suggestion failed
     * due to them exceeding the maximum meeting threshold for chosen week.
     *
     * @return the string to display
     */
    public String failedSuggestion() {
        return ("Sorry, you can't suggest this date and time because you've " +
                "reached the maximum number of meetings allowed in the same week." +
                "\nPlease enter a different date and time (not within the same week): ");
    }

    /**
     * Presents that a list is empty
     */
    public String emptyListMessage() {
        return "Nothing here yet!";
    }

    /**
     * Presents a logout message when a user logs out of their account.
     */
    public void logoutMessage() {
        System.out.println("\nLogging out of your account now. See ya!");
    }

    /**
     * Presents an exit message when user exits the program
     */
    public void exitProgram() {
        System.out.println("\nExiting... Thank you for using our program!");
    }
}
