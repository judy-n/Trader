package AdminUserFunctions;

import SystemFunctions.ReadWriter;
import SystemManagers.UserManager;
import SystemFunctions.SystemPresenter;
import java.io.IOException;

/**
 * Helps let an admin change the threshold values for all users of the program.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @author Judy Naamani
 * @author Liam Huff
 * @version 1.0
 * @since 2020-07-05
 * last modified 2020-08-06
 */
public class ThresholdEditor {
    private UserManager userManager;
    private SystemPresenter systemPresenter;
    private ReadWriter readWriter;
    private String currUsername;

    /**
     * Creates a <ThresholdEditor></ThresholdEditor> with the given admin username and user manager.
     *
     * @param username    the username of the admin who's currently logged in
     * @param userManager the system's user manager
     */
    public ThresholdEditor(String username, UserManager userManager) {

        this.currUsername = username;
        this.userManager = userManager;
        readWriter = new ReadWriter();
        systemPresenter = new SystemPresenter();

        // 1. Display the current value of each threshold and have a field where the user can enter a value?
        //    Lets admins change multiple values at once ig
        //
        //      e.g. The current weekly trade max is 5. Change it to: _________
        //
        //           The current meeting edit max is 3. Change it to: ---------
        //
        //           etc...
        //
        //      > getThresholdStrings() for array of labels
        // 2. If "Set thresholds" button is clicked (label: systemPresenter.thresholdEditor(1)),
        //      create String array with text from all fields (I'll handle empty strings)
        //      in the order: weekly trade, meeting edit, lend min, incomplete trade
        //
        // 3. Pass String array into thresholdInputValidate() to validate input
        //      > if returns false, display error message: systemPresenter.thresholdEditor(2);
        //      > if true, applyThresholdChanges()
    }

    /**
     * Returns the labels for the text fields where the user enters a new threshold value.
     *
     * @return an array of labels for the threshold text fields
     */
    public String[] getThresholdStrings() {
        return systemPresenter.getThresholdStrings(userManager.getThresholdSystem().getAllThresholds());
    }

    /**
     * Validates the new threshold values input by the user.
     * Each non-empty input must be a positive integer.
     *
     * @param inputThresholds the string representations of the user's threshold inputs
     * @return true iff all non-empty inputs are valid
     */
    public boolean thresholdInputValidate(String[] inputThresholds) {
        for (String threshold : inputThresholds) {
            if (!threshold.matches("[\\d]*")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the default threshold values to the new (valid) values input by the user.
     * This changes the threshold values for all currently registered users and for future users.
     * Also notifies all users of the specific thresholds changed.
     *
     * @param inputThresholds the string representations of the user's threshold inputs
     */
    public void applyThresholdChanges(String[] inputThresholds) {
        for (int i = 0; i < inputThresholds.length; i++) {
            if (!inputThresholds[i].isEmpty()) {
                int newThreshold = Integer.parseInt(inputThresholds[i]);
                editThreshold(userManager.getThresholdSystem().getThresholdType(i) + ":",
                        userManager.getThresholdSystem().getAllThresholds()[i], newThreshold, i);

                /* Notify all users of threshold change */
                for (String normalUsername : userManager.getAllNormalUsernames()) {
                    userManager.notifyUser(normalUsername).thresholdUpdate
                            ("THRESHOLD ALL USERS", normalUsername, currUsername, i, newThreshold);
                }
            }
        }
    }

    private void editThreshold(String thresholdType, int oldThreshold, int newThreshold, int thresholdIndex) {
        // Writes to the file.
        String THRESHOLD_FILE_PATH = "src/thresholds.txt";
        try {
            readWriter.saveThresholdsToFile(THRESHOLD_FILE_PATH, thresholdType, oldThreshold, newThreshold);
        } catch (IOException e) {
            systemPresenter.exceptionMessage(1, "Writing", "threshold values");
        }

        // Updates the thresholds for UserManager.
        int[] newDefault = userManager.getThresholdSystem().getAllThresholds();
        newDefault[thresholdIndex] = newThreshold;
        userManager.getThresholdSystem().setAllThresholds(newDefault);
    }
}
