package NormalUserFunctions;

import Entities.User;
import SystemFunctions.Dashboard;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import SystemManagers.ItemManager;
import SystemManagers.TradeManager;
import Entities.NormalUser;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a dashboard once a normal user logs in.
 *
 * @author Ning Zhang
 * @author Judy Naamani
 * @author Yingjia Liu
 * @author Kushagra Mehta
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-03
 */
public class NormalDashboard extends Dashboard {
    private NormalUser currentUser;
    private String currentUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;

    /**
     * Creates a <NormalDashboard></NormalDashboard> with the given normal user,
     * item/user/trade managers, and notification system.
     *
     */
    public NormalDashboard(String username, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {

        this.currentUsername = username;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        this.setPreferredSize(new Dimension(820, 576));
        this.setLayout(null);
        drawDefault();
        this.validate();
        this.repaint();
    }


    /**
     * Creates a <NormalDashboard></NormalDashboard> with the given normal user,
     * item/user/trade managers, and notification system.
     *
     * @param user         the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public NormalDashboard(NormalUser user, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {
        currentUser = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.notifSystem = notifSystem;

        this.setPreferredSize(new Dimension(820, 576));
        this.setLayout(null);
        drawDefault();
        this.validate();
        this.repaint();
    }

    private void drawDefault() {
        JButton userProfilePic = new JButton();
        userProfilePic.setSize(new Dimension(85, 110));
        userProfilePic.setBackground(Color.BLACK);
        this.add(userProfilePic);
        userProfilePic.setLocation(30, 30);

        JLabel inventory = new JLabel("Inventory");
        inventory.setSize(new Dimension(200, 30));
        inventory.setForeground(Color.BLACK);
        this.add(inventory);
        inventory.setLocation(30, 150);

        JLabel wishlist = new JLabel("Wishlist");
        wishlist.setSize(200, 30);
        wishlist.setForeground(Color.BLACK);
        this.add(wishlist);
        wishlist.setLocation(30, 190);
    }
//        String regex = "[0-9]";
//
//        // user frozen and on vacation
//        if ((currentUser.getIsFrozen()) && (currentUser.getIsOnVacation())) {
//            //systemPresenter.normalDashboard(2);
//        }
//
//        // user frozen and not on vacation
//        else if ((currentUser.getIsFrozen()) && !(currentUser.getIsOnVacation())) {
//            //systemPresenter.normalDashboard(3);
//        }
//
//        // user not frozen and on vacation
//        else if (!(currentUser.getIsFrozen()) && (currentUser.getIsOnVacation())) {
//            regex = "[0-7]+[9]";
//            //systemPresenter.normalDashboard(4);
//        }
//
//        // not frozen and not on vacation
//        else {
//            regex = "[0-7]+[9]";
//            //systemPresenter.normalDashboard(1);
//        }

//        try {
//            String temp = bufferedReader.readLine();
//            while (!temp.matches(regex)) {
//                //systemPresenter.invalidInput();
//                temp = bufferedReader.readLine();
//            }
//            input = Integer.parseInt(temp);
//        } catch (IOException e) {
//            //systemPresenter.exceptionMessage();
//        }

//        switch (input) {
//            case 0:
//                //systemPresenter.logoutMessage();
//                break;
//            case 1:
//                new CatalogViewer(currentUsername, itemManager, userManager, tradeManager);
//                break;
//
//            case 2:
//                new InventoryEditor(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//            case 3:
//                new WishlistEditor(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//            case 4:
//                new TradeRequestViewer(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//            case 5:
//                new OngoingTradesViewer(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//            case 6:
//                /* View most recent three completed trades */
//                new CompletedTradesViewer(currentUser, itemManager, userManager, tradeManager).viewRecentThreeTrades();
//                break;
//
//            case 7:
//                /* View top three most frequent trading partners (only counts if trades are completed) */
//                new CompletedTradesViewer(currentUser, itemManager, userManager, tradeManager).viewTopThreeTrader();
//                break;
//
//            case 8:
//                /*
//                 * Unfreeze request option.
//                 * Only appears for frozen accounts.
//                 */
//                new AccountUnfreezer(currentUser, itemManager, userManager, tradeManager);
//                break;
//
//            case 9:
//                // change vacation status.
//                new Vacation(currentUser, itemManager, userManager, tradeManager);
//                break;
//        }

//    private void initializeJComponent(JComponent component, int x_pos, int y_pos, int width, int height){
//        component.setSize(new Dimension(width, height));
//
//        if(component instanceof JLabel){
//            ((JLabel) component).setText("AHHHHH");
//        }
//        component.setForeground(Color.BLACK);
//        component.setBackground(Color.BLACK);
//        this.add(component);
//        component.setLocation(x_pos, y_pos);
//
//    }

    private void initializeButton(JButton button, int width, int height, int xPos, int yPos) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setSize(new Dimension(width, height));
        button.setFocusPainted(false);
        this.add(button);
        button.setLocation(xPos, yPos);

    }

    @Override
    public String getUsername() {
        return currentUser.getUsername();
    }

    @Override
    public User getUser() {return currentUser;}
}
