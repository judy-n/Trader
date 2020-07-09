import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

/**
 * Stores and manages all Trades in the system.
 *
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-08
 */
public class TradeManager {
    private ArrayList<Trade> allTrades;
    private ArrayList<String> cancelledUsers;

    /**
     * Class constructor.
     * Creates a new ArrayList of Trades and assigns its reference to allTrades.
     */
    public TradeManager() {
        allTrades = new ArrayList<>();
        cancelledUsers = new ArrayList<>();
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

    private ArrayList<Trade> getAllOngoingNotCancelledTrades() {
        ArrayList<Trade> allOngoingNotCancelledTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (!t.getIsComplete() && !t.getIsCancelled()) {
                allOngoingNotCancelledTrades.add(t);
            }
        }
        return allOngoingNotCancelledTrades;
    }

    public void cancelAllUnconfirmedTrades() {
        LocalDateTime now = LocalDateTime.now();
        for (Trade t : getAllOngoingNotCancelledTrades()) {
            if (t instanceof TemporaryTrade && t.getHasAgreedMeeting1()) {
                TemporaryTrade temp = (TemporaryTrade) t;
                LocalDateTime meeting1 = temp.getMeetingDateTime1();
                if (now.compareTo(meeting1.plusDays(1)) > 0 && !temp.hasSecondMeeting()) {
                    t.setIsCancelled();
                    addCancelledUsers(t.getInvolvedUsernames());
                } else if (now.compareTo(((TemporaryTrade) t).getMeetingDateTime2().plusDays(1)) > 0
                        && !t.getIsComplete()) {
                    t.setIsCancelled();
                    addCancelledUsers(t.getInvolvedUsernames());
                }
            } else if (t instanceof PermanentTrade && t.getHasAgreedMeeting1()) {
                if (now.compareTo(t.getMeetingDateTime1().plusDays(1)) > 0 && !t.getIsComplete()) {
                    t.setIsCancelled();
                    addCancelledUsers(t.getInvolvedUsernames());
                }
            }
        }
    }

    public void addCancelledUsers(String[] users) {
        cancelledUsers.add(users[0]);
        cancelledUsers.add(users[1]);
    }

    public ArrayList<String> getCancelledUsers() {
        return cancelledUsers;
    }

    public void clearCancelledUsers(){
        cancelledUsers.clear();
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

    private ArrayList<Trade> getAllTrades(String username) {
        ArrayList<Trade> allTradesThisUser = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(username)) {
                allTradesThisUser.add(t);
            }
        }
        return allTradesThisUser;
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

    /**
     * Returns the total number of meetings planned to occur in the same week as the given date for the given user.
     * Only counting agreed-upon meetings that are taking place / took place during that week.
     * One week = Monday to Sunday.
     *
     * @param username the username of the user whose meeting count for the given week is being retrieved
     * @param date     the date for which meetings in the same week are being search for
     * @return the total number of meetings planned to occur in the same week as the given date for the given user
     */
    public int getNumMeetingsThisWeek(String username, LocalDate date) {
        ArrayList<Trade> allTradesThisUser = getAllTrades(username);
        int dow = date.getDayOfWeek().getValue();
        LocalDate dayBeforeWeek = date.minusDays(dow);
        LocalDate dayAfterWeek = date.plusDays(7 - dow + 1);

        int count = 0;
        for (Trade t : allTradesThisUser) {
            if (t.getHasAgreedMeeting1()) {
                LocalDate meeting1 = t.getMeetingDateTime1().toLocalDate();
                if (meeting1.isAfter(dayBeforeWeek) && meeting1.isBefore(dayAfterWeek)) {
                    count++;
                }
            }
            if (t instanceof TemporaryTrade && ((TemporaryTrade) t).hasSecondMeeting()) {
                LocalDate meeting2 = ((TemporaryTrade) t).getMeetingDateTime2().toLocalDate();
                if (meeting2.isAfter(dayBeforeWeek) && meeting2.isBefore(dayAfterWeek)) {
                    count++;
                }
            }
        }
        return count;
    }

    //takes in a user and finds those top three most frequent trade partners
    public String[] getFrequentTradePartners(String username) {
        ArrayList<String> tradePartners = new ArrayList<>();
        Set<String> uniquePartner = new HashSet<>();
        String[] frequentPartners = new String[]{"empty", "empty", "empty"};

        ArrayList<Integer> frequency = new ArrayList<>();
        HashMap<String, Integer> freqToUsername = new HashMap<>();
        ArrayList<String> sortedUsers = new ArrayList<>();

        ArrayList<Trade> completedTrades = getCompletedTrades(username);
        if (completedTrades.isEmpty()) {
            return frequentPartners;
        }
        for (Trade t : completedTrades) {
            if (!t.getInvolvedUsernames()[0].equals(username)) {
                tradePartners.add(t.getInvolvedUsernames()[0]);
                uniquePartner.add(t.getInvolvedUsernames()[0]);
            } else {
                tradePartners.add(t.getInvolvedUsernames()[1]);
                uniquePartner.add(t.getInvolvedUsernames()[1]);
            }
        }
        for (String u : uniquePartner) {
            freqToUsername.put(u, Collections.frequency(tradePartners, u));
            frequency.add(Collections.frequency(tradePartners, u));
        }
        frequency.sort(Collections.reverseOrder());

        for (Map.Entry<String, Integer> e : freqToUsername.entrySet()) {
            for (Integer integer : frequency) {
                if (e.getValue().equals(integer)) {
                    if (!sortedUsers.contains(e.getKey())) {
                        sortedUsers.add(e.getKey());
                    }
                }
            }
        }
        if (sortedUsers.size() == 1) {
            frequentPartners[0] = sortedUsers.get(0);
        } else if (sortedUsers.size() == 2) {
            frequentPartners[0] = sortedUsers.get(0);
            frequentPartners[1] = sortedUsers.get(1);
        } else {
            frequentPartners[0] = sortedUsers.get(0);
            frequentPartners[1] = sortedUsers.get(1);
            frequentPartners[2] = sortedUsers.get(2);
        }
        return frequentPartners;
    }
}
