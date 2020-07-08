import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;

/**
 * TradeGateway is a class that allows TradeManagers to be serialized and de-serialized.
 * It will hold a TradeManager, and have getters and setters for that TradeManager.
 * It will also be able to read and write that TradeManager to a .ser file.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-06
 */
public class TradeGateway {

    /**
     * Constructor for ItemGateway.
     */
    public TradeGateway() {
    }

    /**
     * De-serializes the .ser file that stores TradeManager's serialization.
     *
     * @param filePath the path of the file being read
     */
    public TradeManager readFromFile(String filePath) {
        try {
            boolean fileCreated = new File(filePath).createNewFile();
            //returns true and creates new file if file doesn't exist yet, false otherwise

            if (!fileCreated) {
                FileInputStream fis = new FileInputStream(filePath);
                BufferedInputStream buffer = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(buffer);

                TradeManager tm = (TradeManager) ois.readObject();
                ois.close();
                return tm;
            }
        } catch (IOException e) {
            System.out.println("Reading error ITEM");
        } catch (ClassNotFoundException e) {
            System.out.println("Missing class in files.");
        }
        return new TradeManager();
    }


    /**
     * Serializes the system's TradeManager.
     *
     * @param filePath the path of the file being written to
     * @param tm the ItemManager being serialized
     */
    public void saveToFile(String filePath, TradeManager tm) {

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedOutputStream buffer = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(buffer);

            oos.writeObject(tm);
            oos.close();
        } catch (IOException e) {
            System.out.println("Writing error TRADE");
        }
    }
}
