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
     * @param email    the email being assigned to this AdminUser
     * @param password the password being assigned to this AdminUser
     * @param adminID  the admin ID being assigned to this AdminUser
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
}
