package DemoUserFunctions;

import SystemFunctions.Dashboard;
import SystemFunctions.SystemPresenter;
import SystemManagers.ItemManager;

/**
 * Displays a dashboard for a user choosing to demo the program.
 *
 * @author Judy Naamani
 * @author Ning Zhang
 * @version 2.0
 * @since 2020-07-19
 * last modified 2020-08-12
 */
public class DemoDashboard extends Dashboard {
    private ItemManager itemManager;
    private SystemPresenter systemPresenter;
    private String username;
    private String popUpMessage = "";

    /**
     * Creates a <DemoDashboard></DemoDashboard> with the given item/user managers.
     *
     * @param itemManager the system's item manager
     */
    public DemoDashboard(ItemManager itemManager) {
        this.itemManager = itemManager;
        systemPresenter = new SystemPresenter();
        username = "Demo";
    }

    /**
     * Returns all items available in the system for trade in a String array
     *
     * @return all items available in the system for trade
     */
    public String[] getDemoCatalog() {
        return itemManager.getItemStrings(itemManager.getApprovedItems(), true);
    }

    /**
     * Returns the current username
     *
     * @return the current username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Returns a int indicating the type of dashboard
     *
     * @return the int
     */
    @Override
    public int getType() {
        return 2;
    }

    /**
     * Returns String used for display on JComponents on <DashboardFrame></DashboardFrame>
     *
     * @param type the type of String needed
     * @return the String
     */
    @Override
    public String setUpDash(int type) {
        return systemPresenter.setUpDemoDash(type);
    }

    /**
     * Sets the String needed for pop up display on the <DashFrame></DashFrame>
     *
     * @param type the type of message needed
     */
    @Override
    public void setPopUpMessage(int type) {
        popUpMessage = systemPresenter.getDemoPopMessage();
    }

    /**
     * Returns Strings used used for pop up display on the <DashFrame></DashFrame>
     *
     * @return the need pop message
     */
    @Override
    public String getPopUpMessage() {
        return popUpMessage;
    }

    /**
     * Resets the pop up message
     */
    @Override
    public void resetPopUpMessage() {
        popUpMessage = "";
    }

    /**
     * Returns String array used for display when demo user clicks their user info
     *
     * @return the String array
     */
    @Override
    public String[] getUserInfo() {
        return systemPresenter.getDemoUserInfo();
    }

    /**
     * Not used since demo users don't have a password
     *
     * @param password         new password
     * @param validatePassword validate new password
     */
    @Override
    public void validatePasswordChange(String password, String validatePassword) {
    }

    /**
     * Returns a message displayed for the help sectiong of the demo dashboard
     *
     * @return the message
     */
    @Override
    public String getHelpMessage() {
        return systemPresenter.getDemoHelpMessage();
    }
}
