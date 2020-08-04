package SystemFunctions;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class DashboardFrame extends JDialog{
    private Dashboard dashboard;
    private JDialog DashboardWindow;
    private JFrame parent;
    private JPanel userFunctionPanel;

    public DashboardFrame(Dashboard dashboard, JFrame parent) {
        this.dashboard = dashboard;
        this.parent = parent;
        String username = dashboard.getUsername();
        userFunctionPanel = new JPanel();
        userFunctionPanel.setLayout(new BoxLayout(userFunctionPanel, BoxLayout.Y_AXIS));
        JButton inventory = new JButton("Inventory Editor");
        initializeButton(inventory);
        JButton wishlist = new JButton("Wishlist Editor");
        initializeButton(wishlist);
        JButton tradeRequest = new JButton("View Trade Requests");
        initializeButton(tradeRequest);
        JButton catalog = new JButton("View Catalog");
        initializeButton(catalog);
        JButton ongoingTrade = new JButton("View Ongoing Trades");
        initializeButton(ongoingTrade);
        JButton vacation = new JButton("Status: Vacation");
        initializeButton(vacation);
        JButton unfreeze = new JButton("Status: Frozen");
        initializeButton(unfreeze);
        DashboardWindow = new JDialog(parent, "Dashboard | " + username, true);
        DashboardWindow.setSize(820, 576);
        DashboardWindow.setResizable(false);
        DashboardWindow.setUndecorated(false);
        DashboardWindow.setLayout(new BorderLayout());
        DashboardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //DashboardWindow.add(dashboard, BorderLayout.CENTER);
        ArrayList<String> newArraylist = new ArrayList<>();
        newArraylist.add("HI");
        newArraylist.add("BYE");
        String [] newarray = {"HI", "BYE"};
        userFunctionPanel.add(inventory);
        userFunctionPanel.add(wishlist);
        userFunctionPanel.add(tradeRequest);
        userFunctionPanel.add(catalog);
        userFunctionPanel.add(ongoingTrade);
        userFunctionPanel.add(vacation);
        userFunctionPanel.add(unfreeze);
        DashboardWindow.add(userFunctionPanel, BorderLayout.WEST);

        catalog.addActionListener(e -> displayList(newarray));

        DashboardWindow.setVisible(true);

    }

    private void initializeButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setMaximumSize(new Dimension(200,40));
        button.setMinimumSize(new Dimension(200,40));
        button.setFocusPainted(false);
        userFunctionPanel.add(button);
    }
    private void displayList(String [] arr){
        JPanel userInputPanel = new JPanel();
        userInputPanel.setLayout(new FlowLayout());

        JList<String> catalogDisplayList = new JList<>(arr);
        catalogDisplayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollableCatalog = new JScrollPane(catalogDisplayList);
        scrollableCatalog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableCatalog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        DashboardWindow.add(scrollableCatalog, BorderLayout.CENTER);
        JLabel name = new JLabel("name:");
        JTextField nameInput = new JTextField(20);
        JLabel descrip = new JLabel("Description");
        JTextField descripInput = new JTextField(20);
        JButton enter = new JButton("Add");
        userInputPanel.add(name);
        userInputPanel.add(nameInput);
        userInputPanel.add(descrip);
        userInputPanel.add(descripInput);
        userInputPanel.add(enter);
        DashboardWindow.add(userInputPanel, BorderLayout.SOUTH);
        DashboardWindow.setVisible(true);

    }

}


