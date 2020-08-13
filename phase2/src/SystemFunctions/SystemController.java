package SystemFunctions;

import Entities.User;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import DemoUserFunctions.DemoDashboard;
import NormalUserFunctions.NormalDashboard;
import AdminUserFunctions.AdminDashboard;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The master controller.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-08-11
 */
public class SystemController extends JFrame {
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;
    private SystemPresenter systemPresenter;

    private ReadWriter readWriter;
    private final String USER_MANAGER_PATH = "src/usermanager.ser";
    private final String ITEM_MANAGER_PATH = "src/itemmanager.ser";
    private final String TRADE_MANAGER_PATH = "src/trademanager.ser";
    private final String NOTIF_SYSTEM_PATH = "src/notifsystem.ser";

    /**
     * Creates a <SystemController></SystemController>.
     * Calls methods in the item, user, and trade gateway classes to read and write the system's item, user, and trade managers.
     * If there are no users in the system, this class automatically creates the initial admin with preset login credentials.
     */
    public SystemController() {

        systemPresenter = new SystemPresenter();
        notifSystem = new NotificationSystem();

        readWriter = new ReadWriter();
        tryReadManagers();

        if (userManager.getAllUsers().isEmpty()) {
            tryReadAdmin();
            //for testing
            new SignUpSystem(userManager).createNewNormal("test", "a@b.com", "p", "homeCity", notifSystem);
            new SignUpSystem(userManager).createNewNormal("t","b@c.v","p","homeCity", notifSystem);
            long itemID = itemManager.createItem("fruit", "it's a strawberry", "test");
            long itemID2 = itemManager.createItem("AHHH", "OMG", "test");
            long itemID3 = itemManager.createItem("PEND", "INg", "t");
            itemManager.approveItem(itemID);
            itemManager.approveItem(itemID2);
            itemManager.approveItem(itemID3);
            userManager.addToNormalUserInventory(itemID, "test");
            userManager.addToNormalUserInventory(itemID2, "test");
            userManager.addToNormalUserInventory(itemID3, "t");
            userManager.addToNormalUserWishlist(itemID3, "test");
        }

        int[] fileThresholds = tryReadThresholds();
        assert fileThresholds != null;
        checkThresholdChange(fileThresholds);
        userManager.getThresholdSystem().setAllThresholds(fileThresholds);

        handleIncompleteTrades();
    }

    private void checkThresholdChange(int[] fileThresholds) {

        int[] currThresholds = userManager.getThresholdSystem().getAllThresholds();

        for (int i = 0; i < currThresholds.length; i++) {

            if (fileThresholds[i] != currThresholds[i]) {

                /* Notify all users of threshold change */
                for (String normalUsername : userManager.getAllNormalUsernames()) {
                    userManager.notifyUser(normalUsername).thresholdUpdate
                            ("THRESHOLD CHANGE", normalUsername, "", i, fileThresholds[i]);
                }
                /* Log threshold change via text file */
                userManager.notifyUser(userManager.getAdminUsernameByID(1)).thresholdUpdate
                        ("LOG THRESHOLD CHANGE VIA FILE", "", "", i, fileThresholds[i]);
            }
        }
    }

    private void handleIncompleteTrades() {
        tradeManager.cancelAllUnconfirmedTrades();
        List<String[]> cancelledUserPairs = tradeManager.getCancelledUserPairs();

        for (String[] usernames : cancelledUserPairs) {

            /* Notify both users of incomplete trade (second notif created along with first in NotificationSystem) */
            userManager.notifyUser(usernames[0]).basicUpdate
                    ("INCOMPLETE TRADE", usernames[0], usernames[1]);

            userManager.increaseNormalUserNumIncomplete(usernames[0]);
            userManager.increaseNormalUserNumIncomplete(usernames[1]);

            if (userManager.getNormalUserNumIncomplete(usernames[0]) >
                    userManager.getThresholdSystem().getIncompleteTradeMax()) {
                userManager.addUsernamesToFreeze(usernames[0]);

                /* Notify user of exceeding incomplete trade limit */
                userManager.notifyUser(usernames[0]).basicUpdate
                        ("FREEZE WARNING", usernames[0], "");
            }

            if (userManager.getNormalUserNumIncomplete(usernames[1]) >
                    userManager.getThresholdSystem().getIncompleteTradeMax()) {
                userManager.addUsernamesToFreeze(usernames[1]);

                /* Notify user of exceeding incomplete trade limit */
                userManager.notifyUser(usernames[1]).basicUpdate
                        ("FREEZE WARNING", usernames[1], "");
            }
        }
        tradeManager.clearCancelledUserPairs();
    }

    /**
     * Checks if the inputs are valid when a normal user signs up
     * @param username inputted username
     * @param email inputted email
     * @param password inputted password
     * @param validatePassword inputted validate password
     * @param homeCity inputted home city
     * @return a list of int indicating the types of errors, empty if all inputs are valid
     */
    public ArrayList<Integer> normalUserSignUpCheck(String username, String email, String password,
                                                    String validatePassword, String homeCity) {
        return new SignUpSystem(userManager).validateInputNormal(username, email, password, validatePassword, homeCity);
    }

    /**
     * Creates a new Normal user and a new dashboard
     * @param username inputted username
     * @param email inputted email
     * @param password inputted password
     * @param homeCity inputted home city
     * @param parent the initial program window
     */
    public void normalUserSignUp(String username, String email, String password, String homeCity, JFrame parent) {
        new SignUpSystem(userManager).createNewNormal(username, email, password, homeCity, notifSystem);
        new DashboardFrame(new NormalDashboard(username, itemManager, userManager, tradeManager, notifSystem), parent);
    }

    /**
     * Checks if the input are all valid when a normal user logs in
     * @param usernameOrEmail inputted username or email
     * @param password inputted password
     * @return a list of int indicating the types of error, empty if all valid
     */
    public ArrayList<Integer> userLogin(String usernameOrEmail, String password) {
        return new LoginSystem(userManager).validateInput(usernameOrEmail, password);
    }

    /**
     * Lets a user login
     * @param usernameOrEmail inputted username or email
     * @param parent the initial program window
     */
    public void userLogin(String usernameOrEmail, JFrame parent) {
        String currUsername = userManager.getUserByUsernameOrEmail(usernameOrEmail).getUsername();
        if (userManager.isAdmin(currUsername)) {
            new DashboardFrame(new AdminDashboard(currUsername, itemManager, userManager, notifSystem), parent);
        } else {
            new DashboardFrame(new NormalDashboard(currUsername, itemManager, userManager, tradeManager, notifSystem), parent);
        }
    }

    /**
     * Makes a demo dashboard
     * @param parent the initial program window
     */
    public void demoUser(JFrame parent) {
        new DashboardFrame(new DemoDashboard(itemManager), parent);
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

        try {
            notifSystem = (NotificationSystem) readWriter.readFromFile(NOTIF_SYSTEM_PATH, 4);

            /*
             * Adds the notification system as an observer to all users in the system,
             * since it gets lost during serialization.
             */
            for (User u : userManager.getAllUsers()) {
                u.addObserver(notifSystem);
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "NotificationSystem");
        } catch (ClassNotFoundException e) {
            systemPresenter.exceptionMessage(2, "Reading", "NotificationSystem");
        }
    }

    /**
     * Saves the system's managers to an external file
     */
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

        try {
            readWriter.saveToFile(NOTIF_SYSTEM_PATH, notifSystem);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Writing", "NotificationSystem");
        }
    }

    private void tryReadAdmin() {
        final String INIT_ADMIN_PATH = "src/init_admin_login.txt";
        try {
            String[] adminCredentials = readWriter.readAdminFromFile(INIT_ADMIN_PATH);
            userManager.createAdminUser(adminCredentials[0], adminCredentials[1], adminCredentials[2]);

            // Add observer to initial admin
            userManager.getAdminByUsername(adminCredentials[0]).addObserver(notifSystem);

        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "initial admin credentials");
        }
    }

    private int[] tryReadThresholds() {
        final String THRESHOLDS_PATH = "src/thresholds.txt";
        try {
            return readWriter.readThresholdsFromFile(THRESHOLDS_PATH);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "threshold values");
        }
        return null;
    }
}
