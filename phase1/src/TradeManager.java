import java.util.ArrayList;

/**
 * Stores and manages all Trades in the system.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-03
 */
public class TradeManager {
    private ArrayList<Trade> allTrades;

    /**
     * Class constructor.
     * Creates a new ArrayList of Trades and assigns its reference to allTrades.
     *
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
}
