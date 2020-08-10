package AdminUserFunctions;

import SystemManagers.UserManager;

/**
 * Helps let admins review and accept requests to be unfrozen sent by frozen normal users.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-10
 */
public class AccountUnfreezer {
    private String currUsername;
    private UserManager userManager;

    /**
     * Creates an <AccountUnfreezer></AccountUnfreezer> with the given admin username and user manager.
     *
     * @param username    the username of the admin who's currently logged in
     * @param userManager the system's user manager
     */
    public AccountUnfreezer(String username, UserManager userManager) {
        this.currUsername = username;
        this.userManager = userManager;
    }

    /**
     * Converts the list of usernames who requested to be unfrozen into an array and returns it.
     *
     * @return an array containing all the usernames who requested to be unfrozen
     */
    public String[] getUnfreezeRequests() {
        return userManager.getUnfreezeRequests().toArray(new String[0]);
    }

    /**
     * Accepts the unfreeze request at the given index by removing it from the list of
     * unfreeze requests and notifying the sender.
     *
     * @param index the index of the unfreeze request being accepted
     */
    public void acceptUnfreezeRequest(int index) {
        String unfreezeUsername = userManager.getUnfreezeUsername(index);
        userManager.removeUnfreezeRequest(index);

        /* Notify normal user of account being unfrozen */
        userManager.notifyUser(unfreezeUsername).basicUpdate
                ("UNFROZEN", unfreezeUsername, currUsername);
    }
}
