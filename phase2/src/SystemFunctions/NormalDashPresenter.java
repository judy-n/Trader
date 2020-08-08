package SystemFunctions;

import java.util.ArrayList;
import java.util.List;

/**
 * The presenter used for the NormalUser's dashboard, returns strings to display
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-07
 */
public class NormalDashPresenter {

    /**
     * Returns the text displayed on JComponents when a admin user's
     * dashboard is displayed
     * @param type int indicating type of JComponent
     * @return the String needed to be displayed
     */
    public String getPopUpMessage(int type){
        switch (type){
            case 1:
                return ("Your item has been requested! Please wait for an admin to review it.");
            case 2:
                return ("Item registration failed! Make sure the item name is " +
                        "at least 3 characters and item description is at least 2 words.");
            case 3:
                return ("You may not remove this item as it is currently " +
                        "involved in a trade, or it is involved in one or more trade requests.");
            case 4:
                return ("You have set your status to: on vacation!");
            case 5:
                return ("You have set your status to: not on vacation!");
            case 6:
                return ("You have sent an unfreeze request! Please wait for an admin to review it.");
            case 7:
                return ("You already sent an unfreeze request!");
            default:
                return null;

        }

    }

    /**
     * Returns the text displayed on a pop up window
     * @param type int indicating the type of message
     * @return the String needed to be displayed
     */
    public String setUpDash(int type){
        switch (type){
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
                return ("Edit Vacation Status");
            case 8:
                return ("Send Unfreeze Request");
            default:
                return null;
        }
    }



    /**
     * Presents trade requests user has initiated
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
     * Presents trade requests user has received
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
//        if (itemNames.isEmpty()) {
//            System.out.println("You haven't received any trade requests yet");
//        } else {
//            System.out.print("\nWould you like to accept/reject any of these requests? Enter the request's index (0 to quit): ");
//        }
        return receivedTradeStrings.toArray(new String[0]);
    }
}
