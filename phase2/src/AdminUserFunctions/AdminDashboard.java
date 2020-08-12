package AdminUserFunctions;

import SystemFunctions.Dashboard;
import SystemFunctions.SignUpSystem;
import SystemFunctions.SystemPresenter;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;

/**
 * Controller for all administrative user's dashboard functions.
 *
 * @author Yingjia Liu
 * @author Ning Zhang
 * @version 2.0
 * @since 2020-07-05
 * last modified 2020-08-07
 */

public class AdminDashboard extends Dashboard {
    private String currUsername;
    private UserManager userManager;
    private NotificationSystem notifSystem;
    private AccountFreezer accountFreezer;
    private AccountUnfreezer accountUnfreezer;
    private CatalogEditor catalogEditor;
    private ThresholdEditor thresholdEditor;
    private AdminCreator adminCreator;
    private ActionReverter actionReverter;
    private String popUpMessage = "";
    private SystemPresenter systemPresenter;

    /**
     * Creates an <AdminDashboard></AdminDashboard> with the given admin username,
     * item/user managers, and notification system.
     *
     * @param username    the username of the normal user who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification system
     */
    public AdminDashboard(String username, ItemManager itemManager,
                          UserManager userManager, NotificationSystem notifSystem) {
        this.currUsername = username;
        this.userManager = userManager;
        this.notifSystem = notifSystem;
        systemPresenter = new SystemPresenter();
        accountFreezer = new AccountFreezer(username, userManager);
        accountUnfreezer = new AccountUnfreezer(username, userManager);
        catalogEditor = new CatalogEditor(username, itemManager, userManager);
        thresholdEditor = new ThresholdEditor(username, userManager);
        adminCreator = new AdminCreator(username, userManager, notifSystem);
        actionReverter = new ActionReverter(username, itemManager, userManager, notifSystem);
    }

    /**
     * Returns all normal users that needs to be frozen in a String array
     *
     * @return all normal users that needs to be frozen
     */
    public String[] getFreezeList() {
        return accountFreezer.getFreezeList();
    }

    /**
     * Freeze all normal users that needs to be frozen
     */
    public void freezeAll() {
        accountFreezer.freezeAll();
    }

    /**
     * Returns all unfreeze request sent by normal users in a String array
     *
     * @return all unfreeze requests
     */
    public String[] getUnfreezeRequests() {
        return accountUnfreezer.getUnfreezeRequests();
    }

    /**
     * Unfreeze a normal user of index [index] in the list of unfreeze requests
     *
     * @param index the index of the normal user
     */
    public void unfreezeUser(int index) {
        accountUnfreezer.acceptUnfreezeRequest(index);
    }

    /**
     * Creates a new admin user if the given username, email ,and password are of the correct
     * format
     *
     * @param inputtedUsername inputted username
     * @param inputtedEmail    inputted email
     * @param inputtedPassword inputted password
     */
    public void createNewAdmin(String inputtedUsername, String inputtedEmail, String inputtedPassword) {
        boolean isValid = new SignUpSystem(userManager).validateInput(inputtedUsername,
                inputtedEmail, inputtedPassword, inputtedPassword).isEmpty();
        if (isValid) {
            adminCreator.createNewAdmin(inputtedUsername, inputtedEmail, inputtedPassword);
            setPopUpMessage(3);
        } else {
            setPopUpMessage(2);
        }
    }

    /**
     * Returns all items in all normal user's pending inventory in a String array
     *
     * @return all items in all normal user's pending inventory
     */
    public String[] getPendingCatalog() {
        return catalogEditor.getPendingItemStrings();
    }

    /**
     * Approves an item of index [index] in the list of items in pending catalog
     *
     * @param index the index of the item
     */
    public void approvePendingCatalog(int index) {
        catalogEditor.approveItem(index);
    }

    /**
     * Rejects an item of index [index] in the list of items in pending catalog
     *
     * @param index the index of the item
     */
    public void rejectionPendingCatalog(int index) {
        catalogEditor.rejectItem(index);
    }

    /**
     * Returns the program's current threshold values in a String array
     *
     * @return the program's current threshold values
     */
    public String[] getThresholdStrings() {
        return thresholdEditor.getThresholdStrings();
    }

    /**
     * Change the program's current threshold values if the inputs are of correct format
     *
     * @param inputs the new threshold value inputs
     */
    public void changeThresholds(String[] inputs) {
        if (thresholdEditor.thresholdInputValidate(inputs)) {
            thresholdEditor.applyThresholdChanges(inputs);
        } else {
            setPopUpMessage(1);
        }
    }

    /**
     * Returns all revertible notifications in the system in a String array
     *
     * @return all revertible notifications
     */
    public String[] getRevertibleNotifs() {
        return actionReverter.getRevertibleNotifs();
    }

    /**
     * Reverts a selected user action
     *
     * @param index the index of the action
     */
    public void revertUserAction(int index) {
        actionReverter.revertAction(index);
    }

    /**
     * Returns all the activity log in the system in a String array
     *
     * @return all the activity log in the system
     */
    public String[] getFullActivityLogStrings() {
        return notifSystem.getFullActivityLogStrings();
    }

    /**
     * Returns the ID of the current admin user
     *
     * @return the ID of the current admin user
     */
    public int getAdminID() {
        return userManager.getAdminID(currUsername);
    }

    @Override
    public String getUsername() {
        return currUsername;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public String setUpDash(int type) {
        return systemPresenter.setUpAdminDash(type);
    }

    @Override
    public void setPopUpMessage(int type) {
        popUpMessage = systemPresenter.getAdminPopUpMessage(type);
    }

    @Override
    public String getPopUpMessage() {
        return popUpMessage;
    }

    @Override
    public void resetPopUpMessage() {
        popUpMessage = "";
    }


}
