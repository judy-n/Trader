package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;
import Entities.Item;
import SystemFunctions.SystemPresenter;
import SystemFunctions.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Shows the user their wishlist and lets them edit it through user input.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-31
 */
public class WishlistEditor extends MenuItem {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    /**
     * Creates a <WishlistEditor></WishlistEditor> with the given normal user,
     * item/user/trade managers, and notification system.
     * Prints to the screen the given user's wishlist and options to remove/cancel using <SystemPresenter></SystemPresenter>.
     * This class lets the user remove items from their wishlist through user input.
     * They can only add items to their wishlist when browsing items available for trade.
     *
     * @param user         the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public WishlistEditor(NormalUser user, ItemManager itemManager, UserManager userManager,
                          TradeManager tradeManager, NotificationSystem notifSystem) {

        currentUser = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Item> itemWishlist = itemManager.getItemsByIDs(currentUser.getWishlist());
        systemPresenter.wishlistEditor(itemWishlist);
        try {
            String temp = bufferedReader.readLine();
            while (!temp.matches("[1-2]")) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input == 1) {
                if (currentUser.getWishlist().isEmpty()) {
                    systemPresenter.wishlistRemoveItem(1);
                } else {
                    systemPresenter.wishlistRemoveItem(2);
                    String temp2 = bufferedReader.readLine();
                    while (!temp2.matches("[0-9]+") ||
                            Integer.parseInt(temp2) > itemWishlist.size() || Integer.parseInt(temp2) < 1) {
                        systemPresenter.invalidInput();
                        temp2 = bufferedReader.readLine();
                    }
                    int indexInput = Integer.parseInt(temp2);
                    Item selected = itemWishlist.get(indexInput - 1);
                    systemPresenter.wishlistRemoveItem(selected.getName(), 1);

                    String confirmInput = bufferedReader.readLine();
                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
                        systemPresenter.invalidInput();
                        confirmInput = bufferedReader.readLine();
                    }
                    if (confirmInput.equalsIgnoreCase("y")) {
                        currentUser.removeWishlist(selected.getID());
                        systemPresenter.wishlistRemoveItem(selected.getName(), 2);
                    } else {
                        systemPresenter.cancelled();
                    }
                }
            }
            close();
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
            System.exit(-1);
        }
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return "Wishlist Editor";
    }
}
