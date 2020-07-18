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
    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;
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

        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int maxIndex = itemManager.getNumApprovedItems(username);

        systemPresenter.catalogViewer(itemManager.getApprovedItems(username));

        int timesBorrowed = userManager.getNormalUserTimesBorrowed(username) + tradeManager.getTimesBorrowed(username);
        int timesLent = tradeManager.getTimesLent(username);

        systemPresenter.catalogViewer(1);
        try {
            String temp = bufferedReader.readLine();
            while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > maxIndex) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input != 0) {
                Item selectedItem = itemManager.getApprovedItem(username, input - 1);

                assert selectedItem != null;
                long itemID = selectedItem.getID();

                systemPresenter.catalogViewer(selectedItem, 1);

                String temp2 = bufferedReader.readLine();
                while (!temp2.matches("[0-2]")) {
                    systemPresenter.invalidInput();
                    temp2 = bufferedReader.readLine();
                }
                int tradeOrWishlist = Integer.parseInt(temp2);

                if (tradeOrWishlist == 1 && !selectedItem.getAvailability()) {
                    systemPresenter.catalogViewer(3);

                    //option to add unavailable item to wishlist
                    String confirmInput = bufferedReader.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        systemPresenter.invalidInput();
                        confirmInput = bufferedReader.readLine();
                    }

                    if (confirmInput.equalsIgnoreCase("Y")) {
                        tradeOrWishlist = 2;
                    }

                } else if (tradeOrWishlist == 1) {
                    if (currentUser.getIsFrozen()) {
                        systemPresenter.catalogViewer(2);
                    } else if (userManager.isRequestedInTrade(username, itemID)) {
                        systemPresenter.catalogViewer(6);
                    } else if (timesBorrowed > 0 && ((timesLent - timesBorrowed) < currentUser.getLendMinimum())) {
                        systemPresenter.catalogViewer(currentUser);
                    } else {
                        startTradeAttempt(selectedItem);
                    }
                }

                if (tradeOrWishlist == 2 && !currentUser.getWishlist().contains(itemID)) {
                    currentUser.addWishlist(itemID);
                    systemPresenter.catalogViewer(4);
                } else if (tradeOrWishlist == 2 && currentUser.getWishlist().contains(itemID)) {
                    systemPresenter.catalogViewer(5);
                }
            }
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
        close();
    }

    private void startTradeAttempt(Item selectedItem) throws IOException {

        systemPresenter.catalogViewer(selectedItem, 2);

        String inputConfirm = bufferedReader.readLine();
        while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
            systemPresenter.invalidInput();
            inputConfirm = bufferedReader.readLine();
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

            systemPresenter.catalogViewer(selectedItem, 3);

        } else {
            systemPresenter.cancelled();
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
