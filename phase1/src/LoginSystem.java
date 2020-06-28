import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * LoginSystem.java
 * Logs the User into the system
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-27
 */

public class LoginSystem {
    public String username;
    public String password;
    public String email;

    public LoginSystem(){
        String s = "";
        UserDatabase udb = new UserDatabase();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Would you like to login with username or email?");
            try{
                s = br.readLine();
            }catch (IOException e) {
                System.out.println("Plz try again.");
            }
        }while (!s.equals("username") && !s.equals("email"));

        if(s.equals("username")){
            System.out.println("Please enter your username: ");
            try {
                username = br.readLine();
                while(!udb.usernameExists(username)){
                    System.out.println("Username does not exist! Please try again: ");
                    username = br.readLine();
                }
            } catch (IOException e){
                System.out.println("Plz try again.");
            }
            System.out.println("Please enter your password: ");
            String validPw = udb.usernamePassword(username);
            try{
                password = br.readLine();
                while(!password.equals(validPw)){
                    System.out.println("Password invalid! Please try again: ");
                    password = br.readLine();
                }
            }catch (IOException e){
                System.out.println("Plz try again.");
            }
            System.out.println("Logged in!");
        } else {
            System.out.println("Please enter your email: ");
            try {
                email = br.readLine();
                while(!udb.emailExists(email)){
                    System.out.println("Email does not exist! Please try again: ");
                    email = br.readLine();
                }
            } catch (IOException e){
                System.out.println("Plz try again.");
            }
            System.out.println("Please enter your password: ");
            String validPw = udb.emailPassword(email);
            try{
                password = br.readLine();
                while(!password.equals(validPw)){
                    System.out.println("Password invalid! Please try again: ");
                    password = br.readLine();
                }
            }catch (IOException e){
                System.out.println("Plz try again.");
            }
            System.out.println("Logged in!");

        }

    }
}
