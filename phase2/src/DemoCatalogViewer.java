import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Shows all items available for trade from all users' inventories.
 * Allows demo users to browse items, and prompts them to make an account if they choose an item.
 *
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-07-19
 * last modified 2020-07-19
 */
public class DemoCatalogViewer {
    private DemoUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;

    public DemoCatalogViewer(DemoUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        systemPresenter.catalogViewer(itemManager.getApprovedItems());
        systemPresenter.demoCatalogViewer(1);

        int maxIndex = itemManager.getNumApprovedItems();

        try {
            String temp = bufferedReader.readLine();
            while (!temp.matches("[0-9]+") || Integer.parseInt(temp) > maxIndex) {
                systemPresenter.invalidInput();
                temp = bufferedReader.readLine();
            }
            int input = Integer.parseInt(temp);
            if (input != 0) {
                Item selectedItem = itemManager.getApprovedItem(input - 1);

                assert selectedItem != null;
                long itemID = selectedItem.getID();
            }


        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
    }
}
