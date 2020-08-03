package NormalUserFunctions;

import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.Item;
import Entities.Trade;
import Entities.TemporaryTrade;
import Entities.PermanentTrade;
import SystemFunctions.SystemPresenter;
import SystemFunctions.MenuItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Lets users see their ongoing trades that haven't been cancelled and edit/confirm trade meetings.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-08-01
 */
public class OngoingTradesViewer extends MenuItem {
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    /**
     * Creates a <OngoingTradesViewer></OngoingTradesViewer> with the given normal username,
     * item/user/trade managers, and notification system.
     * Lets users see their ongoing trades that haven't been cancelled and edit/confirm trade meetings.
     *
     * @param username     the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public OngoingTradesViewer(String username, ItemManager itemManager, UserManager userManager,
                               TradeManager tradeManager, NotificationSystem notifSystem) {
        currUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int indexInput;
        int choiceInput;
        List<Trade> ongoingTrades = tradeManager.getOngoingTrades(currUsername);
        List<Item[]> tradeItems = new ArrayList<>();


        for (Trade t : ongoingTrades) {
            String otherUsername = t.getOtherUsername(currUsername);
            long[] tempItemIDs = {t.getLentItemID(currUsername), t.getLentItemID(otherUsername)};
            Item[] tempItems = {itemManager.getItem(tempItemIDs[0]), itemManager.getItem(tempItemIDs[1])};
            tradeItems.add(tempItems);
        }

        systemPresenter.ongoingTrades(ongoingTrades, tradeItems, currUsername);

        if (!ongoingTrades.isEmpty()) {
            try {
                String temp = bufferedReader.readLine();
                while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > ongoingTrades.size()) {
                    temp = bufferedReader.readLine();
                }
                indexInput = Integer.parseInt(temp);

                if (indexInput != 0) {

                    Trade selectedTrade = ongoingTrades.get(indexInput - 1);

                    if (!selectedTrade.getHasAgreedMeeting()) {

                        /* print latest meeting suggestion */
                        systemPresenter.ongoingTrades(1, selectedTrade.getFirstMeetingDateTime(), selectedTrade);

                    } else {

                        /* print first meeting details */
                        systemPresenter.ongoingTrades(2, selectedTrade.getFirstMeetingDateTime(), selectedTrade);

                    }
                    if (selectedTrade instanceof TemporaryTrade) {
                        TemporaryTrade tempSelectedTrade = (TemporaryTrade) selectedTrade;
                        if (tempSelectedTrade.hasSecondMeeting()) {

                            /* print second meeting details */
                            systemPresenter.ongoingTrades(3, tempSelectedTrade.getSecondMeetingDateTime(), selectedTrade);

                        }
                    }

                    systemPresenter.ongoingTrades(1);

                    String temp2 = bufferedReader.readLine();
                    while (!temp.matches("[1-5]")) {
                        temp2 = bufferedReader.readLine();
                    }
                    choiceInput = Integer.parseInt(temp2);

                    switch (choiceInput) {

                        /* Edit meeting time and/or place */
                        case 1:

                            /* can't edit if already agreed upon */
                            if (selectedTrade.getHasAgreedMeeting()) {
                                systemPresenter.ongoingTrades(12);
                                break;
                            }

                            /* can't edit if latest suggestion is yours */
                            if (selectedTrade.getLastEditor().equals(currUsername)) {
                                systemPresenter.ongoingTrades(6);
                                break;
                            }

                            int editCount = selectedTrade.getUserEditCount(currUsername);
                            int editMax = userManager.getNormalUserMeetingEditMax(currUsername);
                            if (editCount < editMax) {
                                systemPresenter.ongoingTrades(editCount, (editCount + 1 == editMax));
                                systemPresenter.ongoingTrades(7);

                                LocalDateTime time = new DateTimeSuggestion(currUsername, userManager, tradeManager).suggestDateTime();

                                systemPresenter.ongoingTrades(8);
                                String placeSuggestion = bufferedReader.readLine();
                                while (placeSuggestion.isEmpty()) {
                                    systemPresenter.invalidInput();
                                    placeSuggestion = bufferedReader.readLine();
                                }
                                selectedTrade.setFirstMeetingDateTime(time);
                                selectedTrade.setFirstMeetingLocation(placeSuggestion);
                                selectedTrade.addUserEditCount(currUsername);

                                /* Notify other user of new suggestion */
                                userManager.getNotifHelper().basicUpdate
                                        ("NEW SUGGESTION", selectedTrade.getOtherUsername(currUsername), currUsername);

                                systemPresenter.ongoingTrades(11);
                            } else {
                                systemPresenter.ongoingTrades(9);
                            }
                            break;

                        /* Confirm this trade's current meeting time and place */
                        case 2:

                            /* can't confirm meeting if already agreed upon */
                            if (selectedTrade.getHasAgreedMeeting()) {
                                systemPresenter.ongoingTrades(5);
                                break;
                            }

                            /* can't confirm your own suggestion */
                            if (selectedTrade.getLastEditor().equals(currUsername)) {
                                systemPresenter.ongoingTrades(6);
                                break;
                            }

                            /* can't confirm a meeting time that has already passed */
                            if (LocalDateTime.now().isAfter(selectedTrade.getFirstMeetingDateTime())) {
                                systemPresenter.ongoingTrades(20);
                                break;
                            }

                            int weeklyTrade = tradeManager.getNumMeetingsThisWeek
                                    (currUsername, selectedTrade.getFirstMeetingDateTime().toLocalDate());
                            if (weeklyTrade > userManager.getNormalUserWeeklyTradeMax(currUsername)) {
                                systemPresenter.ongoingTrades(10);
                            } else {
                                selectedTrade.confirmAgreedMeeting();
                                systemPresenter.ongoingTrades(4);

                                /* Notify other user of meeting agreement */
                                userManager.getNotifHelper().basicUpdate
                                        ("MEETING AGREED", selectedTrade.getOtherUsername(currUsername), currUsername);
                            }
                            break;

                        /* Confirm the latest meeting took place */
                        case 3:

                            /* can't confirm transaction if no agreed meeting */
                            if (!selectedTrade.getHasAgreedMeeting()) {
                                systemPresenter.ongoingTrades(16);
                                break;
                            }

                            /* can't confirm more than once */
                            if (selectedTrade instanceof TemporaryTrade &&
                                    ((TemporaryTrade) selectedTrade).getUserSecondTransactionConfirmation(currUsername)) {
                                systemPresenter.ongoingTrades(18);
                                break;
                            } else if (selectedTrade.getUserFirstTransactionConfirmation(currUsername)) {
                                systemPresenter.ongoingTrades(18);
                                break;
                            }

                            LocalDateTime now = LocalDateTime.now();
                            if (selectedTrade instanceof TemporaryTrade && ((TemporaryTrade) selectedTrade).hasSecondMeeting() &&
                                    now.compareTo(((TemporaryTrade) selectedTrade).getSecondMeetingDateTime()) > 0) {

                                new ConfirmAndCloseTempTrade().confirmAndCloseTempTransaction
                                        (currUsername, (TemporaryTrade) selectedTrade, itemManager);

                                if (selectedTrade.getIsComplete()) {
                                    systemPresenter.ongoingTrades(14);

                                    /* Notify other user of temp trade closing */
                                    userManager.getNotifHelper().basicUpdate
                                            ("MEETING AGREED", selectedTrade.getOtherUsername(currUsername), currUsername);
                                } else {
                                    systemPresenter.ongoingTrades(3);

                                    /* Notify other user of this user confirming before them */
                                    userManager.getNotifHelper().basicUpdate
                                            ("CONFIRM BEFORE", selectedTrade.getOtherUsername(currUsername), currUsername);
                                }
                            } else if (selectedTrade instanceof TemporaryTrade && selectedTrade.getHasAgreedMeeting() &&
                                    now.compareTo(selectedTrade.getFirstMeetingDateTime()) > 0) {
                                selectedTrade.confirmFirstTransaction(currUsername);
                                if (((TemporaryTrade) selectedTrade).hasSecondMeeting()) {
                                    systemPresenter.ongoingTrades(15);
                                } else {
                                    systemPresenter.ongoingTrades(3);
                                }
                            } else if (selectedTrade instanceof PermanentTrade && selectedTrade.getHasAgreedMeeting() &&
                                    now.compareTo(selectedTrade.getFirstMeetingDateTime()) > 0) {
                                new ConfirmAndClosePermTrade().confirmAndClosePermTransaction(currUsername,
                                        (PermanentTrade) selectedTrade, itemManager, userManager);
                                if (selectedTrade.getIsComplete()) {
                                    systemPresenter.ongoingTrades(13);
                                } else {
                                    systemPresenter.ongoingTrades(3);
                                }
                            } else {
                                /* can't confirm transaction before its scheduled time */
                                systemPresenter.ongoingTrades(17);
                            }
                            break;

                        /* Cancel this trade (just cancels, doesn't contribute to possible freezing) */
                        case 4:

                            /* can't cancel if meeting already scheduled */
                            if (selectedTrade.getHasAgreedMeeting()) {
                                systemPresenter.ongoingTrades(19);
                                break;
                            }

                            selectedTrade.setIsCancelled();
                            long[] itemIDs = selectedTrade.getInvolvedItemIDs();
                            if (itemIDs[0] != 0) {
                                Item tempItem1 = itemManager.getItem(itemIDs[0]);
                                tempItem1.setAvailability(true);
                            }
                            if (itemIDs[1] != 0) {
                                Item tempItem2 = itemManager.getItem(itemIDs[1]);
                                tempItem2.setAvailability(true);
                            }
                            systemPresenter.ongoingTrades(2);
                            break;
                        case 5:
                            break;
                    }
                }
            } catch (IOException e) {
                systemPresenter.exceptionMessage();
            }
        }
        close();
    }

    private void close() {
        new NormalDashboard(userManager.getNormalByUsername(currUsername), itemManager, userManager, tradeManager, notifSystem);
    }

    @Override
    public String getTitle() {
        return "Ongoing Trades Viewer";
    }
}
