import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all Items in the system (approved and non-approved).
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Ning Zhang
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-08
 */
public class ItemManager implements Serializable {
    private List<Item> approvedItems;
    private List<Item> pendingItems;

    public ItemManager() {
        approvedItems = new ArrayList<>();
        pendingItems = new ArrayList<>();
    }

    /**
     * Getter for all approved items in the system.
     *
     * @return a list of all approved items in the system
     */
    public List<Item> getApprovedItems() {
        return approvedItems;
    }

    /**
     * Getter for all approved items except for those belonging to the given user.
     *
     * @param username the username of the user whose approved items are being excluded
     * @return a list of all approved items not owned by the given user
     */

    public List<Item> getApprovedItems(String username){
        List<Item> approved = new ArrayList<>();
        for(Item i: approvedItems){
            if(!i.getOwnerUsername().equals(username)){
                approved.add(i);
            }
        }
        return approved;
    }

    /**
     * Getter for all items waiting for approval in the system.
     *
     * @return a list of all items waiting for approval in the system
     */
    public List<Item> getPendingItems() {
        return pendingItems;
    }

    /**
     * Getter for all items, pending and approved.
     *
     * @return a list of all items in the system
     */
    public List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<>();
        allItems.addAll(approvedItems);
        allItems.addAll(pendingItems);
        return allItems;
    }

    /**
     * Returns the total number of approved items in the system.
     *
     * @return the total number of approved items in the system
     */
    public int getNumApprovedItems() {
        return approvedItems.size();
    }

    /**
     * Returns the number of approved items in the system that don't belong to the given user.
     *
     * @param username the username of the user whose approved items aren't being counted
     * @return the number of approved items that don't belong to the given user
     */
    public int getNumApprovedItems(String username) {
        return getApprovedItems(username).size();
    }


    /**
     * Returns the total number of pending items in the system.
     *
     * @return the total number of pending items in the system
     */
    public int getNumPendingItems() {
        return pendingItems.size();
    }

    /**
     * Takes in an approved item ID and returns the associated Item object.
     *
     * @param itemID the ID of an approved item
     * @return the Item associated with the given ID
     */
    public Item getApprovedItem(long itemID) {
        return idGetItem(approvedItems, itemID);
    }

    /**
     * Returns the approved item found at the given index.
     *
     * @param index the index of an Item in the list of approved items
     * @return the Item at the given index
     */
    public Item getApprovedItem(int index) {
        return approvedItems.get(index - 1);
    }

    /**
     * Takes in a pending item ID and returns the associated Item object.
     *
     * @param itemID the ID of an item waiting for approval
     * @return the Item associated with the given ID
     */
    public Item getPendingItem(long itemID) {
        return idGetItem(pendingItems, itemID);
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

    private Item idGetItem(List<Item> items, long itemid) {
        for (Item i : items) {
            if (i.getID() == itemid) {
                return i;
            }
        }
        return null;
    }

    /**
     * Takes in a list of approved item IDs and returns a parallel list of the associated Item objects.
     *
     * @param itemIDs a list of IDs belonging to approved items
     * @return the corresponding list of associated Item objects
     */
    public List<Item> getApprovedItemsByIDs(List<Long> itemIDs) {
        List<Item> items = new ArrayList<>();
        for (long l : itemIDs) {
            items.add(getApprovedItem(l));
        }
        return items;
    }

    /**
     * Takes in a list of pending item IDs and returns a parallel list of the associated Item objects.
     *
     * @param itemIDs a list of IDs belonging to items waiting for approval
     * @return the corresponding list of associated Item objects
     */
    public List<Item> getPendingItemsByIDs(List<Long> itemIDs) {
        List<Item> items = new ArrayList<>();
        for (long l : itemIDs) {
            items.add(getPendingItem(l));
        }
        return items;
    }

    /**
     * Removes the given approved item from the system.
     *
     * @param itemToRemove the approved Item being removed
     */
    public void removeApprovedItem(Item itemToRemove) {
        approvedItems.remove(itemToRemove);
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