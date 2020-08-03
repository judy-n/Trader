package SystemFunctions;

import Entities.User;

import javax.swing.*;

public abstract class Dashboard extends JPanel {
    public abstract String getUsername();

    // remove this method if it isn't used in the future
    public abstract User getUser();
}
