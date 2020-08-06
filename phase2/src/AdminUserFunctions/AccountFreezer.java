package AdminUserFunctions;

import SystemManagers.UserManager;

/**
 * Helps let admins freeze all users on the list of usernames to freeze.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-06
 */
public class AccountFreezer {
    private String currUsername;
    private UserManager userManager;

    /**
     * Creates an <AccountFreezer></AccountFreezer> with the given admin username and user manager.
     *
     * @param username    the username of the admin currently logged in
     * @param userManager the system's user manager
     */
    public AccountFreezer(String username, UserManager userManager) {
        this.currUsername = username;
        this.userManager = userManager;

        // Header: systemPresenter.accountFreezer(1) for "Here are the users that need to be frozen:"
        // 1. Display list of usernames to freeze: getFreezeList()
        // 2. Check if list is empty
        //      > if empty, display "nothing here yet": systemPresenter.emptyListMessage()
        //      > if NOT empty,
        //          3. Ask if they want to freeze all (y/n): systemPresenter.accountFreezer(2)
        //              - maybe just a freeze all button?

        //              > if they choose to freeze all: freezeAll()
        //                  4. Display "all frozen!": systemPresenter.accountFreezer()
    }

    /**
     * Converts the list of usernames to freeze into an array and returns it.
     *
     * @return an array containing all the usernames to freeze
     */
    public String[] getFreezeList() {
        return userManager.getUsernamesToFreeze().toArray(new String[0]);
    }

    /**
     * Freezes all the users in the list of usernames to freeze and notifies each user.
     */
    public void freezeAll() {
        for (String usernameToFreeze : userManager.getUsernamesToFreeze()) {
            userManager.freezeNormalUser(usernameToFreeze);

            /* Notify normal user of account being frozen */
            userManager.notifyUser(usernameToFreeze).basicUpdate
                    ("FROZEN", usernameToFreeze, currUsername);
        }
        userManager.clearUsernamesToFreeze();
    }
}