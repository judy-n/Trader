import java.io.IOException;
import java.util.List;

/**
 * The master controller.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-17
 */
public class SystemController {
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;

    private ReadWriter readWriter;

    private String userManagerPath = "src/usermanager.ser";
    private String itemManagerPath = "src/itemmanager.ser";
    private String tradeManagerPath = "src/trademanager.ser";

    private SystemPresenter sp;

    /**
     * Creates a <SystemController></SystemController>.
     * Calls methods in the item, user, and trade gateway classes to read and write the system's item, user, and trade managers.
     * If there are no users in the system, this class automatically creates the initial admin with preset login credentials.
     */
    public SystemController() {

        sp = new SystemPresenter();

        readWriter = new ReadWriter();
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
            userManager.createAdminUser("Hello_World", "admin01@email.com", "pa55word");
        }
        StartMenu sm = new StartMenu();
        int choice = sm.getUserInput();
        while(choice != 3) {
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
            sm = new StartMenu();
            choice = sm.getUserInput();
        }
        sp.exitProgram();
        System.exit(0);
    }

    private void tryRead() {
        try {
            userManager = (UserManager)readWriter.readFromFile(userManagerPath, 1);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Reading", "UserManager");
        } catch (ClassNotFoundException e) {
            sp.exceptionMessage(2, "Reading", "UserManager");
        }

        try {
            itemManager = (ItemManager) readWriter.readFromFile(itemManagerPath, 2);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Reading", "ItemManager");
        } catch (ClassNotFoundException e) {
            sp.exceptionMessage(2, "Reading", "ItemManager");
        }

        try {
            tradeManager = (TradeManager) readWriter.readFromFile(tradeManagerPath, 3);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Reading", "TradeManager");
        } catch (ClassNotFoundException e) {
            sp.exceptionMessage(2, "Reading", "TradeManager");
        }
    }

    private void tryWrite() {
        try {
            readWriter.saveToFile(userManagerPath, userManager);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Writing", "UserManager");
        }

        try {
            readWriter.saveToFile(itemManagerPath, itemManager);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Writing", "ItemManager");
        }

        try {
            readWriter.saveToFile(tradeManagerPath, tradeManager);
        } catch (IOException e) {
            sp.exceptionMessage(1, "Writing", "TradeManager");
        }
    }
}
