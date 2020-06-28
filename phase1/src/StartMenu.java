import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * StartMenu.java
 * Lets the user choose to sign up and login
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-26
 */
public class StartMenu {
    public StartMenu(){
        String s = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Would you like to signup or login?");
            try{
                s = br.readLine();
            }catch (IOException e) {
                System.out.println("Plz try again.");
            }
        }while (!s.equals("signup") && !s.equals("login"));

        if(s.equals("signup")){
            new SignUpSystem();
        }else{
            new LoginSystem();
        }
    }
}
