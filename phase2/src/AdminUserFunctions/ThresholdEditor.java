package AdminUserFunctions;

import SystemManagers.ItemManager;
import SystemManagers.NotificationSystem;
import SystemManagers.UserManager;
import Entities.AdminUser;
import SystemFunctions.SystemPresenter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Lets an admin change a certain threshold value for a certain user.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @author Liam Huff
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-31
 */
public class ThresholdEditor {
    private AdminUser currentUser;
    private ItemManager itemManager;
    private UserManager userManager;
    private NotificationSystem notifSystem;
    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;

    /**
     * Creates a <ThresholdEditor></ThresholdEditor> with the given admin,
     * item/user managers, and notification system.
     * Lets an admin change a certain user's threshold values through user input.
     *
     * @param user        the admin who's currently logged in
     * @param itemManager the system's item manager
     * @param userManager the system's user manager
     * @param notifSystem the system's notification manager
     */
    public ThresholdEditor(AdminUser user, ItemManager itemManager, UserManager userManager, NotificationSystem notifSystem) {
        currentUser = user;
        this.itemManager = itemManager;
        this.userManager = userManager;
        this.notifSystem = notifSystem;

        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String usernameInput;
        int newThreshold;
        systemPresenter.thresholdEditor(0);

        try {
            String temp1 = bufferedReader.readLine();
            while (!temp1.matches("[0-2]")) {
                systemPresenter.invalidInput();
                temp1 = bufferedReader.readLine();
            }

            if (temp1.matches("1")) {
                systemPresenter.thresholdEditor(1);
                try {
                    usernameInput = bufferedReader.readLine();
                    while (!usernameInput.equals("0") && userManager.normalUsernameExists(usernameInput)) {
                        systemPresenter.invalidInput();
                        usernameInput = bufferedReader.readLine();
                    }

                    if (!usernameInput.equals("0")) {

                        /* in case the username entered belongs to an admin */
                        if (userManager.getNormalByUsername(usernameInput) != null) {
                            systemPresenter.thresholdEditor(3);
                        } else {
                            systemPresenter.thresholdEditor(2);
                            String temp = bufferedReader.readLine();
                            while (!temp.matches("[0-4]")) {
                                systemPresenter.invalidInput();
                                temp = bufferedReader.readLine();
                            }
                            int choiceInput = Integer.parseInt(temp);

                            switch (choiceInput) {
                                case 0:
                                    break;
                                case 1:
                                    systemPresenter.thresholdEditor(1, userManager.getNormalUserWeeklyTradeMax(usernameInput));
                                    newThreshold = thresholdInputCheck();
                                    userManager.setNormalUserWeeklyTradeMax(usernameInput, newThreshold);
                                    break;
                                case 2:
                                    systemPresenter.thresholdEditor(2, userManager.getNormalUserMeetingEditMax(usernameInput));
                                    newThreshold = thresholdInputCheck();
                                    userManager.setNormalUserMeetingEditMax(usernameInput, newThreshold);
                                    break;
                                case 3:
                                    systemPresenter.thresholdEditor(3, userManager.getNormalUserLendMinimum(usernameInput));
                                    newThreshold = thresholdInputCheck();
                                    userManager.setNormalUserLendMinimum(usernameInput, newThreshold);
                                    break;
                                case 4:
                                    systemPresenter.thresholdEditor(4, userManager.getNormalUserIncompleteMax(usernameInput));
                                    newThreshold = thresholdInputCheck();
                                    userManager.setNormalUserIncompleteMax(usernameInput, newThreshold);
                                    break;
                            }
                        }
                    }
                    close();

                } catch (IOException e) {
                    systemPresenter.exceptionMessage();
                }


            } else if (temp1.matches("2")) {
                systemPresenter.thresholdEditor(2);
                String temp2 = bufferedReader.readLine();
                while (!temp2.matches("[0-4]")) {
                    systemPresenter.invalidInput();
                    temp2 = bufferedReader.readLine();
                }
                int choiceInput = Integer.parseInt(temp2);

                switch (choiceInput) {
                    case 0:
                        break;
                    case 1:
                        // Edits the threshold values for all future users that will be created.
                        int oldWeeklyTradeMax = userManager.getCurrentThresholds()[0];
                        systemPresenter.thresholdEditor(1, oldWeeklyTradeMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("weeklyTradeMax:", oldWeeklyTradeMax, newThreshold, 0);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserWeeklyTradeMax(newThreshold);
                        break;
                    case 2:
                        // Edits the threshold values for all future users that will be created.
                        int oldMeetingEditMax = userManager.getCurrentThresholds()[1];
                        systemPresenter.thresholdEditor(2, oldMeetingEditMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("meetingEditMax:", oldMeetingEditMax, newThreshold, 1);

                        // Edits the threshold values for all current registered users.
                        userManager.setALlNormalUserMeetingEditMax(newThreshold);
                    case 3:
                        // Edits the threshold values for all future users that will be created.
                        int oldLendMinimum = userManager.getCurrentThresholds()[2];
                        systemPresenter.thresholdEditor(3, oldLendMinimum);
                        newThreshold = thresholdInputCheck();
                        editThreshold("lendMinimum:", oldLendMinimum, newThreshold, 2);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserLendMinimum(newThreshold);
                        break;
                    case 4:
                        // Edits the threshold values for all future users that will be created.
                        int oldIncompleteMax = userManager.getCurrentThresholds()[3];
                        systemPresenter.thresholdEditor(4, oldIncompleteMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("incompleteMax:", oldIncompleteMax, newThreshold, 3);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserIncompleteMax(newThreshold);
                        break;
                }
            }
            close();
        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }

    }

    private int thresholdInputCheck() throws IOException {
        String temp2 = bufferedReader.readLine();
        while (!temp2.matches("[0-9]+")) {
            systemPresenter.invalidInput();
            temp2 = bufferedReader.readLine();
        }
        return Integer.parseInt(temp2);
    }

    private void editThreshold(String thresholdType, int oldThreshold, int newThreshold, int thresholdIndex) throws FileNotFoundException {
        String THRESHOLD_FILE_PATH = "src/thresholds.txt";
        File file = new File(THRESHOLD_FILE_PATH);
        String oldContent = "";

        BufferedReader reader = new BufferedReader(new FileReader(file));

        try {
            String line = reader.readLine();

            while (line != null) {

                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(thresholdType + oldThreshold,
                    thresholdType + newThreshold);

            FileWriter writer = new FileWriter(file);
            writer.write(newContent);
            reader.close();
            writer.close();

        } catch (IOException e) {
            systemPresenter.exceptionMessage();
        }
        // Updates the thresholds for UserManager.
        int [] newDefault = userManager.getCurrentThresholds();
        newDefault[thresholdIndex] = newThreshold;
        userManager.setCurrentThresholds(newDefault);

    }

    private void close() {
        new AdminDashboard(currentUser, itemManager, userManager, notifSystem);
    }
}
