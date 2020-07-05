import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
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
     * Class constructor.
     * Creates an ItemPresenter with the given logged-in user, item manager, and user manager.
     * Prints to the screen all items available for trade.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public ItemPresenter(NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        this.im = im;
        this.um = um;
        int input;
        int index = 1;
        String inputConfirm;
        max = im.getNumApprovedItems();

        SystemPresenter sp = new SystemPresenter(); // to call strings to print
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sp.itemPresenter(im.getApprovedItems());

        try {
            String tempInput = br.readLine();
            while (!isInteger(tempInput)) {
                tempInput = br.readLine();
            }
            input = Integer.parseInt(tempInput);
            while (input < 0 || input > max) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }
            if (input == 0) {
                new UserDashboard(currentUser, im, um);
            }

            Item i = im.getApprovedItem(input);
            assert i != null;
            sp.itemPresenter(i, 1);
            if (i.getAvailability()) {
                sp.itemPresenter(i, 2);
                inputConfirm = br.readLine();
                while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                    sp.invalidInput();
                    inputConfirm = br.readLine();
                }

                if (inputConfirm.equalsIgnoreCase("Y")) {
                    sp.itemPresenter(i, 3);
                    NormalUser trader = um.getUserByUsername(i.getOwnerUsername());
                    assert trader != null;
                    String[] traders = {currentUser.getUsername(), trader.getUsername()};
                    long[] items = {0, i.getId()};
                    trader.addTradeRequest(traders, items);
                    currentUser.addTradeRequest(traders, items);
                    currentUser.addWishlist(i.getId());
                } else {
                    sp.cancelled();
                }
            } else {
                sp.itemPresenter(i, 4);
            }
            new UserDashboard(currentUser, im, um);
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    // based on code by Bill the Lizard from www.stackoverflow.com
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
