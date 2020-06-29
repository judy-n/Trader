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

    public static void add(Item newItemRequest) {
        allItemRequests.add(newItemRequest);
    }

    public static void remove(Item item) {
        allItemRequests.remove(item);
    }
}
