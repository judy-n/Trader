import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets the user choose to sign up, log in, or exit the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-24
 */
public class StartMenu extends JPanel {
    private SystemController systemController;
    private JLabel background;
    /**
     * Creates a <StartMenu></StartMenu> that lets the user choose their next course of action through user input.
     */
    public StartMenu(SystemController systemController) {
        this.systemController = systemController;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Font font = new Font("SansSerif", Font.PLAIN, 14);

        this.setPreferredSize(new Dimension(820, 576));
        background = new JLabel();
        background.setBackground(Color.GRAY);

        background.setLayout(null);
        JButton login = new JButton("Log In");
        JButton signUp = new JButton("Sign Up");
        JButton demo = new JButton("Demo the Program");
        JButton endProgram = new JButton("Exit the Program");

        // test
        JButton test = new JButton("AHHHH");

        initializeButton(login, 200, 40, 325, 300);
        initializeButton(signUp, 200, 40, 675, 300);
        initializeButton(demo, 200, 40, 512, 375);
        initializeButton(endProgram, 200, 40, 512, 450);

        //
        test.setBackground(Color.BLACK);
        test.setForeground(Color.WHITE);
        test.setFont(font);
        test.setSize(new Dimension(200,40));
        test.setFocusPainted(false);
        background.add(test);
        test.setLocation(100, 100);

        JLabel welcomeText = new JLabel();


        welcomeText.setFont(font);
        welcomeText.setSize(new Dimension(300,40));

        welcomeText.setLocation(512, 50);
        welcomeText.setText(sp.startMenu(1));








        //login.addActionListener(e -> systemController.userLogin());

        //signUp.addActionListener(e -> systemController.normalUserSignUp());

       //demo.addActionListener(e -> systemController.demoUser());

        endProgram.addActionListener(e -> {
            try{
                br.close();
                systemController.tryWriteManagers();
                System.exit(0);
            } catch (IOException ex){
                sp.exceptionMessage();
            }
        });

        this.add(background);
        this.validate();
        this.repaint();
    }

    private void initializeButton(JButton button, int width, int height, int xPos, int yPos){
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setSize(new Dimension(width, height));
        button.setFocusPainted(false);
        background.add(button);
        button.setLocation(xPos, yPos);
    }
}
