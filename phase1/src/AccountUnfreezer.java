import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Lets a frozen NormalUser request to be unfrozen, and lets an AdminUser accept/deny the requests.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */
public class AccountUnfreezer {
    private SystemPresenter sp = new SystemPresenter();
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;
    private AdminUser adminUser;

    //for non-admin requesting to be unfrozen
    public AccountUnfreezer(NormalUser user, ItemManager im, UserManager um, TradeManager tm){
        userManager = um;
        itemManager = im;
        tradeManager = tm;
        currentUser = user;
    }
    //for admin reviewing unfreeze requests
    public AccountUnfreezer(AdminUser user, ItemManager im, UserManager um) {
        itemManager = im;
        userManager = um;
        adminUser = user;
    }

    /**
     * Sends a request to be unfrozen.
     *
     */
    public void requestUnfreeze(){
        userManager.addUnfreezeRequest(currentUser.getUsername());
        sp.requestUnfreeze();
        closeNormal();

    }
    private void closeNormal(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }

    /**
     * Displays the list of Users that requested to be unfrozen, and the admin can choose which ones to unfreeze
     * (or none).
     *
     */
    public void reviewUnfreezeRequests() {
        String input;
        int indexInput;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<NormalUser> users = userManager.getUnfreezeRequests();
        sp.adminGetUnfreezeRequests(users);
        try {
            input = br.readLine();
            while(!input.equalsIgnoreCase("y")&&!input.equalsIgnoreCase("n")){
                sp.invalidInput();
                input = br.readLine();
            }
            if(input.equalsIgnoreCase("y")) {
                do {
                    sp.adminGetUnfreezeRequests(1);
                    int max = userManager.getNumUnfreezeRequest();
                    indexInput = Integer.parseInt(br.readLine());
                    while (indexInput < 0 || indexInput > max) {
                        sp.invalidInput();
                        indexInput = Integer.parseInt(br.readLine());
                    }
                    NormalUser userToUnfreeze = userManager.getNormalByUsername(userManager.getUnfreezeRequest(indexInput));
                    userToUnfreeze.unfreeze();
                    sp.adminGetUnfreezeRequests(2);
                }while(indexInput != 0);
            }
            sp.adminGetUnfreezeRequests(3);
            closeAdmin();
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }
    private void closeAdmin(){
        new AdminDashboard(adminUser, itemManager, userManager);
    }

}
