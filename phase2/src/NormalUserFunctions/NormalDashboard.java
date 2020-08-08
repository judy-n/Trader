package NormalUserFunctions;

import SystemFunctions.Dashboard;
import SystemFunctions.SystemPresenter;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;

/**
 * Controller for all Normal user's dashboard functions.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 2.0
 * @since 2020-06-26
 * last modified 2020-08-07
 */
public class NormalDashboard extends Dashboard {
    private NormalUser currentUser;
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;
    private WishlistEditor wishlistEditor;
    private InventoryEditor inventoryEditor;
    private TradeRequestViewer tradeRequestViewer;
    private OngoingTradesViewer ongoingTradesViewer;
    private CompletedTradesViewer completedTradesViewer;
    private UnfreezeRequester unfreezeRequester;
    private StatusEditor statusEditor;

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
        currentUser = userManager.getNormalByUsername(username);
        wishlistEditor = new WishlistEditor(currUsername, itemManager, userManager);
        inventoryEditor = new InventoryEditor(currUsername, itemManager, userManager, tradeManager);
        ongoingTradesViewer = new OngoingTradesViewer(currUsername, itemManager, userManager, tradeManager);
        tradeRequestViewer = new TradeRequestViewer(currUsername, itemManager, userManager, tradeManager, notifSystem);
        completedTradesViewer = new CompletedTradesViewer(currUsername, itemManager, tradeManager);
        unfreezeRequester = new UnfreezeRequester(currUsername, userManager);
        statusEditor = new StatusEditor(currUsername, userManager);
    }

    /**
     * Switches the normal user's vacation status
     */
    public void editUserStatus(){
        statusEditor.switchVacationStatus();
    }

    /**
     * Sends an unfreeze Request for admin review
     */
    public void sendUnfreezeRequest(){
        if(unfreezeRequester.requestUnfreeze()){
            setPopUpMessage(6);
        }else{
            setPopUpMessage(7);
        }
    }

    /**
     * Returns the normal user's wishlist in a String array
     * @return the normal user's wishlist
     */
    public String[] getWishlist(){
       return wishlistEditor.getWishlistStrings();
    }

    /**
     * Removes item with index [index] from the normal user's wishlist
     * @param index the index of the item
     */
    public void removeFromWishlist(int index){
        wishlistEditor.removeItem(index);
    }

    /**
     * Returns the normal user's inventory in a String array
     * @return the normal user's inventory
     */
    public String[] getInventory(){return inventoryEditor.getInventory();}

    /**
     * Returns the normal user's pending inventory in a String array
     * @return the normal user's pending inventory
     */
    public String[] getPendingInventory(){
        return inventoryEditor.getPendingInventory();
    }

    /**
     * Removes item with index [index] from the normal user's inventory
     * @param index the index of the item
     */
    public void removeFromInventory(int index){
        if(inventoryEditor.validateRemoval(index)) {
            inventoryEditor.removeInventory(index);
        }else{
            setPopUpMessage(3);
        }
    }

    /**
     * Adds an item to the normal user's pending inventory if its name and
     * description is of valid format
     * @param nameInput the name of the item
     * @param descripInput the description of the item
     */
    public void addToInventory(String nameInput, String descripInput){
        if(inventoryEditor.validateInput(nameInput, descripInput)){
            inventoryEditor.addInventory(nameInput, descripInput);
            setPopUpMessage(1);
        }else{
            setPopUpMessage(2);
        }
    }

    /**
     * Returns trade requests the normal user initiated in a String array
     * @return trade requests the normal user initiated
     */
    public String[] getInitiatedTrades(){
        return tradeRequestViewer.getInitiatedTrades();
    }

    /**
     * Returns trade requests the normal user has received in a String array
     * @return trade requests the normal user has received
     */
    public String[] getReceivedTrades(){
        return tradeRequestViewer.getReceivedTrades();
    }


    /**
     * Returns the normal user's three most recent trades in a string array
     * @return the normal user's three most recent trades
     */
    public String[] getRecentThreeTradesStrings(){
        return completedTradesViewer.getRecentThreeTradesStrings();
    }

    /**
     * Returns the normal user's three most frequent trade partners in a string array
     * @return the normal user's three most frequent trade partners
     */
    public String[] getTopThreeTraderStrings(){
        return completedTradesViewer.getTopThreeTraderStrings();
    }

    /**
     * Returns true if the normal user is frozen false otherwise
     * @return if the normal user is frozen
     */
    public boolean isFrozen(){
        return currentUser.getIsFrozen();
    }


    /**
     * Returns normal user's ongoing trades in a String array
     * @return normal user's ongoing trades
     */
    public String[] getOngoingTrades(){
        return ongoingTradesViewer.getOngoingTrades();
    }


    @Override
    public String setUpDash(int type){
        return systemPresenter.setUpNormalDash(type);
    }

    @Override
    public void setPopUpMessage(int type){
        popUpMessage = systemPresenter.getNormalPopUpMessage(type);
    }

//        switch (input) {
//            case 1:
//                new CatalogViewer(currUsername, itemManager, userManager, tradeManager);
//                break;
//
//            case 4:
//                new TradeRequestViewer(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//            case 5:
//                new OngoingTradesViewer(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//        }

    @Override
    public String getUsername() {
        return currUsername;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public String getPopUpMessage(){
        return popUpMessage;
    }

    @Override
    public void resetPopUpMessage() {
        popUpMessage = "";
    }
}
