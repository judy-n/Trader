package SystemFunctions;
/**
 * The presenter used for the AdminUser's dashboard, returns strings to display.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-10
 */
public class AdminDashPresenter {

    /**
     * Returns the text displayed on JComponents when a normal user's
     * dashboard is displayed
     * @param type int indicating type of JComponent
     * @return the String needed to be displayed
     */
    public String setUpDash(int type){
        switch (type){
            case 1:
                return ("Catalog Editor");
            case 2:
                return ("Freeze Accounts");
            case 3:
                return ("Unfreeze Accounts");
            case 4:
                return ("Threshold Editor");
            case 5:
                return ("Create New Admin");
            case 6:
                return ("Undo User Activity");
            default:
                return null;
        }
    }

    /**
     * Returns the text displayed on a pop up window
     * @param type int indicating the type of message
     * @return the String needed to be displayed
     */
    public String getPopUpMessage(int type){
        switch (type){
            case 1:
                return ("Threshold values must be positive integers!");
            case 2:
                return ("Invalid input!");
            case 3:
                return ("New admin created!");
            default:
                return null;
        }
    }

    /**
     * Returns the labels of the thresholds in an array
     *
     * @param currThresholds the current threshold values of the system
     */
    public String[] getThresholdStrings(int[] currThresholds) {

        String[] thresholdStrings = new String[4];

        thresholdStrings[0] = ("The current weekly trade max is: " + currThresholds[0]);
        thresholdStrings[1] = ("The current meeting edit max is: " + currThresholds[1]);
        thresholdStrings[2] = ("The current lend minimum is: " + currThresholds[2]);
        thresholdStrings[3] = ("The current incomplete trade max is: " + currThresholds[3]);

        return thresholdStrings;
    }

}
