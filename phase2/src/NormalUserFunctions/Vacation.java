package NormalUserFunctions;


import Entities.NormalUser;
import SystemManagers.ItemManager;
import SystemManagers.NotificationSystem;
import SystemManagers.TradeManager;
import SystemManagers.UserManager;
import SystemFunctions.SystemPresenter;

/**
 * Lets a normal user change vacation status.
 *
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-08-03
 * last modified 2020-08-03
 */
public class Vacation {
    private SystemPresenter systemPresenter;
    private NotificationSystem notifSystem;
    private ItemManager itemManager;
    private TradeManager tradeManager;
    private UserManager userManager;
    private NormalUser currentUser;

    /**
     * Allows change to the normal user's vacation status with given notification
     * system and item/user/trade managers.
     *
     * @param user         the normal user who is currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public Vacation(NormalUser user, ItemManager itemManager, UserManager userManager,
                    TradeManager tradeManager, NotificationSystem notifSystem) {
        currentUser = user;
        this.userManager = userManager;
        this.notifSystem = notifSystem;
        this.itemManager = itemManager;
        this.tradeManager = tradeManager;
        systemPresenter = new SystemPresenter();

        switchVacationStatus();
        close();
    }

    /* Switches the vacation status of the normal user. */
    private void switchVacationStatus() {
        if (userManager.getUsernamesOnVacation().contains(currentUser.getUsername())) {
            userManager.removeUsernamesOnVacation(currentUser.getUsername());
            currentUser.setOnVacation(false);
            systemPresenter.accountsNotOnVacation();
        } else {
            userManager.addUsernamesOnVacation(currentUser.getUsername());
            currentUser.setOnVacation(true);
            systemPresenter.accountsOnVacation();
        }
    }
    /* ends vacation transaction and gets user back to the dashboard*/
    private void close() {
        new NormalDashboard(currentUser.getUsername(), itemManager, userManager, tradeManager, notifSystem);
    }
}
