/**
 * The master controller.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-06
 */

public class SystemController {
    private AdminDashboard ad;
    private NormalDashboard ud;

    public SystemController() {
        String userManagerPath = "src/usermanager.ser";
        String itemManagerPath = "src/itemmanager.ser";

        UserGateway ug = new UserGateway();
        ItemGateway ig = new ItemGateway();

        UserManager um = ug.readFromFile(userManagerPath);
        ItemManager im = ig.readFromFile(itemManagerPath);

        if (um.getAllUsers().isEmpty()) {
            AdminUser mod01 = new AdminUser("Hello_World", "admin01@email.com", "pa55word", 1);
            um.addUser(mod01);
        }

        int choice = new StartMenu().getUserInput();

        if (choice == 1) {
            NormalUser newUser = new SignUpSystem(um).getNewUser();
            new NormalDashboard(newUser, im, um);

        } else if (choice == 2) {
            User currentUser = new LoginSystem(um).getUser();
            if (currentUser instanceof AdminUser) {
                new AdminDashboard((AdminUser) currentUser, im, um);
            } else {
                new NormalDashboard((NormalUser) currentUser, im, um);
            }
        }

        ug.saveToFile(userManagerPath, um);
        ig.saveToFile(itemManagerPath, im);
        System.exit(0);
    }

}
