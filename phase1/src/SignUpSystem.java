import java.util.Scanner;
import java.util.ArrayList;
/**
 * TradeDatabase.java
 * Stores all Trades
 * @author ???
 * created 2020-06-26
 * last modified 2020-06-27
 */

public class SignUpSystem {
    public SignUpSystem(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter an username");
        String username = sc.nextLine();
        boolean isValid = false;
        while (isValid = false) {
            // Checks if username is available
            ArrayList<User> users = UserDatabase.getAllUser();
            ArrayList<String> usernames = new ArrayList<String>();
            for (User u : users) {
                usernames.add(u.username);
            }
            if (!(usernames.contains(username))) {
                isValid = true;
            }
            else {
                System.out.println("This username is taken. Enter a new one:");
                username = sc.nextLine();
            }
        }
        System.out.println("Please enter an email");
        String email = sc.nextLine();
        System.out.println("Please enter a password");
        String password = sc.nextLine();
        //
        User u = new User(username, email, password);
        UserDatabase.addUser(u);

    }



}
