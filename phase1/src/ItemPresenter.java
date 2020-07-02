import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ItemPresenter.java
 * Shows all Items available for trade in all users' inventory.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */
public class ItemPresenter {
    public User user;
    public int max;
    /**
     * ItemPresenter
     * Creates an item presenter at prints to the screen all items available for trade
     * @param u user
     */
    public ItemPresenter(User u) {
        user = u;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        int index = 1;
        String inputConfirm;
        System.out.println("This is all the item(s) available for trade:");
        max = ItemDatabase.getNumItems();
        ItemDatabase.update();
        for (Item i : ItemDatabase.getAllItems()) {
            System.out.println(index+i.toString());
            index ++;
        }
        System.out.println("Is there an Item you would like to trade for? (0 to quit)");
        try{
            input = Integer.parseInt(br.readLine());
            while(input < 0 || input > max){
                System.out.println("Invalid input try again.");
                input = Integer.parseInt(br.readLine());
            }
                if(input == 0){
                    new UserDashboard(user);
                }
                Item i = ItemDatabase.getItem(input);
                assert i!= null;
                System.out.println("You have chosen: " + input + i.toString());

                if (i.getAvailability()) {
                    System.out.println("Are you sure you want to trade for this item with user, " + i.ownerUsername + " ?(Y/N)");
                    inputConfirm = br.readLine();
                    while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                        System.out.println("Invalid input.");
                        inputConfirm = br.readLine();
                    }
                    if (inputConfirm.equalsIgnoreCase("Y")) {
                        System.out.println("Contacting user, " + i.ownerUsername);
                        User trader = UserDatabase.getUserByUsername(i.ownerUsername);
                        assert trader != null;
                        String[] traders = {user.getUsername(), trader.getUsername()};
                        double [] items = {0.0, i.id};
                        trader.addTradeRequest(traders, items);
                        user.addTradeRequest(traders, items);
                        user.addWishlist(i);
                    } else {
                        System.out.println("Cancelled.");
                    }
                } else {
                    System.out.println("Sorry, this item is currently not available for trade. We suggest adding it to your wishlist!");
                }
            new UserDashboard(user);
        } catch (IOException e){
            System.out.println("You kinda suck bruh.");
        }


    }
}
