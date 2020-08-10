//package SystemFunctions;
//import NormalUserFunctions.NormalDashboard;
//
//import javax.swing.*;
//
//public class NormalDashFrame extends DashboardFrame {
//    private NormalDashboard normalDashboard;
//    private JDialog dashboardWindow;
//    private JFrame parent;
//    private JPanel userFunctionPanel = new JPanel();
//    private JPanel userInputPanel = new JPanel();
//    private JPanel notifPanel = new JPanel();
//    private JPanel optionalPanel;
//    private JButton removeButton;
//
//    private JList<String> listDisplay;
//    private JScrollPane scrollablePane;
//    private JLabel nothingToDisplay;
//    private JLabel optionalLabel;
//    private JLabel optionalLabelTitle;
//
//    private final int CATALOG_VIEWER = 1;
//    private final int INVENTORY = 2;
//    private final int WISHLIST = 3;
//    private final int TRADE_REQUEST = 4;
//    private final int ONGOING = 5;
//    private final int COMPLETED = 6;
//
//    /**
//     * Makes a new modal window that displays the user functions of the user that is
//     * currently logged in
//     *
//     * @param normalDashboard the controller for the dashboard
//     * @param parent          the parent window that made this one
//     */
//    public NormalDashFrame(NormalDashboard normalDashboard, JFrame parent) {
//
//        super(normalDashboard, parent, new JPanel(), new JPanel(), new JPanel(), new JPanel(),
//                new JButton(), new JList<>(), new JScrollPane(), new JLabel());
//        this.normalDashboard = normalDashboard;
//        drawDash();
//    }
//
//    @Override
//    public void drawDash() {
//        JButton inventory = new JButton(normalDashboard.setUpDash(1));
//        inventory.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(INVENTORY);
//            drawListDisplay(normalDashboard.getInventory());
//            drawOptionalDisplayPanel(normalDashboard.getPendingInventory(), INVENTORY);
//        });
//
//        JButton wishlist = new JButton(normalDashboard.setUpDash(2));
//        wishlist.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(WISHLIST);
//            drawListDisplay(normalDashboard.getWishlist());
//        });
//
//        JButton tradeRequest = new JButton(normalDashboard.setUpDash(3));
//        tradeRequest.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(TRADE_REQUEST);
//            drawListDisplay(normalDashboard.getReceivedTrades());
//            drawOptionalDisplayPanel(normalDashboard.getInitiatedTrades(), TRADE_REQUEST);
//        });
//
//        JButton catalog = new JButton(normalDashboard.setUpDash(4));
//
//        JButton ongoingTrade = new JButton(normalDashboard.setUpDash(5));
//        ongoingTrade.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(ONGOING);
//            drawListDisplay(normalDashboard.getOngoingTrades());
//            drawOptionalInputPanel(ONGOING);
//        });
//
//        JButton completeTrade = new JButton(normalDashboard.setUpDash(6));
//        completeTrade.addActionListener(e -> {
//            resetEverything();
//            drawListDisplay(normalDashboard.getRecentThreeTradesStrings());
//            drawOptionalDisplayPanel(normalDashboard.getTopThreeTraderStrings(), COMPLETED);
//        });
//
//        JButton vacation = new JButton(normalDashboard.setUpDash(7));
//        vacation.addActionListener(e -> {
//            normalDashboard.editUserStatus();
//            resetEverything();
//            dashboardWindow.remove(scrollablePane);
//            dashboardWindow.repaint();
//            dashboardWindow.setVisible(true);
//        });
//
//        initializeButton(inventory, 200, 40, userFunctionPanel);
//        initializeButton(wishlist, 200, 40, userFunctionPanel);
//        initializeButton(tradeRequest, 200, 40, userFunctionPanel);
//        initializeButton(catalog, 200, 40, userFunctionPanel);
//        initializeButton(ongoingTrade, 200, 40, userFunctionPanel);
//        initializeButton(completeTrade, 200, 40, userFunctionPanel);
//        initializeButton(vacation, 200, 40, userFunctionPanel);
//
//        if (normalDashboard.isFrozen()) {
//            JButton unfreeze = new JButton(normalDashboard.setUpDash(8));
//            unfreeze.addActionListener(e -> {
//                normalDashboard.sendUnfreezeRequest();
//                super.resetEverything();
//                dashboardWindow.remove(scrollablePane);
//                dashboardWindow.repaint();
//                dashboardWindow.setVisible(true);
//            });
//            initializeButton(unfreeze, 200, 40, userFunctionPanel);
//        }
//    }
//
//    @Override
//    public void drawUserInputPane(int type) {
//        switch (type) {
//            case WISHLIST:
//                userInputPanel.removeAll();
//                removeButton.setText("Remove");
//                initializeButton(removeButton, 100, 20, userInputPanel);
//                userInputPanel.add(removeButton);
//                userInputPanel.repaint();
//                removeButton.addActionListener(e -> {
//                    if (!listDisplay.isSelectionEmpty()) {
//                        int index = listDisplay.getSelectedIndex();
//                        normalDashboard.removeFromWishlist(index);
//                        listDisplay.clearSelection();
//                        redrawDisplayList(normalDashboard.getWishlist());
//                    }
//                });
//                break;
//
//            case INVENTORY:
//                userInputPanel.removeAll();
//                removeButton.setText("Remove");
//                JButton addInv = new JButton("Add");
//                JLabel name = new JLabel("Name: ");
//                JTextField nameInput = new JTextField(20);
//                JLabel description = new JLabel("Description: ");
//                JTextField descripInput = new JTextField(20);
//                userInputPanel.add(name);
//                userInputPanel.add(nameInput);
//                userInputPanel.add(description);
//                userInputPanel.add(descripInput);
//                userInputPanel.repaint();
//                initializeButton(addInv, 100, 20, userInputPanel);
//                initializeButton(removeButton, 100, 20, userInputPanel);
//
//                addInv.addActionListener(e -> {
//                    normalDashboard.addToInventory(nameInput.getText(), descripInput.getText());
//                    nameInput.setText("");
//                    descripInput.setText("");
//                    drawPopUpMessage();
//                    redrawDisplayList(normalDashboard.getInventory());
//                    drawOptionalDisplayPanel(normalDashboard.getPendingInventory(), INVENTORY);
//                });
//
//                removeButton.addActionListener(e -> {
//                    if (!listDisplay.isSelectionEmpty()) {
//                        int index = listDisplay.getSelectedIndex();
//                        normalDashboard.removeFromInventory(index);
//                        drawPopUpMessage();
//                        listDisplay.clearSelection();
//                        redrawDisplayList(normalDashboard.getInventory());
//                    }
//                });
//                break;
//
//            case TRADE_REQUEST:
//                JToggleButton acceptOrDeny = new JToggleButton();
//                JToggleButton permOrTemp = new JToggleButton();
//                JLabel initialTime = new JLabel("Suggest Time:");
//                JLabel initialPlace = new JLabel("Suggest Place:");
//                JTextField initialTimeInput = new JTextField(10);
//                JTextField initialPlaceInput = new JTextField(10);
//                removeButton.setText("Confirm");
//                initializeToggleButton(acceptOrDeny, "Accept Request", "Deny Request");
//                initializeToggleButton(permOrTemp, "Permanent", "Temporary");
//
//                break;
//            case CATALOG_VIEWER:
//                userInputPanel.removeAll();
//                JButton trade = new JButton("Trade");
//                initializeButton(trade, 100, 20, userInputPanel);
//                trade.addActionListener(e -> {
//                    if (!listDisplay.isSelectionEmpty()) {
//                        int index = listDisplay.getSelectedIndex();
//                        //redrawDisplayList();
//                    }
//                });
//                break;
//        }
//        dashboardWindow.repaint();
//        dashboardWindow.setVisible(true);
//    }
//
//    /**
//     * Draws all JComponents necessary for the type of function on the EAST of the
//     * JFrame's border layout
//     * @param stringArray the content to display
//     * @param type        int indicating the type of function
//     */
//    public void drawOptionalDisplayPanel(String[] stringArray, int type) {
//        switch (type) {
//            case INVENTORY:
//                optionalLabelTitle.setText("Pending Inventory");
//                break;
//            case COMPLETED:
//                optionalLabelTitle.setText("Top Three Trade Partners");
//                break;
//            case ONGOING:
//                optionalLabelTitle.setText("Suggest Meeting Details");
//                break;
//            case TRADE_REQUEST:
//                optionalLabelTitle.setText("Initiated Trade Requests");
//                break;
//        }
//        optionalLabel.setText("");
//        if (stringArray.length == 0) {
//            optionalLabel.setText("Nothing here.");
//        } else {
//            StringBuilder stringBuilder = new StringBuilder("<html>");
//            int index = 0;
//            while (index < stringArray.length) {
//                stringBuilder.append(stringArray[index]).append("<br/>");
//                index++;
//            }
//            optionalLabel.setText(stringBuilder.toString());
//        }
//        optionalPanel.add(optionalLabelTitle);
//        optionalPanel.add(optionalLabel);
//        dashboardWindow.repaint();
//        dashboardWindow.setVisible(true);
//    }
//
//    public void drawOptionalInputPanel(int type){
//        switch (type){
//            case ONGOING:
//                optionalLabelTitle.setText("Suggest Meeting Details");
//                JLabel timeSuggestion = new JLabel("Time:");
//                JLabel placeSuggestion = new JLabel("Place:");
//                JTextField timeSuggestionInput = new JTextField(10);
//                JTextField placeSuggestionInput = new JTextField(10);
//                JButton suggest = new JButton("Suggest");
//                //suggest.addActionListener();
//
//                break;
//            case CATALOG_VIEWER:
//                break;
//        }
//
//    }
//
//
//}
//
