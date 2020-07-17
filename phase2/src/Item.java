import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Represents an item with a name, description, unique ID, and owner.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-12
 */
public class Item implements Serializable {
    private long id;
    private final String name;
    private final String description;
    private final String ownerUsername;
    private boolean isAvailable;
    private boolean isRemoved;

    /**
     * Creates an <Item></Item> with the given name, description, and owner username.
     * Also assigns a unique ID to the item and makes the item available for trade by default.
     *
     * @param name          the name being assigned to this <Item></Item>
     * @param description   the description being assigned to this <Item></Item>
     * @param ownerUsername the username of the normal user who owns this <Item></Item>
     */
    public Item(String name, String description, String ownerUsername) {
        this.name = name;
        this.description = description;
        this.ownerUsername = ownerUsername;
        assignID();
        isAvailable = true;
        isRemoved = false;
    }

    /* Helper method that creates and assigns a unique ID to this Item */
    private void assignID() {
        SecureRandom secureRandom = new SecureRandom();
        id = secureRandom.nextLong();
    }

    /**
     * Getter for <Item></Item>'s ID.
     *
     * @return this <Item></Item>'s ID
     */
    public long getID() {
        return id;
    }

    /**
     * Getter for this <Item></Item>'s name.
     *
     * @return this <Item></Item>'s name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for this <Item></Item>'s description.
     *
     * @return this <Item></Item>'s description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the username of this <Item></Item>'s owner.
     *
     * @return the username of this <Item></Item>'s owner.
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Getter for this <Item></Item>'s availability.
     *
     * @return true is this <Item></Item> is currently available for trade, false if not
     */
    public boolean getAvailability() {
        return isAvailable;
    }

    /**
     * Setter for this <Item></Item>'s availability.
     *
     * @param newAvailability whether or not this <Item></Item> is currently available for trade
     */
    public void setAvailability(boolean newAvailability) {
        isAvailable = newAvailability;
    }

    /**
     * Getter for whether or not this <Item></Item> is still in its owner's inventory.
     *
     * @return true if this <Item></Item> is still in inventory, false otherwise
     */
    public boolean isInInventory() {
        return !isRemoved;
    }

    /**
     * Setter for whether or not this <Item></Item> has been removed from its owner's inventory.
     *
     * @param status whether or not this <Item></Item> has been removed from its owner's inventory
     */
    public void setIsRemoved(boolean status) {
        isRemoved = status;
    }

    /**
     * Returns a String representation of this <Item></Item>.
     *
     * @return this <Item></Item>'s ID, name, description, and availability as a String
     */
    @Override
    public String toString() {
        if (isAvailable) {
            return name + ": " + description;
        } else {
            return name + ": " + description + ("   (currently unavailable)");
        }
    }
}
