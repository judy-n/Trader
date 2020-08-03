package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import SystemFunctions.SystemPresenter;

/**
 * Lets a frozen normal user send a request to be unfrozen to admins.
 *
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-08-03
 */
public class UnfreezeRequester {
    private String currUsername;
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
     * @param currUsername  the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public UnfreezeRequester(String currUsername, ItemManager itemManager, UserManager userManager,
                             TradeManager tradeManager, NotificationSystem notifSystem) {
        this.currUsername = currUsername;
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
        if (userManager.getUnfreezeRequests().contains(currUsername)) {
            systemPresenter.requestUnfreeze(1);
        } else {
            userManager.addUnfreezeRequest(currUsername);
            systemPresenter.requestUnfreeze(2);
        }
    }

    private void close() {
        new NormalDashboard(currUsername, itemManager, userManager, tradeManager, notifSystem);
    }
}
