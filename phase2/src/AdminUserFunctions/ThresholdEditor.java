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
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-07-28
 */
public class ThresholdEditor {
    private ItemManager itemManager;
    private UserManager userManager;
    private AdminUser currentUser;
    private SystemPresenter sp;
    private BufferedReader br;

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

        sp = new SystemPresenter();
        br = new BufferedReader(new InputStreamReader(System.in));

        NormalUser subjectUser;
        String usernameInput;
        int newThreshold;
        sp.thresholdEditor(0);

        try {
            String temp1 = br.readLine();
            while (!temp1.matches("[0-2]")) {
                sp.invalidInput();
                temp1 = br.readLine();
            }

            if (temp1.matches("1")) {
                sp.thresholdEditor(1);
            try {
                usernameInput = br.readLine();
                while (!usernameInput.equals("0") && userManager.normalUsernameExists(usernameInput)) {
                    sp.invalidInput();
                    usernameInput = br.readLine();
                }

                if (!usernameInput.equals("0")) {
                    subjectUser = userManager.getNormalByUsername(usernameInput);

                    sp.thresholdEditor(2);
                    String temp = br.readLine();
                    while (!temp.matches("[0-4]")) {
                        sp.invalidInput();
                        temp = br.readLine();
                    }
                    int choiceInput = Integer.parseInt(temp);

                    switch (choiceInput) {
                        case 0:
                            break;
                        case 1:
                            sp.thresholdEditor(1, subjectUser.getWeeklyTradeMax());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setWeeklyTradeMax(newThreshold);
                            break;
                        case 2:
                            sp.thresholdEditor(2, subjectUser.getMeetingEditMax());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setMeetingEditMax(newThreshold);
                            break;
                        case 3:
                            sp.thresholdEditor(3, subjectUser.getLendMinimum());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setLendMinimum(newThreshold);
                            break;
                        case 4:
                            sp.thresholdEditor(4, subjectUser.getIncompleteMax());
                            newThreshold = thresholdInputCheck();
                            subjectUser.setIncompleteMax(newThreshold);
                            break;
                    }
                }
                close();

            } catch (IOException e) {
                sp.exceptionMessage();
            }


        }else if (temp1.matches("2")){
                sp.thresholdEditor(2);
                String temp2 = br.readLine();
                while (!temp2.matches("[0-4]")) {
                    sp.invalidInput();
                    temp2 = br.readLine();
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
                        sp.thresholdEditor(1, oldWeeklyTradeMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("weeklyTradeMax :", oldWeeklyTradeMax, newThreshold);

                        // Edits the threshold values for all current registered users.
                        List<NormalUser> allNormals = um.getAllNormals();
                        for (NormalUser u: allNormals){
                            u.setWeeklyTradeMax(newThreshold);
                        }
                        break;
                    case 2:
                        // Edits the threshold values for all future users that will be created.
                        String line2 = Files.readAllLines(Paths.get("src/thresholds.txt")).get(1);
                        String[] splitLine1 = line2.split(":");
                        int oldMeetingEditMax = Integer.parseInt(splitLine1[1]);
                        sp.thresholdEditor(2, oldMeetingEditMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("meetingEditMax :", oldMeetingEditMax, newThreshold);

                        // Edits the threshold values for all current registered users.
                        allNormals = um.getAllNormals();
                        for (NormalUser u: allNormals){
                            u.setMeetingEditMax(newThreshold);
                        }
                        break;
                    case 3:
                        // Edits the threshold values for all future users that will be created.
                        String line3 = Files.readAllLines(Paths.get("src/thresholds.txt")).get(2);
                        String[] splitLine2 = line3.split(":");
                        int oldLendMinimum = Integer.parseInt(splitLine2[1]);
                        sp.thresholdEditor(3, oldLendMinimum);
                        newThreshold = thresholdInputCheck();
                        editThreshold("lendMinimum :", oldLendMinimum, newThreshold);

                        // Edits the threshold values for all current registered users.
                        allNormals = um.getAllNormals();
                        for (NormalUser u: allNormals){
                            u.setLendMinimum(newThreshold);
                        }
                        break;
                    case 4:
                        // Edits the threshold values for all future users that will be created.
                        String line = Files.readAllLines(Paths.get("src/thresholds.txt")).get(3);
                        String[] splitLine3 = line.split(":");
                        int oldIncompleteMax = Integer.parseInt(splitLine3[1]);
                        sp.thresholdEditor(4, oldIncompleteMax);
                        newThreshold = thresholdInputCheck();
                        editThreshold("incompleteMax :", oldIncompleteMax, newThreshold);

                        // Edits the threshold values for all current registered users.
                        allNormals = um.getAllNormals();
                        for (NormalUser u: allNormals){
                            u.setIncompleteMax(newThreshold);
                        }
                        break;
                }
            }
            close();
        } catch(IOException e){
            sp.exceptionMessage();
        }

    }

    private int thresholdInputCheck() throws IOException {
        String temp2 = br.readLine();
        while (!temp2.matches("[0-9]+")) {
            sp.invalidInput();
            temp2 = br.readLine();
        }
        return Integer.parseInt(temp2);
    }

    private void editThreshold(String thresholdType, int oldThreshold, int newThreshold) throws FileNotFoundException {
        File file = new File("src/thresholds.txt");
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
            sp.exceptionMessage();
        }
    }

    private void close() {
        new AdminDashboard(currentUser, itemManager, userManager);
    }
}
