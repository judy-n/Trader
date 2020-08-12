package SystemFunctions;
import javax.swing.*;

/**
 * Starting point of the program.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-24
 */
public class Launcher extends JFrame{

    public JFrame programWindow;
    public StartMenu startMenu;

    /**
     * The system's launcher
     */
    public Launcher(){
        programWindow = new JFrame("CSC207 | Group 0043");
        programWindow.setSize(820, 576);
        programWindow.setResizable(false);
        programWindow.setUndecorated(false);
        programWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SystemController systemController = new SystemController();
        startMenu = new StartMenu(systemController, programWindow);
        programWindow.add(startMenu);
        programWindow.setVisible(true);

    }
    /**
     * Main method.
     * Starts the program by creating a program window.
     */
    public static void main(String[] args) {
        new Launcher();
    }
}
