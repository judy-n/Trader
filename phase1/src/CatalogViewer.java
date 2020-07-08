import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Shows all Items available for trade in all users' inventory.
 * Allows users to initiate trades and add items to their wishlist.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-06
 */
public class CatalogViewer {
    private NormalUser currentUser;
    private int max;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Class constructor.
     * Creates an ItemPresenter with the given logged-in user, item manager, and user manager.
     * Prints to the screen all items available for trade.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public CatalogViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        int input;
        String inputConfirm;
        max = im.getNumApprovedItems();

        SystemPresenter sp = new SystemPresenter(); // to call strings to print
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sp.catalogViewer(im.getApprovedItems(currentUser));
        if(currentUser.getIsFrozen()){
            sp.catalogViewer(2);
            close();
        }

        sp.catalogViewer(1);
        try {
            input = Integer.parseInt(br.readLine());
            while (input < 0 || input > max) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }
            if (input != 0) {
                Item i = im.getApprovedItem(input);
                assert i != null;
                sp.catalogViewer(i, 1);
                if (i.getAvailability()) {
                    sp.catalogViewer(i, 2);
                    inputConfirm = br.readLine();
                    while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                        sp.invalidInput();
                        inputConfirm = br.readLine();
                    }

                    if (inputConfirm.equalsIgnoreCase("Y")) {
                        sp.catalogViewer(i, 3);
                        NormalUser trader = um.getNormalByUsername(i.getOwnerUsername());
                        assert trader != null;
                        String[] traders = {currentUser.getUsername(), trader.getUsername()};
                        long[] items = {0, i.getID()};
                        trader.addTradeRequest(traders, items);
                        currentUser.addTradeRequest(traders, items);
                        currentUser.addWishlist(i.getID());
                    } else {
                        sp.cancelled();
                    }
                } else {
                    sp.catalogViewer(i, 4);
                }
            }
            close();
        } catch (IOException e) {
            sp.exceptionMessage();
            System.exit(-1);
        }
    }

    private void close(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
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
