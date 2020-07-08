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
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NormalUser currentUser;
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

        initiatedTrades = new LinkedHashMap<>();
        receivedTrades = new LinkedHashMap<>();

        String[] a;
        NormalUser trader;
        long firstItem;
        long secondItem = 0;

        for (String[] key : user.getTradeRequest().keySet()) {
            if (user.getUsername().equals(key[0])) {
                initiatedTrades.put(key, user.getTradeRequest().get(key));
            } else {
                receivedTrades.put(key, user.getTradeRequest().get(key));
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
        if (currentUser.getIsFrozen()) {
            sp.tradeRequestViewer(5);
        } else {
            ArrayList<Item> receivedItems = new ArrayList<>();
            ArrayList<String> receivedOwners = new ArrayList<>();

            if (receivedTrades.isEmpty()) {
                sp.tradeRequestViewer(1);
            } else {
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
                    //handles if the other person is frozen
                    do {
                        String temp = br.readLine();
                        //check
                        while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > index) {
                            sp.invalidInput();
                            temp = br.readLine();
                        }
                        int input = Integer.parseInt(temp);
                        if (input == 0) {
                            break; //?? *screams*
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
                                    String[] tempArray = t.split("-");
                                    while (!isThisDateValid(tempArray[0]) || !isThisTimeValid(tempArray[1])) {
                                        sp.invalidInput();
                                        t = br.readLine();
                                        tempArray = t.split("-");
                                        isThisDateValid(tempArray[0]);
                                        isThisTimeValid(tempArray[1]);
                                    }
                                    String[] temp2 = tempArray[0].split("/");
                                    String[] temp3 = tempArray[1].split("/");
                                    LocalDateTime time = LocalDateTime.of(Integer.parseInt(temp2[2]), Integer.parseInt(temp2[1]),
                                            Integer.parseInt(temp2[0]), Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
                                    sp.tradeRequestViewer(3);
                                    String place = br.readLine();


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
                    } while (trader.getIsFrozen());
                } catch (IOException e) {
                    sp.exceptionMessage();
                }
            }
        }
        close();
    }// end

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


    private boolean isThisTimeValid(String s) {
        String[] arr = s.split("/");
        int hr = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        if (hr < 0 || hr > 23) {
            return false;
        }
        return min >= 0 && min <= 59;
    }


    //Checks if the given date is valid.
    //based on code by mkyong from https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/.
    private boolean isThisDateValid(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            //System.out.println(date);

        } catch (ParseException e) {
            //sp.exceptionMessage();
            //e.printStackTrace();
            return false;
        }
        return true;
    }
}

