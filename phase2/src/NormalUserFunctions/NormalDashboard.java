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
    private String currUsername;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private NotificationSystem notifSystem;


    /**
     * Creates a <NormalDashboard></NormalDashboard> with the given normal user,
     * item/user/trade managers, and notification system.
     *
     * @param username     the username of the normal user who's currently logged in
     * @param itemManager  the system's item manager
     * @param userManager  the system's user manager
     * @param tradeManager the system's trade manager
     * @param notifSystem  the system's notification manager
     */
    public NormalDashboard(String username, ItemManager itemManager, UserManager userManager,
                           TradeManager tradeManager, NotificationSystem notifSystem) {
        this.currUsername = username;
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
//        JButton userProfilePic = new JButton();
//        userProfilePic.setSize(new Dimension(85, 110));
//        userProfilePic.setBackground(Color.BLACK);
//        this.add(userProfilePic);
//        userProfilePic.setLocation(30, 30);
//        JButton inventory = new JButton("Inventory");
//        initializeButton(inventory, 200,30,30,150);
        //inventory.addActionListener(e -> drawPopUpListViewer());
//        JLabel inventory = new JLabel("Inventory");
//        initializeLabel(inventory, 200,30, 30, 150);
//        JLabel wishlist = new JLabel("Wishlist");
//        initializeLabel(wishlist, 200,30,30, 190);
//        String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
//        String labelss[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
//        JList<String> catalogDisplayList = new JList<>(labels);
//        JList<String> inventoryList = new JList<>(labelss);
//
//        //catalogDisplayList.setBounds(30,150, 100,100);
//        catalogDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        JScrollPane scrollableCatalog = new JScrollPane(catalogDisplayList);
//        JScrollPane scrollableInventory = new JScrollPane(inventoryList);
//
//        //scrollableCatalog.setPreferredSize(new Dimension(200,100));
//        scrollableInventory.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollableInventory.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollableCatalog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollableCatalog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        this.add(scrollableCatalog, BorderLayout.CENTER);
        //this.add(scrollableInventory, BorderLayout.EAST);
    }

    private void drawPopUpListViewer(){
        String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        JList<String> catalogDisplayList = new JList<>(labels);
        catalogDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollableCatalog = new JScrollPane(catalogDisplayList);
        scrollableCatalog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableCatalog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


    }

    private void initializeLabel(JLabel label, int width, int height, int x_pos, int y_pos){
        label.setSize(width, height);
        label.setForeground(Color.BLACK);
        this.add(label);
        label.setLocation(x_pos, y_pos);

    }

//        String regex = "[0-7]";
//
//        // user frozen and on vacation
//        if ((currentUser.getIsFrozen()) && (currentUser.getIsOnVacation())) {
//            regex = "[0-8]+[10]";
//            //systemPresenter.normalDashboard(2);
//        }
//
//        // user frozen and not on vacation
//        else if ((currentUser.getIsFrozen()) && !(currentUser.getIsOnVacation())) {
//            regex = "[0-9]";
//            //systemPresenter.normalDashboard(3);
//        }
//
//        // why is this regex not right?? i dont see it.
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
//                new CatalogViewer(currUsername, itemManager, userManager, tradeManager);
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
        return currUsername;
    }

    @Override
    public User getUser() {return currentUser;}
}
