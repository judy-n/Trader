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
    private NormalUser currentUser;
    private int max;
    private ItemManager im;
    private UserManager um;
    /**
     * ItemPresenter
     * Creates an item presenter at prints to the screen all items available for trade
     * @param u user
     */
    public ItemPresenter(NormalUser u, ItemManager im, UserManager um) {
        currentUser = u;
        this.im = im;
        this.um = um;
        int input;
        int index = 1;
        String inputConfirm;
        max = im.getNumApprovedItems();

        SystemPresenter sp = new SystemPresenter(currentUser); // to call strings to print
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sp.itemPresenter("available Items");

        for (Item i : im.getApprovedItems()) {
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
                    new UserDashboard(currentUser, im, um);
                }

                 Item i = im.getApprovedItem(input);
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
                        NormalUser trader = um.getUserByUsername(i.getOwnerUsername());
                        assert trader != null;
                        String[] traders = {currentUser.getUsername(), trader.getUsername()};
                        long [] items = {0, i.getId()};
                        trader.addTradeRequest(traders, items);
                        currentUser.addTradeRequest(traders, items);
                        currentUser.addWishlist(i.getId());
                    }

                    else {
                        sp.itemPresenter("cancelled");
                    }
               }

                else {
                    sp.itemPresenter("not available");
                }
            new UserDashboard(currentUser, im, um);
        }
        catch (IOException e){
            sp.itemPresenter("error");
        }
    }
}
