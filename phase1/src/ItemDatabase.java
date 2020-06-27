import java.util.ArrayList;

/**
 * ItemDatabase.java
 * Stores all Items from all users' inventories
 * @author Judy Naamani
 * created 2020-06-27
 * last modified 2020-06-27
 */
public class ItemDatabase {
    private static ArrayList<Item> allItems;

    public ItemDatabase(){
        allItems = new ArrayList<Item>();
    }
    public static ArrayList<Item> getAllItems(){
        return allItems;
    }
    public void update() {
        ArrayList<User> users = UserDatabase.getAllUser();
        for (User u: users){
            allItems.addAll(u.inventory);
        }
    }


}
