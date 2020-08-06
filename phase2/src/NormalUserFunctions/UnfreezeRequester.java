package NormalUserFunctions;
import SystemManagers.UserManager;

/**
 * Lets a frozen normal user send a request to be unfrozen to admins.
 *
 * @author Yingjia Liu
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-08-05
 */
public class UnfreezeRequester {
    private String currUsername;
    private UserManager userManager;

    /**
     * Creates an <UnfreezeRequester></UnfreezeRequester> with the given normal user,
     * item/user/trade managers, and notification system.
     * Lets a frozen normal user send a request to be unfrozen to admins.
     *
     * @param currUsername  the username of the normal user who's currently logged in
     * @param userManager  the system's user manager
     */
    public UnfreezeRequester(String currUsername, UserManager userManager) {
        this.currUsername = currUsername;
        this.userManager = userManager;
        requestUnfreeze();
    }

    /* Sends a request to be unfrozen. */
    private void requestUnfreeze() {
        if (!userManager.getUnfreezeRequests().contains(currUsername)) {
            userManager.addUnfreezeRequest(currUsername);
        }
    }
}
