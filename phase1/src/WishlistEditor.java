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
    private User currentUser;

    public WishlistEditor(User user){
        // This lets the User remove Items from the wish list. Assuming that they only add Items to
        // the wishlist when browsing items available for trade.
        SystemPresenter sp = new SystemPresenter(user);
        currentUser = user;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        sp.wishlistEditor();
        try {
            input = Integer.parseInt(br.readLine());
            while (input <1 || input >2){
                sp.invalidInput();
                input = Integer.parseInt(br.readLine());
            }

            if (input == 2){ new UserDashboard(currentUser);}


        } catch (IOException e) {
            sp.exceptionMessage();
        }

        if (currentUser.getWishlist().isEmpty()) {
            sp.wishlistAddItem(1);
            new UserDashboard(currentUser);
        }
        else{
            sp.wishlistAddItem(2);
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
                sp.wishlistRemoveItem(selected.getName(), 1);
                String confirmInput = br.readLine();
                while (!confirmInput.equalsIgnoreCase("Y")&&!confirmInput.equalsIgnoreCase("N")){
                    sp.invalidInput();
                    confirmInput = br.readLine();
                }
                if(confirmInput.equalsIgnoreCase("y")){
                    currentUser.removeWishlist(selected);
                    sp.wishlistRemoveItem(selected.getName(), 2);
                }else{
                    sp.cancelled();
                }
                new UserDashboard(currentUser);
            } catch (IOException e) {
                sp.invalidInput();
            }
        }
    }
}
