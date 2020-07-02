import java.util.ArrayList;

/**
 * ItemDatabase.java
 * Stores all Items from all users' inventories.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-29
 */
public class ItemDatabase {
    private static ArrayList<Item> allItems = new ArrayList<>();

    public static ArrayList<Item> getAllItems() {
        return allItems;
    }

    /**
     * This method returns the number of items in the item database
     * @return numItems
     */
    public static int getNumItems(){
        update();
        return allItems.size();
    }

    /**
     * This method returns an item by its id
     * @param id id number
     * @return Item
     */
    public static Item getItem(double id){
        update();
        for(Item i : allItems){
            if (i.getId() == id){
                return i;
            }
        }
        return null;
    }

    /**
     * This method returns an item by its index
     * @param index index
     * @return Item
     */
    public static Item getItem(int index){
        return allItems.get(index - 1);
    }


    public static void update() {
        allItems.clear();
        for ( User u : UserDatabase.getAllUsers()) {
            allItems.addAll(u.getInventory());
        }
    }



}
