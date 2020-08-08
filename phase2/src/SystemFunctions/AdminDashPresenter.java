package SystemFunctions;
/**
 * The presenter used for the AdminUser's dashboard, returns strings to display.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-07
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
                return ("UnFreeze Accounts");
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
                return ("Thresholds must be numbers!");
            case 2:
                return ("Input format is invalid!");
            case 3:
                return ("New admin created!");
            default:
                return null;
        }
    }

}
