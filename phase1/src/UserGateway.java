import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;

/**
 * UserGateway is a class that allows UserManagers to be serialized and de-serialized.
 * It will hold a UserManager, and have getters and setters for that UserManager.
 * It will also be able to read and write that user manager to a .ser file.
 *
 * @author Liam Huff
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-06
 */
public class UserGateway {

    /**
     * Constructor for UserGateway.
     *
     */
    public UserGateway() {

    }

    /**
     * Deserializes the .ser file that contains UserManager's serialization.
     *
     * @param filePath the path of the file being read
     * @return the deserialized UserManager
     */
    public UserManager readFromFile(String filePath) {
        try {
            boolean fileCreated = new File(filePath).createNewFile();
            //returns true and creates new file if file doesn't exist yet, false otherwise

            if (!fileCreated) {
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream buffer = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(buffer);

                UserManager um = (UserManager) ois.readObject();
                ois.close();
                return um;
            }
        } catch (IOException e) {
            System.out.println("Reading error USER");
        } catch (ClassNotFoundException e) {
            System.out.println("Missing class in files.");
        }
        return new UserManager();
    }

    /**
     * Serializes this system's UserManager.
     *
     * @param filePath the path of the file being written to
     * @param um the UserManager being serialized
     */
    public void saveToFile(String filePath, UserManager um) {

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream buffer = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(buffer);

            oos.writeObject(um);
            oos.close();
        } catch (IOException e) {
            System.out.println("Writing error USER");
        }
    }
}
