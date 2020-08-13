package NormalUserFunctions;

import SystemManagers.TradeManager;
import SystemManagers.UserManager;

/**
 * Helps let a normal user change their account's vacation status.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-03
 * last modified 2020-08-12
 */
public class StatusEditor {
    private String currUsername;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Creates a <StatusEditor></StatusEditor> with the given normal username and user manager.
     *
     * @param username    the username of the normal user who's currently logged in
     * @param userManager the system's user manager
     */
    public StatusEditor(String username, UserManager userManager, TradeManager tradeManager) {
        currUsername = username;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
    }

    /**
     * Switches the vacation status of the normal user.
     */
    public boolean switchVacationStatus() {
        if (userManager.getNormalUserOnVacation(currUsername)) {
            userManager.removeUsernamesOnVacation(currUsername);

            /* Notify user of vacation status OFF */
            userManager.notifyUser(currUsername).basicUpdate("OFF VACATION", currUsername, "");
        } else {
            if (!tradeManager.getOngoingTrades(currUsername).isEmpty()) {
                return false;
            } else {
                userManager.addUsernamesOnVacation(currUsername);

                /* Notify user of vacation status ON */
                userManager.notifyUser(currUsername).basicUpdate("ON VACATION", currUsername, "");
            }
        }
        return true;
    }
}
