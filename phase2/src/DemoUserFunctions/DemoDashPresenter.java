package DemoUserFunctions;
/**
 * The presenter used for demo dashboard.
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-08-11
 * last modified 2020-08-11
 */

public class DemoDashPresenter {

    /**
     * Returns Strings used for JComponents on a demo dashboard
     * @param type the type of string needed
     * @return the string for display
     */
    public String setUpDash(int type){
        switch (type){
            case 1:
                return ("View Catalog");
            case 2:
                return ("Trade");
            case 3:
                return ("Sign up to trade ;)");
            default:
                return "";
        }
    }
}
