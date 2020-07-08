///**
// * A use case of Permanent Trade; has a method that confirms and closes trade.
// *
// * @author Ning Zhang
// * @author Yingjia Liu
// * @author Yiwei Chen
// * @version 1.0
// * @since 2020-06-26
// * last modified 2020-07-06
// */
//
//public class ConfirmAndClosePermTradeTransaction {
//    private ItemManager itemManager;
//    private UserManager userManager;
//
//    public void confirmAndClosePermTransaction(String username, PermanentTrade a, ItemManager im, UserManager um) {
//
//        itemManager = im;
//        userManager = um;
//
//        a.confirmTransaction(username);
//        String[] usernames = a.getInvolvedUsernames();
//
//        if (a.getUserTransactionConfirmation(usernames[0]) && a.getUserTransactionConfirmation(usernames[1])) {
//            NormalUser tempUser1 = userManager.getNormalByUsername(usernames[0]);
//            NormalUser tempUser2 = userManager.getNormalByUsername(usernames[1]);
//            assert tempUser1 != null && tempUser2 != null;
//            long[] itemIDs = a.getInvolvedItemIDs();
//            if (itemIDs[0] != 0) {
//                Item tempItem1 = itemManager.getApprovedItem(itemIDs[0]);
//                assert tempItem1 != null;
//                tempUser1.removeInventory(tempItem1.getID());
//                // [remove from ItemManager arraylist]
//                tempUser2.removeWishlist(tempItem1.getID());
//                tempUser2.addInventory(tempItem1.getID());
//            }
//            if (itemIDs[1] != 0) {
//                Item tempItem2 = itemManager.getApprovedItem(itemIDs[1]);
//                assert tempItem2 != null;
//                tempUser2.removeInventory(tempItem2.getID());
//                // [remove from ItemManager arraylist]
//                tempUser1.removeWishlist(tempItem2.getID());
//                tempUser1.addInventory(tempItem2.getID());
//            }
//        }
//    }
//
//}
