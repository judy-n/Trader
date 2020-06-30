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
 * last modified 2020-06-30
 */

public class User implements Serializable {
    public String username;
    private String email;
    private String password;
    public ArrayList<Item> inventory;
    public ArrayList<Integer> wishlist;
    private HashMap<String [], Integer[]> tradeRequests;

    private Boolean isFrozen;

    private int weeklyTradeLimit = 3;
    private int meetingEditLimit = 3;
    private int lendMinimum = 1; //to borrow in a one-way trade, user must have lent at least lendMinimum item(s) more than they have borrowed

    // private Item[] lastestThreeItems = new Item[3];

    /**
     * Creates a User object with given username, email, and password
     *
     * @param username the given username
     * @param email the given email address
     * @param password the given password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
        ArrayList<Item> tempItems = new ArrayList<>();
        for (Integer itemID : wishlist) {
            tempItems.add(ItemDatabase.getItem(itemID));
        }
        return tempItems;
    }

    /**
     * This method adds an item to the user's wishlist
     * @param i item
     */
    public void addWishlist(Item i) {
        wishlist.add(i.id);
    }

    /**
     * This method removes an item from the user's wishlist
     * @param i item
     */
    public void removeWishlist(Item i) {
        wishlist.remove(i.id);
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
    public void addTradeRequest(String [] usernames, Integer[] itemId){
        tradeRequests.put(usernames, itemId);
    }

    /**
     * This method removes a trade request by the item id
     * @param itemId item id
     */
    public void removeTradeRequest(int itemId){

    }


    public HashMap<String [], Integer[]> getTradeRequest(){
        return tradeRequests;
    }

}
