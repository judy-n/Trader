import sun.rmi.runtime.Log;

import java.util.Scanner;

/**
 * StartMenu.java
 * Lets the user choose to sign up and login
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-26
 */
public class StartMenu {
    public StartMenu(){
        String s;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Would you like to signup or login?");
            s = sc.nextLine();
        }while (!s.equals("signup") && !s.equals("login"));

        if(s.equals("signup")){
            new SignUpSystem();
        }else{
            new LoginSystem();
        }
    }
}
