import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * TradeRequestPresenter.java
 * Shows all trade requests received/sent by a user
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-29
 * last modified 2020-06-29
 */


public class TradeRequestPresenter {
    private HashMap<String[], long[]> initiatedTrades = new HashMap<>();
    private HashMap<String[], long[]> receiveTrades = new HashMap<>();

    /**
     * TradeRequestPresenter
     * Creates an trade request presenter at prints to the screen all trade requests received/sent
     *
     * @param user user
     */
    public TradeRequestPresenter(User user) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        SystemPresenter sp = new SystemPresenter(user);

        for (String[] key : user.getTradeRequest().keySet()) {
            if (user.getUsername().equals(key[0])) {
                initiatedTrades.put(key, user.getTradeRequest().get(key));
            } else {
                receiveTrades.put(key, user.getTradeRequest().get(key));
            }
        }
        sp.tradeRequestPresenter("sent requests");
        if (initiatedTrades.isEmpty()) {
            sp.tradeRequestPresenter("none");
        } else {
            for (String[] key : initiatedTrades.keySet()) {
                Item i = ItemDatabase.getItem(initiatedTrades.get(key)[1]);
                System.out.println("Trade for " + i.getName() + " from user " + key[1]);
            }
        }

        sp.tradeRequestPresenter("received requests");
        int index = 1;
        if (receiveTrades.isEmpty()) {
            sp.tradeRequestPresenter("none");
        } else {
            for (String[] key : receiveTrades.keySet()) {
                Item i = ItemDatabase.getItem(receiveTrades.get(key)[1]);
                System.out.println(index+". Trade for " + i.getName() + " from user " + key[0]);
                index ++;
            }
            sp.tradeRequestPresenter("to accept");
            try {
                int input = Integer.parseInt(br.readLine());
                while (input != 0 && input > index) {
                    sp.tradeRequestPresenter("invalid");
                    input = Integer.parseInt(br.readLine());
                }
                if (input == 0) {
                    new UserDashboard(user);
                }
                String[] a = getTradeHelper(input);
                System.out.println("Are you sure you want to trade " + a[1] + " with " + a[0] + "?(Y/N)");
                String inputConfirm = br.readLine();
                while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                    sp.tradeRequestPresenter("invalid");
                    inputConfirm = br.readLine();
                }
                if (inputConfirm.equalsIgnoreCase("y")) {
                    System.out.println("Initiating Trade with " + a[0]);
                    sp.tradeRequestPresenter("suggest time");
                    String t = br.readLine();
                    String [] temp = t.split("-");
                    if (!isThisDateValid(temp[0], "dd/MM/yyyy") || !isThisTimeValid(temp[1])) {
                        sp.tradeRequestPresenter("invalid");
                        t = br.readLine();
                        temp = t.split("-");
                        isThisDateValid(temp[0], "dd/MM/yyyy");
                        isThisTimeValid(temp[1]);
                    }
                    String [] temp2 = temp[0].split("/");
                    String [] temp3 = temp[1].split("/");

                    LocalDateTime time = LocalDateTime.of(Integer.parseInt(temp2[2]), Integer.parseInt(temp2[1]),
                            Integer.parseInt(temp2[0]), Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
                    sp.tradeRequestPresenter("suggest place");
                    String place = br.readLine();

                }

            } catch (IOException e) {
                sp.tradeRequestPresenter("error");
            }
            new UserDashboard(user);
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
        assert ItemDatabase.getItem(listOfKeyValues.get(index-1)[1]) != null;
        String itemName = ItemDatabase.getItem(listOfKeyValues.get(index-1)[1]).getName();
        String traderName = listOfKeys.get(index-1)[0];
        return new String[] {traderName, itemName};



//
//        String itemName = ItemDatabase.getItem(itemId).name;
//        for (String[] key : receiveTrades.keySet()) {
//            if (itemId == (receiveTrades.get(key)[1])) {
//                return new String[]{key[0], itemName};
//            }
//        }
//        return null;
    }

    /**
     * This method checks if the user input time is valid
     * @param s user input
     * @return true if valid false otherwise
     */
    public boolean isThisTimeValid(String s){
        String [] arr = s.split("/");
        int hr = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        if(hr < 0|| hr > 23){
            return false;
        }
        return min >= 0 && min <= 59;
    }

    public void printInventory(String username){
        User u = UserDatabase.getUserByUsername(username);
        for(Item i : u.getInventory()){
            System.out.println(i.toString());
        }
    }

    /**
     * Checks if the given date is valid.
     * based on code by mkyong from https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/.
     *
     * @param dateToValidate the date being validated
     * @param dateFromat the format of the date being validated
     * @return true if valid, false otherwise
     */
    public boolean isThisDateValid(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
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

