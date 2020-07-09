import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Date;

/**
 * Shows all trade requests received/sent by a user and lets them take actions through user input.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-29
 * last modified 2020-07-08
 */
public class TradeRequestViewer {
    private LinkedHashMap<String[], long[]> initiatedTrades;
    private LinkedHashMap<String[], long[]> receivedTrades;

    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    private SystemPresenter sp;
    private BufferedReader br;

    String dateFormat = "dd/MM/yyyy";

    /**
     * Class constructor.
     * Creates an TradeRequestViewer with the given logged-in user and item/user/trade managers.
     * Prints to the screen all trade requests received/sent by the given user and options on actions to take.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public TradeRequestViewer(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        sp = new SystemPresenter();
        br = new BufferedReader(new InputStreamReader(System.in));
        if(currentUser.getIsFrozen()){
            caseUserIsFrozen();
        }else{
            caseUserNotFrozen();
        }
    }// end
    private void caseUserIsFrozen(){
        sp.tradeRequestViewer(5);
        close();
    }

    private void caseUserNotFrozen(){
        initiatedTrades = new LinkedHashMap<>();
        receivedTrades = new LinkedHashMap<>();

        String[] a;
        NormalUser trader;
        long firstItem;
        long secondItem = 0;

        //separate into initiated trades and received trades
        for (String[] key : currentUser.getTradeRequest().keySet()) {
            if (currentUser.getUsername().equals(key[0])) {
                initiatedTrades.put(key, currentUser.getTradeRequest().get(key));
            } else {
                if(!userManager.getNormalByUsername(key[1]).getIsFrozen()){
                    receivedTrades.put(key, currentUser.getTradeRequest().get(key));
                }
            }
        }
        ArrayList<Item> initiatedItems = new ArrayList<>();
        ArrayList<String> initiatedOwners = new ArrayList<>();


        //this just displays the initiated trades no action required
        if (initiatedTrades.isEmpty()) {
            sp.tradeRequestViewer(2);
        } else {
            for (String[] key : initiatedTrades.keySet()) {
                Item i = itemManager.getApprovedItem(initiatedTrades.get(key)[1]);
                initiatedItems.add(i);
                initiatedOwners.add(key[1]);
            }
            sp.tradeRequestViewer(1, initiatedItems, initiatedOwners);
        } //end


        //if user is frozen, can not accept trade requests

        ArrayList<Item> receivedItems = new ArrayList<>();
        ArrayList<String> receivedOwners = new ArrayList<>();
        int index = 1;
        for (String[] key : receivedTrades.keySet()) {
            Item i = itemManager.getApprovedItem(receivedTrades.get(key)[1]);
            receivedItems.add(i);
            receivedOwners.add(key[0]);
            index++;
        }
        sp.tradeRequestViewer(2, receivedItems, receivedOwners);
        sp.tradeRequestViewer(4);
        try {
                    //pick a request to accept
            String temp = br.readLine();
            while(!temp.matches("[0-9]+") || Integer.parseInt(temp) > index) {
                sp.invalidInput();
                temp = br.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input == 0) {
                close();
            } else {
                a = getTradeHelper(input);
                trader = userManager.getNormalByUsername(a[0]);
                firstItem = receivedItems.get(input - 1).getID();
                if (trader.getIsFrozen()) {
                    sp.tradeRequestViewer(3, a[0], a[1]);
                } else {
                    //trade with xx person?
                    sp.tradeRequestViewer(1, a[0], a[1]);
                    String inputConfirm = br.readLine();
                    while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                        sp.invalidInput();
                        inputConfirm = br.readLine();
                    }
                    if (inputConfirm.equalsIgnoreCase("y")) {
                        sp.tradeRequestViewer(2, a[0], a[1]);
                        int twoWayItem;
                        if (!trader.getInventory().isEmpty()) {
                            sp.tradeRequestViewer(7);
                            ArrayList<Item> items = itemManager.getApprovedItemsByIDs(trader.getInventory());
                            sp.tradeRequestViewer(items);
                            String temp2 = br.readLine();
                            //check
                            while (!temp2.matches("[1-9]+") || Integer.parseInt(temp2) > items.size()) {
                                sp.invalidInput();
                                temp2 = br.readLine();
                            }
                            twoWayItem = Integer.parseInt(temp);
                            if (twoWayItem != 0) {
                                secondItem = itemManager.getApprovedItem(trader.getInventory().get(twoWayItem - 1)).getID();
                            }
                        }
                        sp.tradeRequestViewer(8);
                        String permOrTemp = br.readLine();
                        while (!permOrTemp.equals("1") && !(permOrTemp.equals("2"))) {
                            sp.invalidInput();
                            permOrTemp = br.readLine();
                        }
                        sp.tradeRequestViewer(6);
                        String t = br.readLine();

                        DateTimeSuggestion dts = new DateTimeSuggestion();
                        boolean isValid = dts.checkDateTime(t);
                        while(!isValid){
                            t = br.readLine();
                            isValid = dts.checkDateTime(t);
                        }
                        LocalDateTime time = LocalDateTime.of(dts.getYear(), dts.getMonth(),
                                dts.getDay(), dts.getHour(), dts.getMinute());
                        sp.tradeRequestViewer(3);
                        String place = br.readLine();
                        while(place.isEmpty()){
                            sp.invalidInput();
                            place = br.readLine();
                        }
                        itemManager.getApprovedItem(firstItem).setAvailability(false);
                        if (secondItem != 0) {
                            itemManager.getApprovedItem(secondItem).setAvailability(false);
                        }
                        if (permOrTemp.equals("1")) {
                            PermanentTrade pt = new PermanentTrade(new String[]{currentUser.getUsername(), a[0]},
                                    new long[]{firstItem, secondItem}, time, place);
                            tradeManager.addTrade(pt);
                        } else {
                            TemporaryTrade tt = new TemporaryTrade(new String[]{currentUser.getUsername(), a[0]},
                                    new long[]{firstItem, secondItem}, time, place);
                            tradeManager.addTrade(tt);
                        }
                    }
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

    private String[] getTradeHelper(int index) {
        ArrayList<String[]> traders = new ArrayList<>();
        ArrayList<long[]> itemIds = new ArrayList<>();
        for (Map.Entry<String[], long[]> e : receivedTrades.entrySet()) {
            traders.add(e.getKey());
            itemIds.add(e.getValue());
        }
        String trader = traders.get(index - 1)[0];
        long itemId = itemIds.get(index - 1)[1];
        Item firstItem = itemManager.getApprovedItem(itemId);
        String itemName = firstItem.getName();
        return new String[]{trader, itemName};
    }

}

