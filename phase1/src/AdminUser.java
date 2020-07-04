import java.io.Serializable;

/**
 * Represents an administrative user in our trade program.
 * An AdminUser with adminID 1 represents the initial admin.
 *
 * @author Ning Zhang
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-03
 */

public class AdminUser extends User implements Serializable {
    private final int adminID;

    /**
     * Class constructor.
     * Creates an AdminUser with the given username, email, password, and admin ID.
     *
     * @param username the username being assigned to this AdminUser
     * @param email the email being assigned to this AdminUser
     * @param password the password being assigned to this AdminUser
     * @param adminID the admin ID being assigned to this AdminUser
     */
    public AdminUser(String username, String email, String password, int adminID) {
        super(username, email, password);
        this.adminID = adminID;
    }

    /**
     * Getter for this AdminUser's admin ID.
     *
     * @return this AdminUser's admin ID
     */

    public int getAdminID() {
        return adminID;
    }

    /**
     * Changes the given NormalUser's status to frozen.
     *
     * @param user the NormalUser whose account is being frozen
     */
    public void freezeUser(NormalUser user) {
        user.freeze();
    }

    /**
     * Sets the given NormalUser's status to NOT frozen.
     *
     * @param user the NormalUser whose account is being unfrozen
     */
    public void unfreezeUser(NormalUser user) {
        user.unfreeze();
    }

    /**
     * Setter for the given NormalUser's weekly trade limit.
     *
     * @param user the NormalUser whose weekly trade limit is being changed
     * @param newMax the new weekly trade limit
     */
    public void setUserWeeklyTradeMax(NormalUser user, int newMax) {
        user.setWeeklyTradeMax(newMax);
    }

    /**
     * Setter for the given NormalUser's meeting edit limit.
     *
     * @param user the NormalUser whose meeting edit limit is being changed
     * @param newMax the new limit on how many times the given user can edit a meeting
     */
    public void setUserMeetingEditMax(NormalUser user, int newMax) {
        user.setMeetingEditMax(newMax);
    }

    /**
     * Setter for the given NormalUser's minimum lending over borrowing limit.
     *
     * @param user the NormalUser whose minimum lending over borrowing limit is being changed
     * @param newMin the new limit on minimum lending over borrowing
     */
    public void setUserLendMinimum(NormalUser user, int newMin) {
        user.setLendMinimum(newMin);
    }

    /**
     * Setter for the given NormalUser's limit on incomplete trades.
     *
     * @param user the NormalUser whose limit on incomplete trades is being changed
     * @param newMax the new limit on incomplete trades
     */
    public void setUserIncompleteMax(NormalUser user, int newMax) {
        user.setIncompleteMax(newMax);
    }


    /**
     * Adds an approved item to the NormalUser's inventory
     * @param user user
     * @param itemID item id
     */
    public void addApprovedItem(NormalUser user, long itemID){
        user.addInventory(itemID);
    }
}
