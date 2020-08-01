package AdminUserFunctions;
import SystemManagers.*;
import Entities.*;
import java.io.*;
import SystemFunctions.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
    private ItemManager itemManager;
    private UserManager userManager;
    private AdminUser currentUser;
    private SystemPresenter systemPresenter;
    private BufferedReader bufferedReader;
    private ReadWriter readWriter;
    private final String THRESHOLD_FILE_PATH = "src/thresholds.txt";;

    /**
     * Creates a <ThresholdEditor></ThresholdEditor> with the given admin and item/user managers.
     * Lets an admin change a certain user's threshold values through user input.
     *
     * @param user the admin who's currently logged in
     * @param im   the system's item manager
     * @param um   the system's user manager
     */
    public ThresholdEditor(AdminUser user, ItemManager im, UserManager um) {
        currentUser = user;
        itemManager = im;
        userManager = um;
        readWriter = new ReadWriter();


        systemPresenter = new SystemPresenter();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        NormalUser subjectUser;
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
                    subjectUser = userManager.getNormalByUsername(usernameInput);

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
                            systemPresenter.thresholdEditor(1, subjectUser.getWeeklyTradeMax());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setWeeklyTradeMax(newThreshold);
                            break;
                        case 2:
                            systemPresenter.thresholdEditor(2, subjectUser.getMeetingEditMax());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setMeetingEditMax(newThreshold);
                            break;
                        case 3:
                            systemPresenter.thresholdEditor(3, subjectUser.getLendMinimum());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setLendMinimum(newThreshold);
                            break;
                        case 4:
                            systemPresenter.thresholdEditor(4, subjectUser.getIncompleteMax());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setIncompleteMax(newThreshold);
                            break;
                    }
                }
                close();

            } catch (IOException e) {
                systemPresenter.exceptionMessage();
            }


        }else if (temp1.matches("2")){
                systemPresenter.thresholdEditor(2);
                String temp2 = bufferedReader.readLine();
                while (!temp2.matches("[0-4]")) {
                    systemPresenter.invalidInput();
                    temp2 = bufferedReader.readLine();
                }
                int choiceInput = Integer.parseInt(temp2);

                switch(choiceInput) {
                    case 0:
                        break;
                    case 1:
                        // Edits the threshold values for all future users that will be created.
                        int oldWeeklyTradeMax = readWriter.readThresholdsFromFile(THRESHOLD_FILE_PATH)[0];
                        systemPresenter.thresholdEditor(1, oldWeeklyTradeMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("weeklyTradeMax :", oldWeeklyTradeMax, newThreshold, 0);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserWeeklyTradeMax(newThreshold);
                        break;
                    case 2:
                        // Edits the threshold values for all future users that will be created.
                        int oldMeetingEditMax = readWriter.readThresholdsFromFile(THRESHOLD_FILE_PATH)[1];
                        systemPresenter.thresholdEditor(2, oldMeetingEditMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("meetingEditMax :", oldMeetingEditMax, newThreshold, 1);

                        // Edits the threshold values for all current registered users.
                        userManager.setALlNormalUserMeetingEditMax(newThreshold);
                    case 3:
                        // Edits the threshold values for all future users that will be created.
                        int oldLendMinimum = readWriter.readThresholdsFromFile(THRESHOLD_FILE_PATH)[2];
                        systemPresenter.thresholdEditor(3, oldLendMinimum);
                        newThreshold = thresholdInputCheck();
                        editThreshold("lendMinimum :", oldLendMinimum, newThreshold, 2);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserLendMinimum(newThreshold);
                        break;
                    case 4:
                        // Edits the threshold values for all future users that will be created.
                        int oldIncompleteMax = readWriter.readThresholdsFromFile(THRESHOLD_FILE_PATH)[3];
                        systemPresenter.thresholdEditor(4, oldIncompleteMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("incompleteMax :", oldIncompleteMax, newThreshold, 3);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserIncompleteMax(newThreshold);
                        break;
                }
            }
            close();
        } catch(IOException e){
            systemPresenter.exceptionMessage();
        }

    }

    /**
     * Returns a valid threshold based on user input.
     * @return threshold value.
     */
    private int thresholdInputCheck() throws IOException {
        String temp2 = bufferedReader.readLine();
        while (!temp2.matches("[0-9]+")) {
            systemPresenter.invalidInput();
            temp2 = bufferedReader.readLine();
        }
        return Integer.parseInt(temp2);
    }

    /**
     * Updates the threshold values of this thresholdType in the file and in the currentThresholds of userManager.
     *
     * @param thresholdType the name of the threshold that's being edited.
     * @param oldThreshold  the previous value of this threshold
     * @param newThreshold  the new value for the threshold (given by the admin)
     * @param thresholdIndex the index of this threshold (0: weeklyTradeMax, 1: meetingEditMax, 2: lendMinimum, 3: incompleteMax)
     */
    private void editThreshold(String thresholdType, int oldThreshold, int newThreshold, int thresholdIndex) throws FileNotFoundException {
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
        new AdminDashboard(currentUser, itemManager, userManager);
    }
}
