package SystemFunctions;

/**
 * User's dashboard that interprets user inputs
 *
 * @author Ning Zhang
 * @version 1.0
 * @since 2020-07-29
 * last modified 2020-08-04
 */

public abstract class Dashboard{
    public abstract String getUsername();
    public abstract int getType();
    public abstract String setUpDash(int type);
    public abstract void setPopUpMessage(int type);
    public abstract String getPopUpMessage();
    public abstract void resetPopUpMessage();
    public abstract String[] getUserInfo();
    public abstract void validatePasswordChange(String password, String validatePassword);
    public abstract String getHelpMessage();
}
