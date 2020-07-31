package NormalUserFunctions;
import Entities.User;
import SystemFunctions.Dashboard;
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
 * last modified 2020-07-30
 */
public class NormalDashboard extends Dashboard {
    private NormalUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private TradeManager tradeManager;

    /**
     * Creates a <NormalDashboard></NormalDashboard> with the given normal user and item/user/trade managers.
     *
     * @param user the normal user who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     * @param tm   the system's trade manager
     */
    public NormalDashboard(NormalUser user, ItemManager im, UserManager um, TradeManager tm) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        tradeManager = tm;

        this.setPreferredSize(new Dimension(820, 576));
        this.setLayout(null);
        drawPanels();
    }


        private void drawPanels(){
            JButton login = new JButton("WHY");
            JButton signUp = new JButton("startMenuPresenter.signUpSystem(0)");
            JButton demo = new JButton("startMenuPresenter.startMenu(4)");
            JButton endProgram = new JButton("startMenuPresenter.startMenu(5)");

            initializeButton(login, 200, 40, 160, 190);
            initializeButton(signUp, 200, 40, 440, 190);
            initializeButton(demo, 200, 40, 310, 250);
            initializeButton(endProgram, 200, 40, 310, 310);

            JLabel welcomeText = new JLabel("startMenuPresenter.startMenu(1)");

            //welcomeText.setFont(font);
            welcomeText.setSize(new Dimension(300,40));

            welcomeText.setForeground(Color.BLACK);

            this.add(welcomeText);
            welcomeText.setLocation(310, 50);
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
//            regex = "[0-7]+[10]";
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
//                new CatalogViewer(currentUser, itemManager, userManager, tradeManager);
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
//                // set on vacation status.
//                currentUser.onVacation();
//                break;
//
//            case 10:
//                // set not on vacation status.
//                currentUser.notOnVacation();
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

    private void initializeButton(JButton button, int width, int height, int xPos, int yPos){
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setSize(new Dimension(width, height));
        button.setFocusPainted(false);
        this.add(button);
        button.setLocation(xPos, yPos);

    }
    @Override
    public User getUser(){
        return currentUser;
    }
}
