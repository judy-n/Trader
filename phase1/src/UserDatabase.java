import java.util.ArrayList;
/**
 * UserDatabase.java
 * Stores all Users in the system
 * @author Ning Zhang
 * created 2020-06-26
 * last modified 2020-06-27
 */
public class UserDatabase {
    private ArrayList<User> allUser = new ArrayList<>();
    public UserDatabase(){

        //This is just for testing rn will delete later
        User u1 = new User("username", "example@email.com", "pa55word");
        Item i = new Item("Item", "This is an item.");
        u1.addInventory(i);
        allUser.add(u1);
    }

    public void addUser(User u){
        allUser.add(u);
    }
    public User getUserByUsername(String username){
        for (User u : allUser){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public User getUserByEmail(String email){
        for (User u : allUser){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }

    public String usernamePassword(String username){
        for (User u : allUser){
            if(u.getUsername().equals(username))
                return u.getPassword();
        }
        return null;
    }

    public String emailPassword(String email){
        for(User u : allUser){
            if(u.getEmail().equals(email))
                return u.getPassword();
        }
        return null;
    }

    public boolean emailExists(String email){
        for(User u : allUser){
            if(u.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public boolean usernameExists(String username){
        for (User u : allUser){
            if(u.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public ArrayList<Item> allUserInventory(){
        ArrayList<Item> items = new ArrayList<>();
        for (User u : allUser){
            items.addAll(u.getInventory());
        }
        return items;
    }

    //This method is just for testing!! Delete later
    public void printAllUser(){
        for(User u : allUser){
            System.out.println(u.getUsername());
        }
    }


}
