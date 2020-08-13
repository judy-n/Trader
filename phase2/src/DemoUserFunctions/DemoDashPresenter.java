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
            default:
                return "";
        }
    }

    /**
     * Returns String needed for display on the pop up window
     * @return the String
     */
    public String getPopUpMessage(){
        return ("Sign up to trade ;)");
    }

    /**
     * Returns a message used to display on the help section of the dashboard
     * @return a message used to display on the help section of the dashboard
     */
    public String getHelpMessage(){
        return "<html>To see all items available to trade right now, <br/>" +
                "click the \"View Catalog\" button!<html>";
    }

    /**
     * Returns a string array used for display when the demo user clicks their user info
     * @return the string array
     */
    public String[] getUserInfo(){
        return new String[]{"You are currently demoing our trade program!",
                "If you want to trade with other users, consider closing this window and signing up :)"};
    }
}
