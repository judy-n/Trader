//package SystemFunctions;
//
//import AdminUserFunctions.AdminDashboard;
//
//import javax.swing.*;
//
//public class AdminDashFrame extends DashboardFrame {
//    private AdminDashboard adminDashboard;
//    private JDialog dashboardWindow;
//    private JFrame parent;
//    private JPanel userFunctionPanel;
//    private JPanel userInputPanel;
//    private JPanel notifPanel;
//    private JPanel optionalPanel;
//    private JButton removeButton;
//
//    private JList<String> listDisplay;
//    private JScrollPane scrollablePane;
//    private JLabel nothingToDisplay;
//
//    private final int CATALOG_EDITOR = 7;
//    private final int FREEZE = 8;
//    private final int UNFREEZE = 9;
//    private final int UNDO = 10;
//    private final int CREATE = 11;
//    private final int THRESHOLD = 12;
//    /**
//     * Makes a new modal window that displays the user functions of the user that is
//     * currently logged in
//     *
//     * @param adminDashboard    the controller for the dashboard
//     * @param parent            the parent window that made this one
//     */
//    public AdminDashFrame(AdminDashboard adminDashboard, JFrame parent) {
//        super(adminDashboard, parent);
//        this.adminDashboard = adminDashboard;
//        drawDash();
//    }
//
//    @Override
//    public void drawDash() {
//        userInputPanel.removeAll();
//        JButton catalogEditor = new JButton(adminDashboard.setUpDash(1));
//        catalogEditor.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(CATALOG_EDITOR);
//            drawListDisplay(adminDashboard.getPendingCatalog());
//        });
//
//        JButton freezer = new JButton(adminDashboard.setUpDash(2));
//        freezer.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(FREEZE);
//            drawListDisplay(adminDashboard.getFreezeList());
//        });
//
//        JButton unfreezer = new JButton(adminDashboard.setUpDash(3));
//        unfreezer.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(UNFREEZE);
//            drawListDisplay(adminDashboard.getUnfreezeRequests());
//        });
//
//        JButton threshold = new JButton(adminDashboard.setUpDash(4));
//        threshold.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(THRESHOLD);
//            drawListDisplay(adminDashboard.getThresholdStrings());
//        });
//
//        JButton adminCreator = new JButton(adminDashboard.setUpDash(5));
//        adminCreator.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(CREATE);
//            dashboardWindow.remove(scrollablePane);
//            dashboardWindow.repaint();
//            dashboardWindow.setVisible(true);
//        });
//
//        JButton undo = new JButton(adminDashboard.setUpDash(6));
//        undo.addActionListener(e -> {
//            resetEverything();
//            drawUserInputPane(UNDO);
//            drawListDisplay(adminDashboard.getRevertibleNotfis());
//        });
//
//        initializeButton(catalogEditor, 200, 40, userFunctionPanel);
//        initializeButton(freezer, 200, 40, userFunctionPanel);
//        initializeButton(unfreezer, 200, 40, userFunctionPanel);
//        initializeButton(threshold, 200, 40, userFunctionPanel);
//        initializeButton(adminCreator, 200, 40, userFunctionPanel);
//        initializeButton(undo, 200, 40, userFunctionPanel);
//
//    }
//
//    @Override
//    public void drawUserInputPane(int type) {
//        switch (type){
//            case FREEZE:
//                userInputPanel.removeAll();
//                removeButton.setText("Freeze All");
//                initializeButton(removeButton, 100, 20, userInputPanel);
//                removeButton.addActionListener(e -> {
//                    adminDashboard.freezeAll();
//                    redrawDisplayList(adminDashboard.getFreezeList());
//                });
//                break;
//
//            case UNFREEZE:
//                userInputPanel.removeAll();
//                removeButton.setText("Unfreeze");
//                initializeButton(removeButton, 100, 20, userInputPanel);
//                removeButton.addActionListener(e -> {
//                    if (!listDisplay.isSelectionEmpty()) {
//                        int index = listDisplay.getSelectedIndex();
//                        adminDashboard.unfreezeUser(index);
//                    }
//                    listDisplay.clearSelection();
//                    redrawDisplayList(adminDashboard.getUnfreezeRequests());
//                });
//                break;
//
//            case CREATE:
//                JLabel username = new JLabel("Username:");
//                JLabel email = new JLabel("Email:");
//                JLabel password = new JLabel("Password:");
//                JTextField usernameInput = new JTextField(10);
//                JTextField emailInput = new JTextField(10);
//                JTextField passwordInput = new JTextField(10);
//                JButton addAdmin = new JButton("Create");
//                addAdmin.addActionListener(e ->{
//                    adminDashboard.createNewAdmin(usernameInput.getText(),
//                            emailInput.getText(), passwordInput.getText());
//                    usernameInput.setText("");
//                    emailInput.setText("");
//                    passwordInput.setText("");
//                    drawPopUpMessage();
//                });
//                userInputPanel.add(username);
//                userInputPanel.add(usernameInput);
//                userInputPanel.add(email);
//                userInputPanel.add(emailInput);
//                userInputPanel.add(password);
//                userInputPanel.add(passwordInput);
//                initializeButton(addAdmin, 100, 20, userInputPanel);
//                break;
//
//            case CATALOG_EDITOR:
//                JToggleButton approveOrDeny = new JToggleButton();
//                initializeToggleButton(approveOrDeny, "Approve Item", "Deny Item");
//                removeButton.setText("Confirm");
//                removeButton.addActionListener(e -> {
//                    if (!listDisplay.isSelectionEmpty()) {
//                        if (approveOrDeny.isSelected()) {
//                            adminDashboard.rejectionPendingCatalog(listDisplay.getSelectedIndex());
//                        } else {
//                            adminDashboard.approvePendingCatalog(listDisplay.getSelectedIndex());
//                        }
//                    }
//                    listDisplay.clearSelection();
//                    redrawDisplayList(adminDashboard.getPendingCatalog());
//                });
//                userInputPanel.add(approveOrDeny);
//                initializeButton(removeButton, 100, 20, userInputPanel);
//                break;
//
//            case THRESHOLD:
//                JLabel weeklyTrade = new JLabel("Weekly Trade Max:");
//                JLabel meetingEdit = new JLabel("Meeting Edit Max:");
//                JLabel lendMin = new JLabel("Len Minimum:");
//                JLabel incompleteTrade = new JLabel("Incomplete Trade Max:");
//                JTextField weeklyTradeInput = new JTextField(5);
//                JTextField meetingEditInput = new JTextField(5);
//                JTextField lendMinInput = new JTextField(5);
//                JTextField incompleteTradeInput = new JTextField(5);
//                JButton change = new JButton("Confirm");
//                change.addActionListener(e -> {
//                    String[] inputs = {weeklyTradeInput.getText(), meetingEditInput.getText(),
//                            lendMinInput.getText(), incompleteTradeInput.getText()};
//
//                    adminDashboard.changeThresholds(inputs);
//                    redrawDisplayList(adminDashboard.getThresholdStrings());
//                    weeklyTradeInput.setText("");
//                    meetingEditInput.setText("");
//                    lendMinInput.setText("");
//                    incompleteTradeInput.setText("");
//                    drawPopUpMessage();
//                });
//                userInputPanel.add(weeklyTrade);
//                userInputPanel.add(weeklyTradeInput);
//
//                userInputPanel.add(meetingEdit);
//                userInputPanel.add(meetingEditInput);
//
//                userInputPanel.add(lendMin);
//                userInputPanel.add(lendMinInput);
//
//                userInputPanel.add(incompleteTrade);
//                userInputPanel.add(incompleteTradeInput);
//
//                userInputPanel.add(change);
//                break;
//        }
//        dashboardWindow.repaint();
//        dashboardWindow.setVisible(true);
//    }
//
//}
