import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User.java
 * Represents a user.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-29
 */

public class User implements Serializable {
    public String username;
    private String email;
    private String password;
    public ArrayList<Item> inventory;
    public ArrayList<Item> wishlist;
    public Boolean isFrozen;
    public int tradeThreshold = 3;
    HashMap<String [], Integer> tradeRequests;


    // public ArrayList<Trade> lastestThree;

    /**
     * User
     * Creates a User object with username, email, and password
     *
     * @param u username
     * @param e email
     * @param p password
     */
    public User(String u, String e, String p) {
        username = u;
        email = e;
        password = p;
        inventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradeRequests = new HashMap<>();
        isFrozen = false;
    }

    /**
     * This method returns a user by username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns a certain user's inventory
     * @return inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * This method adds an item to the user's inventory
     * @param i item
     */
    public void addInventory(Item i) {
        inventory.add(i);
        i.owner = username;
    }

    /**
     * This method removes an item from the user's inventory
     * @param i item
     */
    public void removeInventory(Item i) {
        inventory.remove(i);
    }

    /**
     * This method returns the user's wishlist
     * @return wishlist
     */
    public ArrayList<Item> getWishlist() {
        return wishlist;
    }

    /**
     * This method adds an item to the user's wishlist
     * @param i item
     */
    public void addWishlist(Item i) {
        wishlist.add(i);
    }

    /**
     * This method removes an item from the user's wishlist
     * @param i item
     */
    public void removeWishlist(Item i) {
        wishlist.remove(i);
    }

    /**
     * This method sets the user's trade threshold
     * @param t trade threshold
     */
    public void setTradeThreshold(int t) {
        tradeThreshold = t;
    }

    /**
     * This method sets the user's status to frozen
     */
    public void setIsFrozen() {
        isFrozen = true;
    }

    /**
     * This method returns the user's email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method returns the user's password
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * This method adds to the user's traderequests
     */
    public void addTradeRequest(String [] usernames, int itemId){
        tradeRequests.put(usernames, itemId);
    }

    /**
     * This method removes a trade request by the item id
     * @param itemId item id
     */
    public void removeTradeRequest(int itemId){

    }

}
