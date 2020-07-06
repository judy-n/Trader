import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * AdminCreator.java
 * lets admin create a new admin user
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-05
 */
public class AdminCreator {
    private ItemManager im;
    private UserManager um;
    private AdminUser currentUser;

    public AdminCreator(AdminUser user, ItemManager im, UserManager um){
        currentUser = user;
        this.im = im;
        this.um = um;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        String username;
        String email;
        String password;
        sp.adminCreator(1);
        try{
            username = br.readLine();
            while(username.length()<3){
                sp.invalidInput();
                username = br.readLine();
            }
            sp.adminCreator(2);
            email = br.readLine();
            while(!(email.contains("@") && email.contains(".")) || email.contains(" ")){
                sp.invalidInput();
                email = br.readLine();
            }
            password = br.readLine();
            AdminUser newAdmin = new AdminUser(username, email, password, um.getAdminId());
            um.addUser(newAdmin);
        }catch (IOException e){
            sp.exceptionMessage();
        }
        sp.adminCreator(4);
        new AdminDashboard(currentUser, im, um);
    }
}
