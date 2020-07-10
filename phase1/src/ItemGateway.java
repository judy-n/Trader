import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;

/**
 * ItemGateway is a class that allows ItemManagers to be serialized and de-serialized.
 *
 * @author Liam Huff
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-10
 */
public class ItemGateway {

    /**
     * Constructor for ItemGateway.
     */
    public ItemGateway() {
    }

    /**
     * De-serializes the .ser file that stores ItemManager's serialization.
     *
     * @param filePath the path of the file being read
     * @return the de-serialized ItemManager if the file exists, a new ItemManager otherwise
     * @throws IOException when an IO error occurs during deserialization
     * @throws ClassNotFoundException when ItemManager or any of the classes it stores can't be found
     */
    public ItemManager readFromFile(String filePath) throws IOException, ClassNotFoundException {

        //returns true and creates new file if file doesn't exist yet, false otherwise
        boolean fileCreated = new File(filePath).createNewFile();

        if (!fileCreated && new File(filePath).length() != 0) {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream buffer = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(buffer);

            ItemManager im = (ItemManager) ois.readObject();
            ois.close();
            return im;
        }
        return new ItemManager();
    }


    /**
     * Serializes the system's ItemManager.
     *
     * @param filePath the path of the file being written to
     * @param im       the ItemManager being serialized
     * @throws IOException when an IO error occurs during serialization
     */
    public void saveToFile(String filePath, ItemManager im) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        BufferedOutputStream buffer = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(buffer);

        oos.writeObject(im);
        oos.close();
    }
}
