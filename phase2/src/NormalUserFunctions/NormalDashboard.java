package NormalUserFunctions;

import SystemFunctions.Dashboard;
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
 * last modified 2020-08-03
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
        currentUser = userManager.getNormalByUsername(username);
        wishlistEditor = new WishlistEditor(currUsername, itemManager, userManager);
        inventoryEditor = new InventoryEditor(currUsername, itemManager, userManager, tradeManager);
        completedTradesViewer = new CompletedTradesViewer(currUsername, itemManager, tradeManager);
    }
    public void editUserStatus(){
        new StatusEditor(currUsername, userManager);
    }

    public void sendUnfreezeRequest(){
        new UnfreezeRequester(currUsername, userManager);
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

    public boolean validateRemovalInv(int index){
        return inventoryEditor.validateRemoval(index);
    }

    public void removeFromInventory(int index){
        inventoryEditor.removeInventory(index);
    }

    public boolean validateInputInv(String nameInput, String descripInput){
        return inventoryEditor.validateInput(nameInput, descripInput);
    }

    public void addToInventory(String nameInput, String descripInput){
        inventoryEditor.addInventory(nameInput, descripInput);
    }

    public String[] getRecentThreeTradesStrings(){
        return completedTradesViewer.getRecentThreeTradesStrings();
    }

    public String[] getTopThreeTraderStrings(){
        return completedTradesViewer.getTopThreeTraderStrings();
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
}
