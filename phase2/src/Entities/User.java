package Entities;

import java.io.Serializable;
import java.util.Observable;

/**
 * <User></User> is an abstract class that contains the basic information every user of this program should have:
 * a username, an email, a password, and getters for all three.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-08-12
 */
public abstract class User extends Observable implements Serializable {
    private String username;
    private String email;
    private String password;

    /**
     * Creates a <User></User> with the given username, email, and password.
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
     * Getter for this <User></User>'s username.
     *
     * @return this <User></User>'s username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for this <User></User>'s email.
     *
     * @return this <User></User>'s email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for this <User></User>'s password.
     *
     * @return this <User></User>'s password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for this <User></User>'s password
     *
     * @param newPassword the new password
     */
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    /**
     * Updates the NotificationSystem and generates a new notification for this user
     * using the given information in the string array passed in.
     *
     * @param notifArg the information needed to create the right notification
     */
    public void updateUser(String[] notifArg) {
        setChanged();
        notifyObservers(notifArg);
        clearChanged();
    }
}
