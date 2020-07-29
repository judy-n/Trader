package SystemFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * Lets the user choose to sign up, log in, or exit the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-29
 */
public class StartMenu extends JPanel {
    private SystemController systemController;
    private Font font = new Font("SansSerif", Font.PLAIN, 20);
    private String emailOrUsername;
    private String inputtedUsername;
    private String inputtedEmail;
    private String inputtedPassword;
    private String validateInputtedPassword;
    private String inputtedHomeCity;
    private StartMenuPresenter startMenuPresenter;
    private final int FIRST_LINE_Y = 100;
    private final int X_POS = 160;
    private final int Y_SPACE = 70;
    private final int X_SPACE = 200;

    /**
     * Creates a <StartMenu></StartMenu> that lets the user choose their next course of action through user input.
     */
    public StartMenu(SystemController systemController) {
        this.systemController = systemController;
        startMenuPresenter = new StartMenuPresenter();

        this.setPreferredSize(new Dimension(820, 576));
        this.setLayout(null);
        mainMenu();
    }

    private void mainMenu(){
        JButton login = new JButton(startMenuPresenter.loginSystem(0));
        JButton signUp = new JButton(startMenuPresenter.signUpSystem(0));
        JButton demo = new JButton(startMenuPresenter.startMenu(4));
        JButton endProgram = new JButton(startMenuPresenter.startMenu(5));


        initializeButton(login, 200, 40, 160, 190);
        initializeButton(signUp, 200, 40, 440, 190);
        initializeButton(demo, 200, 40, 310, 250);
        initializeButton(endProgram, 200, 40, 310, 310);


        JLabel welcomeText = new JLabel(startMenuPresenter.startMenu(1));


        welcomeText.setFont(font);
        welcomeText.setSize(new Dimension(300,40));

        welcomeText.setForeground(Color.BLACK);

        this.add(welcomeText);
        welcomeText.setLocation(310, 50);

        login.addActionListener(e ->
        {
            this.removeAll();
            this.revalidate();
            getUserInfo(startMenuPresenter.loginSystem(0));
            this.repaint();
        });

        signUp.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            getUserInfo(startMenuPresenter.signUpSystem(0));
            this.repaint();
        });
        demo.addActionListener(e -> systemController.demoUser());
        endProgram.addActionListener(e -> {
            //this.removeAll();


            systemController.tryWriteManagers();
            System.exit(0);
        });
        this.validate();
        this.repaint();
    }

    private void initializeButton(JButton button, int width, int height, int xPos, int yPos){
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setSize(new Dimension(width, height));
        button.setFocusPainted(false);
        this.add(button);
        button.setLocation(xPos, yPos);
    }

    private void getUserInfo (String title){
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(font);
        titleLabel.setSize(new Dimension(300,40));
        titleLabel.setForeground(Color.BLACK);
        this.add(titleLabel);
        titleLabel.setLocation(100,50);

        JButton exit = new JButton(startMenuPresenter.startMenu(3));
        initializeButton(exit, 65, 25, 20, 10);
        exit.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            mainMenu();
            this.repaint();
        });


        JLabel password = new JLabel(startMenuPresenter.signUpSystem(7));
        JPasswordField passwordInput = new JPasswordField(50);
        JCheckBox showPassword = new JCheckBox(startMenuPresenter.signUpSystem(14));

        password.setSize(new Dimension(200,40));
        showPassword.setSize(showPassword.getPreferredSize());

        password.setForeground(Color.BLACK);
        this.add(password);
        this.add(passwordInput);
        this.add(showPassword);
        password.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE*2);
        showPassword.setLocation(460,FIRST_LINE_Y + Y_SPACE*2 + 10);
        passwordInput.setBounds(330, FIRST_LINE_Y+ Y_SPACE*2 + 10, 120, 25);

        passwordInput.addActionListener(e -> inputtedPassword = String.valueOf(passwordInput.getPassword()));
        showPassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordInput.setEchoChar((char) 0);
            }else{
                passwordInput.setEchoChar('\u2022');
            }
        });

        if(title.equals(startMenuPresenter.loginSystem(0))){
            JLabel usernameOrEmail = new JLabel(startMenuPresenter.loginSystem(1));
            JTextField usernameEmailInput = new JTextField(50);
            usernameOrEmail.setSize(new Dimension(200,40));
            usernameOrEmail.setForeground(Color.BLACK);
            this.add(usernameOrEmail);
            this.add(usernameEmailInput);
            usernameOrEmail.setLocation(X_POS, FIRST_LINE_Y);
            usernameEmailInput.setBounds(330,110,120,25);

            JButton login = new JButton(startMenuPresenter.loginSystem(0));
            initializeButton(login, 100, 30, 450, 350);

            login.addActionListener(e -> {
                emailOrUsername = usernameEmailInput.getText();
                //more methods goes here
            });
        }else{
            JLabel username = new JLabel(startMenuPresenter.signUpSystem(4));
            JLabel email = new JLabel(startMenuPresenter.signUpSystem(1));
            JLabel validatePassword = new JLabel(startMenuPresenter.signUpSystem(9));
            JLabel homeCity = new JLabel(startMenuPresenter.signUpSystem(13));

            JTextField usernameInput = new JTextField(50);
            JTextField emailInput = new JTextField(50);
            JPasswordField validatePasswordInput = new JPasswordField(50);
            JTextField homeCityInput = new JTextField(50);

            username.setSize(new Dimension(200,80));
            email.setSize(new Dimension(200,40));
            validatePassword.setSize(new Dimension(200,40));
            homeCity.setSize(new Dimension(200,40));

            username.setForeground(Color.BLACK);
            email.setForeground(Color.BLACK);
            validatePassword.setForeground(Color.BLACK);
            homeCity.setForeground(Color.BLACK);

            this.add(username);
            this.add(email);
            this.add(validatePassword);
            this.add(homeCity);

            this.add(usernameInput);
            this.add(emailInput);
            this.add(validatePasswordInput);
            this.add(homeCityInput);

            username.setLocation(X_POS,FIRST_LINE_Y);
            email.setLocation(X_POS,FIRST_LINE_Y + Y_SPACE);
            validatePassword.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE*3);
            homeCity.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE*4);

            usernameInput.setBounds(330,FIRST_LINE_Y + 10,120,25);
            emailInput.setBounds(330,FIRST_LINE_Y + Y_SPACE + 10,120,25);
            validatePasswordInput.setBounds(330, FIRST_LINE_Y + Y_SPACE*3 + 10, 120, 25);
            homeCityInput.setBounds(330, FIRST_LINE_Y + Y_SPACE*4 + 10, 120, 25);

            JButton SignUpButton = new JButton(startMenuPresenter.signUpSystem(0));
            initializeButton(SignUpButton, 100, 30, 450, FIRST_LINE_Y + Y_SPACE*5);

            final ArrayList<Integer>[] allTypeInvalidInput = new ArrayList[]{new ArrayList<>()};

            JLabel invalid = new JLabel();
            StringBuilder warnings = new StringBuilder("<html>");

            SignUpButton.addActionListener(e -> {
                inputtedEmail = emailInput.getText();
                inputtedUsername = usernameInput.getText();
                inputtedPassword = String.valueOf(passwordInput.getPassword());
                validateInputtedPassword = String.valueOf(validatePasswordInput.getPassword());
                inputtedHomeCity = homeCity.getText();
                allTypeInvalidInput[0] = systemController.normalUserSignUp(inputtedUsername,
                        inputtedEmail, inputtedPassword, validateInputtedPassword, inputtedHomeCity);
                for(int type : allTypeInvalidInput[0])
                    warnings.append("<br/>" + startMenuPresenter.signUpSystem(type));
                warnings.append("<html>");
                invalid.setText(warnings.toString());
                invalid.setSize(800,200);
                System.out.println(warnings.toString());
                invalid.setForeground(Color.red);

            });

            this.add(invalid);
            invalid.setLocation(130, 300);

        }
    }
}
