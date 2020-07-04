import java.io.Serializable;
import java.util.Date;

/**
 * Represents an item with a name, description, unique ID, and owner.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-03
 */

public class Item implements Serializable {
    private long id;
    private final String name;
    private final String description;
    private final String ownerUsername;
    private boolean isAvailable;

    /**
     * Class constructor.
     * Creates an Item with the given name, description, and owner username.
     * Also assigns a unique ID to the item and makes the item available for trade by default.
     *
     * @param name          the name being assigned to this Item
     * @param description   the description being assigned to this Item
     * @param ownerUsername the username of the user who owns this Item
     */

    public Item(String name, String description, String ownerUsername) {
        this.name = name;
        this.description = description;
        this.ownerUsername = ownerUsername;
        assignID();
        isAvailable = true;
    }

    //helper method that creates and assigns a unique ID to this Item
    private void assignID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ownerUsername.length(); i++) {
            sb.append((int) ownerUsername.charAt(i));
        }
        id = Long.parseLong(sb.toString() + (new Date().getTime()) / 1000);
        System.out.println(id);
    }

    /**
     * Getter for Item's id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for this item's name
     *
     * @return this item's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for this item's description
     *
     * @return this item's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the owner of this item's username
     *
     * @return ownerUsername
     */
    public String getOwnerUsername() {
        return ownerUsername;
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

    /**
     * Returns a string representation of this Item.
     *
     * @return this Item's ID, name, description, and availability as a string
     */
    public String toString() {
        if (isAvailable) {
            return name + ": " + description;
        } else {
            return name + ": " + description + ("\n   (currently unavailable)");
        }
    }
}
