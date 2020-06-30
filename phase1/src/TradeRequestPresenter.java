import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

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
    public HashMap<String[], Integer> initiatedTrades = new HashMap<>();
    public HashMap<String[], Integer> receiveTrades = new HashMap<>();

    /**
     * TradeRequestPresenter
     * Creates an trade request presenter at prints to the screen all trade requests received/sent
     *
     * @param user user
     */
    public TradeRequestPresenter(User user) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        for (String[] key : user.tradeRequests.keySet()) {
            if (user.getUsername() == key[0]) {
                initiatedTrades.put(key, user.tradeRequests.get(key));
            } else {
                receiveTrades.put(key, user.tradeRequests.get(key));
            }
        }
        System.out.println("Here is all the trade request(s) you sent:");
        if (initiatedTrades.isEmpty()) {
            System.out.println("None!");
        } else {
            for (String[] key : initiatedTrades.keySet()) {
                Item i = ItemDatabase.getItem(initiatedTrades.get(key));
                System.out.println("Trade for " + i.name + " from user " + key[1]);
            }
        }


        System.out.println("Here is all the trade request(s) you received:");

        if (receiveTrades.isEmpty()) {
            System.out.println("None!");
        } else {
            for (String[] key : receiveTrades.keySet()) {
                Item i = ItemDatabase.getItem(receiveTrades.get(key));
                System.out.println("Trade for " + i.name + "(" + i.id + ") from user " + key[0]);
            }
            System.out.println("Would you like to accept any of these requests?(0 to quit)");
            try {
                int input = Integer.parseInt(br.readLine());
                while (input != 0 && !receiveTrades.containsValue(input)) {
                    System.out.println("Invalid input!");
                    input = Integer.parseInt(br.readLine());
                }
                if (input == 0) {
                    new UserDashboard(user);
                }
                String[] a = getTradeHelper(input);
                System.out.println("Are you sure you want to trade " + a[1] + " with " + a[0] + "?(Y/N)");
                String inputConfirm = br.readLine();
                while (!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                    System.out.println("Invalid input try again why don't you");
                    inputConfirm = br.readLine();
                }
                if (inputConfirm.equalsIgnoreCase("y")) {
                    System.out.println("Initiating Trade with " + a[0]);
                    System.out.println("Please suggest a time(YYYY/MM/DD-HH/MM): ");
                    String t = br.readLine();
                    String [] temp = t.split("-");
                    if (!isThisDateValid(temp[0], "dd/MM/yyyy") || !isThisTimeValid(temp[1])) {
                        System.out.println("Invalid input try again");
                        t = br.readLine();
                        temp = t.split("-");
                        isThisDateValid(temp[0], "dd/MM/yyyy");
                        isThisTimeValid(temp[1]);
                    }
                    String [] temp2 = temp[0].split("/");
                    String [] temp3 = temp[1].split("/");

                    LocalDateTime time = LocalDateTime.of(Integer.parseInt(temp2[2]), Integer.parseInt(temp2[1]), Integer.parseInt(temp2[0])
                            , Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
                    System.out.println("Please suggest a place: ");
                    String place = br.readLine();


                }

            } catch (IOException e) {
                System.out.println("No");
            }

        }
    }

    /**
     * This method return an array of strings with item name and username from the
     * item ID
     *
     * @param itemId item ID
     * @return array of username and item name
     */
    public String[] getTradeHelper(int itemId) {
        String itemName = ItemDatabase.getItem(itemId).name;
        for (String[] key : receiveTrades.keySet()) {
            if (itemId == (receiveTrades.get(key))) {
                return new String[]{key[0], itemName};
            }
        }
        return null;
    }

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
     * This method is plagiarized how do we credit
     * https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/
     * @param dateToValidate the user input
     * @param dateFromat the format of the user input
     * @return true if valid false otherwise
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

