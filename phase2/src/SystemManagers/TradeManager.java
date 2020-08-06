package SystemManagers;

import Entities.Trade;
import Entities.TemporaryTrade;
import Entities.PermanentTrade;
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
 * Stores and manages all <Trade></Trade>s in the system.
 *
 * @author Yingjia Liu
 * @author Ning Zhang
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-03
 */
public class TradeManager extends Manager implements Serializable {
    private List<Trade> allTrades;
    private List<String[]> cancelledUserPairs;
    private long TIME_LIMIT = 24; // # of hours allowed for confirming a transaction after it's scheduled time

    /**
     * Creates a <TradeManager></TradeManager>.
     * Initializes the empty lists of all trades and cancelled users.
     */
    public TradeManager() {
        allTrades = new ArrayList<>();
        cancelledUserPairs = new ArrayList<>();
    }

    /**
     * Getter for all trades in the system.
     *
     * @return a list of all trades in the system
     */
    public List<Trade> getAllTrades() {
        return allTrades;
    }

    /**
     * Getter for all temporary trades in the system.
     *
     * @return a list of all temporary trades in the system
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
     * @return a list of all ongoing trades that have not been cancelled
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
     * Getter for the number of times the given user has lent an item.
     *
     * @param username the username of the user to query
     * @return the number of times the given user has lent an item
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
     * Getter for the number of times the given user has borrowed an item.
     *
     * @param username the username of the user to query
     * @return the number of times the given user has borrowed an item
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
            if (t instanceof TemporaryTrade && t.getHasAgreedMeeting()) {
                TemporaryTrade temp = (TemporaryTrade) t;
                LocalDateTime meeting1 = temp.getFirstMeetingDateTime();
                if (now.compareTo(meeting1.plusHours(TIME_LIMIT)) > 0 && !temp.hasSecondMeeting()) {
                    t.setIsCancelled();
                    cancelledUserPairs.add(t.getInvolvedUsernames());
                } else if (now.compareTo(((TemporaryTrade) t).getSecondMeetingDateTime().plusHours(TIME_LIMIT)) > 0
                        && !t.getIsComplete()) {
                    t.setIsCancelled();
                    cancelledUserPairs.add(t.getInvolvedUsernames());
                }
            } else if (t instanceof PermanentTrade && t.getHasAgreedMeeting()) {
                if (now.compareTo(t.getFirstMeetingDateTime().plusHours(TIME_LIMIT)) > 0 && !t.getIsComplete()) {
                    t.setIsCancelled();
                    cancelledUserPairs.add(t.getInvolvedUsernames());
                }
            }
        }
    }

    /**
     * Getter for the list of user pairs with incomplete trades.
     *
     * @return the list of user pairs with incomplete trades
     */
    public List<String[]> getCancelledUserPairs() {
        return cancelledUserPairs;
    }

    /**
     * Clears all users from the list of users with cancelled trades.
     */
    public void clearCancelledUserPairs() {
        cancelledUserPairs.clear();
    }

    /**
     * Takes in an item ID and gets whether or not it's involved in a cancelled trade.
     *
     * @param itemID the ID of the item to query
     * @return true if the given item ID is involved in a cancelled trade, false otherwise
     */
    public boolean getItemInCancelledTrade(long itemID) {
        for (Trade t : allTrades) {
            if ((t.getInvolvedItemIDs()[0] == itemID || t.getInvolvedItemIDs()[1] == itemID) &&
                    t.getIsCancelled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for all permanent trades in the system.
     *
     * @return a list of all permanent trades in the system
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
     * Creates a new <TemporaryTrade></TemporaryTrade> with the given information,
     * then adds it to the list of all trades.
     *
     * @param usernames     an array containing the usernames of the two users involved in the new trade
     * @param itemIDs       an array containing the IDs of the items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for the new trade's meeting
     * @param firstLocation the first location suggested for the new trade's meeting
     */
    public void createTempTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        TemporaryTrade newTempTrade = new TemporaryTrade(usernames, itemIDs, firstDateTime, firstLocation);
        allTrades.add(newTempTrade);
    }

    /**
     * Creates a new <PermanentTrade></PermanentTrade> with the given information,
     * then adds it to the list of all trades.
     *
     * @param usernames     an array containing the usernames of the two users involved in the new trade
     * @param itemIDs       an array containing the IDs of the items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for the new trade's meeting
     * @param firstLocation the first location suggested for the new trade's meeting
     */
    public void createPermTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        PermanentTrade newPermTrade = new PermanentTrade(usernames, itemIDs, firstDateTime, firstLocation);
        allTrades.add(newPermTrade);
    }

    /**
     * Removes the given trade from the list of all Trades.
     *
     * @param tradeToRemove the trade being removed
     */
    public void removeTrade(Trade tradeToRemove) {
        allTrades.remove(tradeToRemove);
    }

    /**
     * Takes in a username and returns a list of all their ongoing trades.
     *
     * @param username the username of the user whose list of ongoing trades is being retrieved
     * @return a list of the given user's ongoing trades
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
     * Getter for all completed trades for the given user.
     *
     * @param username the username of the user whose completed trades are being retrieved
     * @return a list of the given user's completed trades
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
     * Getter for all trades (completed, incomplete, and cancelled) for the given user.
     *
     * @param username the username of the user whose trades are being retrieved
     * @return a list of the given user's trades
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
            if (t.getHasAgreedMeeting()) {
                LocalDate meeting1 = t.getFirstMeetingDateTime().toLocalDate();
                if (meeting1.isAfter(dayBeforeWeek) && meeting1.isBefore(dayAfterWeek)) {
                    count++;
                }
            }
            if (t instanceof TemporaryTrade && ((TemporaryTrade) t).hasSecondMeeting()) {
                LocalDate meeting2 = ((TemporaryTrade) t).getSecondMeetingDateTime().toLocalDate();
                if (meeting2.isAfter(dayBeforeWeek) && meeting2.isBefore(dayAfterWeek)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Takes in a username and returns the associated user's top 3 most frequent trading partners.
     *
     * @param username the username of the user to query
     * @return a list of the usernames of the three users this user trades with most frequently
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
