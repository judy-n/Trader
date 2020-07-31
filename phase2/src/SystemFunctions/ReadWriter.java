package SystemFunctions;

import SystemManagers.ItemManager;
import SystemManagers.Manager;
import SystemManagers.TradeManager;
import SystemManagers.UserManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * ReadWriter reads from and writes to a .ser file that contains a <Manager></Manager>
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Liam Huff
 * @version 1.0
 * @since 2020-07-17
 * last modified 2020-07-22
 */

public class ReadWriter {

    public ReadWriter() {
    }

    /**
     * Deserializes the .ser file that contains <Manager></Manager>'s serialization.
     *
     * @param filePath the path of the file being read
     * @return the de-serialized <Manager></Manager> if the file exists, a new <UserManager></UserManager> otherwise
     * @throws IOException            when an IO error occurs during deserialization
     * @throws ClassNotFoundException when <Manager></Manager> or any of the classes it depends on can't be found
     */
    public Manager readFromFile(String filePath, int type) throws IOException, ClassNotFoundException {

        //returns true and creates new file if file doesn't exist yet, false otherwise
        boolean fileCreated = new File(filePath).createNewFile();

        if (!fileCreated && new File(filePath).length() != 0) {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedInputStream buffer = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(buffer);
            switch (type) {
                case 1:
                    UserManager userManager = (UserManager) objectInputStream.readObject();
                    objectInputStream.close();
                    return userManager;
                case 2:
                    ItemManager itemManager = (ItemManager) objectInputStream.readObject();
                    objectInputStream.close();
                    return itemManager;

                case 3:
                    TradeManager tradeManager = (TradeManager) objectInputStream.readObject();
                    objectInputStream.close();
                    return tradeManager;
            }
        }
        if (type == 1) {
            return new UserManager();
        } else if (type == 2) {
            return new ItemManager();
        } else {
            return new TradeManager();
        }
    }

    /**
     * Serializes this system's manager.
     *
     * @param filePath the path of the file being written to
     * @param manager  the <Manager></Manager> being serialized
     * @throws IOException when an IO error occurs during serialization
     */
    public void saveToFile(String filePath, Manager manager) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        BufferedOutputStream buffer = new BufferedOutputStream(fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(buffer);

        objectOutputStream.writeObject(manager);
        objectOutputStream.close();
    }

    /**
     * Reads in the login credentials of the initial admin.
     * Only called when the program is first run (when there are no users in the database).
     *
     * @param filePath the path of the file being read
     * @throws IOException when an IO error occurs while reading
     */
    public String[] readAdminFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String[] adminCredentials = new String[3];
        String tempString;
        int count = 0;
        while ((tempString = bufferedReader.readLine()) != null) {
            adminCredentials[count] = tempString;
            count++;
        }
        bufferedReader.close();
        return adminCredentials;
    }
}
