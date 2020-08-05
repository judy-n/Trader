package SystemFunctions;
import NormalUserFunctions.NormalDashboard;
import javax.swing.*;
import java.awt.*;
/**
 * JFrame that displays the user's dashboard
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-01
 * last modified 2020-08-04
 */

public class DashboardFrame extends JDialog{
    private Dashboard dashboard;
    private JDialog dashboardWindow;
    private JFrame parent;
    private JPanel userFunctionPanel;
    private JPanel userInputPanel;
    private JList<String> listDisplay;
    private JScrollPane scrollablePane;
    private final int CATALOG_VIEWER = 1;
    private final int INVENTORY = 2;
    private final int WISHLIST = 3;
    private final int TRADE_REQUEST = 4;
    private final int ONGOING = 5;
    private final int COMPLETED = 6;
    private final int CATALOG_EDITOR = 7;
    private final int FREEZE = 8;
    private final int UNFREEZE = 9;
    private final int UNDO = 10;

    public DashboardFrame(Dashboard dashboard, JFrame parent) {
        this.dashboard = dashboard;
        this.parent = parent;
        String username = dashboard.getUsername();
        userFunctionPanel = new JPanel();
        userInputPanel = new JPanel();
        userFunctionPanel.setLayout(new BoxLayout(userFunctionPanel, BoxLayout.Y_AXIS));
        userInputPanel.setLayout(new FlowLayout());
        dashboardWindow = new JDialog(parent, "Dashboard | " + username, true);
        dashboardWindow.setSize(820, 576);
        dashboardWindow.setResizable(false);
        dashboardWindow.setUndecorated(false);
        dashboardWindow.setLayout(new BorderLayout());
        dashboardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton profilePic = new JButton();
        profilePic.setBackground(Color.BLACK);
        profilePic.setMaximumSize(new Dimension(200,200));
        profilePic.setMinimumSize(new Dimension(200,200));
        userFunctionPanel.add(profilePic);

        if(dashboard.isAdmin()){
            drawAdminDash();
        }else {
            drawNormalDash();
        }
        dashboardWindow.add(userFunctionPanel, BorderLayout.WEST);
        dashboardWindow.add(userInputPanel, BorderLayout.SOUTH);
        dashboardWindow.setVisible(true);

    }
    private void drawNormalDash(){
        userInputPanel.removeAll();
        JButton inventory = new JButton("Inventory Editor");
        JButton wishlist = new JButton("Wishlist Editor");
        wishlist.addActionListener(e -> {
            drawListDisplay(((NormalDashboard) dashboard).getWishlist());
            drawUserInputPane(WISHLIST);
        }
        );

        JButton tradeRequest = new JButton("View Trade Requests");
        JButton catalog = new JButton("View Catalog");
        JButton ongoingTrade = new JButton("View Ongoing Trades");

        JButton vacation = new JButton("Edit Vacation Status");
        vacation.addActionListener(e -> ((NormalDashboard) dashboard).editUserStatus());

        JButton unfreeze = new JButton("Send Unfreeze Request");
        unfreeze.addActionListener(e -> ((NormalDashboard) dashboard).sendUnfreezeRequest());

        initializeButton(inventory, 200, 40, userFunctionPanel);
        initializeButton(wishlist, 200, 40, userFunctionPanel);
        initializeButton(tradeRequest, 200, 40, userFunctionPanel);
        initializeButton(catalog, 200, 40, userFunctionPanel);
        initializeButton(ongoingTrade, 200, 40, userFunctionPanel);
        initializeButton(vacation, 200, 40, userFunctionPanel);
        initializeButton(unfreeze, 200, 40, userFunctionPanel);
    }

    private void drawAdminDash(){
        userInputPanel.removeAll();
        JButton catalogEditor = new JButton("Catalog Editor");
        JButton freezer = new JButton("Freeze Accounts");
        JButton unfreezer = new JButton("UnFreeze Accounts");
        JButton threshold = new JButton("Threshold Editor");
        JButton adminCreator = new JButton("Create New Admin");
        JButton undo = new JButton("Undo User Activity");

        initializeButton(catalogEditor, 200, 40, userFunctionPanel);
        initializeButton(freezer, 200, 40, userFunctionPanel);
        initializeButton(unfreezer, 200, 40, userFunctionPanel);
        initializeButton(threshold, 200, 40, userFunctionPanel);
        initializeButton(adminCreator, 200, 40, userFunctionPanel);
        initializeButton(undo, 200, 40, userFunctionPanel);
    }

    private void drawListDisplay(String[] displayItems){
        if(displayItems.length==0) {
            JLabel nothingToDisplay = new JLabel("Nothing here.");
            dashboardWindow.add(nothingToDisplay, BorderLayout.CENTER);
        }else {
            listDisplay = new JList<>(displayItems);
            listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollablePane = new JScrollPane(listDisplay);
            scrollablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollablePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            dashboardWindow.add(scrollablePane, BorderLayout.CENTER);
        }
        dashboardWindow.setVisible(true);
    }

    private void drawUserInputPane(int type){
        switch (type) {
            case WISHLIST:
                userInputPanel.removeAll();
                JButton remove = new JButton("Remove");
                initializeButton(remove, 100,20, userInputPanel);
                userInputPanel.add(remove);
                remove.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        ((NormalDashboard) dashboard).removeFromWishlist(index);
                        listDisplay.clearSelection();
                        dashboardWindow.remove(scrollablePane);
                        dashboardWindow.revalidate();
                        drawListDisplay(((NormalDashboard)dashboard).getWishlist());
                        //System.out.println(((NormalDashboard)dashboard).getWishlist().length);
                        dashboardWindow.repaint();
                    }
                });
                break;

        }
        dashboardWindow.setVisible(true);
    }



    private void initializeButton(JButton button, int width, int height, JPanel panel) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setMaximumSize(new Dimension(width,height));
        button.setMinimumSize(new Dimension(width,height));
        button.setFocusPainted(false);
        panel.add(button);
    }


    //For my reference V
//    private void displayList(String [] arr){
//        JPanel userInputPanel = new JPanel();
//        userInputPanel.setLayout(new FlowLayout());
//
//        JList<String> catalogDisplayList = new JList<>(arr);
//        catalogDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        JScrollPane scrollableCatalog = new JScrollPane(catalogDisplayList);
//        scrollableCatalog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollableCatalog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        DashboardWindow.add(scrollableCatalog, BorderLayout.CENTER);
//        JLabel name = new JLabel("name:");
//        JTextField nameInput = new JTextField(20);
//        JLabel descrip = new JLabel("Description");
//        JTextField descripInput = new JTextField(20);
//        JButton enter = new JButton("Add");
//        userInputPanel.add(name);
//        userInputPanel.add(nameInput);
//        userInputPanel.add(descrip);
//        userInputPanel.add(descripInput);
//        userInputPanel.add(enter);
//        DashboardWindow.add(userInputPanel, BorderLayout.SOUTH);
//        DashboardWindow.setVisible(true);
//
//    }

}


