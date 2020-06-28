/**
 * ItemPresenter.java
 * Shows all items available in all user's inventory
 * @author Ning Zhang
 * created 2020-06-27
 * last modified 2020-06-27
 */
public class ItemPresenter {
    public ItemPresenter(){
        System.out.println("This is all the item(s) available for trade:");

        ItemDatabase.update();
        for (Item i : ItemDatabase.getAllItems()){
            System.out.println(i.toString());
        }


    }
}
