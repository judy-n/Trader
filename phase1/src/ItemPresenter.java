/**
 * ItemPresenter.java
 * Shows all Items available for trade in all users' inventory.
 *
 * @author Ning Zhang
 * created 2020-06-27
 * last modified 2020-06-28
 */
public class ItemPresenter {
    public ItemPresenter() {
        System.out.println("This is all the item(s) available for trade:");

        ItemDatabase.update();
        for (Item i : ItemDatabase.getAllItems()) {
            System.out.println(i.toString());
        }


    }
}
