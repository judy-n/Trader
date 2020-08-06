package SystemFunctions;
import NormalUserFunctions.NormalDashboard;
import AdminUserFunctions.AdminDashboard;
import javax.swing.*;
import java.awt.*;
/**
 * JFrame that displays the user's dashboard
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-01
 * last modified 2020-08-05
 */

public class DashboardFrame extends JDialog{
    private Dashboard dashboard;
    private NormalDashboard normalDashboard;
    private AdminDashboard adminDashboard;
    private JDialog dashboardWindow;
    private JFrame parent;
    private JPanel userFunctionPanel;
    private JPanel userInputPanel;
    private JPanel notifPanel;
    private JPanel optionalPanel;
    private JList<String> listDisplay;
    private JScrollPane scrollablePane;
    private JLabel nothingToDisplay;
    private JLabel optionalLabel;
    private JLabel optionalLabelTitle;
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
        notifPanel = new JPanel();
        optionalPanel = new JPanel();
        nothingToDisplay = new JLabel();
        optionalLabel = new JLabel();
        optionalLabelTitle = new JLabel();
        scrollablePane = new JScrollPane();

        userFunctionPanel.setLayout(new BoxLayout(userFunctionPanel, BoxLayout.Y_AXIS));
        userInputPanel.setLayout(new FlowLayout());
        notifPanel.setLayout(new FlowLayout());
        optionalPanel.setLayout(new BoxLayout(optionalPanel, BoxLayout.Y_AXIS));

        optionalPanel.setPreferredSize(new Dimension(300, 576));

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
            adminDashboard = (AdminDashboard) dashboard;
            drawAdminDash();
        }else {
            normalDashboard = (NormalDashboard) dashboard;
            drawNormalDash();
        }
        dashboardWindow.add(userFunctionPanel, BorderLayout.WEST);
        dashboardWindow.add(userInputPanel, BorderLayout.SOUTH);
        dashboardWindow.add(notifPanel, BorderLayout.NORTH);
        dashboardWindow.add(optionalPanel, BorderLayout.EAST);
        dashboardWindow.add(scrollablePane, BorderLayout.CENTER);
        dashboardWindow.add(nothingToDisplay, BorderLayout.CENTER);
        scrollablePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollablePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        dashboardWindow.setVisible(true);

    }
    private void drawNormalDash(){
        JButton inventory = new JButton("Inventory Editor");
        inventory.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(INVENTORY);
            drawListDisplay(normalDashboard.getInventory());
            drawOptionalPanel(normalDashboard.getPendingInventory(), INVENTORY);
        });

        JButton wishlist = new JButton("Wishlist Editor");
        wishlist.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(WISHLIST);
            drawListDisplay(normalDashboard.getWishlist());
        }
        );

        JButton tradeRequest = new JButton("View Trade Requests");
        JButton catalog = new JButton("View Catalog");

        JButton ongoingTrade = new JButton("View Ongoing Trades");

        JButton completeTrade = new JButton("View Completed Trades");
        completeTrade.addActionListener(e -> {
            resetEverything();
            drawListDisplay(normalDashboard.getRecentThreeTradesStrings());
            drawOptionalPanel(normalDashboard.getTopThreeTraderStrings(), COMPLETED);
        });

        JButton vacation = new JButton("Edit Vacation Status");
        vacation.addActionListener(e -> {
            normalDashboard.editUserStatus();
            resetEverything();
            dashboardWindow.remove(scrollablePane);
            dashboardWindow.repaint();
            dashboardWindow.setVisible(true);
        });

        JButton unfreeze = new JButton("Send Unfreeze Request");
        unfreeze.addActionListener(e -> {
            normalDashboard.sendUnfreezeRequest();
            resetEverything();
            dashboardWindow.remove(scrollablePane);
            dashboardWindow.repaint();
            dashboardWindow.setVisible(true);
        });

        initializeButton(inventory, 200, 40, userFunctionPanel);
        initializeButton(wishlist, 200, 40, userFunctionPanel);
        initializeButton(tradeRequest, 200, 40, userFunctionPanel);
        initializeButton(catalog, 200, 40, userFunctionPanel);
        initializeButton(ongoingTrade, 200, 40, userFunctionPanel);
        initializeButton(completeTrade, 200,40,userFunctionPanel);
        initializeButton(vacation, 200, 40, userFunctionPanel);
        initializeButton(unfreeze, 200, 40, userFunctionPanel);
    }

    private void drawAdminDash(){
        //userInputPanel.removeAll();
        JButton catalogEditor = new JButton("Catalog Editor");
        JButton freezer = new JButton("Freeze Accounts");
        freezer.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(FREEZE);
            drawListDisplay(adminDashboard.getFreezeList());

        });

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
            dashboardWindow.remove(scrollablePane);
            nothingToDisplay.setText("Nothing here.");
            //dashboardWindow.add(nothingToDisplay, BorderLayout.CENTER);
            userInputPanel.removeAll();
        }else {
            listDisplay = new JList<>(displayItems);
            //System.out.println(displayItems);
            listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollablePane.setViewportView(listDisplay);
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
                userInputPanel.repaint();
                remove.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        normalDashboard.removeFromWishlist(index);
                        redrawDisplayList(normalDashboard.getWishlist());
                    }
                });
                break;

            case INVENTORY:
                userInputPanel.removeAll();
                JButton removeInv = new JButton("Remove");
                JButton addInv =  new JButton("Add");
                JLabel name = new JLabel("Name: ");
                JTextField nameInput = new JTextField(20);
                JLabel description = new JLabel("Description: ");
                JTextField descripInput = new JTextField(20);
                userInputPanel.add(name);
                userInputPanel.add(nameInput);
                userInputPanel.add(description);
                userInputPanel.add(descripInput);
                userInputPanel.repaint();
                initializeButton(addInv, 100,20, userInputPanel);
                initializeButton(removeInv, 100,20, userInputPanel);

                addInv.addActionListener(e -> {
                    if(!normalDashboard.validateInputInv(nameInput.getText(), descripInput.getText()))
                        normalDashboard.addToInventory(nameInput.getText(), descripInput.getText());
                    redrawDisplayList(normalDashboard.getInventory());
                });

                removeInv.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        if(normalDashboard.validateRemovalInv(listDisplay.getSelectedIndex())) {
                            normalDashboard.removeFromInventory(index);
                        }
                        redrawDisplayList(normalDashboard.getInventory());
                    }

                });
                break;

            case CATALOG_VIEWER:
                userInputPanel.removeAll();
                JButton trade = new JButton("Trade");
                initializeButton(trade,100,20,userInputPanel);
                trade.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                        int index = listDisplay.getSelectedIndex();
                        //redrawDisplayList();

                    }
                });
                break;

            case FREEZE:
                userInputPanel.removeAll();
                JButton freezeAll = new JButton("Freeze All");
                initializeButton(freezeAll, 100,20, userInputPanel);
                freezeAll.addActionListener(e -> adminDashboard.freezeAll());

        }

        dashboardWindow.setVisible(true);
    }

    private void drawOptionalPanel(String[] stringArray, int type){
        switch (type){
            case INVENTORY:
                optionalLabelTitle.setText("Pending Inventory");
                break;
            case COMPLETED:
                optionalLabelTitle.setText("Top Three Trade Partners");
                break;
                //case
        }
        optionalLabel.setText("");
        if(stringArray.length == 0) {
            optionalLabel.setText("Nothing here.");
        }else {
            StringBuilder stringBuilder = new StringBuilder("<html>");
            int index = 0;
            while (index < stringArray.length) {
                stringBuilder.append(stringArray[index] + "<br/>");
                index++;
            }
            optionalLabel.setText(stringBuilder.toString());
        }
        optionalPanel.add(optionalLabelTitle);
        optionalPanel.add(optionalLabel);
        dashboardWindow.repaint();
        dashboardWindow.setVisible(true);
    }

    private void redrawDisplayList(String[] displayList){
        listDisplay.clearSelection();
        dashboardWindow.revalidate();
        drawListDisplay(displayList);
        dashboardWindow.repaint();
    }

    private void initializeButton(JButton button, int width, int height, JPanel panel) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setMaximumSize(new Dimension(width,height));
        button.setMinimumSize(new Dimension(width,height));
        button.setFocusPainted(false);
        panel.add(button);
    }

    private void resetEverything(){
        nothingToDisplay.setText("");
        userInputPanel.removeAll();
        optionalPanel.removeAll();
    }

}


