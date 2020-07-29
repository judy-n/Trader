import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

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
    private Font font = new Font("SansSerif", Font.PLAIN, 20);
    private String emailOrUsername;
    private String inputtedUsername;
    private String inputtedEmail;
    private String inputtedPassword;
    private String validateInputtedPassword;
    private SystemPresenter systemPresenter;

    /**
     * Creates a <StartMenu></StartMenu> that lets the user choose their next course of action through user input.
     */
    public StartMenu(SystemController systemController) {
        this.systemController = systemController;
        systemPresenter = new SystemPresenter();

        this.setPreferredSize(new Dimension(820, 576));
        this.setLayout(null);
        mainMenu();
    }

    private void mainMenu(){
        JButton login = new JButton(systemPresenter.startMenu(2));
        JButton signUp = new JButton(systemPresenter.startMenu(3));
        JButton demo = new JButton(systemPresenter.startMenu(4));
        JButton endProgram = new JButton(systemPresenter.startMenu(5));


        initializeButton(login, 200, 40, 160, 190);
        initializeButton(signUp, 200, 40, 440, 190);
        initializeButton(demo, 200, 40, 310, 250);
        initializeButton(endProgram, 200, 40, 310, 310);


        JLabel welcomeText = new JLabel(systemPresenter.startMenu(1));


        welcomeText.setFont(font);
        welcomeText.setSize(new Dimension(300,40));

        welcomeText.setForeground(Color.BLACK);

        this.add(welcomeText);
        welcomeText.setLocation(310, 50);

        login.addActionListener(e ->
        {
            this.removeAll();
            this.revalidate();
            getUserInfo("Login");
            this.repaint();
        });

        signUp.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            getUserInfo("Sign Up");
            this.repaint();
        });
        demo.addActionListener(e -> systemController.demoUser());
        endProgram.addActionListener(e -> {
            systemController.tryWriteManagers();
            System.exit(0);
            systemPresenter.exceptionMessage();
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

        JButton exit = new JButton("Back");
        initializeButton(exit, 65, 25, 20, 10);
        exit.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            mainMenu();
            this.repaint();
        });


        JLabel password = new JLabel("Enter password: ");
        JPasswordField passwordInput = new JPasswordField(50);
        JCheckBox showPassword = new JCheckBox("Show password");

        password.setSize(new Dimension(200,40));
        showPassword.setSize(showPassword.getPreferredSize());

        password.setForeground(Color.BLACK);
        this.add(password);
        this.add(passwordInput);
        this.add(showPassword);
        password.setLocation(160, 200);
        showPassword.setLocation(460,210);
        passwordInput.setBounds(330, 210, 120, 25);

        passwordInput.addActionListener(e -> inputtedPassword = String.valueOf(passwordInput.getPassword()));
        showPassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordInput.setEchoChar((char) 0);
            }else{
                passwordInput.setEchoChar('\u2022');
            }
        });

        if(title.equals("Login")){
            JLabel usernameOrEmail = new JLabel("Enter username or email: ");
            JTextField usernameEmailInput = new JTextField(50);
            usernameOrEmail.setSize(new Dimension(200,40));
            usernameOrEmail.setForeground(Color.BLACK);
            this.add(usernameOrEmail);
            this.add(usernameEmailInput);
            usernameOrEmail.setLocation(160, 100);
            usernameEmailInput.setBounds(330,110,120,25);

            JButton login = new JButton("Login");
            initializeButton(login, 100, 30, 450, 350);
            login.addActionListener(e -> {
                emailOrUsername = usernameEmailInput.getText();
                //more methods goes here
            });
        }else{
            JLabel username = new JLabel("Enter a username: ");
            JLabel email = new JLabel("Enter an email");
            JLabel validatePassword = new JLabel("Re-Enter password: ");
            JTextField usernameInput = new JTextField(50);
            JTextField emailInput = new JTextField(50);
            JPasswordField validatePasswordInput = new JPasswordField(50);

            username.setSize(new Dimension(200,40));
            email.setSize(new Dimension(200,40));
            validatePassword.setSize(new Dimension(200,40));
            username.setForeground(Color.BLACK);
            email.setForeground(Color.BLACK);
            validatePassword.setForeground(Color.BLACK);

            this.add(username);
            this.add(email);
            this.add(validatePassword);

            this.add(usernameInput);
            this.add(emailInput);
            this.add(validatePasswordInput);

            username.setLocation(160,100);
            email.setLocation(160,150);
            validatePassword.setLocation(160, 250);

            usernameInput.setBounds(330,110,120,25);
            emailInput.setBounds(330,160,120,25);
            validatePasswordInput.setBounds(330, 260, 120, 25);

            JButton SignUpButton = new JButton("Sign Up");
            initializeButton(SignUpButton, 100, 30, 450, 350);

            AtomicInteger typeInvalidInput = new AtomicInteger();
            JLabel invalid = new JLabel();
            SignUpButton.addActionListener(e -> {
                inputtedEmail = emailInput.getText();
                inputtedUsername = usernameInput.getText();
                inputtedPassword = String.valueOf(passwordInput.getPassword());
                validateInputtedPassword = String.valueOf(validatePasswordInput.getPassword());

                typeInvalidInput.set(systemController.normalUserSignUp(inputtedUsername,
                        inputtedEmail, inputtedPassword, validateInputtedPassword));

                invalid.setText(systemPresenter.signUpSystem(typeInvalidInput.intValue()));
                invalid.setSize(800,30);

                invalid.setForeground(Color.red);
                this.add(invalid);

                invalid.setLocation(130, 400);
            });



        }




    }


}
