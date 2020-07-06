import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lets a frozen NormalUser request to be unfrozen, and lets an AdminUser accept/deny the requests.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */
public class AccountUnfreezer {
    private SystemPresenter sp = new SystemPresenter();
    private UserManager um;
    private ItemManager im;
    private NormalUser currentUser;
    private AdminUser adminUser;

    //for non-admin requesting to be unfrozen
    public AccountUnfreezer(NormalUser u, ItemManager im, UserManager um){
        this.um = um;
        this.im = im;
        currentUser = u;
    }
    //for admin reviewing unfreeze requests
    public AccountUnfreezer(AdminUser u, ItemManager im, UserManager um) {
        this.im = im;
        this.um = um;
        adminUser = u;
    }

    /**
     * Sends a request to be unfrozen.
     *
     */
    public void requestUnfreeze(){
        um.addUnfreezeRequest(currentUser.getUsername());
        sp.requestUnfreeze();
        new NormalDashboard(currentUser, im, um);
    }

    /**
     * Displays the list of Users that requested to be unfrozen, and the admin can choose which ones to unfreeze
     * (or none).
     *
     */
    public void reviewUnfreezeRequests() {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<NormalUser> users = um.getUnfreezeRequests();
        sp.adminGetUnfreezeRequests(users);
        try {
            input = br.readLine();
            while(!input.equalsIgnoreCase("y")&&!input.equalsIgnoreCase("n")){
                sp.invalidInput();
                input = br.readLine();
            }
            if(input.equalsIgnoreCase("y")) {
                sp.adminGetUnfreezeRequests();
                input = br.readLine();
                if (input.isEmpty()) {
                    sp.invalidInput();
                }
                ArrayList<String> usernamesToUnfreeze = new ArrayList<>();
                if (input.contains(",")){
                    String[] usernames = input.split(",");
                    usernamesToUnfreeze.addAll(Arrays.asList(usernames));
                }
                else{
                    usernamesToUnfreeze.add(input);
                }
                for (String username : usernamesToUnfreeze){
                    um.unfreeze(username);
                }
                sp.adminUnfreezeSuccessful();
            }
            if(input.equalsIgnoreCase("n")) {
                um.rejectAllRequests();
            }

            new AdminDashboard(adminUser, im, um);
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }
}
