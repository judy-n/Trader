import java.io.IOException;
import java.util.List;

/**
 * The master controller.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-12
 */
public class SystemController {
    UserGateway ug;
    ItemGateway ig;
    TradeGateway tg;
    UserManager userManager;
    ItemManager itemManager;
    TradeManager tradeManager;

    String userManagerPath = "src/usermanager.ser";
    String itemManagerPath = "src/itemmanager.ser";
    String tradeManagerPath = "src/trademanager.ser";

    SystemPresenter sp;

    /**
     * Creates a <SystemController></SystemController>.
     * Calls methods in the item, user, and trade gateway classes to read and write the system's item, user, and trade managers.
     * If there are no users in the system, this class automatically creates the initial admin with preset login credentials.
     */
    public SystemController() {

        sp = new SystemPresenter();

        ug = new UserGateway();
        ig = new ItemGateway();
        tg = new TradeGateway();

        tryRead();

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
            AdminUser mod01 = new AdminUser("Hello_World", "admin01@email.com", "pa55word", 1);
            userManager.addUser(mod01);
        }

        int choice = new StartMenu().getUserInput();

        if (choice == 1) {
            NormalUser newUser = new SignUpSystem(userManager).createNewNormal();
            new NormalDashboard(newUser, itemManager, userManager, tradeManager);

        } else if (choice == 2) {
            User currentUser = new LoginSystem(userManager).getUser();
            if (currentUser instanceof AdminUser) {
                new AdminDashboard((AdminUser) currentUser, itemManager, userManager);
            } else {
                new NormalDashboard((NormalUser) currentUser, itemManager, userManager, tradeManager);
            }
        }

        tryWrite();
        System.exit(0);
    }

    private void tryRead() {
        try {
            userManager = ug.readFromFile(userManagerPath);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Reading", "UserManager");
        } catch (ClassNotFoundException e) {
            sp.exceptionMessage(2, "Reading", "UserManager");
        }

        try {
            itemManager = ig.readFromFile(itemManagerPath);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Reading", "ItemManager");
        } catch (ClassNotFoundException e) {
            sp.exceptionMessage(2, "Reading", "ItemManager");
        }

        try {
            tradeManager = tg.readFromFile(tradeManagerPath);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Reading", "TradeManager");
        } catch (ClassNotFoundException e) {
            sp.exceptionMessage(2, "Reading", "TradeManager");
        }
    }

    private void tryWrite() {
        try {
            ug.saveToFile(userManagerPath, userManager);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Writing", "UserManager");
        }

        try {
            ig.saveToFile(itemManagerPath, itemManager);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Writing", "ItemManager");
        }

        try {
            tg.saveToFile(tradeManagerPath, tradeManager);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Writing", "TradeManager");
        }
    }
}
