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
                        String line1 = Files.readAllLines(Paths.get("src/thresholds.txt")).get(0);
                        String[] splitLine = line1.split(":");
                        int oldWeeklyTradeMax = Integer.parseInt(splitLine[1]);
                        systemPresenter.thresholdEditor(1, oldWeeklyTradeMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("weeklyTradeMax :", oldWeeklyTradeMax, newThreshold, 0);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserWeeklyTradeMax(newThreshold);
                        break;
                    case 2:
                        // Edits the threshold values for all future users that will be created.
                        String line2 = Files.readAllLines(Paths.get("src/thresholds.txt")).get(1);
                        String[] splitLine1 = line2.split(":");
                        int oldMeetingEditMax = Integer.parseInt(splitLine1[1]);
                        systemPresenter.thresholdEditor(2, oldMeetingEditMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("meetingEditMax :", oldMeetingEditMax, newThreshold, 1);

                        // Edits the threshold values for all current registered users.
                        userManager.setALlNormalUserMeetingEditMax(newThreshold);
                    case 3:
                        // Edits the threshold values for all future users that will be created.
                        String line3 = Files.readAllLines(Paths.get("src/thresholds.txt")).get(2);
                        String[] splitLine2 = line3.split(":");
                        int oldLendMinimum = Integer.parseInt(splitLine2[1]);
                        systemPresenter.thresholdEditor(3, oldLendMinimum);
                        newThreshold = thresholdInputCheck();
                        editThreshold("lendMinimum :", oldLendMinimum, newThreshold, 2);

                        // Edits the threshold values for all current registered users.
                        userManager.setAllNormalUserLendMinimum(newThreshold);
                        break;
                    case 4:
                        // Edits the threshold values for all future users that will be created.
                        String line = Files.readAllLines(Paths.get("src/thresholds.txt")).get(3);
                        String[] splitLine3 = line.split(":");
                        int oldIncompleteMax = Integer.parseInt(splitLine3[1]);
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
        new AdminDashboard(currentUser, itemManager, userManager);
    }
}
