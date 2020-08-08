package NormalUserFunctions;

import SystemFunctions.Dashboard;
import SystemFunctions.SystemPresenter;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;

/**
 * Displays a dashboard once a normal user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Kushagra Mehta
 * @version 1.0
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
    private CompletedTradesViewer completedTradesViewer;
    private UnfreezeRequester unfreezeRequester;

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
        completedTradesViewer = new CompletedTradesViewer(currUsername, itemManager, tradeManager);
        unfreezeRequester = new UnfreezeRequester(currUsername, userManager);
    }
    public void editUserStatus(){
        new StatusEditor(currUsername, userManager);
    }

    public void sendUnfreezeRequest(){
        if(unfreezeRequester.requestUnfreeze()){
            setPopUpMessage(6);
        }else{
            setPopUpMessage(7);
        }
    }

    public String[] getWishlist(){
       return wishlistEditor.getWishlistStrings();
    }

    public void removeFromWishlist(int index){
        wishlistEditor.removeItem(index);
    }

    public String[] getInventory(){return inventoryEditor.getInventory();}

    public String[] getPendingInventory(){
        return inventoryEditor.getPendingInventory();
    }

    public void removeFromInventory(int index){
        if(inventoryEditor.validateRemoval(index)) {
            inventoryEditor.removeInventory(index);
        }else{
            setPopUpMessage(3);
        }
    }

    public void addToInventory(String nameInput, String descripInput){
        if(inventoryEditor.validateInput(nameInput, descripInput)){
            inventoryEditor.addInventory(nameInput, descripInput);
            setPopUpMessage(1);
        }else{
            setPopUpMessage(2);
        }
    }

    public String[] getRecentThreeTradesStrings(){
        return completedTradesViewer.getRecentThreeTradesStrings();
    }

    public String[] getTopThreeTraderStrings(){
        return completedTradesViewer.getTopThreeTraderStrings();
    }

    public boolean isFrozen(){
        return currentUser.getIsFrozen();
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
