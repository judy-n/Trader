import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User is a class that represents a user of our trade program.
 *
 * @author Ning Zhang
 * @author Liam
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-01
 */

public class User implements Serializable {
    public String username;
    private String email;
    private String password;
    public ArrayList<Item> inventory;
    public ArrayList<Integer> wishlist;
    private HashMap<String [], Integer[]> tradeRequests;

    private Boolean isFrozen;

    private int weeklyTradeMax = 3;
    private int meetingEditMax = 3;
    private int lendMinimum = 1; //to borrow in a one-way trade, user must have lent at least lendMinimum item(s) more than they have borrowed
    private int incompleteMax = 5; //the limit on how many incomplete trades the user can have before their account is at risk of being frozen

    // private Item[] lastestThreeItems = new Item[3];

    /**
     * Class constructor.
     * Creates a User with given username, email, and password.
     * Also initializes default empty inventory, wishlist, and tradeRequests, and account status non-frozen.
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

    /**
     * Adds a given Item to this User's inventory.
     *
     * @param itemToAdd the Item being added to inventory
     */
    public void addInventory(Item itemToAdd) {
        inventory.add(itemToAdd);
        itemToAdd.owner = username;
    }

    /**
     * Removes a given Item from this User's inventory.
     *
     * @param itemToRemove the Item being removed from inventory
     */
    public void removeInventory(Item itemToRemove) {
        inventory.remove(itemToRemove);
    }

    /**
     * Getter for this User's wishlist.
     *
     * @return this User's wishlist with IDs converted to their corresponding Items.
     */
    public ArrayList<Item> getItemWishlist() {
        ArrayList<Item> tempItems = new ArrayList<>();
        for (Integer itemID : wishlist) {
            tempItems.add(ItemDatabase.getItem(itemID));
        }
        return tempItems;
    }

    /**
     * Adds a given Item to this User's wishlist.
     *
     * @param itemToAdd the Item being added to the wishlist
     */
    public void addWishlist(Item itemToAdd) {
        wishlist.add(itemToAdd.id);
    }

    /**
     * Removes a given Item from this User's wishlist.
     *
     * @param itemToRemove the Item being removed from the wishlist
     */
    public void removeWishlist(Item itemToRemove) {
        wishlist.remove(itemToRemove.id);
    }

    /**
     * Gets whether or not this User is frozen.
     *
     * @return this User's account status
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * Sets this User's status to frozen.
     *
     */
    public void setIsFrozen() {
        isFrozen = true;
    }

    /**
     * Adds to the user's trade requests.
     *
     * @param usernames an array containing the usernames of the two traders
     * @param itemIDs an array containing the IDs of the Items involved in the trade request
     */
    public void addTradeRequest(String [] usernames, Integer[] itemIDs){
        tradeRequests.put(usernames, itemIDs);
    }

    /**
     * Removes a trade request by the item id.
     *
     * @param itemId item id
     */
    public void removeTradeRequest(int itemId){

    }

    /**
     * Getter for this User's trade requests.
     *
     * @return a HashMap containing all of this User's trade requests
     */
    public HashMap<String [], Integer[]> getTradeRequest(){
        return tradeRequests;
    }

    /**
     * Getter for this User's weekly trade limit.
     *
     * @return this User's weekly limit for trades
     */
    public int getWeeklyTradeMax() {
        return weeklyTradeMax;
    }

    /**
     * Setter for this User's weekly trade limit.
     *
     * @param newMax the given weekly trade limit
     */
    public void setWeeklyTradeMax(int newMax) {
        weeklyTradeMax = newMax;
    }

    /**
     * Getter for this User's meeting edit limit.
     *
     * @return this User's limit on how many times they can edit a meeting
     */
    public int getMeetingEditMax() {
        return meetingEditMax;
    }

    /**
     * Setter for this User's meeting edit limit.
     *
     * @param newMax the given limit on how many times they can edit a meeting
     */
    public void setMeetingEditMax(int newMax) {
        meetingEditMax = newMax;
    }

    /**
     * Getter for this User's minimum lending over borrowing limit.
     *
     * @return this User's minimum lending over borrowing limit
     */
    public int getLendMinimum() {
        return lendMinimum;
    }

    /**
     * Setter for this User's minimum lending over borrowing limit.
     *
     * @param newMin the given minimum lending over borrowing limit
     */
    public void setLendMinimum(int newMin) {
        lendMinimum = newMin;
    }

    /**
     * Getter for this User's limit on incomplete trades.
     *
     * @return this User's limit on incomplete trades
     */
    public int getIncompleteMax() {
        return incompleteMax;
    }

    /**
     * Setter for this User's limit on incomplete trades.
     *
     * @param newMax the given limit on incomplete trades
     */
    public void setIncompleteMax(int newMax) {
        incompleteMax = newMax;
    }

    /**
     * Getter for this user's username
     * @return this user's username
     */
    public String getUsername() {return username;}
}




