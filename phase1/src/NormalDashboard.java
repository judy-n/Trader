import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.sql.Array;
import java.sql.SQLOutput;

/**
 * Displays a dashboard once a non-administrative user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-05
 */


public class NormalDashboard {
    private NormalUser currentUser;
    private int input;
    private ItemManager im;
    private UserManager um;
    private boolean isLoggedOut;

    /**
     * Creates a NormalDashboard that stores the given logged-in user.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public NormalDashboard(NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        this.im = im;
        this.um = um;
        isLoggedOut = false;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int maxChoice = 7;

        if (currentUser.getIsFrozen()) {
            maxChoice = 8;
            sp.normalDashboard(2);
        } else {
            sp.normalDashboard(1);
        }

        try {
            input = Integer.parseInt(br.readLine());
            while (input < 0 || input > maxChoice) {
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            sp.exceptionMessage();
            System.exit(-1);
        }

        switch (input) {
            case 0:
                try {
                    br.close();
                } catch (IOException e) {
                   sp.exceptionMessage();
                }
                isLoggedOut = true;
                sp.normalDashboard(3);
                break;
            case 1:
                new CatalogViewer(currentUser, im, um);
                break;

            case 2:
                new InventoryEditor(currentUser, im, um);
                break;

            case 3:
                new WishlistEditor(currentUser, im, um);
                break;

            case 4:
                new TradeRequestViewer(currentUser, im, um);
                break;

            case 5:
                // view ongoing trades
                // heavily relies on temp trade and perm trade implementation
                // so don't implement until Eric + Yiwei are done with that

                // use getOngoingTrades in TradeManager
                // in the new class created for this option, handle the whole meeting suggestion thing?
                // make sure you check if the meeting is a suggestion or if it's already agreed upon
                // before editing a meeting check if they've reached the max number of times they can edit (meetingEditMax in NormalUser)
                // if both users have reached their max, the recipient of the last suggestion either accepts the suggestion or cancels the trade

                // btw what to do about cancelled trades? (when both users have reached their max edits and the last suggestion is rejected)
                // >> have to let both users know it got cancelled
                break;

            case 6:
                // view most recent three *completed* trades
                // know that you might have to make changes to code later cuz trade classes are being remodeled

                // if trade was two-way, display smth like "Lent [item] and borrowed [item] from [username]"
                // if trade was one-way, "Lent [item] to [username]" or "Borrowed [item] from [username]"
                // you can get what a certain user lent in a Trade by using tradeInstance.getLentItemID(username)
                break;

            case 7:
                // view top three most frequent trading partners (only counts if trades are completed)
                // know that you might have to make changes to code later cuz trade classes are being remodeled

                // add a method in TradeManager that takes in a user and finds those top three most frequent trade partners
                // TradeManager's getCompletedTrades method can help
                // p.s. might wanna use an array of size three so no extra space in memory is used up
                break;

            case 8:
                // unfreeze request option for frozen account
                // new class or stuff it in UserManager too?
                break;
        }
    }

    public boolean getIsLoggedOut(){
        return isLoggedOut;
    }
}
