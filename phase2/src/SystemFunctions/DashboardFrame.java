package SystemFunctions;

import NormalUserFunctions.NormalDashboard;
import AdminUserFunctions.AdminDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * JFrame that displays the user's dashboard
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-01
 * last modified 2020-08-07
 */

public class DashboardFrame extends JDialog {
    private Dashboard dashboard;
    private NormalDashboard normalDashboard;
    private AdminDashboard adminDashboard;

    private JDialog dashboardWindow;
    private JFrame parent;
    private JPanel userFunctionPanel;
    private JPanel userInputPanel;
    private JPanel notifPanel;
    private JPanel optionalPanel;
    private JButton removeButton;

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
    private final int CREATE = 11;
    private final int THRESHOLD = 12;

    /**
     * Makes a new modal window that displays the user functions of the user that is
     * currently logged in
     * @param dashboard the controller for the dashboard
     * @param parent    the parent window that made this one
     */
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
        removeButton = new JButton();

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
        profilePic.setMaximumSize(new Dimension(200, 200));
        profilePic.setMinimumSize(new Dimension(200, 200));
        userFunctionPanel.add(profilePic);

        if (dashboard.isAdmin()) {
            adminDashboard = (AdminDashboard) dashboard;
            drawAdminDash();
        } else {
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

    private void drawNormalDash() {
        JButton inventory = new JButton(normalDashboard.setUpDash(1));
        inventory.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(INVENTORY);
            drawListDisplay(normalDashboard.getInventory());
            drawOptionalDisplayPanel(normalDashboard.getPendingInventory(), INVENTORY);
        });

        JButton wishlist = new JButton(normalDashboard.setUpDash(2));
        wishlist.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(WISHLIST);
            drawListDisplay(normalDashboard.getWishlist());
        });

        JButton tradeRequest = new JButton(normalDashboard.setUpDash(3));
        tradeRequest.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(TRADE_REQUEST);
            drawListDisplay(normalDashboard.getReceivedTrades());
            drawOptionalDisplayPanel(normalDashboard.getInitiatedTrades(), TRADE_REQUEST);
        });

        JButton catalog = new JButton(normalDashboard.setUpDash(4));

        JButton ongoingTrade = new JButton(normalDashboard.setUpDash(5));
        ongoingTrade.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(ONGOING);
            drawListDisplay(normalDashboard.getOngoingTrades());
            drawOptionalInputPanel(ONGOING);
        });

        JButton completeTrade = new JButton(normalDashboard.setUpDash(6));
        completeTrade.addActionListener(e -> {
            resetEverything();
            drawListDisplay(normalDashboard.getRecentThreeTradesStrings());
            drawOptionalDisplayPanel(normalDashboard.getTopThreeTraderStrings(), COMPLETED);
        });

        JButton vacation = new JButton(normalDashboard.setUpDash(7));
        vacation.addActionListener(e -> {
            normalDashboard.editUserStatus();
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
        initializeButton(completeTrade, 200, 40, userFunctionPanel);
        initializeButton(vacation, 200, 40, userFunctionPanel);

        if(normalDashboard.isFrozen()) {
            JButton unfreeze = new JButton(normalDashboard.setUpDash(8));
            unfreeze.addActionListener(e -> {
                normalDashboard.sendUnfreezeRequest();
                resetEverything();
                dashboardWindow.remove(scrollablePane);
                dashboardWindow.repaint();
                dashboardWindow.setVisible(true);
            });
            initializeButton(unfreeze, 200, 40, userFunctionPanel);
        }
    }
    /**
     * Draws all JComponents for admin user's functions
     */
    public void drawAdminDash() {
        userInputPanel.removeAll();
        JButton catalogEditor = new JButton(adminDashboard.setUpDash(1));
        catalogEditor.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(CATALOG_EDITOR);
            drawListDisplay(adminDashboard.getPendingCatalog());
        });

        JButton freezer = new JButton(adminDashboard.setUpDash(2));
        freezer.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(FREEZE);
            drawListDisplay(adminDashboard.getFreezeList());
        });

        JButton unfreezer = new JButton(adminDashboard.setUpDash(3));
        unfreezer.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(UNFREEZE);
            drawListDisplay(adminDashboard.getUnfreezeRequests());
        });

        JButton threshold = new JButton(adminDashboard.setUpDash(4));
        threshold.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(THRESHOLD);
            drawListDisplay(adminDashboard.getThresholdStrings());
        });

        JButton adminCreator = new JButton(adminDashboard.setUpDash(5));
        adminCreator.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(CREATE);
            dashboardWindow.remove(scrollablePane);
            dashboardWindow.repaint();
            dashboardWindow.setVisible(true);
        });

        JButton undo = new JButton(adminDashboard.setUpDash(6));
        undo.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(UNDO);
            drawListDisplay(adminDashboard.getRevertibleNotifs());
        });

        initializeButton(catalogEditor, 200, 40, userFunctionPanel);
        initializeButton(freezer, 200, 40, userFunctionPanel);
        initializeButton(unfreezer, 200, 40, userFunctionPanel);
        initializeButton(threshold, 200, 40, userFunctionPanel);
        initializeButton(adminCreator, 200, 40, userFunctionPanel);
        initializeButton(undo, 200, 40, userFunctionPanel);
    }

    /**
     * Draws the JScrollpane in the CENTRE of the JFrame's border layout
     * @param displayItems the contents of the JScrollpane
     */
    public void drawListDisplay(String[] displayItems) {
        if (displayItems.length == 0) {
            dashboardWindow.remove(scrollablePane);
            nothingToDisplay.setText("Nothing here yet!");
            userInputPanel.remove(removeButton);
        } else {
            listDisplay = new JList<>(displayItems);
            listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollablePane.setViewportView(listDisplay);
            dashboardWindow.add(scrollablePane, BorderLayout.CENTER);
        }
        dashboardWindow.setVisible(true);
    }

    /**
     * Draws all JComponents necessary for the type of function on the SOUTH of the
     * JFrame's border layout
     * @param type int indicating the type of function
     */
    public void drawUserInputPane(int type) {
        switch (type) {
            case WISHLIST:
                userInputPanel.removeAll();
                removeButton.setText("Remove");
                initializeButton(removeButton, 100, 20, userInputPanel);
                userInputPanel.add(removeButton);
                userInputPanel.repaint();
                removeButton.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        normalDashboard.removeFromWishlist(index);
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getWishlist());
                    }
                });
                break;

            case INVENTORY:
                userInputPanel.removeAll();
                removeButton.setText("Remove");
                JButton addInv = new JButton("Add");
                JLabel name = new JLabel("Name: ");
                JTextField nameInput = new JTextField(20);
                JLabel description = new JLabel("Description: ");
                JTextField descripInput = new JTextField(20);
                userInputPanel.add(name);
                userInputPanel.add(nameInput);
                userInputPanel.add(description);
                userInputPanel.add(descripInput);
                userInputPanel.repaint();
                initializeButton(addInv, 100, 20, userInputPanel);
                initializeButton(removeButton, 100, 20, userInputPanel);

                addInv.addActionListener(e -> {
                    normalDashboard.addToInventory(nameInput.getText(), descripInput.getText());
                    nameInput.setText("");
                    descripInput.setText("");
                    drawPopUpMessage();
                    redrawDisplayList(normalDashboard.getInventory());
                    drawOptionalDisplayPanel(normalDashboard.getPendingInventory(), INVENTORY);
                });

                removeButton.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        normalDashboard.removeFromInventory(index);
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getInventory());
                    }
                });
                break;

            case TRADE_REQUEST:
                JToggleButton acceptOrDeny = new JToggleButton();
                JToggleButton permOrTemp = new JToggleButton();
                JLabel initialTime = new JLabel("Suggest Time:");
                JLabel initialPlace = new JLabel("Suggest Place:");
                JTextField initialTimeInput = new JTextField(10);
                JTextField initialPlaceInput = new JTextField(10);
                removeButton.setText("Confirm");
                initializeToggleButton(acceptOrDeny, "Accept Request", "Deny Request");
                initializeToggleButton(permOrTemp, "Permanent", "Temporary");


                break;
            case CATALOG_VIEWER:
                userInputPanel.removeAll();
                JButton trade = new JButton("Trade");
                initializeButton(trade, 100, 20, userInputPanel);
                trade.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        //redrawDisplayList();
                    }
                });
                break;

            case FREEZE:
                userInputPanel.removeAll();
                removeButton.setText("Freeze All");
                initializeButton(removeButton, 100, 20, userInputPanel);
                removeButton.addActionListener(e -> {
                    adminDashboard.freezeAll();
                    redrawDisplayList(adminDashboard.getFreezeList());
                });
                break;

            case UNFREEZE:
                //userInputPanel.removeAll();
                removeButton.setText("Unfreeze");
                initializeButton(removeButton, 100, 20, userInputPanel);
                removeButton.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        adminDashboard.unfreezeUser(index);
                    }
                    listDisplay.clearSelection();
                    redrawDisplayList(adminDashboard.getUnfreezeRequests());
                });
                break;

            case CREATE:
                JLabel username = new JLabel("Username:");
                JLabel email = new JLabel("Email:");
                JLabel password = new JLabel("Password:");
                JTextField usernameInput = new JTextField(10);
                JTextField emailInput = new JTextField(10);
                JTextField passwordInput = new JTextField(10);
                JButton addAdmin = new JButton("Create");
                addAdmin.addActionListener(e ->{
                    adminDashboard.createNewAdmin(usernameInput.getText(),
                            emailInput.getText(), passwordInput.getText());
                    usernameInput.setText("");
                    emailInput.setText("");
                    passwordInput.setText("");
                    drawPopUpMessage();
                });
                userInputPanel.add(username);
                userInputPanel.add(usernameInput);
                userInputPanel.add(email);
                userInputPanel.add(emailInput);
                userInputPanel.add(password);
                userInputPanel.add(passwordInput);
                initializeButton(addAdmin, 100, 20, userInputPanel);
                break;

            case CATALOG_EDITOR:
                JToggleButton approveOrDeny = new JToggleButton();
                initializeToggleButton(approveOrDeny, "Approve Item", "Deny Item");
                removeButton.setText("Confirm");
                removeButton.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        if (approveOrDeny.isSelected()) {
                            adminDashboard.rejectionPendingCatalog(listDisplay.getSelectedIndex());
                        } else {
                            adminDashboard.approvePendingCatalog(listDisplay.getSelectedIndex());
                        }
                    }
                    listDisplay.clearSelection();
                    redrawDisplayList(adminDashboard.getPendingCatalog());
                });
                userInputPanel.add(approveOrDeny);
                initializeButton(removeButton, 100, 20, userInputPanel);
                break;

            case THRESHOLD:
                JLabel weeklyTrade = new JLabel("Weekly Trade Max:");
                JLabel meetingEdit = new JLabel("Meeting Edit Max:");
                JLabel lendMin = new JLabel("Lend Minimum:");
                JLabel incompleteTrade = new JLabel("Incomplete Trade Max:");
                JTextField weeklyTradeInput = new JTextField(5);
                JTextField meetingEditInput = new JTextField(5);
                JTextField lendMinInput = new JTextField(5);
                JTextField incompleteTradeInput = new JTextField(5);
                JButton change = new JButton("Confirm");
                change.addActionListener(e -> {
                    String[] inputs = {weeklyTradeInput.getText(), meetingEditInput.getText(),
                            lendMinInput.getText(), incompleteTradeInput.getText()};

                    adminDashboard.changeThresholds(inputs);
                    redrawDisplayList(adminDashboard.getThresholdStrings());
                    weeklyTradeInput.setText("");
                    meetingEditInput.setText("");
                    lendMinInput.setText("");
                    incompleteTradeInput.setText("");
                    drawPopUpMessage();
                });
                userInputPanel.add(weeklyTrade);
                userInputPanel.add(weeklyTradeInput);

                userInputPanel.add(meetingEdit);
                userInputPanel.add(meetingEditInput);

                userInputPanel.add(lendMin);
                userInputPanel.add(lendMinInput);

                userInputPanel.add(incompleteTrade);
                userInputPanel.add(incompleteTradeInput);

                userInputPanel.add(change);
                break;

            case UNDO:
                userInputPanel.removeAll();
                removeButton.setText("Undo");
                initializeButton(removeButton, 100, 20, userInputPanel);
                removeButton.addActionListener(e ->{
                    if(!listDisplay.isSelectionEmpty()){
                        adminDashboard.revertUserAction(listDisplay.getSelectedIndex());
                    }
                    listDisplay.clearSelection();
                    redrawDisplayList(adminDashboard.getUnfreezeRequests());
                });
                break;
        }
        dashboardWindow.repaint();
        dashboardWindow.setVisible(true);
    }

    /**
     * Draws all JComponents necessary for the type of function on the EAST of the
     * JFrame's border layout
     * @param stringArray the content to display
     * @param type        int indicating the type of function
     */
    public void drawOptionalDisplayPanel(String[] stringArray, int type) {
        switch (type) {
            case INVENTORY:
                optionalLabelTitle.setText("Pending Inventory");
                break;
            case COMPLETED:
                optionalLabelTitle.setText("Top Three Trade Partners");
                break;
            case ONGOING:
                optionalLabelTitle.setText("Suggest Meeting Details");
                break;
            case TRADE_REQUEST:
                optionalLabelTitle.setText("Initiated Trade Requests");
                break;
        }
        optionalLabel.setText("");
        if (stringArray.length == 0) {
            optionalLabel.setText("Nothing here.");
        } else {
            StringBuilder stringBuilder = new StringBuilder("<html>");
            int index = 0;
            while (index < stringArray.length) {
                stringBuilder.append(stringArray[index]).append("<br/>");
                index++;
            }
            optionalLabel.setText(stringBuilder.toString());
        }
        optionalPanel.add(optionalLabelTitle);
        optionalPanel.add(optionalLabel);
        dashboardWindow.repaint();
        dashboardWindow.setVisible(true);
    }

    public void drawOptionalInputPanel(int type){
        switch (type){
            case ONGOING:
                optionalLabelTitle.setText("Suggest Meeting Details");
                JLabel timeSuggestion = new JLabel("Time:");
                JLabel placeSuggestion = new JLabel("Place:");
                JTextField timeSuggestionInput = new JTextField(10);
                JTextField placeSuggestionInput = new JTextField(10);
                JButton suggest = new JButton("Suggest");
                //suggest.addActionListener();

                break;
            case CATALOG_VIEWER:
                break;
        }
    }

    /**
     * Updates the JScrollpane once a change has been made to its content
     * @param displayList the new content to display
     */
    public void redrawDisplayList(String[] displayList) {
        dashboardWindow.revalidate();
        drawListDisplay(displayList);
        dashboardWindow.repaint();
    }

    /**
     * Initializes given JButton with width, height, and the panel to draw it on
     * @param button    the JButton to initialize
     * @param width     the width of the JButton
     * @param height    the height of the JButton
     * @param panel     the panel to draw it on
     */
    public void initializeButton(JButton button, int width, int height, JPanel panel) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setFocusPainted(false);
        panel.add(button);
    }

    public void initializeToggleButton(JToggleButton button, String ogText, String clickedText){
        button.setText(ogText);
        button.addChangeListener(e -> {
            if (button.isSelected()) {
                button.setText(clickedText);
            } else {
                button.setText(ogText);
            }
        });
    }

    /**
     * Resets the JFrame
     */
    public void resetEverything() {
        for (ActionListener actionListener : removeButton.getActionListeners()) {
            removeButton.removeActionListener(actionListener);
        }
        nothingToDisplay.setText("");
        userInputPanel.removeAll();
        optionalPanel.removeAll();
    }

    /**
     * Draws a pop up window containing a warning message if necessary
     */
    public void drawPopUpMessage(){
        if(!dashboard.getPopUpMessage().isEmpty()) {
            JOptionPane.showMessageDialog(parent, dashboard.getPopUpMessage());
            dashboard.resetPopUpMessage();
        }
    }

}