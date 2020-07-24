import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SystemMenuBar extends JMenuBar{
    public SystemMenuBar(){
        createMenuBar();
    }
    private void createMenuBar(){
        JMenu helpMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.setToolTipText("About");
        about.setMnemonic(KeyEvent.VK_TAB);
        about.addActionListener(e -> popUpWindow());
    }
    private void popUpWindow(){
        JFrame dialogWindow = new JFrame("About this project");
        dialogWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogWindow.setSize(300,400);
        JOptionPane.showMessageDialog(dialogWindow, "SAMPLE SAMPLE");
    }
}

