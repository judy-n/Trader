package SystemFunctions;

import Entities.User;

import javax.swing.*;

public class DashboardFrame extends JFrame{
    private Dashboard dashboard;
    public JFrame DashboardWindow;

    public DashboardFrame(Dashboard dashboard) {
        this.dashboard = dashboard;
        User user = dashboard.getUser();

        DashboardWindow = new JFrame("Dashboard | "+ user.getUsername());
        DashboardWindow.setSize(820, 576);
        DashboardWindow.setResizable(false);
        DashboardWindow.setUndecorated(false);
        DashboardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DashboardWindow.setLayout(null);

        DashboardWindow.add(dashboard);
        DashboardWindow.setVisible(true);

    }
}


