import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets an admin approve or reject pending items.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-09
 */
public class CatalogEditor {
    private AdminUser currentAdmin;
    private ItemManager itemManager;
    private UserManager userManager;

    /**
     * Creates a catalog editor that lets an admin user approve or deny certain items
     * @param user admin user
     * @param im the system's item manager
     * @param um the system's user manager
     */
    public CatalogEditor(AdminUser user, ItemManager im, UserManager um) {
        currentAdmin = user;
        itemManager = im;
        userManager = um;

        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int input;
        int max = itemManager.getNumPendingItems();
        try {
            do {
                if (itemManager.getPendingItems().isEmpty()) {
                    sp.catalogEditor(1);
                    break;
                } else {
                    sp.catalogEditor(itemManager.getPendingItems());
                }
                sp.catalogEditor(2);
                String temp = br.readLine();
                while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > max) {
                    sp.invalidInput();
                    temp = br.readLine();
                }
                input = Integer.parseInt(temp);

                if (input != 0) {
                    Item i = itemManager.getPendingItem(input);
                    NormalUser itemOwner = userManager.getNormalByUsername(i.getOwnerUsername());
                    sp.catalogEditor(i);
                    String temp2 = br.readLine();
                    while (!temp2.matches("[0-2]")) {
                        sp.invalidInput();
                        temp2 = br.readLine();
                    }
                    int actionInput = Integer.parseInt(temp2);
                    if (actionInput == 1) {
                        itemManager.approveItem(i);
                        itemOwner.addInventory(i.getID());
                    } else if (actionInput == 2){
                        itemManager.rejectItem(i);
                        itemOwner.removePendingInventory(i.getID());
                    } else {
                        input = 0;
                    }
                }
            } while (input != 0);
            close();

        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }

    private void close() {
        new AdminDashboard(currentAdmin, itemManager, userManager);
    }
}
