import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Stores and manages all Trades in the system.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-06
 */
public class TradeManager {
    private ArrayList<Trade> allTrades;

    /**
     * Class constructor.
     * Creates a new ArrayList of Trades and assigns its reference to allTrades.
     */
    public TradeManager() {
        allTrades = new ArrayList<>();
    }

    /**
     * Getter for all Trades in the system.
     *
     * @return an ArrayList of all Trade in the system
     */
    public ArrayList<Trade> getAllTrades() {
        return allTrades;
    }

    /**
     * Getter for all temporary trades in the system.
     *
     * @return an ArrayList of all TemporaryTrades in the system
     */
    public ArrayList<TemporaryTrade> getAllTempTrades() {
        ArrayList<TemporaryTrade> allTempTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t instanceof TemporaryTrade) {
                allTempTrades.add((TemporaryTrade) t);
            }
        }
        return allTempTrades;
    }

    /**
     * Getter for all permanent trades in the system.
     *
     * @return an ArrayList of all PermanentTrades in the system
     */
    public ArrayList<PermanentTrade> getAllPermTrades() {
        ArrayList<PermanentTrade> allPermTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t instanceof PermanentTrade) {
                allPermTrades.add((PermanentTrade) t);
            }
        }
        return allPermTrades;
    }

    /**
     * Adds the given Trade to the ArrayList of all Trades.
     *
     * @param tradeToAdd the Trade being added
     */
    public void addTrade(Trade tradeToAdd) {
        allTrades.add(tradeToAdd);
    }

    /**
     * Removes the given Trade from the ArrayList of all Trades.
     *
     * @param tradeToRemove the Trade being removed
     */
    public void removeTrade(Trade tradeToRemove) {
        allTrades.remove(tradeToRemove);
    }

    /**
     * Takes in a username and returns a list of all their ongoing Trades.
     *
     * @param username the username of the user whose list of ongoing trades is being retrieved
     * @return a list of the given user's ongoing Trades
     */
    public ArrayList<Trade> getOngoingTrades(String username) {
        ArrayList<Trade> ongoingTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(username) && !t.getIsComplete()) {
                ongoingTrades.add(t);
            }
        }
        return ongoingTrades;
    }

    private ArrayList<Trade> getCompletedTrades(String username) {
        ArrayList<Trade> completedTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(username) && t.getIsComplete()) {
                completedTrades.add(t);
            }
        }
        return completedTrades;
    }

    /**
     * Takes in a username and returns a list of all their three most recent trades.
     * The trade at index 0 of the list is the most recent trade.
     *
     * @param username the username of the user whose three most recent trades are being retrieved
     * @return a list of the given user's three most recent trades
     */
    public Trade[] getRecentThreeTrades(String username) {
        Trade[] recentThree = new Trade[3];
        ArrayList<Trade> completedTrades = getCompletedTrades(username);

        for (int i = 0; i < 3; i++) {
            if (!completedTrades.isEmpty()) {
                Trade tempRecent = Collections.max(completedTrades);
                recentThree[i] = tempRecent;
                completedTrades.remove(tempRecent);
            }
        }
        return recentThree;
    }

    //takes in a user and finds those top three most frequent trade partners
    public String [] getFrequentTradePartners (String username) {
        ArrayList<String> tradePartners = new ArrayList<>();
        Set<String> uniquePartner = new HashSet<>();
        String [] frequentPartners = new String[3];
        ArrayList<Trade> completedTrades= getCompletedTrades(username);
        if(completedTrades.isEmpty()){
            return new String[]{"", "", ""};
        }
        HashMap<Integer, String> freqToUsername = new HashMap<>();
        for (Trade t : completedTrades) {
            if (!t.getInvolvedUsernames()[0].equals(username)){
                tradePartners.add(t.getInvolvedUsernames()[0]);
                uniquePartner.add(t.getInvolvedUsernames()[0]);
            }
            else{
                tradePartners.add(t.getInvolvedUsernames()[1]);
                uniquePartner.add(t.getInvolvedUsernames()[1]);
            }
        }
        for(String u : uniquePartner){
            freqToUsername.put(Collections.frequency(tradePartners, u), u);
        }
        Integer [] freq = (Integer[]) freqToUsername.keySet().toArray();
        Arrays.sort(freq, Collections.reverseOrder());

        if(freq.length == 1){
            return new String[]{freqToUsername.get(freq[0]), "", ""};
        }else if(freq.length == 2){
            return new String[]{freqToUsername.get(freq[0]), freqToUsername.get(freq[1]), ""};
        }else {
            int index = 0;
            while (index < 3) {
                for (Integer i : freq) {
                    frequentPartners[index] = freqToUsername.get(i);
                    index++;
                }
            }
        }
        return frequentPartners;
    }
}
