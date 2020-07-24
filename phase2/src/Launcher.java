import javax.swing.*;
import java.awt.*;

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
    public Launcher(){
        super("CSC207 | Group 0043");
        this.setSize(new Dimension(1024, 720));
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SystemController systemController = new SystemController();
        StartMenu startMenu = new StartMenu(systemController);
        this.add(startMenu);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new SystemController();
    }
}
