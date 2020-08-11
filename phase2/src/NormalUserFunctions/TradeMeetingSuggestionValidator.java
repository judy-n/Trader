package NormalUserFunctions;

import SystemFunctions.DateTimeHandler;
import SystemManagers.TradeManager;
import SystemManagers.UserManager;

/**
 * Validates the trade meeting details suggested by a user.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-09
 * last modified 2020-08-09
 */
public class TradeMeetingSuggestionValidator {
    private String currUsername;
    private UserManager userManager;
    private TradeManager tradeManager;
    private DateTimeHandler dateTimeHandler;

    /**
     * Creates a new <TradeMeetingSuggestionValidator></TradeMeetingSuggestionValidator>
     * with the given normal username, user/trade managers, date/time handler, and system presenter.
     *
     * @param username        the username of the normal user who's currently logged in
     * @param userManager     the system's user manager
     * @param tradeManager    the system's trade manager
     * @param dateTimeHandler the system's date and time handler
     */
    public TradeMeetingSuggestionValidator(String username, UserManager userManager,
                                           TradeManager tradeManager, DateTimeHandler dateTimeHandler) {
        currUsername = username;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.dateTimeHandler = dateTimeHandler;
    }

    /**
     * Checks if the date/time and location suggested by the user are valid.
     * <p>
     * The date/time suggested must be in valid format and in the future.
     * The suggestion must also not fall in a week during which the user has
     * already scheduled the maximum number of meetings allowed.
     * <p>
     * The suggested location must consist of alphanumerical characters with
     * some extra character exceptions (e.g. A period for Rd. or Ave.).
     *
     * @param suggestedDateTime the date and time suggested by the user
     * @param suggestedLocation the location suggested by the user
     * @return an int representing an error type if the date/time or location is not valid
     */
    public int validateSuggestion(String suggestedDateTime, String suggestedLocation) {

        boolean dateTimeIsValid = dateTimeHandler.checkDateTime(suggestedDateTime);
        int errorType;

        if (dateTimeIsValid) {
            int tradesInWeek = tradeManager.getNumMeetingsThisWeek
                    (currUsername, dateTimeHandler.getLocalDateTime(suggestedDateTime).toLocalDate());
            if (tradesInWeek == userManager.getThresholdSystem().getWeeklyTradeMax()) {
                errorType = 13;
            } else {
                errorType = 0;
            }
        } else {
            errorType = 30;
        }

        if (errorType == 0 &&
                !suggestedLocation.matches("[A-Za-z0-9]+([\\s][A-Za-z0-9.]+)*")) {
            errorType = 30;
        }

        return errorType;
    }
}
