package AdminUserFunctions;

import SystemFunctions.Dashboard;
import SystemFunctions.SignUpSystem;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;

import java.util.ArrayList;

/**
 * Displays a dashboard once an administrative user logs in.
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

    /**
     * Creates an <AdminDashboard></AdminDashboard> with the given admin username,
     * item/user managers, and notification system.
     *  
     */
    public AdminDashboard(String username, ItemManager itemManager,
                          UserManager userManager, NotificationSystem notifSystem) {

        this.currUsername = username;
        this.userManager = userManager;
        accountFreezer = new AccountFreezer(username, userManager);
        accountUnfreezer = new AccountUnfreezer(username, userManager);
        catalogEditor = new CatalogEditor(username, itemManager, userManager);
//
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
//        switch (input) {
//            case 4:
//                new ThresholdEditor(currUsername, itemManager, userManager, notifSystem);
//                break;
        //}
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
            new AdminCreator(currUsername, userManager).createNewAdmin(inputtedUsername, inputtedEmail, inputtedPassword);
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



    @Override
    public String getUsername() {
        return currUsername;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
