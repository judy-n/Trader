import java.io.Serializable;
import static java.lang.System.currentTimeMillis;

/**
 * Represents an item with a name, description, ID, and owner.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-01
 */

public class Item implements Serializable {
    public double id;
    public String name;
    public String description;
    public String ownerUsername;
    private boolean isAvailable;

    /**
     * Class constructor.
     * Creates an Item with the given name, description.
     * Also assigns a unique ID to the item and makes the item available for trade by default.
     *
     * @param name the given name
     * @param description the given description
     */

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        isAvailable = true;
    }

    public void assignID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ownerUsername.length(); i++) {
            sb.append((int) ownerUsername.charAt(i));
        }
        id = Double.parseDouble(sb.toString() + (double) System.currentTimeMillis());

        //test
        System.out.println(System.currentTimeMillis() + "VS");
        //System.out.println(id + "VS");
        System.out.println(sb);
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
