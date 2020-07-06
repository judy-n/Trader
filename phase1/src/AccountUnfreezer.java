import java.util.ArrayList;
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
    /**
     * Return the frozen Users that requested to be unfrozen.
     * @return NormalUsers to unfreeze.
     */
    public ArrayList<NormalUser> getUnfreezeRequests() { return unfreezeRequests;}

    /**
     * Request to be unfrozen.
     */
    public void requestUnfreeze(){
            unfreezeRequests.add(currentUser);
            SystemPresenter sp = new SystemPresenter();
            sp.requestUnfreeze();
        }



}
