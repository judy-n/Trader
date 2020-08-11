package SystemFunctions;

import Entities.Trade;
import java.util.List;

/**
 * The presenter used for the entire program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 2.0
 * @since 2020-07-03
 * last modified 2020-08-10
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

    public String loginSystem(int input) { return startMenuPresenter.loginSystem(input);}

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
     * Tells a user that they are borrowing more than their threshold.
     *
     * @param lendMinimum the lending minimum of the current user
     * @return the string to display
     */
    public String lendWarning(int lendMinimum) {
        return normalDashPresenter.lendWarning(lendMinimum);
    }

    /**
     * Presents the number of edits a user has made, and if they are on their final edit
     *
     * @param numEdits    the number of edits the user has made
     * @param isFinalEdit true iff this is the user's final edit
     */
    public String ongoingTrades(int numEdits, boolean isFinalEdit) {
        return normalDashPresenter.ongoingTrades(numEdits, isFinalEdit);
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
        return normalDashPresenter.getOngoingTradeStrings(ongoingTrades, tradeItemIDs, tradeItemNames, username, dateTimeHandler);
    }

    /**
     * Returns the labels of the thresholds in an array
     *
     * @param currThresholds the current threshold values of the system
     */
    public String[] getThresholdStrings(int[] currThresholds) {
        return adminDashPresenter.getThresholdStrings(currThresholds);
    }

    /**
     * Returns the labels for elements when making a trade request.
     *
     * @param input the case associated with which list to display
     */
    public String tradeRequestSetup(int input) {
        return normalDashPresenter.tradeRequestSetup(input);
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
