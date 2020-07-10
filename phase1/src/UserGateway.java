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
 *
 * @author Liam Huff
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-10
 */
public class UserGateway {

    /**
     * Constructor for UserGateway.
     */
    public UserGateway() {
    }

    /**
     * Deserializes the .ser file that contains UserManager's serialization.
     *
     * @param filePath the path of the file being read
     * @return the de-serialized UserManager if the file exists, a new UserManager otherwise
     * @throws IOException            when an IO error occurs during deserialization
     * @throws ClassNotFoundException when UserManager or any of the classes it stores can't be found
     */
    public UserManager readFromFile(String filePath) throws IOException, ClassNotFoundException {

        //returns true and creates new file if file doesn't exist yet, false otherwise
        boolean fileCreated = new File(filePath).createNewFile();

        if (!fileCreated && new File(filePath).length() != 0) {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream buffer = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(buffer);

            UserManager um = (UserManager) ois.readObject();
            ois.close();
            return um;
        }
        return new UserManager();
    }

    /**
     * Serializes this system's UserManager.
     *
     * @param filePath the path of the file being written to
     * @param um       the UserManager being serialized
     * @throws IOException when an IO error occurs during serialization
     */
    public void saveToFile(String filePath, UserManager um) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        BufferedOutputStream buffer = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(buffer);

        oos.writeObject(um);
        oos.close();
    }
}
