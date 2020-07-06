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
import java.io.File;
import java.util.ArrayList;

/**
 * ItemGateway is a class that allows ItemManagers to be serialized and de-serialized.
 * It will hold a ItemManager, and have getters and setters for that ItemManager.
 * It will also be able to read and write that ItemManager to a .ser file.
 *
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
     *
     * @param iManager item manager
     */
    public ItemGateway(ItemManager iManager) {
        this.iManager = iManager;
    }

    /**
     * De-serializes a .ser file and separates approved items from pending items in the stored ItemManager.
     *
     * @param path the path of the file
     * @throws ClassNotFoundException - if the de-serialized class can't be made
     */
    public void readFromFile(String path) throws ClassNotFoundException {
        try {
            boolean fileCreated = new File(path).createNewFile();
            //returns true and creates new file if file doesn't exist yet, false otherwise

            if (!fileCreated) {
                InputStream file = new FileInputStream(path);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                // deserialize the Map
                ArrayList<Item> allItems = (ArrayList<Item>) input.readObject();
                input.close();

                ArrayList<Item> allApproved = new ArrayList<>();
                ArrayList<Item> allPending = new ArrayList<>();
                for (Item i : allItems) {
                    if (i.getIsApproved()) {
                        allApproved.add(i);
                    } else {
                        allPending.add(i);
                    }
                }
                iManager.setApprovedItems(allApproved);
                iManager.setPendingItems(allPending);
            }
        } catch (IOException ex) {
            System.out.println("IO Error Occurred ITEM");
        }
    }


    /**
     * ahhhh
     *
     * @param filePath the path of the file
     * @throws IOException throws IOException when there is an error with input
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(iManager.getAllItems());
        output.close();
    }
}
