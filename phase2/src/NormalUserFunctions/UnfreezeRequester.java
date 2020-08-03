package NormalUserFunctions;

import SystemManagers.NotificationSystem;
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
 * last modified 2020-07-31
 */
public class UnfreezeRequester {
    private NormalUser currentUser;
    private UserManager userManager;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;
    private SystemPresenter systemPresenter;

    /**
     * Creates an <UnfreezeRequester></UnfreezeRequester> with the given normal user,
     * item/user/trade managers, and notification system.
     * Lets a frozen normal user send a request to be unfrozen to admins.
     *
     * @param user         the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public UnfreezeRequester(NormalUser user, ItemManager itemManager, UserManager userManager,
                             TradeManager tradeManager, NotificationSystem notifSystem) {
        currentUser = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;
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
        new NormalDashboard(currentUser.getUsername(), itemManager, userManager, tradeManager, notifSystem);
    }
}
