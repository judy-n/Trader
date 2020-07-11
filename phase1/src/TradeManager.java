import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
 * last modified 2020-07-11
 */
public class TradeManager implements Serializable {
    private List<Trade> allTrades;
    private List<String> cancelledUsers;

    /**
     * Class constructor.
     * Creates a new list of Trades and assigns its reference to allTrades.
     */
    public TradeManager() {
        allTrades = new ArrayList<>();
        cancelledUsers = new ArrayList<>();
    }

    /**
     * Getter for all Trades in the system.
     *
     * @return a list of all Trades in the system
     */
    public List<Trade> getAllTrades() {
        return allTrades;
    }

    /**
     * Getter for all temporary trades in the system.
     *
     * @return a list of all TemporaryTrades in the system
     */
    public List<TemporaryTrade> getAllTempTrades() {
        List<TemporaryTrade> allTempTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t instanceof TemporaryTrade) {
                allTempTrades.add((TemporaryTrade) t);
            }
        }
        return allTempTrades;
    }

    /**
     * Getter for all of the ongoing trades that have not been cancelled.
     *
     * @return all ongoing trades that have not been cancelled
     */
    private List<Trade> getAllOngoingNotCancelledTrades() {
        List<Trade> allOngoingNotCancelledTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (!t.getIsComplete() && !t.getIsCancelled()) {
                allOngoingNotCancelledTrades.add(t);
            }
        }
        return allOngoingNotCancelledTrades;
    }

    /**
     * Getter for the number of times a user has lent an item.
     *
     * @param username the user to query
     * @return the number of times this user has lent an item
     */
    public int getTimesLent(String username) {
        int timesLent = 0;
        List<Trade> trades = getCompletedTrades(username);
        trades.addAll(getAllOngoingNotCancelledTrades());
        for (Trade t : trades) {
            if (t.getLentItemID(username) != 0) {
                timesLent++;
            }
        }
        return timesLent;
    }

    /**
     * Getter for the number of times a user has borrowed an item.
     *
     * @param username the user to query
     * @return the number of times this user has borrowed an item
     */
    public int getTimesBorrowed(String username) {
        int timesBorrowed = 0;
        List<Trade> trades = getCompletedTrades(username);
        trades.addAll(getAllOngoingNotCancelledTrades());
        for (Trade t : trades) {
            if (t.getLentItemID(t.getOtherUsername(username)) != 0) {
                timesBorrowed++;
            }
        }
        return timesBorrowed;
    }

    /**
     * Cancels all unconfirmed trades. This is when two users have scheduled a meeting for their
     * transaction but at least one of them has not yet confirmed the transaction within one day
     * of the meeting date/time.
     */
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


    /**
     * Adds users to cancelledUsers
     *
     * @param users the user to be added
     */
    public void addCancelledUsers(String[] users) {
        cancelledUsers.add(users[0]);
        cancelledUsers.add(users[1]);
    }

    /**
     * Getter for cancelledUsers
     *
     * @return cancelledUsers the cancelled users
     */
    public List<String> getCancelledUsers() {
        return cancelledUsers;
    }

    /**
     * Clears all users from cancelledUsers
     *
     */
    public void clearCancelledUsers() {
        cancelledUsers.clear();
    }

    /**
     * Takes in an item and gets whether or not it's involved in a cancelled trade.
     *
     * @param item the item to query
     * @return true if the given item is involved in a cancelled trade, false otherwise
     */
    public boolean getItemInCancelledTrade(Item item) {
        for (Trade t : allTrades) {
            if ((t.getInvolvedItemIDs()[0] == item.getID() || t.getInvolvedItemIDs()[1] == item.getID()) &&
                    t.getIsCancelled()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Getter for all permanent trades in the system.
     *
     * @return a list of all PermanentTrades in the system
     */
    public List<PermanentTrade> getAllPermTrades() {
        List<PermanentTrade> allPermTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t instanceof PermanentTrade) {
                allPermTrades.add((PermanentTrade) t);
            }
        }
        return allPermTrades;
    }

    /**
     * Adds the given Trade to the list of all Trades.
     *
     * @param tradeToAdd the Trade being added
     */
    public void addTrade(Trade tradeToAdd) {
        allTrades.add(tradeToAdd);
    }

    /**
     * Removes the given Trade from the list of all Trades.
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
    public List<Trade> getOngoingTrades(String username) {
        List<Trade> ongoingTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(username) && !t.getIsComplete() && !t.getIsCancelled()) {
                ongoingTrades.add(t);
            }
        }
        return ongoingTrades;
    }

    /**
     * Getter for all completed trades for a specific user
     *
     * @param username the username of the user
     * @return completedTrades this user's completed trades
     */
    private List<Trade> getCompletedTrades(String username) {
        List<Trade> completedTrades = new ArrayList<>();
        for (Trade t : allTrades) {
            if (t.isInvolved(username) && t.getIsComplete()) {
                completedTrades.add(t);
            }
        }
        return completedTrades;
    }

    /**
     * Getter for all trades (completed & incomplete) by a specific user
     *
     * @param username this user's username
     * @return allTradesThisUser all of this user's trades
     */
    private List<Trade> getAllTrades(String username) {
        List<Trade> allTradesThisUser = new ArrayList<>();
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
        List<Trade> completedTrades = getCompletedTrades(username);

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
        List<Trade> allTradesThisUser = getAllTrades(username);
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

    /**
     * Takes in a username and returns the associated user's top 3 most frequent trading partners
     *
     * @param username the queried user's username
     * @return A list of the usernames of the three users this user trades with most frequently
     */
    //takes in a user and finds those top three most frequent trade partners
    public String[] getFrequentTradePartners(String username) {
        List<String> tradePartners = new ArrayList<>();
        Set<String> uniquePartner = new HashSet<>();
        String[] frequentPartners = new String[]{"empty", "empty", "empty"};

        List<Integer> frequency = new ArrayList<>();
        HashMap<String, Integer> freqToUsername = new HashMap<>();
        List<String> sortedUsers = new ArrayList<>();

        List<Trade> completedTrades = getCompletedTrades(username);
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
