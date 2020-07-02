import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * WishlistEditor.java
 * Lets user editor their wishlist
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-01
 * last modified 2020-07-01
 */

public class WishlistEditor {
    public User currentUser;

    public WishlistEditor(User user){
        // This lets the User remove Items from the wish list. Assuming that they only add Items to
        // the wishlist when browsing items available for trade.
        currentUser = user;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        System.out.println("Choose one of the options: ");
        System.out.println("1 - Remove item from wish list" +
                "\n2 - Cancel ");
        try {
            input = Integer.parseInt(br.readLine());
            while (input <1 || input >2){
                System.out.println("Invalid input try again.");
                input = Integer.parseInt(br.readLine());
            }

            if (input == 2){ new UserDashboard(currentUser);}


        } catch (IOException e) {
            System.out.println("Plz try again.");
        }

        if (currentUser.wishlist.isEmpty()) {
            System.out.println("Your wish list is empty.");
            new UserDashboard(currentUser);
        }
        else{
            System.out.println("Enter the ID of the item you would like to remove:");
            ArrayList<Item> wishlistItems = currentUser.getItemWishlist();
            int index = 1;

            for (Item i: wishlistItems){
                System.out.println(index + i.toString());
                index ++;
            }
            int indexInput;
            try {
                indexInput = Integer.parseInt(br.readLine());
                Item selected = wishlistItems.get(indexInput - 1);
                System.out.println("Remove "+selected.name+" from your wishlist?(Y/N)");
                String confirmInput = br.readLine();
                while (!confirmInput.equalsIgnoreCase("Y")&&!confirmInput.equalsIgnoreCase("N")){
                    System.out.println("Invalid input try again.");
                    confirmInput = br.readLine();
                }
                if(confirmInput.equalsIgnoreCase("y")){
                    currentUser.removeWishlist(selected);
                    System.out.println(selected.name + " is removed from your wishlist!");
                }else{
                    System.out.println("Cancelled.");
                }
                new UserDashboard(currentUser);
            } catch (IOException e) {
                System.out.println("Plz try again.");
            }
        }
    }
}
