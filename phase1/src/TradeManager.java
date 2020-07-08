import java.util.*;

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
        String [] frequentPartners = new String[]{"no one yet", "no one yet", "no one yet"};

        ArrayList<Integer> frequency  = new ArrayList<>();
        HashMap<String, Integer> freqToUsername = new HashMap<>();
        ArrayList<String> sortedUsers = new ArrayList<>();

        ArrayList<Trade> completedTrades= getCompletedTrades(username);
        if(completedTrades.isEmpty()){
            return frequentPartners;
        }
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
            freqToUsername.put(u, Collections.frequency(tradePartners, u));
            frequency.add(Collections.frequency(tradePartners,u));
        }
        frequency.sort(Collections.reverseOrder());

        for(Map.Entry<String, Integer> e : freqToUsername.entrySet()){
            for (Integer integer : frequency) {
                if (e.getValue().equals(integer)) {
                    if (!sortedUsers.contains(e.getKey())) {
                        sortedUsers.add(e.getKey());
                    }
                }
            }
        }
        if(sortedUsers.size() == 1){
            frequentPartners[0] = sortedUsers.get(0);
        }else if(sortedUsers.size() == 2){
            frequentPartners[0] = sortedUsers.get(0);
            frequentPartners[1] = sortedUsers.get(1);
        }else{
            frequentPartners[0] = sortedUsers.get(0);
            frequentPartners[1] = sortedUsers.get(1);
            frequentPartners[2] = sortedUsers.get(2);
        }
        return frequentPartners;
    }
}
