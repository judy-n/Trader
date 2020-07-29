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
 * last modified 2020-07-28
 */
public class OngoingTradesViewer extends MenuItem {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Creates a <OngoingTradesViewer></OngoingTradesViewer> with the given normal user and item/user/trade managers.
     * Lets users see their ongoing trades that haven't been cancelled and edit/confirm trade meetings.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public OngoingTradesViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter systemPresenter = new SystemPresenter();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int indexInput;
        int choiceInput;
        String currUsername = currentUser.getUsername();
        List<Trade> ongoingTrades = tradeManager.getOngoingTrades(currUsername);
        List<Item[]> tradeItems = new ArrayList<>();


        for (Trade t : ongoingTrades) {
            String otherUsername = t.getOtherUsername(currUsername);
            long[] tempItemIDs = {t.getLentItemID(currUsername), t.getLentItemID(otherUsername)};
            Item[] tempItems = {itemManager.getApprovedItem(tempItemIDs[0]), itemManager.getApprovedItem(tempItemIDs[1])};
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

                    Trade selected = ongoingTrades.get(indexInput - 1);

                    if (!selected.getHasAgreedMeeting1()) {

                        /* print latest meeting suggestion */
                        systemPresenter.ongoingTrades(1, selected.getMeetingDateTime1(), selected);

                    } else {

                        /* print first meeting details */
                        systemPresenter.ongoingTrades(2, selected.getMeetingDateTime1(), selected);

                    }
                    if (selected instanceof TemporaryTrade) {
                        TemporaryTrade tempSelected = (TemporaryTrade) selected;
                        if (tempSelected.hasSecondMeeting()) {

                            /* print second meeting details */
                            systemPresenter.ongoingTrades(3, tempSelected.getMeetingDateTime2(), selected);

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
                            if (selected.getHasAgreedMeeting1()) {
                                systemPresenter.ongoingTrades(12);
                                break;
                            }

                            /* can't edit if latest suggestion is yours */
                            if (selected.getLastEditor().equals(currUsername)) {
                                systemPresenter.ongoingTrades(6);
                                break;
                            }

                            int editCount = selected.getUserEditCount1(currUsername);
                            int editMax = currentUser.getMeetingEditMax();
                            if (editCount < editMax) {
                                systemPresenter.ongoingTrades(editCount, (editCount + 1 == editMax));
                                systemPresenter.ongoingTrades(7);

                                LocalDateTime time = new DateTimeSuggestion(currentUser, tradeManager).suggestDateTime();

                                systemPresenter.ongoingTrades(8);
                                String placeSuggestion = bufferedReader.readLine();
                                while (placeSuggestion.isEmpty()) {
                                    systemPresenter.invalidInput();
                                    placeSuggestion = bufferedReader.readLine();
                                }
                                selected.setMeetingDateTime1(time);
                                selected.setMeetingLocation1(placeSuggestion);
                                selected.addUserEditCount1(currUsername);
                                systemPresenter.ongoingTrades(11);
                            } else {
                                systemPresenter.ongoingTrades(9);
                            }
                            break;

                        /* Confirm this trade's current meeting time and place */
                        case 2:

                            /* can't confirm meeting if already agreed upon */
                            if (selected.getHasAgreedMeeting1()) {
                                systemPresenter.ongoingTrades(5);
                                break;
                            }

                            /* can't confirm your own suggestion */
                            if (selected.getLastEditor().equals(currUsername)) {
                                systemPresenter.ongoingTrades(6);
                                break;
                            }

                            /* can't confirm a meeting time that has already passed */
                            if (LocalDateTime.now().isAfter(selected.getMeetingDateTime1())) {
                                systemPresenter.ongoingTrades(19);
                                break;
                            }

                            int weeklyTrade = tradeManager.getNumMeetingsThisWeek(currUsername, selected.getMeetingDateTime1().toLocalDate());
                            if (weeklyTrade > currentUser.getWeeklyTradeMax()) {
                                systemPresenter.ongoingTrades(10);
                            } else {
                                selected.confirmAgreedMeeting1();
                                systemPresenter.ongoingTrades(4);
                            }
                            break;

                        /* Confirm the latest meeting took place */
                        case 3:

                            /* can't confirm transaction if no agreed meeting */
                            if (!selected.getHasAgreedMeeting1()) {
                                systemPresenter.ongoingTrades(15);
                                break;
                            }

                            /* can't confirm more than once */
                            if (selected instanceof TemporaryTrade &&
                                    ((TemporaryTrade) selected).getUserTransactionConfirmation2(currUsername)) {
                                systemPresenter.ongoingTrades(17);
                                break;
                            } else if (selected.getUserTransactionConfirmation1(currUsername)) {
                                systemPresenter.ongoingTrades(17);
                                break;
                            }

                            LocalDateTime now = LocalDateTime.now();
                            if (selected instanceof TemporaryTrade && ((TemporaryTrade) selected).hasSecondMeeting() &&
                                    now.compareTo(((TemporaryTrade) selected).getMeetingDateTime2()) > 0) {
                                new ConfirmAndCloseTempTrade().confirmAndCloseTempTransaction(currUsername,
                                        (TemporaryTrade) selected, itemManager);
                                if (selected.getIsComplete()) {
                                    systemPresenter.ongoingTrades(13);
                                } else {
                                    systemPresenter.ongoingTrades(3);
                                }
                            } else if (selected instanceof TemporaryTrade && selected.getHasAgreedMeeting1() &&
                                    now.compareTo(selected.getMeetingDateTime1()) > 0) {
                                selected.confirmTransaction1(currUsername);
                                if (((TemporaryTrade) selected).hasSecondMeeting()) {
                                    systemPresenter.ongoingTrades(14);
                                } else {
                                    systemPresenter.ongoingTrades(3);
                                }
                            } else if (selected instanceof PermanentTrade && selected.getHasAgreedMeeting1() &&
                                    now.compareTo(selected.getMeetingDateTime1()) > 0) {
                                new ConfirmAndClosePermTrade().confirmAndClosePermTransaction(currUsername,
                                        (PermanentTrade) selected, itemManager, userManager);
                                if (selected.getIsComplete()) {
                                    systemPresenter.ongoingTrades(13);
                                } else {
                                    systemPresenter.ongoingTrades(3);
                                }
                            } else {
                                /* can't confirm transaction before its scheduled time */
                                systemPresenter.ongoingTrades(16);
                            }
                            break;

                        /* Cancel this trade (just cancels, doesn't contribute to possible freezing) */
                        case 4:

                            /* can't cancel if meeting already scheduled */
                            if (selected.getHasAgreedMeeting1()) {
                                systemPresenter.ongoingTrades(18);
                                break;
                            }

                            selected.setIsCancelled();
                            long[] itemIDs = selected.getInvolvedItemIDs();
                            if (itemIDs[0] != 0) {
                                Item tempItem1 = im.getApprovedItem(itemIDs[0]);
                                tempItem1.setAvailability(true);
                            }
                            if (itemIDs[1] != 0) {
                                Item tempItem2 = im.getApprovedItem(itemIDs[1]);
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

    /**
     * Constructs an OngoingTradesViewer based on username rather than user (PLEASE USE THIS ONE INSTEAD)
     * @param currentUsername the username of the current user
     * @param im the item manager
     * @param um the user manager
     * @param tm the trade manager
     */
    public OngoingTradesViewer(String currentUsername, ItemManager im, UserManager um, TradeManager tm) {
        new OngoingTradesViewer(um.getNormalByUsername(currentUsername), im, um, tm);
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }

    @Override
    String getTitle() {
        return "Ongoing Trades Viewer";
    }
}
