import java.io.Serializable;

/**
 * Represents an item with a name, description, ID, and owner.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */

public class Item implements Serializable {
    public final int id;
    public String name;
    public String description;
    public static int numItems = 1; //maybe replace Item ID assignment by checking against ItemDatabase.allItemIDs (needs a getter)
    public String owner;
    private boolean isAvailable;

    /**
     * Class constructor.
     * Creates an Item with the given name and description.
     * Also assigns a unique ID to the item and makes the item available for trade by default.
     *
     * @param name the given name
     * @param description the given description
     */

    public Item(String name, String description) {
        this.name = name;
        this.description = description;

        //TODO: figure out what to do about item ID (reading in Items doesn't add to numItems, messing up the whole ID order)
        id = numItems;
        numItems++;

        isAvailable = true;
    }

    /**
     * Returns a string representation of this Item.
     *
     * @return this Item's ID, name, description, and availability as a string
     */
    public String toString() {
        if (isAvailable) {
            return id + ". " + name + ": " + description;
        } else {
            return id + ". " + name + ": " + description + ("\n   (currently unavailable)");
        }
    }

    /**
     * Getter for this Item's availability.
     *
     * @return whether or not this Item is currently available for trade
     */
    public boolean getAvailability() {
        return isAvailable;
    }

    /**
     * Setter for this Item's availability.
     *
     * @param newAvailability whether or not this Item is currently available for trade
     */
    public void setAvailability(boolean newAvailability) {
        isAvailable = newAvailability;
    }
}
