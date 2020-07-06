import java.io.IOException;

/**
 * The master controller.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-05
 */

public class SystemController {
    private AdminDashboard ad;
    private NormalDashboard ud;

    public SystemController() {
        String userManagerPath1 = "src/normalusers.ser";
        String userManagerPath2 = "src/adminusers.ser";
        String itemManagerPath = "src/itemmanager.ser";

        UserManager um = new UserManager();
        ItemManager im = new ItemManager();

        UserGateway ug = new UserGateway(um);
        ItemGateway ig = new ItemGateway(im);

        boolean isLoggedOut;
        try {
            ug.readNormalFromFile(userManagerPath1);
            ug.readAdminFromFile(userManagerPath2);
            ig.readFromFile(itemManagerPath);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

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

        try {
            ug.saveNormalToFile(userManagerPath1);
            ug.saveAdminToFile(userManagerPath2);
            ig.saveToFile(itemManagerPath);
            System.out.println("reached save code");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);

    }

}
