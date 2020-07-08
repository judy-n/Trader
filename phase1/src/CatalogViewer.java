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
 * last modified 2020-07-08
 */
public class CatalogViewer {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter sp;
    private BufferedReader br;

    /**
     * Class constructor.
     * Creates an ItemPresenter with the given logged-in user and item/user/trade managers.
     * Prints to the screen all items available for trade (excluding the current user's items).
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public CatalogViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        sp = new SystemPresenter();
        br = new BufferedReader(new InputStreamReader(System.in));

        int input;
        int maxIndex = itemManager.getNumApprovedItems(currentUser.getUsername());

        sp.catalogViewer(itemManager.getApprovedItems(currentUser.getUsername()));
        sp.catalogViewer(1);
        try {
            String temp = br.readLine();
            while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > maxIndex) {
                sp.invalidInput();
                temp = br.readLine();
            }
            input = Integer.parseInt(temp);
            if (input != 0) {
                Item selectedItem = itemManager.getApprovedItem(input);
                assert selectedItem != null;
                sp.catalogViewer(selectedItem, 1);

                String temp2 = br.readLine();
                while (!temp2.matches("[0-2]+") || Integer.parseInt(temp2) > 2) {
                    sp.invalidInput();
                    temp2 = br.readLine();
                }
                int tradeOrWishlist = Integer.parseInt(temp2);

                if (tradeOrWishlist == 1) {
                    if (currentUser.getIsFrozen()) {
                        sp.catalogViewer(2);
                    } else {
                        startTradeAttempt(selectedItem);
                    }
                } else if (tradeOrWishlist == 2) {
                    currentUser.addWishlist(selectedItem.getID());
                }
            }
            close();
        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    private void startTradeAttempt(Item selectedItem) throws IOException {
        if (selectedItem.getAvailability()) {

            sp.catalogViewer(selectedItem, 2);

            String inputConfirm = br.readLine();
            while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                sp.invalidInput();
                inputConfirm = br.readLine();
            }
            if (inputConfirm.equalsIgnoreCase("Y")) {

                sp.catalogViewer(selectedItem, 3);

                NormalUser trader = userManager.getNormalByUsername(selectedItem.getOwnerUsername());
                assert trader != null;
                String[] traders = {currentUser.getUsername(), trader.getUsername()};
                long[] items = {0, selectedItem.getID()};
                trader.addTradeRequest(traders, items);
                currentUser.addTradeRequest(traders, items);
                currentUser.addWishlist(selectedItem.getID());
            } else {
                sp.cancelled();
            }
        } else {
            sp.catalogViewer(selectedItem, 4);
        }
    }
    private void close(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
