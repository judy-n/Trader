package Entities;

import java.io.Serializable;

/**
 * Represents an administrative user in our trade program.
 * An <AdminUser></AdminUser> with <adminID></adminID> 1 represents the initial admin.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-11
 */

public class AdminUser extends User implements Serializable {
    private final int ADMIN_ID;

    /**
     * Creates an <AdminUser></AdminUser> with the given username, email, password, and admin ID.
     *
     * @param username the username being assigned to this <AdminUser></AdminUser>
     * @param email    the email being assigned to this <AdminUser></AdminUser>
     * @param password the password being assigned to this <AdminUser></AdminUser>
     * @param adminID  the admin ID being assigned to this <AdminUser></AdminUser>
     */
    public AdminUser(String username, String email, String password, int adminID) {
        super(username, email, password);
        ADMIN_ID = adminID;
    }

    /**
     * Getter for this <AdminUser></AdminUser>'s admin ID.
     *
     * @return this <AdminUser></AdminUser>'s admin ID
     */
    public int getAdminID() {
        return ADMIN_ID;
    }
}
