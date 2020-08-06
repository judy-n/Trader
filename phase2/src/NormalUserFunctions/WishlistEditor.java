package NormalUserFunctions;

import SystemManagers.UserManager;
import SystemManagers.ItemManager;

/**
 * Helps show the user their wishlist and let them edit it through user input.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-08-06
 */
public class WishlistEditor {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;

    /**
     * Creates a <WishlistEditor></WishlistEditor> with the given normal username and item/user managers.
     *
     * @param currUsername the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     */
    public WishlistEditor(String currUsername, ItemManager itemManager, UserManager userManager) {

        this.currUsername = currUsername;
        this.itemManager = itemManager;
        this.userManager = userManager;


//        SystemPresenter systemPresenter = new SystemPresenter();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//
//        List<Item> itemWishlist = itemManager.getItemsByIDs(userManager.getNormalUserWishlist(currUsername));
//        systemPresenter.wishlistEditor(itemWishlist);
//        try {
//            String temp = bufferedReader.readLine();
//            while (!temp.matches("[1-2]")) {
//                systemPresenter.invalidInput();
//                temp = bufferedReader.readLine();
//            }
//            int input = Integer.parseInt(temp);
//            if (input == 1) {
//                if (userManager.getNormalUserWishlist(currUsername).isEmpty()) {
//                    systemPresenter.wishlistRemoveItem(1);
//                } else {
//                    systemPresenter.wishlistRemoveItem(2);
//                    String temp2 = bufferedReader.readLine();
//                    while (!temp2.matches("[0-9]+") ||
//                            Integer.parseInt(temp2) > itemWishlist.size() || Integer.parseInt(temp2) < 1) {
//                        systemPresenter.invalidInput();
//                        temp2 = bufferedReader.readLine();
//                    }
//                    int indexInput = Integer.parseInt(temp2);
//                    Item selected = itemWishlist.get(indexInput - 1);
//                    systemPresenter.wishlistRemoveItem(selected.getName(), 1);
//
//                    String confirmInput = bufferedReader.readLine();
//                    while (!confirmInput.equalsIgnoreCase("Y") && !confirmInput.equalsIgnoreCase("N")) {
//                        systemPresenter.invalidInput();
//                        confirmInput = bufferedReader.readLine();
//                    }
//                    if (confirmInput.equalsIgnoreCase("y")) {
//                        userManager.removeFromNormalUserWishlist(selected.getID(), currUsername);
//                        systemPresenter.wishlistRemoveItem(selected.getName(), 2);
//                    } else {
//                        systemPresenter.cancelled();
//                    }
//                }
//            }
//            close();
//        } catch (IOException e) {
//            systemPresenter.exceptionMessage();
//            System.exit(-1);
//        }
    }

    /**
     * Converts the current user's wishlist into an array of string representations and returns it.
     *
     * @return an array containing string representations of all items of the current user's wishlist
     */
    public String[] getWishlistStrings() {
        // Passing second arg as true means each string representation will include the item's owner
        return itemManager.getItemStringsID(userManager.getNormalUserWishlist(currUsername), true);
    }

    /**
     * Removes the item ID at the given index from the current user's wishlist.
     *
     * @param index the index of the item ID being removed
     */
    public void removeItem(int index) {
        long selectedID = userManager.getNormalUserWishlist(currUsername).get(index);
        userManager.removeFromNormalUserWishlist(selectedID, currUsername);
    }
}
