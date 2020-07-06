import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * Displays a list of usernames that need to be frozen
 * and lets admin user freeze them
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-05
 */
public class AccountFreezer {
    private ItemManager im;
    private UserManager um;
    private AdminUser currentUser;

    public AccountFreezer(AdminUser user, ItemManager im, UserManager um){
        this.im = im;
        this.um = um;
        currentUser = user;
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> usernames = um.getUsernamesToFreeze();
        ArrayList<NormalUser> users = new ArrayList<>();
        SystemPresenter sp = new SystemPresenter();
        sp.accountFreezer(usernames);
        for(String username: usernames){
            users.add(um.getNormalByUsername(username));
        }
        try{
            input = br.readLine();
            while(!input.equalsIgnoreCase("y")&&!input.equalsIgnoreCase("n")){
                sp.invalidInput();
                input = br.readLine();
            }
            if(input.equalsIgnoreCase("y")){
                for(NormalUser u: users){
                    u.freeze();
                }
                um.clearUsernamesToFreeze();
            }
            sp.accountFreezer();
            new AdminDashboard(currentUser, im, um);
        }catch (IOException e){
            sp.exceptionMessage();
        }
    }

}
