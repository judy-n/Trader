/**
 * ItemPresenter.java
 * Shows all Items available for trade in all users' inventory.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */
public class ItemPresenter {
    /**
     * ItemPresenter
     * Creates an item present at prints to the screen all items available for trade
     */

    public ItemPresenter() {
        System.out.println("This is all the item(s) available for trade:");

        ItemDatabase.update();
        for (Item i : ItemDatabase.getAllItems()) {
            System.out.println(i.toString());
        }


    }
}
