import java.util.ArrayList;

/**
 * AdminUser.java
 * Represents an administrative user.
 *
 * @author Ning Zhang
 * created 2020-06-26
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

    public void removeUserInventory(User u, Item i) {
        u.removeInventory(i);
    }

    public void setUserIsFrozen(User u) {
        u.setIsFrozen();
    }

    public void addNeedToFreeze(User u) {
        needToFreeze.add(u);
    }

    public void setAllToFrozen() {
        for (User u : needToFreeze) {
            setUserIsFrozen(u);
        }
    }

    public void setUserThreshold(User u, int i) {
        u.setTradeThreshold(i);
    }

}
