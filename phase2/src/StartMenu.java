import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
 * last modified 2020-07-20
 */
public class StartMenu extends JPanel {
    private int userInput;
    private SystemController systemController;
    /**
     * Creates a <StartMenu></StartMenu> that lets the user choose their next course of action through user input.
     */
    public StartMenu(SystemController systemController) {
        this.systemController = systemController;
        userInput = 0;
        SystemPresenter sp = new SystemPresenter();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        JLabel welcomeText = new JLabel();
        JButton login = new JButton("Log In");
        JButton signUp = new JButton("Sign Up");
        JButton demo = new JButton("Demo the Program");
        JButton endProgram = new JButton("Exit the Program");
        this.setPreferredSize(new Dimension(1024, 720));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setLayout(null);

        welcomeText.setFont(font);
        welcomeText.setSize(new Dimension(300,40));

        welcomeText.setLocation(512, 50);
        welcomeText.setText(sp.startMenu(1));
        initializeButton(login, 200, 40, 325, 300);
        initializeButton(signUp, 200, 40, 675, 300);
        initializeButton(demo, 200, 40, 512, 375);
        initializeButton(endProgram, 200, 40, 512, 450);


        login.addActionListener(e -> systemController.userLogin());

        signUp.addActionListener(e -> systemController.normalUserSignUp());

        demo.addActionListener(e -> systemController.demoUser());


        endProgram.addActionListener(e -> {
            try{
                br.close();
                System.exit(0);
            } catch (IOException ex){
                sp.exceptionMessage();
            }
        });




//        sp.startMenu(1);
//        try {
//            String temp = br.readLine();
//            while (!temp.matches("[0-3]")) {
//                sp.invalidInput();
//                temp = br.readLine();
//            }
//            userInput = Integer.parseInt(temp);
//        } catch (IOException e) {
//            sp.exceptionMessage();
//        }

//        if (userInput == 0) {
//            try {
//                br.close();
//            } catch (IOException e) {
//                sp.streamExceptionMessage();
//            }
//        }
    }

//    /**
//     * Getter for the option input by the user.
//     *
//     * @return the option input by the user
//     */
//    public int getUserInput() {
//        return userInput;
//    }

    private void initializeButton(JButton button, int width, int height, int xPos, int yPos){
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setSize(new Dimension(width, height));
        button.setLocation(xPos, yPos);
    }
}
