package SystemFunctions;

import DemoUserFunctions.DemoDashboard;
import NormalUserFunctions.NormalDashboard;
import AdminUserFunctions.AdminDashboard;

import javax.swing.*;
import java.awt.*;


/**
 * JFrame that displays the user's dashboard.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-01
 * last modified 2020-08-12
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
    private final int NOTIF = 13;
    private final int USER_INFO = 14;
    private final String NOTHING_MESSAGE = "    Nothing here yet!";

    /**
     * Makes a new modal window that displays the user functions of the user that is
     * currently logged in
     *
     * @param dashboard the controller for the dashboard
     * @param parent    the parent window that made this one
     */
    public DashboardFrame(Dashboard dashboard, JFrame parent) {
        this.dashboard = dashboard;
        this.parent = parent;
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
        userFunctionPanel.setPreferredSize(new Dimension(200,576));
        userInputPanel.setLayout(new FlowLayout());
        notifPanel.setLayout(new FlowLayout());
        optionalPanel.setLayout(new BoxLayout(optionalPanel, BoxLayout.Y_AXIS));
        optionalPanel.setPreferredSize(new Dimension(300, 576));
        dashboardWindow = new JDialog(parent, "Dashboard | " + dashboard.getUsername(), true);
        dashboardWindow.setSize(820, 576);
        dashboardWindow.setResizable(false);
        dashboardWindow.setUndecorated(false);
        dashboardWindow.setLayout(new BorderLayout());
        dashboardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton profilePic = new JButton();
        initializeButton(profilePic, 200,200, userFunctionPanel);
        profilePic.setIcon(new ImageIcon("src/default.png"));
        profilePic.addActionListener(e -> {
            resetEverything();
            optionalPanel.setVisible(false);
            drawListDisplay(dashboard.getUserInfo());
            if(dashboard.getType() != 2){
                drawUserInputPane(USER_INFO);
            }
        });

        initializeMenuBar();

        if (dashboard.getType() == 0) {
            adminDashboard = (AdminDashboard) dashboard;
            drawAdminDash();
        } else if (dashboard.getType() == 1) {
            normalDashboard = (NormalDashboard) dashboard;
            drawNormalDash();
        } else {
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
            if (normalDashboard.isOnVacation()) {
                inventory.setEnabled(false);
                wishlist.setEnabled(false);
                tradeRequest.setEnabled(false);
                catalog.setEnabled(false);
                ongoingTrade.setEnabled(false);
                completeTrade.setEnabled(false);
            } else {
                inventory.setEnabled(true);
                wishlist.setEnabled(true);
                tradeRequest.setEnabled(true);
                catalog.setEnabled(true);
                ongoingTrade.setEnabled(true);
                completeTrade.setEnabled(true);
            }

            dashboardWindow.repaint();
            dashboardWindow.setVisible(true);
        });

        JButton notifications = new JButton(normalDashboard.setUpDash(9));
        notifications.addActionListener(e -> {
            resetEverything();
            drawListDisplay(normalDashboard.getNotifStrings());
            drawUserInputPane(NOTIF);
        });

        JButton unfreeze = new JButton(normalDashboard.setUpDash(8));
        unfreeze.addActionListener(e -> {
            normalDashboard.sendUnfreezeRequest();
            drawPopUpMessage();
            resetEverything();
            dashboardWindow.remove(scrollablePane);
            dashboardWindow.repaint();
            dashboardWindow.setVisible(true);
        });

        initializeButton(catalog, 200, 35, userFunctionPanel);
        initializeButton(inventory, 200, 35, userFunctionPanel);
        initializeButton(wishlist, 200, 35, userFunctionPanel);
        initializeButton(tradeRequest, 200, 35, userFunctionPanel);
        initializeButton(ongoingTrade, 200, 35, userFunctionPanel);
        initializeButton(completeTrade, 200, 35, userFunctionPanel);
        initializeButton(vacation, 200, 35, userFunctionPanel);
        initializeButton(notifications, 200, 35, userFunctionPanel);

        if (normalDashboard.isOnVacation()) {
            inventory.setEnabled(false);
            wishlist.setEnabled(false);
            tradeRequest.setEnabled(false);
            catalog.setEnabled(false);
            ongoingTrade.setEnabled(false);
            completeTrade.setEnabled(false);
        }
        if (normalDashboard.isFrozen()) {
            initializeButton(unfreeze, 200, 35, userFunctionPanel);
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

        JButton fullActivity = new JButton(adminDashboard.setUpDash(21));
        fullActivity.addActionListener(e -> {
            resetEverything();
            drawListDisplay(adminDashboard.getFullActivityLogStrings());
        });

        initializeButton(catalogEditor, 200, 40, userFunctionPanel);
        initializeButton(freezer, 200, 40, userFunctionPanel);
        initializeButton(unfreezer, 200, 40, userFunctionPanel);
        initializeButton(threshold, 200, 40, userFunctionPanel);
        initializeButton(undo, 200, 40, userFunctionPanel);

        // Only the initial admin can add new admins to the system and view the full activity log.
        if (adminDashboard.getAdminID() == 1) {
            initializeButton(adminCreator, 200, 40, userFunctionPanel);
            initializeButton(fullActivity, 200, 40, userFunctionPanel);
        }

    }

    private void drawDemoDash() {
        JButton demoCatalog = new JButton(demoDashboard.setUpDash(1));
        demoCatalog.addActionListener(e -> {
            resetEverything();
            drawUserInputPane(DEMO);
            drawListDisplay(demoDashboard.getDemoCatalog());
        });
        initializeButton(demoCatalog, 200, 40, userFunctionPanel);
    }

    private void drawListDisplay(String[] displayItems) {
        if (displayItems.length == 0) {
            dashboardWindow.remove(scrollablePane);
            nothingToDisplay.setText(NOTHING_MESSAGE);
        } else {
            listDisplay = new JList<>(displayItems);
            listDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollablePane.setViewportView(listDisplay);
            dashboardWindow.add(scrollablePane, BorderLayout.CENTER);
        }
        dashboardWindow.setVisible(true);
    }

    private void drawUserInputPane(int type) {
        optionalPanel.setVisible(false);

        switch (type) {
            case DEMO:
                JButton fakeTrade = new JButton(demoDashboard.setUpDash(2));
                fakeTrade.addActionListener(e -> {
                    demoDashboard.setPopUpMessage(1);
                    drawPopUpMessage();
                });
                initializeButton(fakeTrade, 100, 40, userInputPanel);
                break;

            case USER_INFO:
                JTextField newPassword = new JTextField(20);
                JTextField reNewPassword = new JTextField(20);
                JButton changePassword = new JButton(dashboard.setUpDash(31));
                initializeLabelledTextField(newPassword, dashboard.setUpDash(29), userInputPanel);
                initializeLabelledTextField(reNewPassword, dashboard.setUpDash(30), userInputPanel);
                initializeButton(changePassword, 100, 40,  userInputPanel);
                changePassword.addActionListener(e -> {
                    dashboard.validatePasswordChange(newPassword.getText(), reNewPassword.getText());
                    drawPopUpMessage();
                    newPassword.setText("");
                    reNewPassword.setText("");
                    redrawDisplayList(dashboard.getUserInfo());
                });
                break;

            case WISHLIST:
                JButton removeWishlist = new JButton(normalDashboard.setUpDash(10));
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
                JButton removeInv = new JButton(normalDashboard.setUpDash(10));
                JButton addInv = new JButton(normalDashboard.setUpDash(11));
                JTextField nameInput = new JTextField(20);
                JTextField descripInput = new JTextField(20);
                initializeLabelledTextField(nameInput, normalDashboard.setUpDash(13), userInputPanel);
                initializeLabelledTextField(descripInput, normalDashboard.setUpDash(14), userInputPanel);
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
                if (normalDashboard.isFrozen()) {
                    normalDashboard.setPopUpMessage(36);
                    drawPopUpMessage();
                } else {
                    JToggleButton acceptOrDeny = new JToggleButton();
                    JToggleButton permOrTemp = new JToggleButton();
                    JTextField initialTimeInput = new JTextField(10);
                    JTextField initialPlaceInput = new JTextField(10);
                    JButton confirm = new JButton(normalDashboard.setUpDash(12));
                    confirm.addActionListener(e -> {
                        if (!listDisplay.isSelectionEmpty()) {
                            if (acceptOrDeny.isSelected()) {
                                normalDashboard.rejectTradeRequest(listDisplay.getSelectedIndex());
                            } else {
                                normalDashboard.acceptTradeRequest(listDisplay.getSelectedIndex(),
                                        initialTimeInput.getText(), initialPlaceInput.getText(), !permOrTemp.isSelected());
                                drawPopUpMessage();
                            }
                        }
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getReceivedTrades());
                    });
                    initializeToggleButton(acceptOrDeny, normalDashboard.setUpDash(15), normalDashboard.setUpDash(16), userInputPanel);
                    initializeLabelledTextField(initialTimeInput, normalDashboard.setUpDash(17), userInputPanel);
                    initializeLabelledTextField(initialPlaceInput, normalDashboard.setUpDash(18), userInputPanel);
                    initializeToggleButton(permOrTemp, normalDashboard.setUpDash(19), normalDashboard.setUpDash(20), userInputPanel);
                    initializeButton(confirm, 100, 20, userInputPanel);
                }
                break;

            case CATALOG_VIEWER:
                JToggleButton wishlistOrTrade = new JToggleButton(normalDashboard.setUpDash(21));
                JButton trade = new JButton(normalDashboard.setUpDash(12));
                JButton sendTrade = new JButton(normalDashboard.setUpDash(22));
                sendTrade.setEnabled(false);
                trade.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        if (wishlistOrTrade.isSelected()) {
                            normalDashboard.addToWishlist(listDisplay.getSelectedIndex());
                            drawPopUpMessage();
                        } else {
                            normalDashboard.setIndexOfItemRequested(listDisplay.getSelectedIndex());
                            drawPopUpMessage();

                            if (normalDashboard.canSetUpTradeRequest(listDisplay.getSelectedIndex())) {
                                drawOptionalDisplayPanel(normalDashboard.getSuggestedItems(listDisplay.getSelectedIndex()), CATALOG_VIEWER);
                                listDisplay.clearSelection();
                                redrawDisplayList(normalDashboard.currentUserInventoryInCatalog());
                                sendTrade.setEnabled(true);
                                trade.setEnabled(false);
                                wishlistOrTrade.setEnabled(false);
                            }
                        }
                    }
                });

                sendTrade.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.requestItemInTwoWayTrade(listDisplay.getSelectedIndex());
                    } else {
                        normalDashboard.requestItemInOneWayTrade();
                    }
                    drawPopUpMessage();
                    listDisplay.clearSelection();
                    optionalPanel.setVisible(false);
                    redrawDisplayList(normalDashboard.getCatalog());
                    sendTrade.setEnabled(false);
                    trade.setEnabled(true);
                    wishlistOrTrade.setEnabled(true);
                });

                initializeToggleButton(wishlistOrTrade, normalDashboard.setUpDash(21),
                        normalDashboard.setUpDash(23), userInputPanel);
                initializeButton(trade, 100, 20, userInputPanel);
                initializeButton(sendTrade, 100, 20, userInputPanel);
                break;

            case ONGOING:
                JButton cancelTrade = new JButton(normalDashboard.setUpDash(24));
                JButton agreeTrade = new JButton(normalDashboard.setUpDash(25));
                JButton confirmTransaction = new JButton(normalDashboard.setUpDash(26));
                editTrade = new JButton(normalDashboard.setUpDash(27));

                cancelTrade.addActionListener(e -> {
                    optionalPanel.removeAll();
                    optionalPanel.setVisible(false);
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.cancelTrade(listDisplay.getSelectedIndex());
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                    }
                });

                confirmTransaction.addActionListener(e -> {
                    optionalPanel.removeAll();
                    optionalPanel.setVisible(false);
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.confirmTrade(listDisplay.getSelectedIndex());
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                    }
                });

                agreeTrade.addActionListener(e -> {
                    optionalPanel.removeAll();
                    optionalPanel.setVisible(false);
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.agreeTrade(listDisplay.getSelectedIndex());
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                    }
                });

                editTrade.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        if (normalDashboard.canEditMeeting(listDisplay.getSelectedIndex())) {
                            editTrade.setEnabled(false);
                            drawOptionalInputPanel(ONGOING);
                        } else {
                            drawPopUpMessage();
                        }
                    }
                });

                initializeButton(agreeTrade, 100, 20, userInputPanel);
                initializeButton(confirmTransaction, 100, 20, userInputPanel);
                initializeButton(editTrade, 100, 20, userInputPanel);
                initializeButton(cancelTrade, 100, 20, userInputPanel);
                break;

            case NOTIF:
                JButton markAsRead = new JButton(normalDashboard.setUpDash(28));
                initializeButton(markAsRead, 100, 20, userInputPanel);
                markAsRead.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.markNotifAsRead(listDisplay.getSelectedIndex());
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getNotifStrings());
                    }
                });
                break;

            case FREEZE:
                JButton freezeAll = new JButton(adminDashboard.setUpDash(7));
                initializeButton(freezeAll, 100, 20, userInputPanel);
                freezeAll.addActionListener(e -> {
                    adminDashboard.freezeAll();
                    redrawDisplayList(adminDashboard.getFreezeList());
                });
                break;

            case UNFREEZE:
                JButton unfreeze = new JButton(adminDashboard.setUpDash(8));
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
                JTextField usernameInput = new JTextField(10);
                JTextField emailInput = new JTextField(10);
                JTextField passwordInput = new JTextField(10);
                JButton addAdmin = new JButton(adminDashboard.setUpDash(12));
                addAdmin.addActionListener(e -> {
                    adminDashboard.createNewAdmin(usernameInput.getText(),
                            emailInput.getText(), passwordInput.getText());
                    usernameInput.setText("");
                    emailInput.setText("");
                    passwordInput.setText("");
                    drawPopUpMessage();
                });
                initializeLabelledTextField(usernameInput, adminDashboard.setUpDash(9), userInputPanel);
                initializeLabelledTextField(emailInput, adminDashboard.setUpDash(10), userInputPanel);
                initializeLabelledTextField(passwordInput, adminDashboard.setUpDash(11), userInputPanel);
                initializeButton(addAdmin, 100, 20, userInputPanel);
                break;

            case CATALOG_EDITOR:
                JToggleButton approveOrDeny = new JToggleButton();
                JButton confirmEdit = new JButton(adminDashboard.setUpDash(13));
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
                initializeToggleButton(approveOrDeny, adminDashboard.setUpDash(14),
                        adminDashboard.setUpDash(15), userInputPanel);
                initializeButton(confirmEdit, 100, 20, userInputPanel);
                break;

            case THRESHOLD:
                JTextField weeklyTradeInput = new JTextField(5);
                JTextField meetingEditInput = new JTextField(5);
                JTextField lendMinInput = new JTextField(5);
                JTextField incompleteTradeInput = new JTextField(5);
                JButton change = new JButton(adminDashboard.setUpDash(13));
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
                initializeLabelledTextField(weeklyTradeInput, adminDashboard.setUpDash(16), userInputPanel);
                initializeLabelledTextField(meetingEditInput, adminDashboard.setUpDash(17), userInputPanel);
                initializeLabelledTextField(lendMinInput, adminDashboard.setUpDash(18), userInputPanel);
                initializeLabelledTextField(incompleteTradeInput, adminDashboard.setUpDash(19), userInputPanel);
                initializeButton(change, 100, 20, userInputPanel);
                break;

            case UNDO:
                JButton undo = new JButton(adminDashboard.setUpDash(20));
                initializeButton(undo, 100, 20, userInputPanel);
                undo.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
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

    private void drawOptionalDisplayPanel(String[] stringArray, int type) {
        optionalPanel.setVisible(true);

        switch (type) {
            case INVENTORY:
                optionalLabelTitle.setText(normalDashboard.setUpDashTitles(1));
                break;
            case COMPLETED:
                optionalLabelTitle.setText(normalDashboard.setUpDashTitles(2));
                break;
            case TRADE_REQUEST:
                optionalLabelTitle.setText(normalDashboard.setUpDashTitles(4));
                break;
            case CATALOG_VIEWER:
                optionalLabelTitle.setText(normalDashboard.setUpDashTitles(5));
                break;
        }
        optionalLabel.setText("");
        if (stringArray.length == 0) {
            optionalLabel.setText(NOTHING_MESSAGE);
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

    private void drawOptionalInputPanel(int type) {
        optionalPanel.setVisible(true);

        switch (type) {
            case ONGOING:
                optionalLabelTitle.setText(normalDashboard.setUpDashTitles
                        (normalDashboard.getNumEdits(listDisplay.getSelectedIndex())));
                JTextField timeSuggestionInput = new JTextField(20);
                JTextField placeSuggestionInput = new JTextField(20);
                JButton suggest = new JButton(normalDashboard.setUpDashTitles(6));
                suggest.addActionListener(e -> {
                    if (!listDisplay.isSelectionEmpty()) {
                        normalDashboard.editOngoingTrade(listDisplay.getSelectedIndex(),
                                timeSuggestionInput.getText(), placeSuggestionInput.getText());
                        drawPopUpMessage();
                        listDisplay.clearSelection();
                        redrawDisplayList(normalDashboard.getOngoingTrades());
                        optionalPanel.removeAll();
                        optionalPanel.setVisible(false);
                        editTrade.setEnabled(true);
                    }
                });
                optionalPanel.add(optionalLabelTitle);

                initializeLabelledTextField(timeSuggestionInput, normalDashboard.setUpDashTitles(7), optionalPanel);
                timeSuggestionInput.setMaximumSize(timeSuggestionInput.getPreferredSize());
                initializeLabelledTextField(placeSuggestionInput, normalDashboard.setUpDashTitles(8), optionalPanel);
                placeSuggestionInput.setMaximumSize(placeSuggestionInput.getPreferredSize());

                initializeButton(suggest, 100, 30, optionalPanel);
                dashboardWindow.repaint();
                dashboardWindow.setVisible(true);
                break;
            case CATALOG_VIEWER:
                break;
        }
    }

    private void redrawDisplayList(String[] displayList) {
        nothingToDisplay.setText("");
        dashboardWindow.revalidate();
        drawListDisplay(displayList);
        dashboardWindow.repaint();
    }

    private void initializeButton(JButton button, int width, int height, JPanel panel) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setFocusPainted(false);
        panel.add(button);
    }

    private void initializeToggleButton(JToggleButton button, String ogText, String clickedText, JPanel panel) {
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

    private void initializeLabelledTextField(JTextField textField, String text, JPanel panel) {
        JLabel label = new JLabel(text);
        panel.add(label);
        panel.add(textField);
    }

    private void resetEverything() {
        nothingToDisplay.setText("");
        userInputPanel.removeAll();
        optionalPanel.removeAll();
    }

    private void drawPopUpMessage() {
        if (!dashboard.getPopUpMessage().isEmpty()) {
            JOptionPane.showMessageDialog(parent, dashboard.getPopUpMessage());
            dashboard.resetPopUpMessage();
        }
    }

    private void initializeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu about = new JMenu("About");
        JMenuItem help = new JMenuItem("Help");
        about.add(help);
        menuBar.add(about);
        help.addActionListener(e -> JOptionPane.showMessageDialog(parent, dashboard.getHelpMessage()));
        dashboardWindow.setJMenuBar(menuBar);

    }
}