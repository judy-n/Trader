package NormalUserFunctions;

import SystemFunctions.Dashboard;
import SystemFunctions.SystemPresenter;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;

/**
 * Controller for all Normal user's dashboard functions.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 2.0
 * @since 2020-06-26
 * last modified 2020-08-12
 */
public class NormalDashboard extends Dashboard {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;
    private CatalogViewer catalogViewer;
    private WishlistEditor wishlistEditor;
    private InventoryEditor inventoryEditor;
    private TradeRequestViewer tradeRequestViewer;
    private OngoingTradesViewer ongoingTradesViewer;
    private CompletedTradesViewer completedTradesViewer;
    private UnfreezeRequester unfreezeRequester;
    private StatusEditor statusEditor;
    private NotificationViewer notificationViewer;

    private String popUpMessage = "";
    private SystemPresenter systemPresenter;

    /**
     * Creates a <NormalDashboard></NormalDashboard> with the given normal user,
     * item/user/trade managers, and notification system.
     *
     * @param username     the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public NormalDashboard(String username, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {
        this.currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;
        systemPresenter = new SystemPresenter();
        catalogViewer = new CatalogViewer(currUsername, itemManager, userManager, tradeManager);
        wishlistEditor = new WishlistEditor(currUsername, itemManager, userManager);
        inventoryEditor = new InventoryEditor(currUsername, itemManager, userManager, tradeManager, notifSystem);
        ongoingTradesViewer = new OngoingTradesViewer(currUsername, itemManager, userManager, tradeManager);
        tradeRequestViewer = new TradeRequestViewer(currUsername, itemManager, userManager, tradeManager, notifSystem);
        completedTradesViewer = new CompletedTradesViewer(currUsername, itemManager, tradeManager);
        unfreezeRequester = new UnfreezeRequester(currUsername, userManager);
        statusEditor = new StatusEditor(currUsername, userManager);
        notificationViewer = new NotificationViewer(currUsername, notifSystem);
    }

    /**
     * Switches the normal user's vacation status.
     */
    public void editUserStatus() {
        statusEditor.switchVacationStatus();
    }

    /**
     * Sends an unfreeze request for admin review.
     */
    public void sendUnfreezeRequest() {
        if (unfreezeRequester.requestUnfreeze()) {
            setPopUpMessage(6);
        } else {
            setPopUpMessage(7);
        }
    }

    /**
     * Returns the normal user's wishlist in a String array.
     *
     * @return the normal user's wishlist
     */
    public String[] getWishlist() {
        return wishlistEditor.getWishlistStrings();
    }

    /**
     * Removes item with index [index] from the normal user's wishlist.
     *
     * @param index the index of the item
     */
    public void removeFromWishlist(int index) {
        wishlistEditor.removeItem(index);
    }

    /**
     * Returns the normal user's inventory in a String array.
     *
     * @return the normal user's inventory
     */
    public String[] getInventory() {
        return inventoryEditor.getInventory();
    }

    /**
     * Returns the normal user's pending inventory in a String array.
     *
     * @return the normal user's pending inventory
     */
    public String[] getPendingInventory() {
        return inventoryEditor.getPendingInventory();
    }

    /**
     * Removes item with index [index] from the normal user's inventory.
     *
     * @param index the index of the item
     */
    public void removeFromInventory(int index) {
        if (inventoryEditor.validateRemoval(index)) {
            inventoryEditor.removeInventory(index);
        } else {
            setPopUpMessage(3);
        }
    }

    /**
     * Adds an item to the normal user's pending inventory if its name and
     * description is of valid format.
     *
     * @param nameInput    the name of the item
     * @param descripInput the description of the item
     */
    public void addToInventory(String nameInput, String descripInput) {
        if (inventoryEditor.validateInput(nameInput, descripInput)) {
            inventoryEditor.addInventory(nameInput, descripInput);
            setPopUpMessage(1);
        } else {
            setPopUpMessage(2);
        }
    }

    /**
     * Returns all available Items in the catalog as a String array.
     *
     * @return all available Items in the catalog
     */
    public String[] getCatalog() {
        return catalogViewer.getCatalogStrings();
    }

    /**
     * Adds item from catalog viewer to the normal user's wishlist.
     *
     * @param index the index of the item
     */
    public void addToWishlist(int index) {
        if (!catalogViewer.addToWishlist(index)) {
            setPopUpMessage(11);
        } else {
            setPopUpMessage(34);
        }
    }

    /**
     * Sends a two way trade request to the item owner.
     *
     * @param index index of the item to lend
     */
    public void requestItemInTwoWayTrade(int index) {
        catalogViewer.requestItemInTwoWayTrade(index);
    }

    /**
     * Sends a one way trade request to the item owner.
     */
    public void requestItemInOneWayTrade() {
        if (catalogViewer.requestItemInOneWayTrade() != 0) {
            setPopUpMessage(catalogViewer.requestItemInOneWayTrade());
        }
    }

    /**
     * Returns the system's suggestion of items to lend in a String array.
     *
     * @param index the index of the item the normal user wants to borrow
     * @return suggested items in exchange of the selected item
     */
    public String[] getSuggestedItems(int index) {
        return catalogViewer.getSuggestedItems(index);
    }

    /**
     * Returns the normal user's current inventory.
     *
     * @return the normal user's current inventory
     */
    public String[] currentUserInventoryInCatalog() {
        return catalogViewer.getCurrUserInventory();
    }

    /**
     * Checks if the user is allowed to request the selected item in a trade.
     *
     * @param index the index of the catalog item selected by the user
     * @return true iff the user if allowed to set up a trade request for the selected item
     */
    public boolean canSetUpTradeRequest(int index) {
        return catalogViewer.canTradeRequestItem(index) == 0 && catalogViewer.canTradeRequestItem() == 0;
    }

    /**
     * Sets the index of the item the normal user wants to borrow.
     *
     * @param index the index of the item
     */
    public void setIndexOfItemRequested(int index) {
        if (catalogViewer.canTradeRequestItem(index) != 0) {
            setPopUpMessage(catalogViewer.canTradeRequestItem(index));
        } else if (catalogViewer.canTradeRequestItem() != 0) {
            setPopUpMessage(systemPresenter.lendWarning(catalogViewer.canTradeRequestItem()));
        } else {
            catalogViewer.setIndexOfItemRequested(index);
            setPopUpMessage(35);
        }
    }

    /**
     * Returns trade requests the normal user initiated in a String array.
     *
     * @return trade requests the normal user initiated
     */
    public String[] getInitiatedTrades() {
        return tradeRequestViewer.getInitiatedTrades();
    }

    /**
     * Returns trade requests the normal user has received in a String array.
     *
     * @return trade requests the normal user has received
     */
    public String[] getReceivedTrades() {
        return tradeRequestViewer.getReceivedTrades();
    }

    /**
     * Returns the normal user's three most recent trades in a string array.
     *
     * @return the normal user's three most recent trades
     */
    public String[] getRecentThreeTradesStrings() {
        return completedTradesViewer.getRecentThreeTradesStrings();
    }

    /**
     * Returns the normal user's three most frequent trade partners in a string array.
     *
     * @return the normal user's three most frequent trade partners
     */
    public String[] getTopThreeTraderStrings() {
        return completedTradesViewer.getTopThreeTraderStrings();
    }

    /**
     * Returns true if the normal user is frozen, false otherwise.
     *
     * @return if the normal user is frozen
     */
    public boolean isFrozen() {
        return userManager.getNormalUserIsFrozen(currUsername);
    }

    /**
     * Returns true if the normal user is on vacation, false otherwise.
     *
     * @return if the normal use is on vacation
     */
    public boolean isOnVacation() {
        return userManager.getNormalUserOnVacation(currUsername);
    }

    /**
     * Accepts the selected trade request.
     *
     * @param index    the index of the trade request
     * @param timeDate the initial time and date
     * @param place    the initial place
     * @param isPerm   true if it's a permanent trade false if it's a temporary trade
     */
    public void acceptTradeRequest(int index, String timeDate, String place, boolean isPerm) {
        if (!tradeRequestViewer.canAcceptRequest(index)) {
            setPopUpMessage(10);
        } else {
            if (tradeRequestViewer.validateSuggestion(timeDate, place) != 0) {
                setPopUpMessage(tradeRequestViewer.validateSuggestion(timeDate, place));
            } else {
                tradeRequestViewer.acceptTradeRequest(index, isPerm, timeDate, place);
            }
        }
    }

    /**
     * Rejects the selected trade request.
     *
     * @param index the index of the trade request
     */
    public void rejectTradeRequest(int index) {
        tradeRequestViewer.rejectTradeRequest(index);
    }

    /**
     * Returns normal user's ongoing trades in a String array.
     *
     * @return normal user's ongoing trades
     */
    public String[] getOngoingTrades() {
        return ongoingTradesViewer.getOngoingTrades();
    }

    /**
     * Returns normal user's current number of edits made on a ongoing trade
     * of index [index].
     *
     * @param index the index of the ongoing trade
     * @return number of edits or a warning if it is the last edit
     */
    public String getNumEdits(int index) {
        return ongoingTradesViewer.displayNumOfEdits(index);
    }

    /**
     * Cancels the ongoing trade with index [index] if it can be cancelled.
     *
     * @param index the index of the ongoing trade
     */
    public void cancelTrade(int index) {
        if (!ongoingTradesViewer.cancelTrade(index)) {
            setPopUpMessage(22);
        }
    }

    /**
     * Agrees to an ongoing trade's trade details.
     *
     * @param index the index of the ongoing trade
     */
    public void agreeTrade(int index) {
        setPopUpMessage(ongoingTradesViewer.agreeMeeting(index));
    }

    /**
     * Confirms that an ongoing trade is completed.
     *
     * @param index the index of the ongoing trade
     */
    public void confirmTrade(int index) {
        if (ongoingTradesViewer.canConfirmLatestTransaction(index) != 0) {
            setPopUpMessage(ongoingTradesViewer.canConfirmLatestTransaction(index));
        } else {
            setPopUpMessage(ongoingTradesViewer.confirmLatestTransaction(index));
        }
    }

    /**
     * Checks whether or not the user can edit the meeting for the selected trade.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return true iff the user is allowed to edit the meeting details for the selected trade
     */
    public boolean canEditMeeting(int index) {
        if (ongoingTradesViewer.canEditMeeting(index) != 0) {
            setPopUpMessage(ongoingTradesViewer.canEditMeeting(index));
            return false;
        }
        return true;
    }

    /**
     * Change the selected ongoing trade's trade details.
     *
     * @param index    the index of the ongoing trade
     * @param timeDate the new date time
     * @param place    the new place
     */
    public void editOngoingTrade(int index, String timeDate, String place) {
        if (ongoingTradesViewer.validateSuggestion(timeDate, place) == 0) {
            ongoingTradesViewer.setMeeting(index, timeDate, place);
        } else {
            setPopUpMessage(ongoingTradesViewer.validateSuggestion(timeDate, place));
        }
    }

    /**
     * Returns all notifications the normal user has received in a String array.
     *
     * @return all notifications the normal user has received
     */
    public String[] getNotifStrings() {
        return notificationViewer.getNotifStrings();
    }

    /**
     * Marks the selected notification as read.
     *
     * @param index the index of the notification
     */
    public void markNotifAsRead(int index) {
        notificationViewer.markNotifAsRead(index);
    }

    /**
     * Sets up the labels displayed for different components of the dashboard window.
     *
     * @param type the case corresponding to the label being retrieved
     * @return the string to display
     */
    public String setUpDashTitles(int type) {
        return systemPresenter.setUpNormalDashTitles(type);
    }

    /**
     * Sets up the labels displayed for different components of the dashboard window.
     * Special case that accepts a string as argument.
     *
     * @param strNeeded the string to include in the label
     * @return the string to display
     */
    public String setUpDashTitles(String strNeeded) {
        return systemPresenter.setUpNormalDashTitles(strNeeded);
    }

    /**
     * Set the pop up message (special case)
     *
     * @param popUpMessage the new pop up message
     */
    public void setPopUpMessage(String popUpMessage) {
        this.popUpMessage = popUpMessage;
    }

    /**
     * Validates the new password
     * @param password the new password
     * @param validatePassword the new password again
     */
    @Override
    public void validatePasswordChange(String password, String validatePassword){
        if(password.matches("[\\S]{6,20}")&&validatePassword.equals(password)){
            userManager.changeUserPassword(currUsername, password);
        }else{
            setPopUpMessage(30);
        }
    }

    /**
     * Returns Strings used for JComponents on the <DashFrame></DashFrame>
     * @param type the type of string
     * @return the string needed
     */
    @Override
    public String setUpDash(int type) {
        return systemPresenter.setUpNormalDash(type);
    }

    /**
     * Sets the String needed for pop up display on the <DashFrame></DashFrame>
     * @param type the type of message needed
     */
    @Override
    public void setPopUpMessage(int type) {
        popUpMessage = systemPresenter.getNormalPopUpMessage(type);
    }

    /**
     * Returns the normal user's username
     * @return the normal user's username
     */
    @Override
    public String getUsername() {
        return currUsername;
    }

    /**
     * Returns int indicating the type of dashboard
     * @return the int indicating the type of dashboard
     */
    @Override
    public int getType() {
        return 1;
    }

    /**
     * Returns Strings used used for pop up display on the <DashFrame></DashFrame>
     * @return the need pop message
     */
    @Override
    public String getPopUpMessage() {
        return popUpMessage;
    }

    /**
     * Resets the pop up message
     */
    @Override
    public void resetPopUpMessage() {
        popUpMessage = "";
    }

    /**
     * Returns the normal user's info in a String array
     * @return the normal user's info
     */
    @Override
    public String[] getUserInfo() {
        return systemPresenter.getNormalUserInfo(currUsername,
                userManager.getNormalByUsername(currUsername).getEmail(),
                userManager.getUserPassword(currUsername),
                userManager.getNormalUserHomeCity(currUsername));
    }
}
