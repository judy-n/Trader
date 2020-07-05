import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Shows all trade requests received/sent by a user and lets them take actions through user input.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-29
 * last modified 2020-07-04
 */


public class TradeRequestPresenter {
    private HashMap<String[], long[]> initiatedTrades;
    private HashMap<String[], long[]> receiveTrades;
    private ItemManager im;
    private UserManager um;
    private NormalUser currentUser;

    /**
     * Class constructor.
     * Creates an TradeRequestPresenter with the given logged-in user, item manager, and user manager.
     * Prints to the screen all trade requests received/sent by the given user and options on actions to take.
     *
     * @param user the non-admin user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public TradeRequestPresenter(NormalUser user, ItemManager im, UserManager um) {
        currentUser = user;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter();
        initiatedTrades = new HashMap<>();
        receiveTrades = new HashMap<>();
        this.im = im;
        this.um = um;

        for (String[] key : user.getTradeRequest().keySet()) {
            if (user.getUsername().equals(key[0])) {
                initiatedTrades.put(key, user.getTradeRequest().get(key));
            } else {
                receiveTrades.put(key, user.getTradeRequest().get(key));
            }
        }
        ArrayList<Item> initiatedItems = new ArrayList<>();
        ArrayList<String> initiatedOwners = new ArrayList<>();

        if (initiatedTrades.isEmpty()) {
            sp.tradeRequestPresenter(2);
        } else {

            for (String[] key : initiatedTrades.keySet()) {
                Item i = im.getApprovedItem(initiatedTrades.get(key)[1]);
                initiatedItems.add(i);
                initiatedOwners.add(key[1]);
            }
            sp.tradeRequestPresenter(1, initiatedItems, initiatedOwners);
        }

        ArrayList<Item> receivedItems = new ArrayList<>();
        ArrayList<String> receivedOwners = new ArrayList<>();
        int index = 1;
        if (receiveTrades.isEmpty()) {
            sp.tradeRequestPresenter(1);
        } else {
            for (String[] key : receiveTrades.keySet()) {
                Item i = im.getApprovedItem(receiveTrades.get(key)[1]);
                receivedItems.add(i);
                receivedOwners.add(key[0]);
                index++;
            }
            sp.tradeRequestPresenter(2, receivedItems, receivedOwners);
            try {
                int input = Integer.parseInt(br.readLine());
                while (input != 0 && input > index) {
                    sp.invalidInput();
                    input = Integer.parseInt(br.readLine());
                }
                if (input == 0) {
                    new UserDashboard(currentUser, im, um);
                }
                String[] a = getTradeHelper(input);

                sp.tradeRequestPresenter(1, a[0], a[1]);
                String inputConfirm = br.readLine();
                while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                    sp.invalidInput();
                    inputConfirm = br.readLine();
                }
                if (inputConfirm.equalsIgnoreCase("y")) {

                    sp.tradeRequestPresenter(2, a[0], a[1]);
                    String t = br.readLine();
                    String[] temp = t.split("-");
                    if (!isThisDateValid(temp[0], "dd/MM/yyyy") || !isThisTimeValid(temp[1])) {
                        sp.invalidInput();
                        t = br.readLine();
                        temp = t.split("-");
                        isThisDateValid(temp[0], "dd/MM/yyyy");
                        isThisTimeValid(temp[1]);
                    }
                    String[] temp2 = temp[0].split("/");
                    String[] temp3 = temp[1].split("/");

                    LocalDateTime time = LocalDateTime.of(Integer.parseInt(temp2[2]), Integer.parseInt(temp2[1]),
                            Integer.parseInt(temp2[0]), Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
                    sp.tradeRequestPresenter(3);
                    String place = br.readLine();

                }

            } catch (IOException e) {
                sp.exceptionMessage();
            }
            new UserDashboard(currentUser, im, um);
        }
    }

    /**
     * This method return an array of strings with item name and username from the
     * item ID
     *
     * @param index item ID
     * @return array of username and item name
     */
    public String[] getTradeHelper(int index) {
        Set<String[]> keySet = receiveTrades.keySet();
        ArrayList<String[]> listOfKeys = (ArrayList<String[]>) keySet;
        Collection<long[]> keyValues = receiveTrades.values();
        ArrayList<long[]> listOfKeyValues = (ArrayList<long[]>) keyValues;
        assert im.getApprovedItem(listOfKeyValues.get(index - 1)[1]) != null;
        String itemName = im.getApprovedItem(listOfKeyValues.get(index - 1)[1]).getName();
        String traderName = listOfKeys.get(index - 1)[0];
        return new String[]{traderName, itemName};
    }

    /**
     * This method checks if the user input time is valid
     *
     * @param s user input
     * @return true if valid false otherwise
     */
    private boolean isThisTimeValid(String s) {
        String[] arr = s.split("/");
        int hr = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        if (hr < 0 || hr > 23) {
            return false;
        }
        return min >= 0 && min <= 59;
    }

    public void printInventory(String username) {
        NormalUser u = um.getUserByUsername(username);
        ArrayList<Item> items = im.getApprovedItemsByIDs(u.getInventory());
        for (Item i : items) {
            System.out.println(i);
        }
    }

    /**
     * Checks if the given date is valid.
     * based on code by mkyong from https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/.
     *
     * @param dateToValidate the date being validated
     * @param dateFormat     the format of the date being validated
     * @return true if valid, false otherwise
     */
    private boolean isThisDateValid(String dateToValidate, String dateFormat) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }
}

