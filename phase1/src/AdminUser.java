import java.util.ArrayList;

/**
 * AdminUser.java
 * Represents an administrative user.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */

public class AdminUser extends User {

    private ArrayList<User> needToFreeze;

    /**
     * AdminUser
     * Creates a User object with username, email, and password
     *
     * @param u username
     * @param e email
     * @param p password
     */
    public AdminUser(String u, String e, String p) {
        super(u, e, p);
        needToFreeze = new ArrayList<>();
    }

    /**
     * This method removes a certain item from a certain user's inventory
     * @param u user
     * @param i item
     */
    public void removeUserInventory(User u, Item i) {
        u.removeInventory(i);
    }

    /**
     * This method changes a certain user's status to frozen
     * @param u user
     */
    public void setUserIsFrozen(User u) {
        u.setIsFrozen();
    }

    /**
     * This method adds a user to the list of users that need to be frozen
     * @param u user
     */
    public void addNeedToFreeze(User u) {
        needToFreeze.add(u);
    }

    /**
     * This method sets all users in the list to frozen
     */
    public void setAllToFrozen() {
        for (User u : needToFreeze) {
            setUserIsFrozen(u);
        }
    }

    /**
     * This method sets a certain user's trade threshold
     * @param u user
     * @param i trade threshold
     */
    public void setUserThreshold(User u, int i) {
        u.setTradeThreshold(i);
    }

}
