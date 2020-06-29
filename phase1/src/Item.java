/**
 * Item.java
 * Represents an item.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */

public class Item {
    public int id;
    public String name;
    public String description;
    public static int numItems;

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
    }

    /**
     * This method returns a string representation of the item
     * @return item name and description as a string
     */

    public String toString() {
        return name + ": " + description;
    }
}
