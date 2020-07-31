package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;
import SystemFunctions.SystemPresenter;

/**
 * Lets a frozen normal user send a request to be unfrozen to admins.
 *
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-07-30
 */
public class UnfreezeRequester {

    private NormalUser currentUser;
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private SystemPresenter systemPresenter;

    /**
     * Creates an <UnfreezeRequester></UnfreezeRequester> with the given normal user and item/user/trade managers.
     * Lets a frozen normal user send a request to be unfrozen to admins.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public UnfreezeRequester(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        systemPresenter = new SystemPresenter();

        requestUnfreeze();
        close();
    }

    /* Sends a request to be unfrozen. */
    private void requestUnfreeze() {
        if (userManager.getUnfreezeRequests().contains(currentUser.getUsername())) {
            systemPresenter.requestUnfreeze(1);
        } else {
            userManager.addUnfreezeRequest(currentUser.getUsername());
            systemPresenter.requestUnfreeze(2);
        }
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
