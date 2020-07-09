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
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-08
 * last modified 2020-07-08
 */
public class TradeGateway {

    /**
     * Constructor for TradeGateway.
     */
    public TradeGateway() {
    }

    /**
     * Deserializes the .ser file that stores TradeManager's serialization.
     *
     * @param filePath the path of the file being read
     * @return the de-serialized TradeManager if the file exists, a new TradeManager otherwise
     * @throws IOException            when an IO error occurs during deserialization
     * @throws ClassNotFoundException when TradeManager or any of the classes it stores can't be found
     */
    public TradeManager readFromFile(String filePath) throws IOException, ClassNotFoundException {

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
        return new TradeManager();
    }


    /**
     * Serializes the system's TradeManager.
     *
     * @param filePath the path of the file being written to
     * @param tm       the TradeManager being serialized
     * @throws IOException when an IO error occurs during serialization
     */
    public void saveToFile(String filePath, TradeManager tm) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        BufferedOutputStream buffer = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(buffer);

        oos.writeObject(tm);
        oos.close();
    }
}
