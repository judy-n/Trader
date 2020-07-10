import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Shows all the ongoing trades for the user.
 *
 * @author Kushagra Mehta
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-09
 */
public class OngoingTradesViewer {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Creates a OngoingTradesViewer for users to see their incompleted trades
     * @param user current non admin user
     * @param im the system's item manager
     * @param um the system's user manager
     * @param tm the system's trade manager
     */

    public OngoingTradesViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int indexInput;
        int choiceInput;
        String currUsername = currentUser.getUsername();
        ArrayList<Trade> ongoingTrades = tradeManager.getOngoingTrades(currUsername);
        ArrayList<Item[]> tradeItems = new ArrayList<>();

        for (Trade t : ongoingTrades) {
            String otherUsername = t.getOtherUsername(currUsername);
            long[] tempItemIDs = {t.getLentItemID(currUsername), t.getLentItemID(otherUsername)};
            Item[] tempItems = {itemManager.getApprovedItem(tempItemIDs[0]), itemManager.getApprovedItem(tempItemIDs[1])};
            tradeItems.add(tempItems);
        }

        sp.ongoingTrades(ongoingTrades, tradeItems, currUsername);

        try {
            String temp = br.readLine();
            while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > ongoingTrades.size()) {
                temp = br.readLine();
            }
            indexInput = Integer.parseInt(temp);

            if (indexInput != 0) {
                Trade selected = ongoingTrades.get(indexInput - 1);
                sp.ongoingTrades(1);
                String temp2 = br.readLine();
                while (!temp.matches("[1-5]")) {
                    temp2 = br.readLine();
                }
                choiceInput = Integer.parseInt(temp2);

                if (!selected.getHasAgreedMeeting1()) { //print latest meeting suggestion
                    sp.ongoingTrades(1, selected.getMeetingDateTime1(), selected);
                } else {    //print first meeting details
                    sp.ongoingTrades(2, selected.getMeetingDateTime1(), selected);
                }
                if (selected instanceof TemporaryTrade) {
                    TemporaryTrade tempSelected = (TemporaryTrade) selected;
                    if (tempSelected.hasSecondMeeting()) {  //print second meeting details
                        sp.ongoingTrades(3, tempSelected.getMeetingDateTime2(), selected);
                    }
                }

                switch (choiceInput) {
                    //Edit meeting time and/or place
                    case 1:
                        if (selected.getHasAgreedMeeting1()) {  //can't edit if already agreed upon
                            sp.ongoingTrades(12);
                            break;
                        } else if (selected.getLastEditor().equals(currUsername)) { //can't edit if latest suggestion is yours
                            sp.ongoingTrades(6);
                            break;
                        }
                        int editCount = selected.getUserEditCount1(currUsername);
                        int editMax = currentUser.getMeetingEditMax();
                        if (editCount < editMax) {
                            sp.ongoingTrades(editCount, (editCount + 1 == editMax));
                            sp.ongoingTrades(7);

                            LocalDateTime time = new DateTimeSuggestion(currentUser, tradeManager).suggestDateTime();

                            sp.ongoingTrades(8);
                            String placeSuggestion = br.readLine();
                            while (placeSuggestion.isEmpty()) {
                                sp.invalidInput();
                                placeSuggestion = br.readLine();
                            }
                            selected.setMeetingDateTime1(time);
                            selected.setMeetingLocation1(placeSuggestion);
                            selected.addUserEditCount1(currUsername);
                            sp.ongoingTrades(11);
                        } else {
                            sp.ongoingTrades(9);
                        }
                        break;
                    //Confirm this trade's current meeting time and place
                    case 2:
                        if (selected.getHasAgreedMeeting1()) {  //can't confirm meeting if already agreed upon
                            sp.ongoingTrades(5);
                            break;
                        }
                        if (selected.getLastEditor().equals(currUsername)) {    //can't confirm your own suggestion
                            sp.ongoingTrades(6);
                            break;
                        }
                        int weeklyTrade = tradeManager.getNumMeetingsThisWeek(currUsername, selected.getMeetingDateTime1().toLocalDate());
                        if (weeklyTrade > currentUser.getWeeklyTradeMax()) {
                            sp.ongoingTrades(10);
                        } else {
                            selected.confirmAgreedMeeting1();
                            sp.ongoingTrades(4);
                        }
                        break;
                    //Confirm the latest meeting took place
                    case 3:
                        if (!selected.getHasAgreedMeeting1()) { //can't confirm transaction if no agreed meeting
                            sp.ongoingTrades(15);
                            break;
                        }
                        LocalDateTime now = LocalDateTime.now();
                        if (selected instanceof TemporaryTrade && ((TemporaryTrade) selected).hasSecondMeeting() &&
                                now.compareTo(((TemporaryTrade) selected).getMeetingDateTime2()) > 0) {
                            new ConfirmAndCloseTempTrade().confirmAndCloseTempTransaction(currUsername,
                                    (TemporaryTrade) selected, itemManager);
                            if (selected.getIsComplete()) {
                                sp.ongoingTrades(13);
                            } else {
                                sp.ongoingTrades(3);
                            }
                        } else if (selected instanceof TemporaryTrade && selected.getHasAgreedMeeting1() &&
                                now.compareTo(selected.getMeetingDateTime1()) > 0) {
                            selected.confirmTransaction1(currUsername);
                            if (((TemporaryTrade) selected).hasSecondMeeting()) {
                                sp.ongoingTrades(14);
                            } else {
                                sp.ongoingTrades(3);
                            }
                        } else if (selected instanceof PermanentTrade && selected.getHasAgreedMeeting1() &&
                                now.compareTo(selected.getMeetingDateTime1()) > 0){
                            new ConfirmAndClosePermTrade().confirmAndClosePermTransaction(currUsername,
                                    (PermanentTrade) selected, itemManager, userManager);
                            if (selected.getIsComplete()) {
                                sp.ongoingTrades(13);
                            } else {
                                sp.ongoingTrades(3);
                            }
                        } else {    //can't confirm transaction before its scheduled time
                            sp.ongoingTrades(16);
                        }
                        break;
                    //Cancel this trade (just cancels, doesn't contribute to possible freezing)
                    case 4:
                        selected.setIsCancelled();
                        sp.ongoingTrades(2);
                        break;
                    case 5:
                        break;
                }
            }
        } catch (IOException e) {
            sp.exceptionMessage();
        }
        close();
    }

    private void close() {
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
