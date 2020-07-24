import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * SystemDisplay
 * display class that holds all the different components
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-22
 * last modified 2020-07-22
 */

public class SystemDisplay extends JFrame {
    private TitledBorder title;
    private JPanel contentPane;
    private SystemMenuBar menuBar;

    public SystemDisplay(){
        super("CSC207 | Group_0043");
        contentPane = new JPanel(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new SystemMenuBar();
        this.setJMenuBar(menuBar);
        this.setContentPane(contentPane);
        //addComponentsToPane(this);

    }
    public void addComponentsToPane(SystemDisplay display){
        GridBagConstraints constraints = new GridBagConstraints();
        //galleryPanel =  new GalleryPanel(display, constraints);


    }
}
