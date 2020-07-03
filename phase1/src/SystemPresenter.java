/**
 * SystemPresenter.java
 * All the print stuff
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-03
 */

public class SystemPresenter {
    private User currentUser;

    public SystemPresenter(User user){
        currentUser = user;
    }

    public void inventoryEditor(){
        System.out.println("Choose one of the options: ");
        System.out.println("1 - Add an item to inventory" +
                "\n2 - Remove item from inventory " +
                "\n3 - Cancel ");
    }
    public void inventoryAddItem(int input){
        switch (input) {
            case 1 :
                System.out.println("Enter the name of the item to add(at least 3 letters):");
                break;

            case 2:
                System.out.println("Enter a description(at least 2 words):");
                break;

            case 3:
                System.out.println("Are you sure you want to add this item?(Y/N)");
                break;

            case 4:
                System.out.println("Your item has been requested! Please wait for an admin to review it.");
                break;
        }
    }
    public void inventoryAddItem(String name, String description){
        System.out.println(name + " : "+description);
    }

    public void inventoryRemoveItem(int input){
        switch (input){
            case 1:
                System.out.println("No items to remove.");
                break;
            case 2:
                System.out.println("Enter the ID of the item you would like to remove:");
                break;
        }
    }
    public void inventoryRemoveItem(String name, int index, int input){
        if(input == 1){
            System.out.println("Remove " + index + ". " + name + " from your inventory?(Y/N)");
        }else{
            System.out.println(name + " is removed from your inventory!");
        }
    }

    public void wishlistEditor(){
        System.out.println("Choose one of the options: ");
        System.out.println("1 - Remove item from wish list" +
                "\n2 - Cancel ");
    }

    public void wishlistAddItem(int input){
        switch (input){
            case 1:
                System.out.println("Your wish list is empty.");
                break;
            case 2:
                System.out.println("Enter the ID of the item you would like to remove:");
                break;
        }
    }

    public void wishlistRemoveItem(String name, int input){
        switch (input){
            case 1:
                System.out.println("Remove "+ name +" from your wishlist?(Y/N)");
                break;
            case 2:
                System.out.println(name + " is removed from your wishlist!");
                break;
        }
    }




    public void cancelled(){
        System.out.println("Cancelled!");
    }

    public void exceptionMessage(){
        System.out.println("Error reading user input! Please try again.");
    }


    public void invalidInput(){
        System.out.println("Invalid input try again.");
    }

}
