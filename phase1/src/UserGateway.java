import java.io.*;
import java.util.ArrayList;

/**
 * UserGateway is a class that allows UserManagers to be serialized and de-serialized.
 * It will hold a UserManager, and have getters and setters for that UserManager.
 * It will also be able to read and write that user manager to a .ser file.
 * @author Liam Huff
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-03
 */


public class UserGateway {
    private UserManager uManager;


    /**
     * Constructor for UserGateway, sets the UserManager
     * @param uManager
     */
    public UserGateway(UserManager uManager) {
        this.uManager = uManager;
    }

    /**
     * Getter for this gateway's UserManager
     * @return uManager
     */
    public UserManager getUserManager() {
        return uManager;
    }

    /**
     * Setter for this user's UserManager
     * @param uManager
     */
    public void setUserManager(UserManager uManager) {
        this.uManager = uManager;
    }

    /**
     * De-serializes a .ser file and makes that file's usermanager this Gateway's usermanager.
     * @param path the path of the file
     * @throws ClassNotFoundException - if the de-serialized class can't be made
     */
    public void readFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            uManager = (UserManager) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("IO Error Occurred");
        }
    }


    /**
     * Saves serialized UserManager to .ser file
     * @param filePath the path of the file
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(uManager);
        output.close();
    }
}
