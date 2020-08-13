package NormalUserFunctions;

import SystemFunctions.DateTimeHandler;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Trade;
import Entities.TemporaryTrade;
import Entities.PermanentTrade;
import SystemFunctions.SystemPresenter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps let users see their ongoing trades and edit/confirm/cancel trade meetings.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-09
 */
public class OngoingTradesViewer {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter systemPresenter;
    private DateTimeHandler dateTimeHandler;

    /**
     * Creates a <OngoingTradesViewer></OngoingTradesViewer> with the given
     * normal username and item/user/trade managers.
     *
     * @param username     the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     */
    public OngoingTradesViewer(String username, ItemManager itemManager, UserManager userManager,
                               TradeManager tradeManager) {
        currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.systemPresenter = new SystemPresenter();
        dateTimeHandler = new DateTimeHandler();
    }

    /**
     * Returns an array of string representations of the current user's ongoing trades.
     *
     * @return an array of string representations of the current user's ongoing trades
     */
    public String[] getOngoingTrades() {
        List<Trade> ongoingTrades = tradeManager.getOngoingTrades(currUsername);
        List<String[]> tradeItemNames = new ArrayList<>();
        List<long[]> tradeItemIDs = new ArrayList<>();

        for (Trade t : ongoingTrades) {
            String otherUsername = t.getOtherUsername(currUsername);
            long[] tempItemIDs = {t.getLentItemID(currUsername), t.getLentItemID(otherUsername)};

            String[] tempItemNames = new String[2];
            if (tempItemIDs[0] != 0) {
                tempItemNames[0] = itemManager.getItemName(tempItemIDs[0]);
            }
            if (tempItemIDs[1] != 0) {
                tempItemNames[1] = itemManager.getItemName(tempItemIDs[1]);
            }

            tradeItemNames.add(tempItemNames);
            tradeItemIDs.add(tempItemIDs);
        }
        return systemPresenter.getOngoingTradeStrings
                (ongoingTrades, tradeItemIDs, tradeItemNames, currUsername, dateTimeHandler);
    }

    /**
     * Takes in the index of an ongoing trade and determines if the user is allowed to edit its meeting details.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return an error message iff the user is not allowed to edit the meeting details, an empty string otherwise
     */
    public int canEditMeeting(int index) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);

        /* can't edit if already agreed upon */
        if (selectedTrade.getHasAgreedMeeting()) {
            return 15;
        }

        /* can't edit if latest suggestion is yours */
        if (selectedTrade.getLastEditor().equals(currUsername)) {
            return 9;
        }

        /* can't edit if you've reached the max # of edits allowed */
        int editCount = selectedTrade.getUserEditCount(currUsername);
        int editMax = userManager.getThresholdSystem().getMeetingEditMax();
        if (editCount == editMax) {
            return 12;
        }
        return 0;
    }

    /**
     * Displays the number of times the current user has edited the meeting details
     * of the ongoing trade at the given index.
     * Also warns them if their current attempt to edit is their last.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return the string to display
     */
    public String displayNumOfEdits(int index) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);
        int editCount = selectedTrade.getUserEditCount(currUsername);
        int editMax = userManager.getThresholdSystem().getMeetingEditMax();
        return systemPresenter.ongoingTrades(editCount, (editCount + 1 == editMax));
    }

    /**
     * Checks if the date/time and location suggested by the user are valid.
     *
     * @param suggestedDateTime the date and time suggested by the user
     * @param suggestedLocation the location suggested by the user
     * @return an error message iff the date/time or location is not valid, an empty string otherwise
     */
    public int validateSuggestion(String suggestedDateTime, String suggestedLocation) {
        return new TradeMeetingSuggestionValidator
                (currUsername, userManager, tradeManager, dateTimeHandler)
                .validateSuggestion(suggestedDateTime, suggestedLocation);
    }

    /**
     * Sets the date/time and location of the ongoing trade at the given index to the given date/time and location.
     * Notifies the other user in the trade about the new suggestion.
     *
     * @param index         the index of the ongoing trade whose meeting details are being modified
     * @param validDateTime a valid date and time suggested by the user
     * @param validLocation a valid location suggested by the user
     */
    public void setMeeting(int index, String validDateTime, String validLocation) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);
        String traderUsername = selectedTrade.getOtherUsername(currUsername);

        selectedTrade.setFirstMeetingDateTime(dateTimeHandler.getLocalDateTime(validDateTime));
        selectedTrade.setFirstMeetingLocation(validLocation);
        selectedTrade.addUserEditCount(currUsername);

        /* Notify other user of new suggestion */
        userManager.notifyUser(traderUsername).basicUpdate
                ("NEW SUGGESTION", traderUsername, currUsername);
    }

    /**
     * Checks if the current user is allowed to agree with the meeting of the ongoing trade at the given index,
     * then agrees with the meeting if allowed.
     * Notifies the other user in the trade about the meeting agreement.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return an error message iff the user is not allowed to agree with the meeting, an empty string otherwise
     */
    public int agreeMeeting(int index) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);
        String traderUsername = selectedTrade.getOtherUsername(currUsername);

        /* can't agree with meeting if already agreed upon */
        if (selectedTrade.getHasAgreedMeeting()) {
            return 24;
        }

        /* can't agree with your own suggestion */
        if (selectedTrade.getLastEditor().equals(currUsername)) {
            return 9;
        }

        /* can't agree with a meeting time that has already passed */
        if (LocalDateTime.now().isAfter(selectedTrade.getFirstMeetingDateTime())) {
            return 25;
        }

        int weeklyTrade = tradeManager.getNumMeetingsThisWeek
                (currUsername, selectedTrade.getFirstMeetingDateTime().toLocalDate());

        /* can't agree with meeting that falls in week that already has max number of meetings allowed */
        if (weeklyTrade == userManager.getThresholdSystem().getWeeklyTradeMax()) {
            return 26;
        }

        selectedTrade.confirmAgreedMeeting();

        /* Notify other user of meeting agreement */
        userManager.notifyUser(traderUsername).basicUpdate
                ("MEETING AGREED", traderUsername, currUsername);

        return 0;
    }

    /**
     * Takes in the index of an ongoing trade and determines if the user is allowed
     * to confirm the latest transaction of the trade.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return an error message iff the user is not allowed to confirm the latest transaction,
     * an empty string otherwise
     */
    public int canConfirmLatestTransaction(int index) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);

        /* can't confirm transaction if no agreed meeting */
        if (!selectedTrade.getHasAgreedMeeting()) {
            return 19;
        }

        /* can't confirm more than once */
        if ((selectedTrade instanceof TemporaryTrade &&
                ((TemporaryTrade) selectedTrade).hasSecondMeeting() &&
                ((TemporaryTrade) selectedTrade).getUserSecondTransactionConfirmation(currUsername)) ||
                (selectedTrade instanceof TemporaryTrade && !((TemporaryTrade) selectedTrade).hasSecondMeeting()
                        && selectedTrade.getUserFirstTransactionConfirmation(currUsername)) ||
                (selectedTrade instanceof PermanentTrade &&
                        selectedTrade.getUserFirstTransactionConfirmation(currUsername)))  {
            return 21;
        }

        LocalDateTime now = LocalDateTime.now();
        if (selectedTrade instanceof TemporaryTrade && ((TemporaryTrade) selectedTrade).hasSecondMeeting() &&
                now.compareTo(((TemporaryTrade) selectedTrade).getSecondMeetingDateTime()) > 0) {
            return 0;

        } else if (selectedTrade instanceof TemporaryTrade && selectedTrade.getHasAgreedMeeting() &&
                now.compareTo(selectedTrade.getFirstMeetingDateTime()) > 0) {
            return 0;

        } else if (selectedTrade instanceof PermanentTrade && selectedTrade.getHasAgreedMeeting() &&
                now.compareTo(selectedTrade.getFirstMeetingDateTime()) > 0) {
            return 0;

        } else {
            /* can't confirm transaction before its scheduled time */
            return 20;
        }
    }

    /**
     * Confirms the latest transaction of the ongoing trade at the given index, then returns a
     * message letting the user know the results of their confirmation.
     * Notifies the other user in the trade about the confirmation.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return a message letting the user know the results of their confirmation
     */
    public int confirmLatestTransaction(int index) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);
        String traderUsername = selectedTrade.getOtherUsername(currUsername);

        if (selectedTrade instanceof TemporaryTrade && ((TemporaryTrade) selectedTrade).hasSecondMeeting()) {

            // confirm second transaction of a temporary trade
            new ConfirmAndCloseTempTrade().confirmAndCloseTempTransaction
                    (currUsername, (TemporaryTrade) selectedTrade, itemManager);

            if (selectedTrade.getIsComplete()) {
                /* Notify other user of temp trade closing */
                userManager.notifyUser(traderUsername).basicUpdate
                        ("CONFIRM TEMP TRADE SECOND TRANSACTION AFTER", traderUsername, currUsername);
                return 17;
            }
        } else if (selectedTrade instanceof TemporaryTrade) {

            // confirm first transaction of a temporary trade
            selectedTrade.confirmFirstTransaction(currUsername);

            if (((TemporaryTrade) selectedTrade).hasSecondMeeting()) {
                /* Notify other user of temp trade first transaction closing */
                userManager.notifyUser(traderUsername).basicUpdate
                        ("CONFIRM TEMP TRADE FIRST TRANSACTION AFTER", traderUsername, currUsername);
                return 18;
            }
        } else {

            // confirm transaction of a permanent trade
            new ConfirmAndClosePermTrade().confirmAndClosePermTransaction
                    (currUsername, (PermanentTrade) selectedTrade, itemManager, userManager);

            if (selectedTrade.getIsComplete()) {
                /* Notify other user of perm trade closing */
                userManager.notifyUser(traderUsername).basicUpdate
                        ("CONFIRM PERM TRADE AFTER", traderUsername, currUsername);
                return 16;
            }
        }

        // If the code reaches this point, it means that the current user has confirmed
        // the latest transaction before the other user in the trade has.

        /* Notify other user of current user confirming before them */
        userManager.notifyUser(traderUsername).basicUpdate
                ("CONFIRM BEFORE", traderUsername, currUsername);

        return 23;
    }

    /**
     * Checks if the current user is allowed to cancel the ongoing trade at the given index,
     * then cancels it if allowed.
     * Notifies the other user in the trade about the cancellation.
     *
     * @param index the index of the ongoing trade selected by the user
     * @return an error message iff the user is not allowed to cancel the trade, an empty string otherwise
     */
    public boolean cancelTrade(int index) {
        Trade selectedTrade = tradeManager.getOngoingTrades(currUsername).get(index);
        String traderUsername = selectedTrade.getOtherUsername(currUsername);

        /* can't cancel if meeting already scheduled */
        if (selectedTrade.getHasAgreedMeeting()) {
            return false;
        }

        // Since the trade is cancelled before the first meeting is scheduled,
        // we know the users still have their respective items.
        // Therefore, we can automatically make their items available for trade again.
        long[] itemIDs = selectedTrade.getInvolvedItemIDs();
        if (itemIDs[0] != 0) {
            itemManager.setItemAvailability(itemIDs[0], true);
        }
        if (itemIDs[1] != 0) {
            itemManager.setItemAvailability(itemIDs[1], true);
        }

        selectedTrade.setIsCancelled();

        /* Notify the other user of cancelled trade */
        String itemName;
        if (selectedTrade.getLentItemID(traderUsername) != 0) {
            itemName = itemManager.getItemName(selectedTrade.getLentItemID(traderUsername));
        } else {
            itemName = itemManager.getItemName(selectedTrade.getLentItemID(currUsername));
        }
        userManager.notifyUser(traderUsername).itemUpdate
                ("TRADE CANCELLED", traderUsername, currUsername, itemName);

        return true;
    }
}
