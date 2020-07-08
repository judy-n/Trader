import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets AdminUser approve/deny pending items
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-05
 */
public class CatalogEditor {
    private ItemManager im;
    private UserManager um;
    private AdminUser currentUser;
    private int max;

    public CatalogEditor(AdminUser user, ItemManager im, UserManager um) {
        this.im = im;
        this.um = um;
        int input;
        String actionInput;
        max = im.getNumPendingItems();
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        currentUser = user;

        try {
            do {
                if (im.getPendingItems().isEmpty()) {
                    sp.catalogEditor(1);
                    break;
                } else {
                    sp.catalogEditor(im.getPendingItems());
                }
                sp.catalogEditor(2);
                String temp;
                temp = br.readLine();
                //check
                while (!temp.matches("[0-9]+") || Integer.parseInt(temp)>max) {
                    sp.invalidInput();
                    temp = br.readLine();
                }
                input = Integer.parseInt(temp);

                if (input != 0) {
                    Item i = im.getPendingItem(input);
                    NormalUser itemOwner = um.getNormalByUsername(i.getOwnerUsername());
                    sp.catalogEditor(i);
                    actionInput = br.readLine();
                    while (!(actionInput.equals("1")) && !(actionInput.equals("2"))) {
                        sp.invalidInput();
                        actionInput = br.readLine();
                    }
                    if (actionInput.equals("1")) {
                        i.setApproved();
                        im.approveItem(i);
                        itemOwner.addInventory(i.getID());
                    } else {
                        im.rejectItem(i);
                        itemOwner.removePendingInventory(i.getID());
                    }
                }
            } while (input != 0);

            new AdminDashboard(currentUser, im, um);

        } catch (IOException e) {
            sp.exceptionMessage();
        }
    }


}
