import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all <Item></Item>s in the system (approved and non-approved).
 * Even if an item has been removed from a user's inventory, it still exists in <ItemManager></ItemManager>.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Ning Zhang
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-28
 */
public class ItemManager extends Manager implements Serializable {
    private List<Item> approvedItems;
    private List<Item> pendingItems;

    /**
     * Creates and <ItemManager></ItemManager>.
     * Initializes the empty lists of approved items and pending items.
     */
    public ItemManager() {
        approvedItems = new ArrayList<>();
        pendingItems = new ArrayList<>();
    }

    /**
     * Creates a new <Item></Item> with the given name and description,
     * then adds it to the list of pending items.
     *
     * @param name the new item's name
     * @param description the new item's description
     * @param owner the username of the new item's owner
     * @return the newly created <Item></Item>
     */
    public long createItem(String name, String description, String owner) {
        Item newItem = new Item(name, description, owner);
        pendingItems.add(newItem);
        return newItem.getID();
    }

    /**
     * Getter for all approved items in the system, excluding those that have been removed from inventory.
     *
     * @return a list of all approved items in the system, excluding those that have been removed from inventory
     */
    public List<Item> getApprovedItems() {
        List<Item> approved = new ArrayList<>();
        for (Item i : approvedItems) {
            if (i.isInInventory()) {
                approved.add(i);
            }
        }
        return approved;
    }

    /**
     * Getter for all approved items that haven't been removed from inventory,
     * excluding those belonging to the given user.
     *
     * @param username the username of the user whose approved items are being excluded
     * @return a list of all approved items in inventories not owned by the given user
     */
    public List<Item> getApprovedItems(String username) {
        List<Item> approved = new ArrayList<>();
        for (Item i : approvedItems) {
            if (!i.getOwnerUsername().equals(username) && i.isInInventory()) {
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
     * Getter for all items, pending and approved and those removed from inventory.
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
     * Returns the total number of approved items in the system, excluding those removed from inventory.
     *
     * @return the total number of approved items in the system, excluding those removed from inventory
     */
    public int getNumApprovedItems() {
        return getApprovedItems().size();
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
     * Takes in an approved item ID and returns the associated <Item></Item> object.
     *
     * @param itemID the ID of an approved item
     * @return the <Item></Item> associated with the given ID
     */
    public Item getApprovedItem(long itemID) {
        return idGetItem(approvedItems, itemID);
    }

    /**
     * Returns the approved item found at the given index.
     *
     * @param index the index of an item in the list of approved items
     * @return the item at the given index
     */
    public Item getApprovedItem(int index) {
        return approvedItems.get(index - 1);
    }

    /**
     * Takes in a list of items and returns a list that only includes the items that are available for trade.
     *
     * @param items a list of items of which the available ones are being retrieved
     * @return a list of items that only includes the available items from the given list
     */
    public List<Item> getAvailableItems(List<Item> items) {
        List<Item> availableItems = new ArrayList<>();
        for (Item i : items) {
            if (i.getAvailability()) {
                availableItems.add(i);
            }
        }
        return availableItems;
    }

    /**
     * Takes in a pending item ID and returns the associated <Item></Item> object.
     *
     * @param itemID the ID of an item waiting for approval
     * @return the pending <Item></Item> associated with the given ID
     */
    public Item getPendingItem(long itemID) {
        return idGetItem(pendingItems, itemID);
    }

    /**
     * Returns the pending item at the given index.
     *
     * @param index the index of an item in the list of pending items
     * @return the pending item at the given index
     */
    public Item getPendingItem(int index) {
        return pendingItems.get(index - 1);
    }

    /*
     * Helper method that takes in a list of items and an ID,
     * then returns the item associated with the given ID if it exists in the given list.
     */
    private Item idGetItem(List<Item> items, long itemID) {
        for (Item i : items) {
            if (i.getID() == itemID) {
                return i;
            }
        }
        return null;
    }

    /**
     * Takes in a list of approved item IDs and returns a parallel list of the associated <Item></Item> objects.
     *
     * @param itemIDs a list of IDs belonging to approved items
     * @return the corresponding list of associated <Item></Item> objects
     */
    public List<Item> getApprovedItemsByIDs(List<Long> itemIDs) {
        List<Item> items = new ArrayList<>();
        for (long id : itemIDs) {
            items.add(getApprovedItem(id));
        }
        return items;
    }

    /**
     * Takes in a list of pending item IDs and returns a parallel list of the associated <Item></Item> objects.
     *
     * @param itemIDs a list of IDs belonging to items waiting for approval
     * @return the corresponding list of associated <Item></Item> objects
     */
    public List<Item> getPendingItemsByIDs(List<Long> itemIDs) {
        List<Item> items = new ArrayList<>();
        for (long id : itemIDs) {
            items.add(getPendingItem(id));
        }
        return items;
    }

    /**
     * Removes the given item from the list of pending items and adds it to the list of approved items.
     *
     * @param itemToApprove the item being approved
     */
    public void approveItem(Item itemToApprove) {
        pendingItems.remove(itemToApprove);
        approvedItems.add(itemToApprove);
    }

    /**
     * Rejects the given item by removing it from the list of pending items.
     *
     * @param itemToReject the item being rejected
     */
    public void rejectItem(Item itemToReject) {
        pendingItems.remove(itemToReject);
    }
}