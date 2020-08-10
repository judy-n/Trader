package SystemFunctions;

import Entities.NormalUser;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import NormalUserFunctions.DemoDashboard;
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
 * last modified 2020-08-06
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
            long itemID = itemManager.createItem("fruit", "it's a strawberry", "test");
            long itemID2 = itemManager.createItem("AHHH", "OMG", "test");
            long itemID3 = itemManager.createItem("PEND", "INg", "test");
            itemManager.approveItem(itemID);
            itemManager.approveItem(itemID2);
            userManager.addToNormalUserPending(itemID3, "test");
            userManager.addToNormalUserInventory(itemID, "test");
            userManager.addToNormalUserWishlist(itemID2, "test");
        }


        int[] defaultThresholds = tryReadThresholds();
        assert defaultThresholds != null;
        userManager.getThresholdSystem().setAllThresholds(defaultThresholds);

        handleIncompleteTrades();
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

    public ArrayList<Integer> normalUserSignUpCheck(String username, String email, String password,
                                                    String validatePassword, String homeCity) {
        return new SignUpSystem(userManager).validateInputNormal(username, email, password, validatePassword, homeCity);
    }

    public void normalUserSignUp(String username, String email, String password, String homeCity, JFrame parent) {
        new SignUpSystem(userManager).createNewNormal(username, email, password, homeCity, notifSystem);
        new DashboardFrame(new NormalDashboard(username, itemManager, userManager, tradeManager, notifSystem), parent);
    }

    public ArrayList<Integer> userLogin(String usernameOrEmail, String password) {
        return new LoginSystem(userManager).validateInput(usernameOrEmail, password);
    }

    public String userLogin(String usernameOrEmail, JFrame parent) {
        String currUsername = userManager.getUserByUsernameOrEmail(usernameOrEmail).getUsername();
        if (userManager.isAdmin(currUsername)) {
            new DashboardFrame(new AdminDashboard(currUsername, itemManager, userManager, notifSystem), parent);
        } else {
            new DashboardFrame(new NormalDashboard(currUsername, itemManager, userManager, tradeManager, notifSystem), parent);
        }
        return currUsername;
    }


    public void demoUser() {
        new DemoDashboard(itemManager, userManager);
    }

    public void clearCurrUserNotifs(String currUsername) {
        notifSystem.clearNotifsForUser(currUsername);
    }

    public boolean currUserIsAdmin(String currUsername) {
        return userManager.isAdmin(currUsername);
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
             * Adds the notification system as an observer to all normal users in the system and the
             * initial admin, since it gets lost during serialization.
             */
            for (NormalUser u : userManager.getAllNormals()) {
                u.addObserver(notifSystem);
            }
            if (!userManager.getAllAdmins().isEmpty()) {
                userManager.getAllAdmins().get(0).addObserver(notifSystem);
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Reading", "NotificationSystem");
        } catch (ClassNotFoundException e) {
            systemPresenter.exceptionMessage(2, "Reading", "NotificationSystem");
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

            // Add observer only to initial admin (all other admins not observed by notif system)
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
