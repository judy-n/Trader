import java.util.ArrayList;

/**
 * Represents an administrative user in our trade program.
 * An AdminUser with adminID 1 represents the initial admin.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */

public class AdminUser extends User {

    public static int numAdmins = 1; //doesn't work with serialization
    private final int adminID;
    private ArrayList<User> needToFreeze;

    /**
     * Class constructor.
     * Creates an AdminUser with the given username, email, and password.
     * Also assigns this AdminUser a unique ID.
     *
     * @param username the given username
     * @param email the given email
     * @param password the given password
     */
    public AdminUser(String username, String email, String password) {
        super(username, email, password);
        needToFreeze = new ArrayList<>();
        adminID = numAdmins;
        numAdmins++;
    }

    /**
     * Getter for this AdminUser's admin ID.
     *
     * @return this AdminUser's adminID
     */
    public int getAdminID() {
        return adminID;
    }

    /**
     * Changes the given User's status to frozen.
     *
     * @param user the given User
     */
    public void freezeUser(User user) {
        user.freeze();
    }

    /**
     * Adds the given user to the list of users that need to be frozen.
     *
     * @param user the given User
     */
    public void addNeedToFreeze(User user) {
        needToFreeze.add(user);
    }

    /**
     * Sets all user accounts in the list to frozen.
     *
     */
    public void freezeAll() {
        for (User u : needToFreeze) {
            freezeUser(u);
        }
    }

    /**
     * Sets the given User's status to NOT frozen.
     *
     * @param user the User whose account is being unfrozen
     */
    public void unfreezeUser(User user) {
        user.unfreeze();
    }

    /**
     * Setter for the given User's weekly trade limit.
     *
     * @param user the given User
     * @param newMax the given weekly trade limit
     */
    public void setUserWeeklyTradeMax(User user, int newMax) {
        user.setWeeklyTradeMax(newMax);
    }

    /**
     * Setter for the given User's meeting edit limit.
     *
     * @param user the given User
     * @param newMax the given limit on how many times the user can edit a meeting
     */
    public void setUserMeetingEditMax(User user, int newMax) {
        user.setMeetingEditMax(newMax);
    }

    /**
     * Setter for the given User's minimum lending over borrowing limit.
     *
     * @param user the given User
     * @param newMin the given limit on incomplete trades
     */
    public void setUserLendMinimum(User user, int newMin) {
        user.setLendMinimum(newMin);
    }

    /**
     * Setter for the given User's limit on incomplete trades.
     *
     * @param user the given User
     * @param newMax the given limit on incomplete trades
     */
    public void setUserIncompleteMax(User user, int newMax) {
        user.setIncompleteMax(newMax);
    }
}
