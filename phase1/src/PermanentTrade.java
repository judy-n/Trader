import java.time.LocalDateTime;

/**
 * PermanentTrade.java
 * Represents a permanent trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-30
 */
public class PermanentTrade extends Trade {

    /**
     * PermanentTrade
     * Creates a PermanentTrade
     * @param usernames an array containing the usernames of the two Users involved in this TemporaryTrade
     * @param itemIDs an array containing the IDs of the Items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this TemporaryTrade's meeting
     * @param firstLocation the first location suggested for this TemporaryTrade's meeting
     */
    public PermanentTrade(String[] usernames, double[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
    }

    @Override
    public void confirmTransaction(String username) {
        super.confirmTransaction(username);
        String[] usernames = getInvolvedUsernames();

        if (getUserTransactionConfirmation(usernames[0]) && getUserTransactionConfirmation(usernames[1])) {
            NormalUser tempUser1 = UserDatabase.getUserByUsername(usernames[0]);
            NormalUser tempUser2 = UserDatabase.getUserByUsername(usernames[1]);
            assert tempUser1 != null && tempUser2 != null;
            double[] itemIDs = getInvolvedItemIDs();
            if (itemIDs[0] != 0) {
                Item tempItem1 = ItemDatabase.getItem(itemIDs[0]);
                assert tempItem1 != null;
                tempUser1.removeInventory(tempItem1.getId());
                // [remove from ItemManager arraylist]
                tempUser2.removeWishlist(tempItem1.getId());
            }
            if (itemIDs[1] != 0) {
                Item tempItem2 = ItemDatabase.getItem(itemIDs[1]);
                assert tempItem2 != null;
                tempUser2.removeInventory(tempItem2.getId());
                // [remove from ItemManager arraylist]
                tempUser1.removeWishlist(tempItem2.getId());
            }
        }
    }
}

