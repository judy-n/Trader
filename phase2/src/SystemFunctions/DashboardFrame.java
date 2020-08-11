package SystemFunctions;

import NormalUserFunctions.DemoDashboard;
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
 * last modified 2020-08-07
 */

public class DashboardFrame extends JDialog {
    private Dashboard dashboard;
    private NormalDashboard normalDashboard;
    private AdminDashboard adminDashboard;
    private DemoDashboard demoDashboard;

    private JDialog dashboardWindow;
    private JFrame parent;
    private JPanel userFunctionPanel;
    private JPanel userInputPanel;
    private JPanel notifPanel;
    private JPanel optionalPanel;
    private JButton editTrade;

    private JList<String> listDisplay;
    private JScrollPane scrollablePane;
    private JLabel nothingToDisplay;
    private JLabel optionalLabel;
    private JLabel optionalLabelTitle;
    private final int DEMO = 0;
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
        listDisplay = new JList<>();

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

        if (dashboard.getType() == 0) {
            adminDashboard = (AdminDashboard) dashboard;
            drawAdminDash();
        } else if(dashboard.getType() == 1) {
            normalDashboard = (NormalDashboard) dashboard;
            drawNormalDash();
        }else{
            demoDashboard = (DemoDashboard) dashboard;
            drawDemoDash();
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
        catalog.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(CATALOG_VIEWER);
            drawListDisplay(normalDashboard.getCatalog());
        });

        JButton ongoingTrade = new JButton(normalDashboard.setUpDash(5));
        ongoingTrade.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(ONGOING);
            drawListDisplay(normalDashboard.getOngoingTrades());
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

        initializeButton(catalog, 200, 40, userFunctionPanel);
        initializeButton(inventory, 200, 40, userFunctionPanel);
        initializeButton(wishlist, 200, 40, userFunctionPanel);
        initializeButton(tradeRequest, 200, 40, userFunctionPanel);
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

    private void drawAdminDash() {
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

    private void drawDemoDash(){
        JButton demoCatalog = new JButton("View Catalog");
        demoCatalog.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(DEMO);
            drawListDisplay(demoDashboard.getDemoCatalog());
        });
        initializeButton(demoCatalog, 200, 40, userFunctionPanel);
    }

    /**
     * Draws the JScrollpane in the CENTRE of the JFrame's border layout
     * @param displayItems the contents of the JScrollpane
     */
    public void drawListDisplay(String[] displayItems) {
        if (displayItems.length == 0) {
            dashboardWindow.remove(scrollablePane);
            nothingToDisplay.setText("Nothing here yet!");
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
            case DEMO:
                JButton fakeTrade = new JButton("Trade");
                fakeTrade.addActionListener(e -> JOptionPane.showMessageDialog(parent, "Sign up to trade ;)"));
                initializeButton(fakeTrade, 100,40, userInputPanel);
                break;

            case WISHLIST:
                JButton removeWishlist = new JButton("Remove");
                removeWishlist.setText("Remove");
                initializeButton(removeWishlist, 100, 20, userInputPanel);
                userInputPanel.add(removeWishlist);
                userInputPanel.repaint();
                removeWishlist.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        int index = listDisplay.getSelectedIndex();
                        normalDashboard.removeFromWishlist(index);
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getWishlist());
                    }
                });
                break;

            case INVENTORY:
                JButton removeInv = new JButton("Remove");
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
                initializeButton(removeInv, 100, 20, userInputPanel);

                addInv.addActionListener(e -> {
                    normalDashboard.addToInventory(nameInput.getText(), descripInput.getText());
                    nameInput.setText("");
                    descripInput.setText("");
                    drawPopUpMessage();
                    redrawDisplayList(normalDashboard.getInventory());
                    drawOptionalDisplayPanel(normalDashboard.getPendingInventory(), INVENTORY);
                });

                removeInv.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.removeFromInventory(listDisplay.getSelectedIndex());
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getInventory());
                    }
                });
                break;

            case TRADE_REQUEST:
                JToggleButton acceptOrDeny = new JToggleButton();
                JToggleButton permOrTemp = new JToggleButton();
                JTextField initialTimeInput = new JTextField(10);
                JTextField initialPlaceInput = new JTextField(10);
                JButton confirm = new JButton("Confirm");
                confirm.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                        if(acceptOrDeny.isSelected()){
                            normalDashboard.rejectTradeRequest(listDisplay.getSelectedIndex());
                        }else{
                            normalDashboard.acceptTradeRequest(listDisplay.getSelectedIndex(),
                                    initialTimeInput.getText(), initialPlaceInput.getText(), permOrTemp.isSelected());

                        }
                    }
                    listDisplay.clearSelection();
                    redrawDisplayList(normalDashboard.getReceivedTrades());
                });
                initializeToggleButton(acceptOrDeny, "Accept Request", "Deny Request", userInputPanel);
                initializeLabelledTextField(initialTimeInput, "Suggest Time:" , userInputPanel);
                initializeLabelledTextField(initialPlaceInput, "Suggest Place:", userInputPanel);
                initializeToggleButton(permOrTemp, "Permanent", "Temporary", userInputPanel);
                initializeButton(confirm, 100, 20, userInputPanel);
                break;

            case CATALOG_VIEWER:
                JToggleButton wishlistOrTrade = new JToggleButton("Trade");
                JButton trade = new JButton("Confirm");
                JButton sendTrade = new JButton("Send Trade Request");
                sendTrade.setEnabled(false);
                trade.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        if(wishlistOrTrade.isSelected()){
                            normalDashboard.addToWishlist(listDisplay.getSelectedIndex());
                            drawPopUpMessage();
                        }else{
                            normalDashboard.setTradeLendItemIndex(listDisplay.getSelectedIndex());
                            drawOptionalDisplayPanel(normalDashboard.getSuggestedItems(listDisplay.getSelectedIndex()),CATALOG_VIEWER);
                            listDisplay.clearSelection();
                            redrawDisplayList(normalDashboard.currentUserInventoryInCatalog());
                            sendTrade.setEnabled(true);
                            trade.setEnabled(false);
                        }
                    }
                });

                sendTrade.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                        normalDashboard.requestItemInTwoWayTrade(listDisplay.getSelectedIndex());
                    }else{
                        normalDashboard.requestItemInOneWayTrade();
                    }
                    listDisplay.clearSelection();
                    redrawDisplayList(normalDashboard.getCatalog());
                    sendTrade.setEnabled(false);
                    trade.setEnabled(true);
                });

                initializeToggleButton(wishlistOrTrade, "Trade", "Wishlist", userInputPanel);
                initializeButton(trade, 100, 20, userInputPanel);
                initializeButton(sendTrade, 100, 20, userInputPanel);
                break;

            case ONGOING:
                JButton cancelTrade = new JButton("Cancel");
                JButton agreeTrade = new JButton("Agree");
                JButton confirmTransaction = new JButton("Confirm Transaction");
                editTrade = new JButton("Edit Trade");

                cancelTrade.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                        normalDashboard.cancelTrade(listDisplay.getSelectedIndex());
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                    }
                });

                confirmTransaction.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                        normalDashboard.confirmTrade(listDisplay.getSelectedIndex());
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                    }
                });

                agreeTrade.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                        normalDashboard.agreeTrade(listDisplay.getSelectedIndex());
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                    }
                });

                editTrade.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()){
                    editTrade.setEnabled(false);
                        new JOptionPane(normalDashboard.getNumEdits(listDisplay.getSelectedIndex()));
                        drawOptionalInputPanel(ONGOING);
                    }
                });

                initializeButton(agreeTrade, 100, 20, userInputPanel);
                initializeButton(confirmTransaction, 100, 20, userInputPanel);
                initializeButton(editTrade, 100, 20, userInputPanel);
                initializeButton(cancelTrade, 100, 20, userInputPanel);
                break;

            case FREEZE:
                JButton freezeAll = new JButton("Freeze All");
                initializeButton(freezeAll, 100, 20, userInputPanel);
                freezeAll.addActionListener(e -> {
                    adminDashboard.freezeAll();
                    redrawDisplayList(adminDashboard.getFreezeList());
                });
                break;

            case UNFREEZE:
                JButton unfreeze = new JButton("Unfreeze");
                initializeButton(unfreeze, 100, 20, userInputPanel);
                unfreeze.addActionListener(e -> {
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
                JButton confirmEdit = new JButton("Confirm");
                confirmEdit.addActionListener(e -> {
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
                initializeToggleButton(approveOrDeny, "Approve Item", "Deny Item", userInputPanel);
                initializeButton(confirmEdit, 100, 20, userInputPanel);
                break;

            case THRESHOLD:
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
                initializeLabelledTextField(weeklyTradeInput, "Weekly Trade Max:", userInputPanel);
                initializeLabelledTextField(meetingEditInput, "Meeting Edit Max:", userInputPanel);
                initializeLabelledTextField(lendMinInput, "Lend Minimum:", userInputPanel);
                initializeLabelledTextField(incompleteTradeInput, "Incomplete Trade max:", userInputPanel);
                initializeButton(change, 100, 20, userInputPanel);
                break;

            case UNDO:
                JButton undo = new JButton("Undo");
                initializeButton(undo, 100, 20, userInputPanel);
                undo.addActionListener(e ->{
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
            case CATALOG_VIEWER:
                optionalLabelTitle.setText("Suggested Items");
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
                JTextField timeSuggestionInput = new JTextField(20);
                timeSuggestionInput.setMaximumSize(timeSuggestionInput.getPreferredSize());
                JTextField placeSuggestionInput = new JTextField(20);
                placeSuggestionInput.setMaximumSize(placeSuggestionInput.getPreferredSize());
                JButton suggest = new JButton("Suggest");
                suggest.addActionListener(e -> {
                    if(!listDisplay.isSelectionEmpty()) {
                        normalDashboard.editOngoingTrade(listDisplay.getSelectedIndex(),
                                timeSuggestionInput.getText(), placeSuggestionInput.getText());
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                        optionalPanel.removeAll();
                        editTrade.setEnabled(true);
                    }
                });
                optionalPanel.add(optionalLabelTitle);
                optionalPanel.add(timeSuggestion);
                optionalPanel.add(timeSuggestionInput);
                optionalPanel.add(placeSuggestion);
                optionalPanel.add(placeSuggestionInput);
                initializeButton(suggest,100,30, optionalPanel);
                dashboardWindow.repaint();
                dashboardWindow.setVisible(true);
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
        nothingToDisplay.setText("");
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

    private void initializeToggleButton(JToggleButton button, String ogText, String clickedText, JPanel panel){
        button.setText(ogText);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.addChangeListener(e -> {
            if (button.isSelected()) {
                button.setText(clickedText);
            } else {
                button.setText(ogText);
            }
        });
        panel.add(button);
    }

    private void initializeLabelledTextField(JTextField textField, String text, JPanel panel){
        JLabel label = new JLabel(text);
        panel.add(label);
        panel.add(textField);
    }


    /**
     * Resets the JFrame
     */
    private void resetEverything() {
//        for (ActionListener actionListener : removeButton.getActionListeners()) {
//            removeButton.removeActionListener(actionListener);
//        }
        nothingToDisplay.setText("");
        userInputPanel.removeAll();
        optionalPanel.removeAll();
    }


    private void drawPopUpMessage(){
        if(!dashboard.getPopUpMessage().isEmpty()) {
            JOptionPane.showMessageDialog(parent, dashboard.getPopUpMessage());
            dashboard.resetPopUpMessage();
        }
    }

}