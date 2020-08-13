package NormalUserFunctions;

import Entities.TemporaryTrade;
import Entities.Trade;
import SystemFunctions.DateTimeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * The presenter used for the NormalUser's dashboard, returns strings to display
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-12
 */
public class NormalDashPresenter {

    /**
     * Returns the text displayed on a pop up window.
     *
     * @param type int indicating the type of message
     * @return the String needed to be displayed
     */
    public String getPopUpMessage(int type) {
        switch (type) {
            case 1:
                return ("<html>Your item has been requested!<br/>Please wait for an admin to review it.<html>");
            case 2:
                return ("<html>Item registration failed!<br/>Make sure the item name is " +
                        "at least 3 characters and item description is at least 2 words.<html>");
            case 3:
                return ("<html>You may not remove this item as it is currently " +
                        "involved in a trade,<br/>or it is involved in one or more trade requests.<html>");
            case 4:
                return ("You have set your status to: on vacation!");
            case 5:
                return ("You have set your status to: not on vacation!");
            case 6:
                return ("<html>Your request has been sent in!<br/>Please allow some time for an admin to review it.<html>");
            case 7:
                return ("<html>You already sent an unfreeze request.<br/>Please wait for an admin to review it.<html>");
            case 9:
                return ("<html>You were the last person to suggest the meeting details! " +
                        "<br/>Please wait for the other user to agree or send a suggestion of their own.<html>");
            case 10:
                return ("One of the items involved in this request is currently being lent to someone else!");
            case 11:
                return ("This item is already in your wishlist!");
            case 12:
                return ("You've reached your maximum number of edits!");
            case 13:
                return ("<html>Sorry, you can't suggest this date and time because you've" +
                        "<br/>reached the maximum number of meetings allowed in the same week." +
                        "<br/>Please enter a different date and time (not within the same week).<html>");
            case 15:
                return ("This meeting has already been agreed upon, so you cannot edit it.");
            case 16:
                return ("<html>Your trade partner has also confirmed the transaction, so this permanent trade is now closed." +
                        "<br/>The item you lent in this permanent trade has automatically been removed from your inventory," +
                        "<br/>and if the other user's item was in your wishlist it has been removed.<html>");
            case 17:
                return ("<html>Your trade partner has also confirmed the transaction, so this temporary trade is now closed." +
                        "<br/>The item you lent is now available for trade again.<html>");
            case 18:
                return ("<html>Your trade partner has also confirmed the transaction, " +
                        "<br/>so your second meeting has been set to exactly 30 days" +
                        "<br/>from the first meeting (same time, same place).<html>");
            case 19:
                return ("You and your trade partner have not yet agreed upon a meeting!");
            case 20:
                return ("Cannot confirm a meeting before it is scheduled to take place.");
            case 21:
                return ("You've already confirmed that the latest transaction took place!");
            case 22:
                return ("You may not cancel a trade after the meeting has already been scheduled.");
            case 23:
                return ("Latest transaction confirmed!");
            case 24:
                return ("You and your trade partner have already agreed upon a meeting.");
            case 25:
                return ("<html>The suggested meeting time has already passed!" +
                        "<br/>You'll have to suggest a new time and place.<html>");
            case 26:
                return ("<html>Sorry, you can't confirm this date and time because you've " +
                        "<br/>reached the maximum number of meetings allowed in the same week.<html>");
            case 27:
                return ("<html>You cannot initiate any trades at the moment due to your account being frozen." +
                        "<br/>However, you may still add items to your wishlist.<html>");
            case 28:
                return ("<html>This item's owner is currently frozen!" +
                        "<br/>However, you may still add this item to your wishlist.<html>");
            case 29:
                return ("<html>Sorry, this item is currently not available for trade." +
                        "<br/>However, you may still add it to your wishlist.<html>");
            case 30:
                return ("Invalid input. Please try again.");
            case 31:
                return ("<html>You've already sent a request to borrow this item or" +
                        "<br/>received a request in which this item is being offered to you in a trade.");
            case 32:
                return ("Your first trade request must be a two-way trade!");
            case 33:
                return ("<html>Sorry! To maintain the balance of how many more items you've lent than you've borrowed," +
                        "<br/>you must choose an item to lend to the other user.");
            case 34:
                return ("Item successfully added to your wishlist!");
            case 35:
                return ("<html>To request a two-way trade, select an item from your inventory (shown on left) " +
                        "to lend and press \"Send Trade Request\"." +
                        "<br/>To make it one-way, don't select anything and press the button.");
            case 36:
                return ("You can't go on vacation right now as you have ongoing trades!");
            case 37:
                return ("<html>You cannot initiate any trades at the moment due to your account being frozen." +
                        "<br/>Any trade requests you've sent won't be visible to their recipients until you are unfrozen.");

            default:
                return ("");
        }
    }

    /**
     * Returns the text displayed on JComponents when a admin user's
     * dashboard is displayed.
     *
     * @param type int indicating type of JComponent
     * @return the String needed to be displayed
     */
    public String setUpDash(int type) {
        switch (type) {
            case 1:
                return ("Inventory Editor");
            case 2:
                return ("Wishlist Editor");
            case 3:
                return ("View Trade Requests");
            case 4:
                return ("View Catalog");
            case 5:
                return ("View Ongoing Trades");
            case 6:
                return ("View Completed Trades");
            case 7:
                return ("Change Vacation Status");
            case 8:
                return ("Send Unfreeze Request");
            case 9:
                return ("View Notifications");
            case 10:
                return ("Remove");
            case 11:
                return ("Add");
            case 12:
                return ("Confirm");
            case 13:
                return ("Name:");
            case 14:
                return ("Description:");
            case 15:
                return ("Accept");
            case 16:
                return ("Reject");
            case 17:
                return ("Suggest Time (yyyy/MM/dd-HH:mm) :");
            case 18:
                return ("Suggest Place:");
            case 19:
                return ("Permanent");
            case 20:
                return ("Temporary");
            case 21:
                return ("Trade");
            case 22:
                return ("Send Trade Request");
            case 23:
                return ("Wishlist");
            case 24:
                return ("Cancel");
            case 25:
                return ("Agree To Meeting");
            case 26:
                return ("Confirm Transaction");
            case 27:
                return ("Suggest Trade Meeting");
            case 28:
                return ("Mark as Read");
            case 29:
                return ("New password: ");
            case 30:
                return ("Validate password: ");
            case 31:
                return ("Change");
            default:
                return null;
        }
    }

    /**
     * Returns Strings needed for display on the optional panel of <DashboardFrame></DashboardFrame>.
     *
     * @param type the type of String needed
     * @return the String
     */
    public String setUpNormalDashTitles(int type) {
        switch (type) {
            case 1:
                return ("Pending Inventory");
            case 2:
                return ("Top Three Trade Partners");
            case 4:
                return ("Initiated Trade Requests");
            case 5:
                return ("Suggested items to lend in this trade request:");
            case 6:
                return ("Suggest");
            case 7:
                return ("Time (yyyy/MM/dd-HH:mm) :");
            case 8:
                return ("Place:");
            default:
                return "";
        }
    }

    /**
     * Returns String showing the number of edits the normal user has made to the chosen
     * ongoing trade, displayed on the optional panel of the <DashFrame></DashFrame>.
     *
     * @param numEditStr the number of edits in a string
     * @return the string for display on the optional panel
     */
    public String setUpNormalDashTitles(String numEditStr) {
        return ("<html>Suggest Meeting Details<br/>" + numEditStr + "<br/> v  v <html>");
    }

    /**
     * Presents trade requests user has initiated.
     *
     * @param itemNames  the names of the items in the initiated trade requests
     * @param recipients the recipients of the initiated trade requests
     */
    public String[] presentInitiatedTradeRequests(List<String[]> itemNames, List<String> recipients) {
        ArrayList<String> initiatedTradeStrings = new ArrayList<>();
        int index = 1;
        for (String[] i : itemNames) {
            if (i[0].equals("")) {
                initiatedTradeStrings.add(index + ". One-way trade for [" + i[1] + "] sent to " + recipients.get(index - 1));
            } else {
                initiatedTradeStrings.add(index + ". Two-way trade lending [" + i[0] +
                        "] for [" + i[1] + "] sent to " + recipients.get(index - 1));
            }
            index++;
        }
        return initiatedTradeStrings.toArray(new String[0]);
    }

    /**
     * Presents trade requests user has received.
     *
     * @param itemNames the names of the items in the received trade requests
     * @param senders   the senders the received trade requests
     */
    public String[] presentReceivedTradeRequests(List<String[]> itemNames, List<String> senders) {
        ArrayList<String> receivedTradeStrings = new ArrayList<>();
        int index = 1;
        for (String[] i : itemNames) {
            if (i[0].equals("")) {
                receivedTradeStrings.add(index + ". One-way trade asking for [" + i[1] + "] sent from " + senders.get(index - 1));
            } else {
                receivedTradeStrings.add(index + ". Two-way trade asking for [" + i[1] +
                        "] in return for [" + i[0] + "] sent from " + senders.get(index - 1));
            }
            index++;
        }
        return receivedTradeStrings.toArray(new String[0]);
    }

    /**
     * Presents the number of edits a user has made, and if they are on their final edit.
     *
     * @param numEdits    the number of edits the user has made
     * @param isFinalEdit true iff this is the user's final edit
     */
    public String ongoingTrades(int numEdits, boolean isFinalEdit) {
        if (isFinalEdit) {
            return ("<html># of edits you've made so far: " + numEdits +
                    "<br/> Warning: This is the last time you can suggest a meeting.<html>");
        } else {
            return ("Number of edits you've made so far: " + numEdits);
        }
    }

    /**
     * Returns a String warning for when user is violating the lend minimum rule.
     *
     * @param lendMinimum the lend minimum of the system
     * @return the String warning
     */
    public String lendWarning(int lendMinimum) {
        return ("You're borrowing too much! You need to lend AT LEAST " + lendMinimum + " more item(s) than you've borrowed.");
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
     * Returns the labels for elements when making a trade request.
     *
     * @param input the case associated with which list to display
     */
    public String tradeRequestSetup(int input) {
        if (input == 1) {
            return ("Uh oh! We couldn't find any items that the other user might want to borrow from you :(");
        } else {
            return null;
        }
    }

    /**
     * Returns the normal user's information in a String array.
     *
     * @param username the normal user's username
     * @param email    the normal user's email
     * @param password the normal user's password
     * @param homeCity the normal user's homeCity
     * @return the normal user's information
     */
    public String[] getUserInfo(String username, String email, String password, String homeCity) {
        return new String[]{"Username: " + username, "Email: " + email,
                "Password: " + password, "Home City: " + homeCity};
    }

    /**
     * Returns a message used to display on the help section of the dashboard.
     *
     * @return a message used to display on the help section of the dashboard
     */
    public String getHelpMessage() {
        return "<html>Info on how to use the toggle button:" +
                "<br/>Once you've selected an entry in the main panel to take action for, the toggle button's label" +
                "<br/>will display the action that will be taken once you click the \"Confirm\" button next to it." +
                "<br/>Where the toggle button is used:" +
                "<br/>- Viewing Catalog: switch between wishlist-ing and trading for the selected catalog item" +
                "<br/>- Viewing Trade Requests: switch between accepting and rejecting a received trade request," +
                "<br/>and switch between making it a permanent or temporary trade when accepting the request.<html>";
    }
}
