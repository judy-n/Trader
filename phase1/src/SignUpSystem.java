import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SignUpSystem.java
 * Lets the user sign up
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-27
 */
public class SignUpSystem {
    public String username;
    public String email;
    public String password;

    public SignUpSystem(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserDatabase udb = new UserDatabase();
        System.out.println("Please enter an email: ");
        try{
            String e = br.readLine();
            while(udb.emailExists(e)){
                System.out.println("Email is already associated with an account! Please try again:");
                e = br.readLine();
            }
            email = e;
        } catch (IOException e) {
            System.out.println("Plz try again.");
        }

        System.out.println("Please enter an username: ");
        try{
            String un = br.readLine();
            while(udb.usernameExists(un)) {
                System.out.println("Username already exists! Please try again: ");
                un = br.readLine();
            }
            username = un;
        } catch (IOException e) {
            System.out.println("Plz try again.");
        }

        System.out.println("Please enter a password: ");
        try{
            String pw1 = br.readLine();
            System.out.println("Please verify your password: ");
            String pw2 = br.readLine();
            while (!pw1.equals(pw2)){
                System.out.println("Please try again:");
                pw2 = br.readLine();
            }
            password = pw1;
        }catch (IOException e){
            System.out.println("Plz try again.");
        }

        System.out.println("Thank you for signing up!");
        User u = new User(username, email, password);
        udb.addUser(u);
        udb.printAllUser();
    }



}
