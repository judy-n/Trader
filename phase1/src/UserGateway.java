import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * UserGateway is a class that allows UserManagers to be serialized and de-serialized.
 * It will hold a UserManager, and have getters and setters for that UserManager.
 * It will also be able to read and write that user manager to a .ser file.
 *
 * @author Liam Huff
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-03
 */


public class UserGateway {
    private UserManager uManager;


    /**
     * Constructor for UserGateway, sets the UserManager
     *
     * @param uManager UserManager
     */
    public UserGateway(UserManager uManager) {
        this.uManager = uManager;
    }

    /**
     * De-serializes a .ser file and makes that file's usermanager this Gateway's usermanager.
     *
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
            System.out.println("IO Error Occurred USER");
        }
    }


    /**
     * Saves serialized UserManager to .ser file
     *
     * @param filePath the path of the file
     * @throws IOException throws IOException when there is an error with input
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
