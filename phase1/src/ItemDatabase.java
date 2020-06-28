import java.util.ArrayList;

/**
 * ItemDatabase.java
 * Stores all Items from all users' inventories
 * @author Judy Naamani
 * @author Yingjia Liu
 * created 2020-06-27
 * last modified 2020-06-28
 */
public class ItemDatabase {
    private static ArrayList<Item> allItems = new ArrayList<>();

    public static ArrayList<Item> getAllItems(){
        return allItems;
    }

    public static void update() {
        ArrayList<User> users = UserDatabase.getAllUsers();
        for (User u: users){
            allItems.addAll(u.inventory);
        }
    }

}
