import java.time.LocalDate;

/**
 * Use Case of Temporary Trade; confirms it.
 *
 * @author Yiwei Chen
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class ConfirmTempTradeTransaction {
    private ItemManager itemManager;
    private UserManager userManager;

    public void confirmTempTransaction(String username, TemporaryTrade a, ItemManager im, UserManager um){

        itemManager = im;
        userManager = um;

        a.confirmTransaction(username);
        String[] usernames = a.getInvolvedUsernames();

        if (a.getUserTransactionConfirmation(usernames[0]) && a.getUserTransactionConfirmation(usernames[1])) {
            NormalUser tempUser1 = userManager.getNormalByUsername(usernames[0]);
            NormalUser tempUser2 = userManager.getNormalByUsername(usernames[1]);
            assert tempUser1 != null && tempUser2 != null;
            long[] itemIDs = a.getInvolvedItemIDs();
            if (itemIDs[0] != 0 && itemIDs[1] != 0) {
                Item tempItem1 = itemManager.getApprovedItem(itemIDs[0]);
                assert tempItem1 != null;
                tempItem1.setAvailability(false);
                Item tempItem2 = itemManager.getApprovedItem(itemIDs[1]);
                assert tempItem2 != null;
                tempItem2.setAvailability(false);
                LocalDate currentDate = LocalDate.now();
                LocalDate dueDate1 = currentDate.plusDays(itemManager.getApprovedItem(itemIDs[0]).getBorrowTime());
                LocalDate dueDate2 = currentDate.plusDays(itemManager.getApprovedItem(itemIDs[1]).getBorrowTime());
                a.setEndDateTime(dueDate1, dueDate2);
            }
            else if (itemIDs[0] != 0) {
                Item tempItem1 = itemManager.getApprovedItem(itemIDs[0]);
                assert tempItem1 != null;
                tempItem1.setAvailability(false);
                LocalDate currentDate = LocalDate.now();
                LocalDate dueDate1 = currentDate.plusDays(itemManager.getApprovedItem(itemIDs[0]).getBorrowTime());
                a.setEndDateTime(dueDate1, null);
            }
            else {
                Item tempItem2 = itemManager.getApprovedItem(itemIDs[1]);
                assert tempItem2 != null;
                tempItem2.setAvailability(false);
                LocalDate currentDate = LocalDate.now();
                LocalDate dueDate2 = currentDate.plusDays(itemManager.getApprovedItem(itemIDs[1]).getBorrowTime());
                a.setEndDateTime(null, dueDate2);
            }
        }
    }

}
