/**
 * Item.java
 * Represents an item.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */

public class Item {
    public int id;
    public String name;
    public String description;
    public static int numItems = 1;
    public String owner;
    private boolean isAvailable;

    /**
     * Item
     * Creates an item with a name and a description and id
     * @param name name
     * @param description description
     */

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        id = numItems;
        numItems++;
        isAvailable = true;
    }

    /**
     * This method returns a string representation of the item
     * @return item name and description as a string
     */
    public String toString() {
        if (isAvailable) {
            return id + ". " + name + ": " + description;
        } else {
            return id + ". " + name + ": " + description + ("\n   (currently unavailable)");
        }
    }

    public boolean getAvailability() {
        return isAvailable;
    }

    public void setAvailability(boolean newAvailability) {
        isAvailable = newAvailability;
    }
}
