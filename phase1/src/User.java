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
 * last modified 2020-07-02
 */

public class User implements Serializable {
    private String username;
    private String email;
    private String password;
    private ArrayList<Item> inventory;
    private ArrayList<Item> pendingInventory;
    private ArrayList<Long> wishlist;
    private HashMap<String[], long[]> tradeRequests;

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
     * @param username the username being assigned to this User
     * @param email the email address being assigned to this User
     * @param password the password being assigned to this User
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        inventory = new ArrayList<>();
        pendingInventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        tradeRequests = new HashMap<>();
        isFrozen = false;
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

    /**
     * Moves the given approved Item from this User's pending inventory to their regular inventory.
     *
     * @param itemToAdd the approved Item being added to this User's inventory
     */
    public void addApprovedInventory(Item itemToAdd) {
        inventory.add(itemToAdd);
        pendingInventory.remove(itemToAdd);
    }

    /**
     * Removes a given Item from this User's inventory.
     *
     * @param itemToRemove the Item being removed from this User's inventory
     */
    public void removeInventory(Item itemToRemove) {
        inventory.remove(itemToRemove);
    }

    /**
     * Getter for this User's inventory of Items waiting for approval.
     *
     * @return this User's pending inventory
     */
    public ArrayList<Item> getPendingInventory() {
        return pendingInventory;
    }

    /**
     * Adds the given Item waiting for approval to this User's pending inventory.
     *
     * @param itemToAdd the Item being added to this User's pending inventory
     */
    public void addPendingInventory(Item itemToAdd) {
        pendingInventory.add(itemToAdd);
        itemToAdd.ownerUsername = username;
        itemToAdd.assignID();
    }

    /**
     * Converts and returns this User's wishlist of IDs to a list containing their associated Items.
     *
     * @return this User's wishlist with IDs converted to their corresponding Items
     */
    public ArrayList<Item> getItemWishlist() {
        ArrayList<Item> tempItems = new ArrayList<>();
        for (double itemID : wishlist) {
            tempItems.add(ItemDatabase.getItem(itemID));
        }
        return tempItems;
    }

    /**
     * Adds a given Item to this User's wishlist.
     *
     * @param itemToAdd the Item being added to this User's wishlist
     */
    public void addWishlist(Item itemToAdd) {
        wishlist.add(itemToAdd.id);
    }

    /**
     * Removes a given Item from this User's wishlist.
     *
     * @param itemToRemove the Item being removed from this User's wishlist
     */
    public void removeWishlist(Item itemToRemove) {
        wishlist.remove(itemToRemove.id);
    }

    /**
     * Gets whether or not this User is frozen.
     *
     * @return true if this User's account is frozen, false otherwise
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     * Sets this User's status to frozen.
     *
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * Sets this User's status to NOT frozen.
     *
     */
    public void unfreeze() {
        isFrozen = false;
    }

    /**
     * Adds to the user's trade requests.
     *
     * @param usernames an array containing the usernames of the two traders
     * @param itemIDs an array containing the IDs of the Items involved in the trade request
     */
    public void addTradeRequest(String[] usernames, long[] itemIDs){
        tradeRequests.put(usernames, itemIDs);
    }

    /**
     * Removes a trade request by the given item id.
     *
     * @param itemId the ID of an Item involved in the trade request being removed
     */
    public void removeTradeRequest(int itemId){

    }

    /**
     * Getter for this User's trade requests.
     *
     * @return a HashMap containing all of this User's trade requests
     */
    public HashMap<String [], long[]> getTradeRequest(){
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
     * @param newMax the new weekly trade limit
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
     * @param newMax the new limit on how many times they can edit a meeting
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
     * @param newMin the new minimum lending over borrowing limit
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
     * @param newMax the new limit on incomplete trades
     */
    public void setIncompleteMax(int newMax) {
        incompleteMax = newMax;
    }

    /**
     * Setter for this user's username
     * @param username the new username
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Getter for this user's inventory
     * @return This user's inventory
     */
    public ArrayList<Item> getInventory() {return inventory;}

    /**
     * Setter for this user's inventory
     * @param inventory new inventory
     */
    public void setInventory(ArrayList<Item> inventory) {this.inventory = inventory;}


    /**
     * Getter for this user's wishlist
     * @return this user's Wishlist
     */
    public ArrayList<Long> getWishlist() {return wishlist;}

    /**
     * Setter for this user's wishlist
     * @param wishlist new wishlist
     */
    public void setWishlist(ArrayList<Long> wishlist) {this.wishlist = wishlist;}
}




