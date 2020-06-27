import java.util.Scanner;

public class SignUpSystem {
    public SignUpSystem(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter an username");
        String username = sc.nextLine();
        System.out.println("Please enter an email");
        String email = sc.nextLine();
        System.out.println("Please enter a password");
        String password = sc.nextLine();
        //
        User u = new User(username, email, password);

    }



}
