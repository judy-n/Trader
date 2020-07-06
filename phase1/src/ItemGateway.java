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
 * It will hold a ItemManager, and have getters and setters for that ItemManager.
 * It will also be able to read and write that ItemManager to a .ser file.
 *
 * @author Liam Huff
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-06
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
     */
    public ItemManager readFromFile(String filePath) {
        try {
            boolean fileCreated = new File(filePath).createNewFile();
            //returns true and creates new file if file doesn't exist yet, false otherwise

            if (!fileCreated) {
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream buffer = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(buffer);

                ItemManager im = (ItemManager) ois.readObject();
                ois.close();
                return im;
            }
        } catch (IOException e) {
            System.out.println("Reading error ITEM");
        } catch (ClassNotFoundException e) {
            System.out.println("Missing class in files.");
        }
        return new ItemManager();
    }


    /**
     * Serializes the system's ItemManager.
     *
     * @param filePath the path of the file being written to
     * @param im the ItemManager being serialized
     */
    public void saveToFile(String filePath, ItemManager im) {

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream buffer = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(buffer);

            oos.writeObject(im);
            oos.close();
        } catch (IOException e) {
            System.out.println("Writing error ITEM");
        }
    }
}
