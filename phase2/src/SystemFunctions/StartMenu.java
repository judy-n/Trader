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
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-08-06
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
    private SystemPresenter systemPresenter;
    private final int FIRST_LINE_Y = 100;
    private final int X_POS = 160;
    private final int Y_SPACE = 70;
    private final int X_SPACE = 200;
    private JLabel invalid;
    private final ArrayList<Integer>[] allTypeInvalidInput = new ArrayList[]{new ArrayList<>()};
    private JFrame parent;

    /**
     * Creates a <StartMenu></StartMenu> with the given system controller and parent frame
     * that lets the user choose their next course of action through user input.
     *
     * @param systemController the master controller for the program
     * @param parent           the parent frame
     */
    public StartMenu(SystemController systemController, JFrame parent) {
        this.parent = parent;
        this.systemController = systemController;
        systemPresenter = new SystemPresenter();
        invalid = new JLabel();
        this.setPreferredSize(new Dimension(820, 576));
        this.setLayout(null);
        mainMenu();
    }

    private void mainMenu() {
        JButton login = new JButton(systemPresenter.loginSystem(6));
        JButton signUp = new JButton(systemPresenter.signUpSystem(19));
        JButton demo = new JButton(systemPresenter.startMenu(4));
        JButton endProgram = new JButton(systemPresenter.startMenu(5));

        initializeButton(login, 200, 40, 160, 190);
        initializeButton(signUp, 200, 40, 440, 190);
        initializeButton(demo, 200, 40, 310, 250);
        initializeButton(endProgram, 200, 40, 310, 310);

        JLabel welcomeText = new JLabel(systemPresenter.startMenu(1));

        welcomeText.setFont(font);
        welcomeText.setSize(new Dimension(300, 40));

        welcomeText.setForeground(Color.BLACK);

        this.add(welcomeText);
        welcomeText.setLocation(310, 50);

        login.addActionListener(e ->
        {
            this.removeAll();
            this.revalidate();
            getUserInfo(systemPresenter.loginSystem(0));
            this.repaint();
        });

        signUp.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            getUserInfo(systemPresenter.signUpSystem(0));
            this.repaint();
        });

        demo.addActionListener(e -> systemController.demoUser());

        endProgram.addActionListener(e -> {
            systemController.tryWriteManagers();
            System.exit(0);
        });
        this.validate();
        this.repaint();
    }

    private void initializeButton(JButton button, int width, int height, int xPos, int yPos) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setSize(new Dimension(width, height));
        button.setFocusPainted(false);
        this.add(button);
        button.setLocation(xPos, yPos);
    }

    private void getUserInfo(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(font);
        titleLabel.setSize(new Dimension(300, 40));
        titleLabel.setForeground(Color.BLACK);
        this.add(titleLabel);
        titleLabel.setLocation(100, 50);

        JButton exit = new JButton(systemPresenter.startMenu(3));
        initializeButton(exit, 65, 25, 20, 10);
        exit.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            mainMenu();
            this.repaint();
        });

        JLabel password = new JLabel(systemPresenter.signUpSystem(7));
        JPasswordField passwordInput = new JPasswordField(50);
        JCheckBox showPassword = new JCheckBox(systemPresenter.signUpSystem(14));

        password.setSize(new Dimension(200, 40));
        showPassword.setSize(showPassword.getPreferredSize());

        password.setForeground(Color.BLACK);
        this.add(password);
        this.add(passwordInput);
        this.add(showPassword);
        password.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE * 2);
        showPassword.setLocation(460, FIRST_LINE_Y + Y_SPACE * 2 + 10);
        passwordInput.setBounds(330, FIRST_LINE_Y + Y_SPACE * 2 + 10, 120, 25);

        passwordInput.addActionListener(e -> inputtedPassword = String.valueOf(passwordInput.getPassword()));
        showPassword.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordInput.setEchoChar((char) 0);
            } else {
                passwordInput.setEchoChar('\u2022');
            }
        });

        if (title.equals(systemPresenter.loginSystem(0))) {
            JLabel usernameOrEmail = new JLabel(systemPresenter.loginSystem(1));
            JTextField usernameEmailInput = new JTextField(50);
            usernameOrEmail.setSize(new Dimension(200, 40));
            usernameOrEmail.setForeground(Color.BLACK);
            this.add(usernameOrEmail);
            this.add(usernameEmailInput);
            usernameOrEmail.setLocation(X_POS, FIRST_LINE_Y);
            usernameEmailInput.setBounds(330, 110, 120, 25);

            JButton login = new JButton(systemPresenter.loginSystem(0));
            initializeButton(login, 100, 30, 450, 350);

            login.addActionListener(e -> {
                StringBuilder warnings = new StringBuilder("<html>");
                emailOrUsername = usernameEmailInput.getText();
                inputtedPassword = String.valueOf(passwordInput.getPassword());
                allTypeInvalidInput[0] = systemController.userLogin(emailOrUsername, inputtedPassword);
                makeInvalidInputWarning(allTypeInvalidInput[0], "login");
                if (allTypeInvalidInput[0].isEmpty()) {
                    loggedIn();
                    systemController.userLogin(emailOrUsername, parent);
                    this.setVisible(true);
                }
            });
        } else {
            JLabel username = new JLabel(systemPresenter.signUpSystem(4));
            JLabel email = new JLabel(systemPresenter.signUpSystem(1));
            JLabel validatePassword = new JLabel(systemPresenter.signUpSystem(9));
            JLabel homeCity = new JLabel(systemPresenter.signUpSystem(13));

            JTextField usernameInput = new JTextField(50);
            JTextField emailInput = new JTextField(50);
            JPasswordField validatePasswordInput = new JPasswordField(50);
            JTextField homeCityInput = new JTextField(50);

            username.setSize(new Dimension(200, 80));
            email.setSize(new Dimension(200, 40));
            validatePassword.setSize(new Dimension(200, 40));
            homeCity.setSize(new Dimension(200, 40));

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

            username.setLocation(X_POS, FIRST_LINE_Y);
            email.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE);
            validatePassword.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE * 3);
            homeCity.setLocation(X_POS, FIRST_LINE_Y + Y_SPACE * 4);

            usernameInput.setBounds(330, FIRST_LINE_Y + 10, 120, 25);
            emailInput.setBounds(330, FIRST_LINE_Y + Y_SPACE + 10, 120, 25);
            validatePasswordInput.setBounds(330, FIRST_LINE_Y + Y_SPACE * 3 + 10, 120, 25);
            homeCityInput.setBounds(330, FIRST_LINE_Y + Y_SPACE * 4 + 10, 120, 25);

            JButton SignUpButton = new JButton(systemPresenter.signUpSystem(19));
            initializeButton(SignUpButton, 100, 30, 560, FIRST_LINE_Y + Y_SPACE * 5);

            SignUpButton.addActionListener(e -> {
                inputtedEmail = emailInput.getText();
                inputtedUsername = usernameInput.getText();
                inputtedPassword = String.valueOf(passwordInput.getPassword());
                validateInputtedPassword = String.valueOf(validatePasswordInput.getPassword());
                inputtedHomeCity = homeCityInput.getText();
                System.out.println(inputtedHomeCity);
                allTypeInvalidInput[0] = systemController.normalUserSignUpCheck(inputtedUsername,
                        inputtedEmail, inputtedPassword, validateInputtedPassword, inputtedHomeCity);
                makeInvalidInputWarning(allTypeInvalidInput[0], "sign-up");
                if (allTypeInvalidInput[0].isEmpty()) {
                    loggedIn();
                    System.out.println(parent.getTitle());
                    systemController.normalUserSignUp(inputtedUsername, inputtedEmail, inputtedPassword,
                            inputtedHomeCity, parent);
                    this.setVisible(true);
                }
            });
        }
    }

    private void loggedIn() {
        this.removeAll();
        this.revalidate();
        JButton logoutButton = new JButton(systemPresenter.loginSystem(7));
        initializeButton(logoutButton, 200, 40, X_POS, FIRST_LINE_Y + Y_SPACE);
        logoutButton.addActionListener(e -> {
            this.removeAll();
            this.revalidate();
            mainMenu();
            this.repaint();
        });
        JLabel loggedInMsg = new JLabel(systemPresenter.loginSystem(5));
        loggedInMsg.setForeground(Color.BLACK);
        loggedInMsg.setSize(400, 40);
        loggedInMsg.setFont(font);
        this.add(loggedInMsg);
        loggedInMsg.setLocation(X_POS, FIRST_LINE_Y);
        this.repaint();
    }

    private void makeInvalidInputWarning(ArrayList<Integer> allTypeInvalidInput, String loginOrSignup) {
        StringBuilder warnings = new StringBuilder("<html>");
        for (int type : allTypeInvalidInput)
            if (loginOrSignup.equals("login")) {
                warnings.append(systemPresenter.loginSystem(type)).append("<br/>");
            } else if (loginOrSignup.equals("sign-up")) {
                warnings.append(systemPresenter.signUpSystem(type)).append("<br/>");
            }
        warnings.append("<html>");
        invalid.setText(warnings.toString());
        invalid.setSize(800, 200);
        invalid.setForeground(Color.red);
        this.add(invalid);
        invalid.setLocation(X_POS, 380);
    }
}
