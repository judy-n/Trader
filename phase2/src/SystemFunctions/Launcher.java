package SystemFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Starting point of the program.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-13
 */
public class Launcher extends JFrame {

    public JFrame programWindow;
    public StartMenu startMenu;

    /**
     * Creates a <Launcher></Launcher>, the first window in the program.
     */
    public Launcher() {
        programWindow = new JFrame();
        programWindow.setSize(820, 576);
        programWindow.setResizable(false);
        programWindow.setUndecorated(true);
        FrameDragListener frameDragListener = new FrameDragListener(programWindow);
        programWindow.addMouseListener(frameDragListener);
        programWindow.addMouseMotionListener(frameDragListener);

        programWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    /**
     * Lets the user drag the window from anywhere on the JFrame
     * Code based on StackOverFlow user Erik Pragt
     * https://stackoverflow.com/questions/16046824/making-a-java-swing-frame-movable-and-setundecorated
     */
    private class FrameDragListener extends MouseAdapter {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }
}
