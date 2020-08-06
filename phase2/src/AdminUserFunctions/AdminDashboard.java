package AdminUserFunctions;

import SystemFunctions.Dashboard;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;

/**
 * Displays a dashboard once an administrative user logs in.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-05
 */

public class AdminDashboard extends Dashboard {
    private String currUsername;
    private UserManager userManager;
    private AccountFreezer accountFreezer;

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
//        SystemPresenter systemPresenter = new SystemPresenter();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
//        try {
//            String temp;
//            temp = bufferedReader.readLine();
//            while (!temp.matches(regex)) {
//                systemPresenter.invalidInput();
//                temp = bufferedReader.readLine();
//            }
//            input = Integer.parseInt(temp);
//        } catch (IOException e) {
//            systemPresenter.exceptionMessage();
//        }
//
//        switch (input) {
//            case 0:
//                systemPresenter.logoutMessage();
//                break;
//            case 1:
//                new CatalogEditor(currUsername, itemManager, userManager, notifSystem);
//                break;
//            case 2:
//                new AccountFreezer(currUsername, itemManager, userManager, notifSystem);
//                break;
//            case 3:
//                new AccountUnfreezer(currUsername, itemManager, userManager, notifSystem);
//                break;
//
//            case 4:
//                new ThresholdEditor(currUsername, itemManager, userManager, notifSystem);
//                break;
//            case 5:
//                new AdminCreator(currUsername, itemManager, userManager, notifSystem);
//                break;
        //}
    }

    public String[] getFreezeList(){
        return accountFreezer.getFreezeList();
    }

    public void freezeAll(){
        accountFreezer.freezeAll();
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
