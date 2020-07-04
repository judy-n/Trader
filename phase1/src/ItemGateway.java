import java.io.*;
import java.util.ArrayList;

/**
 * ItemGateway is a class that allows ItemManagers to be serialized and de-serialized.
 * It will hold a ItemManager, and have getters and setters for that ItemManager.
 * It will also be able to read and write that ItemManager to a .ser file.
 * @author Liam Huff
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-03
 * last modified 2020-07-03
 */


public class ItemGateway {
    private ItemManager iManager;


    /**
     * Constructor for UserGateway, sets the ItemManager
     * @param iManager item manager
     */
    public ItemGateway(ItemManager iManager) {
        this.iManager = iManager;
    }

    /**
     * De-serializes a .ser file and makes that file's ItemManager this Gateway's ItemManager.
     * @param path the path of the file
     * @throws ClassNotFoundException - if the de-serialized class can't be made
     */
    public void readFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize the Map
            iManager = (ItemManager) input.readObject();
            input.close();
        } catch (IOException ex) {
            System.out.println("IO Error Occurred");
        }
    }


    /**
     * Saves serialized ItemManager to .ser file
     * @param filePath the path of the file
     * @throws IOException throws IOException when there is an error with input
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(iManager);
        output.close();
    }
}
