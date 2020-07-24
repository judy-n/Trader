import javax.swing.*;

public class MenuItemFrame extends JFrame {
    final private int HEIGHT = 720;
    final private int WIDTH = 1024;
    private JFrame menuItemFrame;
    public MenuItemFrame(MenuItem menuItem){
        super(menuItem.getTitle());
        menuItemFrame = new JFrame();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);

    }
}
