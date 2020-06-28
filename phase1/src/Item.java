/**
 * Item.java
 * Represents an item.
 *
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-28
 */

public class Item {
    public int id;
    public String name;
    public String description;
    public static int numItems;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        id = numItems;
        numItems++;
    }

    public String toString() {
        return name + ": " + description;
    }
}
