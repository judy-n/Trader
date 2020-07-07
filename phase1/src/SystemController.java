/**
 * The master controller.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-06
 */

public class SystemController {
    public SystemController() {
        String userManagerPath = "src/usermanager.ser";
        String itemManagerPath = "src/itemmanager.ser";

        UserGateway ug = new UserGateway();
        ItemGateway ig = new ItemGateway();
        //add tradeGateway

        UserManager userManager = ug.readFromFile(userManagerPath);
        ItemManager itemManager = ig.readFromFile(itemManagerPath);
        // for now
        TradeManager tradeManager = new TradeManager();

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

        ug.saveToFile(userManagerPath, userManager);
        ig.saveToFile(itemManagerPath, itemManager);
        //add trademanager here too
        System.exit(0);
    }
}
