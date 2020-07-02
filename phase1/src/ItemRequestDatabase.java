import java.util.ArrayList;

/**
 * ItemRequestDatabase.java
 * Stores all Items waiting for admin approval.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-29
 * last modified 2020-06-29
 */
public class ItemRequestDatabase {
    private static ArrayList<Item> allItemRequests = new ArrayList<>();

    public static ArrayList<Item> getAllItemRequests() {
        return allItemRequests;
    }

    public static void update() {
        allItemRequests.clear();
        for ( User u : UserDatabase.getAllUsers()) {
            allItemRequests.addAll(u.getPendingInventory());
        }
    }

    public static void remove(Item item) {
        allItemRequests.remove(item);
    }
}
