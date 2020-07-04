/**
 * User is an abstract class that contains the basic information every user of this program should have:
 * a username, an email, a password, and getters for all three.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-03
 */

public abstract class User {
    private String username;
    private String email;
    private String password;

    /**
     * Class constructor.
     * A constructor that can be called when creating instances of a subclass of User.
     *
     * @param username the username being assigned to the user
     * @param email    the email address being assigned to the user
     * @param password the password being assigned to the user
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Getter for this User's username.
     *
     * @return this User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for this User's email.
     *
     * @return this User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for this User's password.
     *
     * @return this User's password
     */
    public String getPassword() {
        return password;
    }
}
