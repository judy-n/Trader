package SystemFunctions;

import Entities.User;
import javax.swing.*;

public class DashboardFrame extends JDialog{
    private Dashboard dashboard;
    public JDialog DashboardWindow;
    public JFrame parent;

    public DashboardFrame(Dashboard dashboard, JFrame parent) {
        this.dashboard = dashboard;
        this.parent = parent;
        String username = dashboard.getUsername();
        System.out.println(username);
        DashboardWindow = new JDialog(parent, "Dashboard | "+username, true);
        DashboardWindow.setSize(820, 576);
        DashboardWindow.setResizable(false);
        DashboardWindow.setUndecorated(false);
        DashboardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DashboardWindow.add(dashboard);
        DashboardWindow.setVisible(true);

    }
}


