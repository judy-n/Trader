import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Shows the user their wishlist and lets them edit it through user input.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-04
 */

public class WishlistEditor {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Class constructor.
     * Creates a WishlistEditor with the given logged-in user, item manager, and user manager.
     * Prints to the screen the given user's wishlist and options to remove/cancel.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public WishlistEditor(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        // This lets the User remove Items from the wish list. Assuming that they only add Items to
        // the wishlist when browsing items available for trade.
        SystemPresenter sp = new SystemPresenter();
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        ArrayList<Item> itemWishlist = im.getApprovedItemsByIDs(currentUser.getWishlist());
        sp.wishlistEditor(itemWishlist);
        try {
            input = Integer.parseInt(br.readLine());
            while (input < 1 || input > 2) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }
            if (input == 1) {   //remove item
                if (currentUser.getWishlist().isEmpty()) {
                    sp.wishlistRemoveItem(1);
                    close();
                } else {
                    sp.wishlistRemoveItem(2);
                    int indexInput;
                    try {
                        indexInput = Integer.parseInt(br.readLine());
                        Item selected = itemWishlist.get(indexInput - 1);
                        sp.wishlistRemoveItem(selected.getName(), 1);
                        String confirmInput = br.readLine();
                        while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                            sp.invalidInput();
                            confirmInput = br.readLine();
                        }
                        if (confirmInput.equalsIgnoreCase("y")) {
                            currentUser.removeWishlist(selected.getID());
                            sp.wishlistRemoveItem(selected.getName(), 2);
                        } else {
                            sp.cancelled();
                        }
                        close();
                    } catch (IOException e) {
                        sp.invalidInput();
                    }
                }
            } else {    //cancel
                close();
            }
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
