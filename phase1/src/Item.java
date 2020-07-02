import java.io.Serializable;
import java.util.Date;

/**
 * Represents an item with a name, description, ID, and owner.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-02
 */

public class Item implements Serializable {
    private long id;
    private String name;
    private String description;
    private String ownerUsername;
    private boolean isAvailable;

    /**
     * Class constructor.
     * Creates an Item with the given name, description.
     * Also assigns a unique ID to the item and makes the item available for trade by default.
     *
     * @param name the name being assigned to this Item
     * @param description the description being assigned to this Item
     */

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        isAvailable = true;
    }

    /**
     * Getter for Item's id
     * @return id
     */
    public long getId() {return id;}

    /**
     * Setter for this item's id
     * @param id this item's id
     */
    public void setId(long id) {this.id = id; }

    /**
     * Getter for this item's name
     * @return this item's name
     */
    public String getName() {return name;}

    /**
     * Setter for this item's name
     * @param name this item's name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Getter for this item's description
     * @return this item's description
     */
    public String getDescription() {return description;}

    /**
     * Setter for this item's description
     * @param description this item's new description
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Getter for the owner of this item's username
     * @return ownerUsername
     */
    public String getOwnerUsername() {return ownerUsername;}

    /**
     * Setter for this item's owner's username
     * @param ownerUsername this item's new owner's username
     */
    public void setOwnerUsername(String ownerUsername) {this.ownerUsername = ownerUsername;}

    public void assignID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ownerUsername.length(); i++) {
            sb.append((int) ownerUsername.charAt(i));
        }
//        id = Double.parseDouble(sb.toString() + (double) System.currentTimeMillis());
        id = Long.parseLong(sb.toString() + (new Date().getTime())/1000);
        System.out.println(id);
    }

    /**
     * Returns a string representation of this Item.
     *
     * @return this Item's ID, name, description, and availability as a string
     */
    public String toString() {
        if (isAvailable) {
            return  ". " + name + ": " + description;
        } else {
            return  ". " + name + ": " + description + ("\n   (currently unavailable)");
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
