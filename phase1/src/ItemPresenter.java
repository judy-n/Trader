import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
/**
 * ItemPresenter.java
 * Shows all items available in all user's inventory
 * @author Ning Zhang
 * created 2020-06-27
 * last modified 2020-06-27
 */
public class ItemPresenter {
    public ItemPresenter(){
        UserDatabase udb = new UserDatabase();
        System.out.println("This is all the item(s) available for trade:");
        for (Item i : udb.allUserInventory()){
            System.out.println(i.toString());
        }


    }
}
