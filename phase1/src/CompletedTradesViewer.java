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
        //this doesn't need SystemPresent since it is a presenter
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
    }

    public void viewCompletedTrades(){
        ArrayList<Trade> completedTrades = tradeManager.getCompletedTrades(currentUser);

        System.out.println("Here are all your completed trades: ");
        for(Trade t : completedTrades){
            //a toString method for trade will be added later
            System.out.println(t);
        }
        close();
    }

    public void viewFrequentTrader(){
        String [] frequentTrader = tradeManager.getFrequentTradePartners(currentUser);
        System.out.println("Here are your top most frequent trade partners:");
        for(int i = 0; i< frequentTrader.length; i++){
            System.out.println((i+1)+". "+ frequentTrader[i]);
        }
        close();
    }



    public void close(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}