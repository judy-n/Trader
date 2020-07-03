import java.util.ArrayList;

/**
 * ItemDatabase.java
 * Stores all Items from all users' inventories.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Ning Zhang
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-03
 */
public class ItemManager {
    private static ArrayList<Item> approvedItems = new ArrayList<>();

    private static ArrayList<Item> pendingItems = new ArrayList<>();

    /**
     * Getter for the approved items of this item manager
     *
     * @return approvedItems
     */
    public static ArrayList<Item> getApprovedItems() {
        return approvedItems;
    }

    /**
     * Getter for the pending items of this item manager
     *
     * @return pendingItems
     */
    public static ArrayList<Item> getPendingItems() {
        return pendingItems;
    }

    /**
     * This method returns the number of items in the item database
     *
     * @return numItems
     */
    public static int getNumApprovedItems() {
        return approvedItems.size();
    }

    /**
     * Private helper function for finding items in ArrayLists based on item ids
     *
     * @param items  the list to be searched through
     * @param itemid the id of the item to find
     * @return the item that matches the id within the list, or null if none exists
     */
    private static Item idGetItem(ArrayList<Item> items, long itemid) {
        for (Item i : items) {
            if (i.getId() == itemid) {
                return i;
            }
        }
        return null;
    }

    /**
     * This method returns an ApprovedItem by its id
     *
     * @param itemid id number
     * @return Item
     */
    public static Item getApprovedItem(long itemid) {
        return idGetItem(getApprovedItems(), itemid);
    }

    /**
     * This method returns an approvedItem by its index
     *
     * @param index index
     * @return Item
     */
    public static Item getApprovedItem(int index) {
        return getApprovedItems().get(index - 1);
    }

    /**
     * This method returns a pendingItem by its id
     *
     * @param itemid id number
     * @return Item
     */
    public static Item getPendingItem(long itemid) {
        return idGetItem(getPendingItems(), itemid);
    }

    /**
     * This method returns a pendingItem by its index
     *
     * @param index index
     * @return Item
     */
    public static Item getPendingItem(int index) {
        return getPendingItems().get(index - 1);
    }

    /**
     * Adds item to the list of approved items
     * @param item item to be added
     */
    public static void addApprovedItem(Item item) {
        approvedItems.add(item);
    }

    /**
     * Adds item to the list of pending items
     * @param item item to be added
     */
    public static void addPendingItem(Item item) {
        pendingItems.add(item);
    }

    public static void deleteItem(long itemid) {
        try {
            getPendingItems().remove(getPendingItem(itemid));
        } catch (Exception e) {
            System.out.println("Item Is Not Pending");
        }
    }

    /**
     * Removes an item from the pending items and adds it to the approved items, if that item is pending.
     * @param itemid the item to be added
     */
    public void approveItem(long itemid) {
        try {
        Item item = getPendingItem(itemid);
        getPendingItems().remove(item);
        addApprovedItem(item);
        // should catch an exception if the item isn't pending:
        } catch (Exception e)  {
            System.out.println("Item Is Not Pending");
        }
    }
}