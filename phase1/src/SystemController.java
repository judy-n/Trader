import java.io.IOException;

/**
 * The master controller.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-04
 */

public class SystemController {
    public UserManager um;
    public ItemManager im;

    public SystemController() {
        String serializedUserManagerInfo = "src/usermanager.ser";
        String serializedItemManagerInfo = "src/itemmanager.ser";
        UserManager um = new UserManager();
        ItemManager im = new ItemManager();
        UserGateway ug = new UserGateway(um);
        ItemGateway ig = new ItemGateway(im);
        try {
            ug.saveToFile(serializedUserManagerInfo);
            ig.saveToFile(serializedItemManagerInfo);
            ug.readFromFile(serializedUserManagerInfo);
            ig.readFromFile(serializedItemManagerInfo);
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Stop.");
        }
        AdminUser mod01 = new AdminUser("Hello_World", "admin01@email.com", "pa55word", 1);
        um.addUser(mod01);

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
            UserDashboard ud = new UserDashboard(sus.getNewUser(), im, um);

        } else {
            LoginSystem ls = new LoginSystem(um);
            boolean isLoggedIn = ls.getIsLoggedIn();
            while (!isLoggedIn) {
                isLoggedIn = ls.getIsLoggedIn();
            }
            UserDashboard ud = new UserDashboard((NormalUser) ls.getUser(), im, um);
        }
    }

}
