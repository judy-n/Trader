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
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-08
 */
public class OngoingTradesViewer {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    public OngoingTradesViewer (NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;
        int indexInput;
        int choiceInput;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DateTimeSuggestion dts = new DateTimeSuggestion();
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
        int max = ongoingTrades.size();

        try{
            String temp = br.readLine();
            while (!temp.matches("[0-9]+")||Integer.parseInt(temp) > max){
                temp = br.readLine();
            }
            indexInput = Integer.parseInt(temp);
            Trade selected = ongoingTrades.get(indexInput - 1);
            sp.ongoingTrades(1);
            String temp2 = br.readLine();
            while(!temp.matches("[1-5]")){
                temp2 = br.readLine();
            }
            choiceInput = Integer.parseInt(temp2);

            switch (choiceInput){
                //Edit meeting time and/or place
                case 1:
                    if(selected.getLastEditor().equals(currUsername)){
                        sp.ongoingTrades(6);
                    }else {
                        if (selected.getUserEditCount1(currUsername) < currentUser.getMeetingEditMax()) {
                            sp.ongoingTrades(7);
                            LocalDateTime time;
                            int currentTrades;

                            do {
                                String timeSuggestion = br.readLine();
                                boolean isValid = dts.checkDateTime(timeSuggestion);
                                while (!isValid) {
                                    sp.invalidInput();
                                    timeSuggestion = br.readLine();
                                    isValid = dts.checkDateTime(timeSuggestion);
                                }
                                time = LocalDateTime.of(dts.getYear(), dts.getMonth(),
                                        dts.getDay(), dts.getHour(), dts.getMinute());
                                currentTrades = tradeManager.getNumMeetingsThisWeek(currUsername, time.toLocalDate());
                            }while (currentTrades>currentUser.getWeeklyTradeMax());


                            sp.ongoingTrades(8);
                            String placeSuggestion = br.readLine();
                            while (placeSuggestion.isEmpty()) {
                                sp.invalidInput();
                                placeSuggestion = br.readLine();
                            }
                            selected.setMeetingDateTime1(time);
                            selected.setMeetingLocation1(placeSuggestion);
                            selected.addUserEditCount1(currUsername);
                        }else{
                            sp.ongoingTrades(9);
                        }
                    }
                    break;
                    //Confirm this trade's current meeting time and place
                case 2:
                    if(selected instanceof TemporaryTrade && ((TemporaryTrade)selected).hasSecondMeeting()){
                        sp.ongoingTrades(5);
                    }else {
                        int weeklyTrade = tradeManager.getNumMeetingsThisWeek(currUsername, selected.getMeetingDateTime1().toLocalDate());
                        if(weeklyTrade>currentUser.getWeeklyTradeMax()){
                            sp.ongoingTrades(10);
                        }else{
                            selected.confirmAgreedMeeting1();
                            sp.ongoingTrades(4);
                        }
                    }
                    break;
                    //Confirm this trade took place
                case 3:
                    if(selected instanceof TemporaryTrade && ((TemporaryTrade)selected).hasSecondMeeting()) {
                        ((TemporaryTrade)selected).confirmTransaction2(currUsername);
                    }else{
                        selected.confirmTransaction1(currUsername);
                    }
                    sp.ongoingTrades(3);
                    break;
                    //Cancel this trade (Penalties will apply)
                case 4:
                    selected.setIsCancelled();
                    sp.ongoingTrades(2);
                    break;
                case 5:
                    close();
                    break;
            }

        }catch (IOException e){
            sp.exceptionMessage();
        }
        close();
    }

    public void close(){
        new NormalDashboard(currentUser, itemManager, userManager, tradeManager);
    }
}
