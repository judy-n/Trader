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

        boolean isLoggedOut;

        UserManager um = ug.readFromFile(userManagerPath);
        ItemManager im = ig.readFromFile(itemManagerPath);

        if (um.getAllUsers().isEmpty()) {
            AdminUser mod01 = new AdminUser("Hello_World", "admin01@email.com", "pa55word", 1);
            um.addUser(mod01);
        }

        StartMenu sm = new StartMenu();
        int choice = sm.getChoice();
        while (choice == 0) {
            choice = sm.getChoice();
        }

        if (choice == 1) {
            SignUpSystem sus = new SignUpSystem(um);
            boolean isSignedUp = sus.getSignedUp();
            while (!isSignedUp) {
                isSignedUp = sus.getSignedUp();
            }
            ud = new NormalDashboard(sus.getNewUser(), im, um);
            isLoggedOut = ud.getIsLoggedOut();

        } else {
            LoginSystem ls = new LoginSystem(um);
            boolean isLoggedIn = ls.getIsLoggedIn();
            while (!isLoggedIn) {
                isLoggedIn = ls.getIsLoggedIn();
            }
            if (ls.getIsAdmin()) {
                ad = new AdminDashboard((AdminUser) ls.getUser(), im, um);
                isLoggedOut = ad.getIsLoggedOut();
            } else {
                ud = new NormalDashboard((NormalUser) ls.getUser(), im, um);
                isLoggedOut = ud.getIsLoggedOut();
            }

        }

        while (!isLoggedOut) {
            isLoggedOut = (ad.getIsLoggedOut() || ud.getIsLoggedOut());
        }

        ug.saveToFile(userManagerPath, um);
        ig.saveToFile(itemManagerPath, im);
        System.exit(0);

    }

}
