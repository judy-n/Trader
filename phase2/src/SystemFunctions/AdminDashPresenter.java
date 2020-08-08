package SystemFunctions;
/**
 * The presenter used for the AdminUser's dashboard, returns strings to display
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-07
 * last modified 2020-08-07
 */
public class AdminDashPresenter {

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
