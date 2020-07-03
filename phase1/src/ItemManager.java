import java.util.ArrayList;

/**
 * ItemDatabase.java
 * Stores all Items in the system (approved and non-approved).
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
    private ArrayList<Item> approvedItems = new ArrayList<>();

    private ArrayList<Item> pendingItems = new ArrayList<>();

    /**
     * Getter for the approved items of this item manager
     *
     * @return approvedItems
     */
    public ArrayList<Item> getApprovedItems() {
        return approvedItems;
    }

    /**
     * Getter for the pending items of this item manager
     *
     * @return pendingItems
     */
    public ArrayList<Item> getPendingItems() {
        return pendingItems;
    }

    /**
     * This method returns the number of items in the item database
     *
     * @return numItems
     */
    public int getNumApprovedItems() {
        return approvedItems.size();
    }

    //prof said private methods don't need javadoc
    private Item idGetItem(ArrayList<Item> items, long itemid) {
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
    public Item getApprovedItem(long itemid) {
        return idGetItem(approvedItems, itemid);
    }

    /**
     * This method returns an approvedItem by its index
     *
     * @param index index
     * @return Item
     */
    public Item getApprovedItem(int index) {
        return approvedItems.get(index - 1);
    }

    /**
     * This method returns a pendingItem by its id
     *
     * @param itemid id number
     * @return Item
     */
    public Item getPendingItem(long itemid) {
        return idGetItem(pendingItems, itemid);
    }

    /**
     * This method returns a pendingItem by its index
     *
     * @param index index
     * @return Item
     */
    public Item getPendingItem(int index) {
        return pendingItems.get(index - 1);
    }

    /**
     * Adds item to the list of pending items.
     *
     * @param itemToAdd the item being added
     */
    public void addPendingItem(Item itemToAdd) {
        pendingItems.add(itemToAdd);
    }

    /**
     * Removes an item from the pending items and adds it to the approved items, if that item is pending.
     *
     * @param itemToApprove the item being approved
     */
    public void approveItem(Item itemToApprove) {
        pendingItems.remove(itemToApprove);
        approvedItems.add(itemToApprove);
    }

    /**
     * Rejects the given Item by removing it from pendingItems.
     *
     * @param itemToReject the Item being rejected
     */
    public void rejectItem(Item itemToReject) {
        pendingItems.remove(itemToReject);
    }

}