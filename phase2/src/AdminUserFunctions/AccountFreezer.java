package AdminUserFunctions;

import SystemManagers.UserManager;

/**
 * Contains the methods that allow an admin to freeze all users on the list of usernames to freeze.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-05
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

        // 1. Display list of usernames to freeze:
        //      systemPresenter.accountFreezer(1) for "Here are the users that need to be frozen:"
        //      getFreezeList()
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
        return (String[]) userManager.getUsernamesToFreeze().toArray();
    }

    /**
     * Freezes all the users in the list of usernames to freeze.
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