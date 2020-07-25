import javax.swing.*;
import java.awt.*;
import java.sql.Statement;

/**
 * Starting point of the program.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-24
 */
public class Launcher extends JFrame{
    /**
     * Main method.
     * Starts the program by creating a <SystemController></SystemController>.
     */
    public JFrame programWindow;
    public StartMenu startMenu;

    public Launcher(){
        programWindow = new JFrame("CSC207 | Group 0043");
        programWindow.setSize(820, 576);
        programWindow.setResizable(false);
        programWindow.setUndecorated(false);
        programWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SystemController systemController = new SystemController();
        startMenu = new StartMenu(systemController);
        programWindow.add(startMenu);
        programWindow.setVisible(true);

    }

    public static void main(String[] args) {
        new Launcher();
    }
}
