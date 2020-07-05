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
    private UserManager um;
    private ItemManager im;
    private AdminDashboard ad;
    private NormalDashboard ud;

    public SystemController() {
        String serializedUserManagerInfo = "usermanager.ser";
        String serializedItemManagerInfo = "itemmanager.ser";
        UserManager um = new UserManager();
        ItemManager im = new ItemManager();
        UserGateway ug = new UserGateway(um);
        ItemGateway ig = new ItemGateway(im);
        try {
            ug.readFromFile(serializedUserManagerInfo);
            ig.readFromFile(serializedItemManagerInfo);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
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
            ud = new NormalDashboard(sus.getNewUser(), im, um);

        } else {
            LoginSystem ls = new LoginSystem(um);
            boolean isLoggedIn = ls.getIsLoggedIn();
            while (!isLoggedIn) {
                isLoggedIn = ls.getIsLoggedIn();
            }
          if(ls.getIsAdmin()){
              ad = new AdminDashboard((AdminUser) ls.getUser(), im, um);
          }else {
              ud = new NormalDashboard((NormalUser) ls.getUser(), im, um);
          }

        }
        boolean isLoggedOut = (ad.getIsLoggedOut()||ud.getIsLoggedOut());
        while(!isLoggedOut){
            isLoggedOut = (ad.getIsLoggedOut()||ud.getIsLoggedOut());
        }
        try {
            ug.saveToFile(serializedUserManagerInfo);
            ig.saveToFile(serializedItemManagerInfo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.exit(0);

    }

}
