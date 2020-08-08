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
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-05
 */

public class AdminDashboard extends Dashboard {
    private String currUsername;
    private UserManager userManager;
    private AccountFreezer accountFreezer;
    private AccountUnfreezer accountUnfreezer;
    private CatalogEditor catalogEditor;
    private ThresholdEditor thresholdEditor;
    private AdminCreator adminCreator;
    private String popUpMessage = "";
    private SystemPresenter systemPresenter;
    /**
     * Creates an <AdminDashboard></AdminDashboard> with the given admin username,
     * item/user managers, and notification system.
     *  
     */
    public AdminDashboard(String username, ItemManager itemManager,
                          UserManager userManager, NotificationSystem notifSystem) {

        this.currUsername = username;
        this.userManager = userManager;

        systemPresenter = new SystemPresenter();
        accountFreezer = new AccountFreezer(username, userManager);
        accountUnfreezer = new AccountUnfreezer(username, userManager);
        catalogEditor = new CatalogEditor(username, itemManager, userManager);
        thresholdEditor = new ThresholdEditor(username, userManager);
        adminCreator = new AdminCreator(username, userManager);
//        String regex = "[0-4]";
//        int adminID = userManager.getAdminID(currUsername);
//
//        systemPresenter.showAdminID(adminID);
//        if (adminID != 1) {
//            systemPresenter.adminDashboard(1);
//        } else {
//            regex = "[0-5]";
//            systemPresenter.adminDashboard(2);
//        }
//
//        NEED TO ADD CASE undo action
    }

    public String[] getFreezeList(){
        return accountFreezer.getFreezeList();
    }

    public void freezeAll(){
        accountFreezer.freezeAll();
    }

    public String[] getUnfreezeRequests(){
        return accountUnfreezer.getUnfreezeRequests();
    }

    public void unfreezeUser(int index){
        accountUnfreezer.acceptUnfreezeRequest(index);
    }

    public void createNewAdmin(String inputtedUsername, String inputtedEmail, String inputtedPassword){
        boolean isValid = new SignUpSystem(userManager).validateInput(inputtedUsername,
                inputtedEmail, inputtedPassword, inputtedPassword).isEmpty();
        if(isValid) {
            adminCreator.createNewAdmin(inputtedUsername, inputtedEmail, inputtedPassword);
            setPopUpMessage(3);
        }else{
            setPopUpMessage(2);
        }
    }

    public String[] getPendingCatalog(){
        return catalogEditor.getPendingItemStrings();
    }

    public void approvePendingCatalog(int index){
        catalogEditor.approveItem(index);
    }

    public void rejectionPendingCatalog(int index){
        catalogEditor.rejectItem(index);
    }

    public String[] getThresholdStrings(){
        return thresholdEditor.getThresholdStrings();
    }

    public void changeThresholds(String[] inputs){
        if(thresholdEditor.thresholdInputValidate(inputs)){
            thresholdEditor.applyThresholdChanges(inputs);
        }else{
            setPopUpMessage(1);
        }
    }

    @Override
    public String getUsername() {
        return currUsername;
    }

    @Override
    public boolean isAdmin() {
        return true;
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
