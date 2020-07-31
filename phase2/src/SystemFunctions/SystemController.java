package SystemFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.User;
import Entities.NormalUser;
import Entities.AdminUser;
import NormalUserFunctions.DemoDashboard;
import NormalUserFunctions.NormalDashboard;
import AdminUserFunctions.AdminDashboard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The master controller.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-30
 */
public class SystemController {
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;

    private ReadWriter readWriter;

    private final String USER_MANAGER_PATH = "src/usermanager.ser";
    private final String ITEM_MANAGER_PATH = "src/itemmanager.ser";
    private final String TRADE_MANAGER_PATH = "src/trademanager.ser";

    private SystemPresenter systemPresenter;

    /**
     * Creates a <SystemController></SystemController>.
     * Calls methods in the item, user, and trade gateway classes to read and write the system's item, user, and trade managers.
     * If there are no users in the system, this class automatically creates the initial admin with preset login credentials.
     */
    public SystemController() {

        systemPresenter = new SystemPresenter();

        readWriter = new ReadWriter();
        tryReadManagers();

        tradeManager.cancelAllUnconfirmedTrades();
        List<String> cancelledUsers = tradeManager.getCancelledUsers();

        for (String username : cancelledUsers) {
            userManager.getNormalByUsername(username).increaseNumIncomplete();
        }
        for (String username : cancelledUsers) {
            NormalUser user = userManager.getNormalByUsername(username);
            if (user.getNumIncomplete() > user.getIncompleteMax()) {
                userManager.addUsernamesToFreeze(user.getUsername());
            }
        }
        tradeManager.clearCancelledUsers();

        if (userManager.getAllUsers().isEmpty()) {
            tryReadAdmin();
        }

//        while (choice != 0) {
//            if (choice == 1) {
//                NormalUser newUser = new SignUpSystem(userManager).createNewNormal();
//                new NormalDashboard(newUser, itemManager, userManager, tradeManager);
//
//            } else if (choice == 2) {
//                User currentUser = new LoginSystem(userManager).getUser();
//                if (currentUser instanceof AdminUser) {
//                    new AdminDashboard((AdminUser) currentUser, itemManager, userManager);
//                } else {
//                    new NormalDashboard((NormalUser) currentUser, itemManager, userManager, tradeManager);
//                }
//
//            } else if (choice == 3) {
//                DemoUser currentUser = new DemoUser();
//                new DemoDashboard(currentUser, itemManager, userManager, tradeManager);
//            }
//            tryWriteManagers();
//            sm = new StartMenu();
//            choice = sm.getUserInput();
//        }
//        systemPresenter.exitProgram();
//        System.exit(0);
    }

    public ArrayList<Integer> normalUserSignUp(String username, String email, String password,
                                               String validatePassword, String homeCity){
        SignUpSystem signUpSystem = new SignUpSystem(userManager);
        ArrayList<Integer> invalidInput = signUpSystem.validateInput(username, email, password, validatePassword);
        if(invalidInput.isEmpty()){
            NormalUser newUser = new SignUpSystem(userManager).createNewNormal(username, email, password, homeCity);
            new NormalDashboard(newUser, itemManager, userManager, tradeManager);
        }
        return invalidInput;
    }

    public ArrayList<Integer> userLogin(String usernameOrEmail, String password){
        LoginSystem newLoginSystem = new LoginSystem(userManager);
        ArrayList<Integer> invalidInput = newLoginSystem.validateInput(usernameOrEmail, password);
        if(invalidInput.isEmpty()) {
            User currentUser = newLoginSystem.getUser();
            if (currentUser instanceof AdminUser) {
                new AdminDashboard((AdminUser) currentUser, itemManager, userManager);
            } else {
                new NormalDashboard((NormalUser) currentUser, itemManager, userManager, tradeManager);
            }
        }
        return invalidInput;
    }

    public void demoUser(){
        new DemoDashboard(itemManager, userManager);
    }


    private void tryReadManagers() {
        try {
            userManager = (UserManager) readWriter.readFromFile(USER_MANAGER_PATH, 1);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "UserManager");
        } catch (ClassNotFoundException e) {
            systemPresenter.exceptionMessage(2, "Reading", "UserManager");
        }

        try {
            itemManager = (ItemManager) readWriter.readFromFile(ITEM_MANAGER_PATH, 2);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "ItemManager");
        } catch (ClassNotFoundException e) {
            systemPresenter.exceptionMessage(2, "Reading", "ItemManager");
        }

        try {
            tradeManager = (TradeManager) readWriter.readFromFile(TRADE_MANAGER_PATH, 3);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "TradeManager");
        } catch (ClassNotFoundException e) {
            systemPresenter.exceptionMessage(2, "Reading", "TradeManager");
        }
    }

    public void tryWriteManagers() {
        try {
            readWriter.saveToFile(USER_MANAGER_PATH, userManager);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Writing", "UserManager");
        }

        try {
            readWriter.saveToFile(ITEM_MANAGER_PATH, itemManager);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Writing", "ItemManager");
        }

        try {
            readWriter.saveToFile(TRADE_MANAGER_PATH, tradeManager);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Writing", "TradeManager");
        }
    }

    private void tryReadAdmin() {
        String initAdminPath = "src/init_admin_login.txt";
        try {
            String[] adminCredentials = readWriter.readAdminFromFile(initAdminPath);
            userManager.createAdminUser(adminCredentials[0], adminCredentials[1], adminCredentials[2]);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "initial admin credentials");
            e.printStackTrace();
        }
    }
}
