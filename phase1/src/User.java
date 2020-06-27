import java.util.ArrayList;

/**
 * User.java
 * Represents a User
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-26
 */

public class User {
    public String username;
    private String email;
    private String password;
    public ArrayList<Item> inventory;
    public ArrayList<Item> wishlist;
    public Boolean isFrozen;
    public int tradeThreshold = 3;
   // public ArrayList<Trade> lastestThree;

    /**
     * User
     * Creates a User object with username, email, and password
     * @param u username
     * @param e email
     * @param p password
     */
    public User(String u, String e, String p){
        username = u;
        email = e;
        password = p;
        inventory = new ArrayList<>();
        wishlist = new ArrayList<>();
        isFrozen = false;
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }
    public void addInventory(Item i){
        inventory.add(i);
    }
    public void removeInventory(Item i){
        inventory.remove(i);
    }
    public ArrayList<Item> getWishlist(){
        return wishlist;
    }
    public void addWishlist(Item i){
        wishlist.add(i);
    }
    public void removeWishlist(Item i){
        wishlist.remove(i);
    }
    public void setTradeThreshold(int t){
        tradeThreshold = t;
    }
    public void setIsFrozen(){
        isFrozen = !isFrozen;
    }

}
