import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Shows all the completed trades for the user, sorted by date.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class CompletedTradesViewer {
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;


    public CompletedTradesViewer (NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        ArrayList<Trade> completedTrades = tm.getCompletedTrades(currentUser);

        System.out.println("Here are all your completed trades: ");
        for(Trade t : completedTrades){
            //a toString method for trade will be added later
            System.out.println(t);
        }

        close();
    }
    public void close(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}