package NormalUserFunctions;

import SystemFunctions.Dashboard;
import SystemManagers.ItemManager;

/**
 * Displays a dashboard for a user choosing to demo the program.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 2.0
 * @since 2020-07-19
 * last modified 2020-08-10
 */

public class DemoDashboard extends Dashboard {
    private ItemManager itemManager;
    private String username;

    /**
     * Creates a <DemoDashboard></DemoDashboard> with the given item/user managers.
     *
     * @param itemManager the system's item manager
     */
    public DemoDashboard(ItemManager itemManager) {
        this.itemManager = itemManager;
        username = "Demo";
    }

    /**
     * Returns all items available in the system for trade in a String array
     * @return all items available in the system for trade
     */
    public String[] getDemoCatalog(){
        return itemManager.getItemStrings(itemManager.getApprovedItems(), true);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String setUpDash(int type) {
        return null;
    }

    @Override
    public void setPopUpMessage(int type) {
    }

    @Override
    public String getPopUpMessage() {
        return null;
    }

    @Override
    public void resetPopUpMessage() {
    }
}
