package SystemFunctions;

import Entities.Trade;
import DemoUserFunctions.DemoDashPresenter;
import NormalUserFunctions.NormalDashPresenter;
import AdminUserFunctions.AdminDashPresenter;

import java.util.List;

/**
 * The presenter used for the entire program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 2.0
 * @since 2020-07-03
 * last modified 2020-08-13
 */
public class SystemPresenter {
    private final StartMenuPresenter startMenuPresenter;
    private final NormalDashPresenter normalDashPresenter;
    private final AdminDashPresenter adminDashPresenter;
    private final ExceptionPresenter exceptionPresenter;
    private final DemoDashPresenter demoDashPresenter;

    /**
     * Creates a new <SystemPresenter></SystemPresenter>, which is a facade class that stores different presenters
     * separated based on which GUI class uses them.
     */
    public SystemPresenter() {
        startMenuPresenter = new StartMenuPresenter();
        normalDashPresenter = new NormalDashPresenter();
        adminDashPresenter = new AdminDashPresenter();
        exceptionPresenter = new ExceptionPresenter();
        demoDashPresenter = new DemoDashPresenter();
    }

    /**
     * Returns Strings used for display on the start menu.
     *
     * @param input the type of String needed
     * @return the String used for display
     */
    public String startMenu(int input) {
        return startMenuPresenter.startMenu(input);
    }

    /**
     * Returns Strings used for display on the sign up page.
     *
     * @param input the type of String needed
     * @return the String used for display
     */
    public String signUpSystem(int input) {
        return startMenuPresenter.signUpSystem(input);
    }

    /**
     * Returns Strings used for display on the log in page.
     *
     * @param input the type of String needed
     * @return the String used for display
     */
    public String loginSystem(int input) {
        return startMenuPresenter.loginSystem(input);
    }

    /**
     * Returns a pop up message for display on a normal user's dashboard.
     *
     * @param type the type of pop up message
     * @return the pop up message for display
     */
    public String getNormalPopUpMessage(int type) {
        return normalDashPresenter.getPopUpMessage(type);
    }

    /**
     * Returns Strings used for JComponents on a normal user's dashboard
     * on user function panel and user input panel.
     *
     * @param type the type of string needed
     * @return the string for display
     */
    public String setUpNormalDash(int type) {
        return normalDashPresenter.setUpDash(type);
    }

    /**
     * Returns Strings used for JComponents on a normal user's dashboard on optional panel.
     *
     * @param strNeeded the string to include in the label
     * @return the string to display
     */
    public String setUpNormalDashTitles(String strNeeded) {
        return normalDashPresenter.setUpNormalDashTitles(strNeeded);
    }

    /**
     * Returns Strings used for JComponents on a normal user's dashboard
     * on optional panel. Special case taking string as argument.
     *
     * @param type the type of String needed
     * @return the Sting needed
     */
    public String setUpNormalDashTitles(int type) {
        return normalDashPresenter.setUpNormalDashTitles(type);
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
    public String[] getNormalUserInfo(String username, String email, String password, String homeCity) {
        return normalDashPresenter.getUserInfo(username, email, password, homeCity);
    }

    /**
     * Returns Strings used for JComponents on an admin user's dashboard.
     *
     * @param type the type of String needed
     * @return the String for display
     */
    public String setUpAdminDash(int type) {
        return adminDashPresenter.setUpDash(type);
    }

    /**
     * Returns Strings used for JComponents on the demo dashboard
     *
     * @param type the type of String needed
     * @return the String for display
     */
    public String setUpDemoDash(int type) {
        return demoDashPresenter.setUpDash(type);
    }

    /**
     * Returns a pop up message for display on an admin user's dashboard.
     *
     * @param type the type of pop up message
     * @return the pop up message for display
     */
    public String getAdminPopUpMessage(int type) {
        return adminDashPresenter.getPopUpMessage(type);
    }

    /**
     * Returns the admin user's info in a String array.
     *
     * @param username the admin user's username
     * @param email    the admin user's email
     * @param id       the admin user's id
     * @return the admin user's info
     */
    public String[] getAdminUserInfo(String username, String email, String password, int id) {
        return adminDashPresenter.getUserInfo(username, email, password, id);
    }

    /**
     * Returns a pop up message for display on the demo dashboard.
     *
     * @return the String
     */
    public String getDemoPopMessage() {
        return demoDashPresenter.getPopUpMessage();
    }

    /**
     * Returns a String array used for display when demo user clicks their user info.
     *
     * @return the String array
     */
    public String[] getDemoUserInfo() {
        return demoDashPresenter.getUserInfo();
    }

    /**
     * Presents trade requests user has initiated.
     *
     * @param itemNames  the names of the items in the initiated trade requests
     * @param recipients the recipients of the initiated trade requests
     */
    public String[] presentInitiatedTradeRequests(List<String[]> itemNames, List<String> recipients) {
        return normalDashPresenter.presentInitiatedTradeRequests(itemNames, recipients);
    }

    /**
     * Presents trade requests user has received.
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
     * Presents the number of edits a user has made, and if they are on their final edit.
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
     * Returns the labels of the thresholds in an array.
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
     * Presents errors based on a type and a process, based off an input.
     *
     * @param input   the input
     * @param process the process
     * @param type    the type of error
     */
    public void exceptionMessage(int input, String process, String type) {
        exceptionPresenter.exceptionMessage(input, process, type);
    }

    /**
     * Returns a message displayed for the help sectiong of the demo dashboard.
     *
     * @return the message
     */
    public String getDemoHelpMessage() {
        return demoDashPresenter.getHelpMessage();
    }

    /**
     * Returns a message displayed for the help section of the normal dashboard.
     *
     * @return the message
     */
    public String getNormalHelpMessage() {
        return normalDashPresenter.getHelpMessage();
    }

    /**
     * Returns a message displayed for the help section of the admin dashboard.
     *
     * @return the message
     */
    public String getAdminHelpMessage() {
        return adminDashPresenter.getHelpMessage();
    }
}
