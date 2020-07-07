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
     * Takes in a user and returns a list of all their ongoing Trades.
     *
     * @param user the user whose list of ongoing trades is being retrieved
     * @return a list of the given user's ongoing Trades
     */
    public ArrayList<Trade> getOngoingTrades(NormalUser user) {
        ArrayList<Trade> ongoingTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(user.getUsername()) && !t.getIsComplete()) {
                ongoingTrades.add(t);
            }
        }
        return ongoingTrades;
    }

    /**
     * Takes in a user and returns a list of all their completed Trades.
     *
     * @param user the user whose list of completed trades is being retrieved
     * @return a list of the given user's completed Trades
     */
    public ArrayList<Trade> getCompletedTrades(NormalUser user) {
        ArrayList<Trade> completedTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(user.getUsername()) && t.getIsComplete()) {
                completedTrades.add(t);
            }
        }
        return completedTrades;
    }

    //takes in a user and finds those top three most frequent trade partners
    public String [] getFrequentTradePartners (NormalUser user) {
        ArrayList<String> tradePartners = new ArrayList<>();
        Set<String> uniquePartner = new HashSet<>();
        String [] frequentPartners = new String[3];
        ArrayList<Trade> completedTrades= getCompletedTrades(user);
        if(completedTrades.isEmpty()){
            return new String[]{"", "", ""};
        }
        HashMap<Integer, String> freqToUsername = new HashMap<>();
        for (Trade t : completedTrades) {
            if (!t.getInvolvedUsernames()[0].equals(user.getUsername())){
                tradePartners.add(t.getInvolvedUsernames()[0]);
                uniquePartner.add(t.getInvolvedUsernames()[0]);
            }
            else{
                tradePartners.add(t.getInvolvedUsernames()[1]);
                uniquePartner.add(t.getInvolvedUsernames()[1]);
            }
        }
        for(String username : uniquePartner){
            freqToUsername.put(Collections.frequency(tradePartners, username), username);
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
