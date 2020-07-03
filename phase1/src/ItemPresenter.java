import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ItemPresenter.java
 * Shows all Items available for trade in all users' inventory.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-03
 */
public class ItemPresenter {
    private NormalUser user;
    private int max;
    /**
     * ItemPresenter
     * Creates an item presenter at prints to the screen all items available for trade
     * @param u user
     */
    public ItemPresenter(NormalUser u) {
        user = u;
        int input;
        int index = 1;
        String inputConfirm;
        max = ItemManager.getNumApprovedItems();

        SystemPresenter sp = new SystemPresenter(user); // to call strings to print
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sp.itemPresenter("available Items");

        for (Item i : ItemManager.getApprovedItems()) {
            System.out.println(index+i.toString());
            index ++;
        }
        sp.itemPresenter("choose trade");

        try{
            input = Integer.parseInt(br.readLine());
            while(input < 0 || input > max){
                sp.itemPresenter("try again");
                input = Integer.parseInt(br.readLine());
            }
                if(input == 0){
                    new UserDashboard(user);
                }

                Item i = ItemManager.getApprovedItem(input);
                assert i!= null;
                System.out.println("You have chosen: " + input + i.toString());

                if (i.getAvailability()) {
                    System.out.println("Are you sure you want to trade for this item with user, " + i.getOwnerUsername() + " ?(Y/N)");
                    inputConfirm = br.readLine();

                    while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                        sp.itemPresenter("try again");
                        inputConfirm = br.readLine();
                    }

                    if (inputConfirm.equalsIgnoreCase("Y")) {
                        System.out.println("Contacting user, " + i.getOwnerUsername());
                        User trader = UserDatabase.getUserByUsername(i.getOwnerUsername());
                        assert trader != null;
                        String[] traders = {user.getUsername(), trader.getUsername()};
                        long [] items = {0, i.getId()};
                        trader.addTradeRequest(traders, items);
                        user.addTradeRequest(traders, items);
                        user.addWishlist(i);
                    }

                    else {
                        sp.itemPresenter("cancelled");
                    }
                }

                else {
                    sp.itemPresenter("not available");
                }
            new UserDashboard(user);
        }
        catch (IOException e){
            sp.itemPresenter("error");
        }
    }
}
