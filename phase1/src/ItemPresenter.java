import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ItemPresenter.java
 * Shows all Items available for trade in all users' inventory.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-06-28
 */
public class ItemPresenter {
    public User user;
    public int max;
    /**
     * ItemPresenter
     * Creates an item present at prints to the screen all items available for trade
     */
    public ItemPresenter(User u) {
        user = u;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input;
        String inputConfirm;
        System.out.println("This is all the item(s) available for trade:");
        max = ItemDatabase.getNumItems();
        ItemDatabase.update();
        for (Item i : ItemDatabase.getAllItems()) {
            System.out.println(i.toString());
        }
        System.out.println("Is there an Item you would like to trade for? (0 to quit)");
        try{
            input = Integer.parseInt(br.readLine());
            while(input < 0 || input > max){
                System.out.println("Invalid input try again.");
                input = Integer.parseInt(br.readLine());
            }

                Item i = ItemDatabase.getItem(input);
                //nullpointer :/
                System.out.println("You have chosen: " + i.toString());
                System.out.println("Are you sure u want to trade for this item with user, " + i.owner + " ?(Y/N)");
                inputConfirm = br.readLine();
                while(!inputConfirm.equalsIgnoreCase("y") && !inputConfirm.equalsIgnoreCase("n")) {
                    System.out.println("Invalid input.");
                    inputConfirm = br.readLine();
                }
                if(inputConfirm.equalsIgnoreCase("Y")){
                    System.out.println("Contacting user, "+ i.owner);


                }else{
                    System.out.println("Cancelled.");
                }
            new UserDashboard(user);
        } catch (IOException e){
            System.out.println("You kinda suck bruh.");
        }


    }

}
