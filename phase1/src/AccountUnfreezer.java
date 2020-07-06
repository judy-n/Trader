import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lets a frozen NormalUser request an unfreeze, and an AdminUser can accept/deny the requests.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */
public class AccountUnfreezer {
    private UserManager um;
    private ItemManager im;
    private NormalUser currentUser;
    private static ArrayList<NormalUser> unfreezeRequests;

    public AccountUnfreezer(UserManager um, ItemManager im, NormalUser u){
        this.um = um;
        this.im = im;
        currentUser = u;
        unfreezeRequests = new ArrayList<>();
    }
    public AccountUnfreezer(UserManager um, ItemManager im){
        this.um = um;
        this.im = im;
        unfreezeRequests = new ArrayList<>();
    }

    /**
     * Return the frozen Users that requested to be unfrozen.
     * @return Users to unfreeze.
     */
    public ArrayList<NormalUser> getUnfreezeRequests() {
        return unfreezeRequests;
    }

    /**
     * Request to be unfrozen.
     */
    public void requestUnfreeze(){
            unfreezeRequests.add(currentUser);
            SystemPresenter sp = new SystemPresenter();
            sp.requestUnfreeze();
        }

    /**
     * Displays the list of Users that requested to be unfrozen, and the admin can choose which ones to unfreeze
     * (or none).
     */
    public void reviewUnfreezeRequests() {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        ArrayList<NormalUser> users = getUnfreezeRequests();
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
                    NormalUser u = um.getNormalByUsername(username);
                    u.unfreeze();
                    unfreezeRequests.remove(u);
                }
                sp.adminUnfreezeSuccessful();
            }
            if(input.equalsIgnoreCase("n")) {
                unfreezeRequests.clear();
            }
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }
}
