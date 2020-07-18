import com.sun.org.apache.xml.internal.resolver.Catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Shows all items available for trade from all users' inventories except for the user who's currently logged in.
 * Allows users to initiate trades and add items to their wishlist.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-13
 */
public class CatalogViewer {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter sp;
    private BufferedReader br;
    private String username;
    /**
     * Creates an <CatalogViewer></CatalogViewer> with the given normal user and item/user/trade managers.
     * Displays all items available for trade (excluding the current user's items).
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public CatalogViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        username = user.getUsername();

        sp = new SystemPresenter();
        br = new BufferedReader(new InputStreamReader(System.in));

        int maxIndex = itemManager.getNumApprovedItems(username);

        sp.catalogViewer(itemManager.getApprovedItems(username));

        int timesBorrowed = userManager.getNormalUserTimesBorrwed(username) + tradeManager.getTimesBorrowed(username);
        int timesLent = tradeManager.getTimesLent(username);

        sp.catalogViewer(1);
        try {
            String temp = br.readLine();
            while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > maxIndex) {
                sp.invalidInput();
                temp = br.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input != 0) {
                Item selectedItem = itemManager.getApprovedItem(username, input - 1);

                assert selectedItem != null;
                long itemID = selectedItem.getID();

                sp.catalogViewer(selectedItem, 1);

                String temp2 = br.readLine();
                while (!temp2.matches("[0-2]")) {
                    sp.invalidInput();
                    temp2 = br.readLine();
                }
                int tradeOrWishlist = Integer.parseInt(temp2);

                if (tradeOrWishlist == 1 && !selectedItem.getAvailability()) {
                    sp.catalogViewer(3);

                    //option to add unavailable item to wishlist
                    String confirmInput = br.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        sp.invalidInput();
                        confirmInput = br.readLine();
                    }

                    if (confirmInput.equalsIgnoreCase("Y")) {
                        tradeOrWishlist = 2;
                    }

                } else if (tradeOrWishlist == 1) {
                    if (currentUser.getIsFrozen()) {
                        sp.catalogViewer(2);
                    } else if (userManager.isRequestedInTrade(username, itemID)) {
                        sp.catalogViewer(6);
                    } else if (timesBorrowed > 0 && ((timesLent - timesBorrowed) < currentUser.getLendMinimum())) {
                        sp.catalogViewer(currentUser);
                    } else {
                        startTradeAttempt(selectedItem);
                    }
                }

                if (tradeOrWishlist == 2 && !currentUser.getWishlist().contains(itemID)) {
                    currentUser.addWishlist(itemID);
                    sp.catalogViewer(4);
                } else if (tradeOrWishlist == 2 && currentUser.getWishlist().contains(itemID)) {
                    sp.catalogViewer(5);
                }
            }
        } catch (IOException e) {
            sp.exceptionMessage();
        }
        close();
    }

    private void startTradeAttempt(Item selectedItem) throws IOException {

        sp.catalogViewer(selectedItem, 2);

        String inputConfirm = br.readLine();
        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
            sp.invalidInput();
            inputConfirm = br.readLine();
        }
        if (inputConfirm.equalsIgnoreCase("Y")) {

            NormalUser trader = userManager.getNormalByUsername(selectedItem.getOwnerUsername());
            assert trader != null;
            String[] traders = {username, trader.getUsername()};
            long[] items = {0, selectedItem.getID()};
            trader.addTradeRequest(traders, items);
            currentUser.addTradeRequest(traders, items);
            if (!currentUser.getWishlist().contains(selectedItem.getID())) {
                currentUser.addWishlist(selectedItem.getID());
            }

            sp.catalogViewer(selectedItem, 3);

        } else {
            sp.cancelled();
        }
    }

    /**
     * Creates a catalog viewer for the given username's associated user
     * @param username the username
     * @param im the item manager
     * @param um the user manager
     * @param tm the trade manager
     */
    public CatalogViewer(String username, ItemManager im, UserManager um, TradeManager tm) {
        new CatalogViewer(um.getNormalByUsername(username), im, um, tm);
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
