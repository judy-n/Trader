import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * Allows the user to suggest a date and time for a trade meeting through user input
 * and runs checks on the validity of the date/time as well as if the meeting suggested doesn't
 * fall in the same week as a week with maximum number of trades allowed for the user.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-08
 * last modified 2020-07-28
 */
public class DateTimeSuggestion {
    private NormalUser currentUser;
    private TradeManager tradeManager;
    private SystemPresenter systemPresenter;
    private final String dateFormat = "yyyy/MM/dd";
    private String[] dateTime;
    private String[] date;
    private String[] time;

    /**
     * Creates a <DateTimeSuggestion></DateTimeSuggestion> with the given normal user and trade manager.
     *
     * @param user the normal user who's currently logged in
     * @param tm   the system's trade manager
     */
    public DateTimeSuggestion(NormalUser user, TradeManager tm) {
        currentUser = user;
        tradeManager = tm;
        systemPresenter = new SystemPresenter();
    }

    /**
     * Allows the user to suggest a date and time for a trade meeting through user input
     * and runs checks on the validity of the date/time as well as if the meeting suggested doesn't
     * fall in the same week as a week with maximum number of trades allowed for the user.
     *
     * @return a valid date and time suggested by the user
     * @throws IOException when an IO error occurs while user input is being read
     */
    public LocalDateTime suggestDateTime() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        LocalDateTime time;
        int tradesInWeek;
        do {
            String timeSuggestion = bufferedReader.readLine().trim();
            boolean isValid = checkDateTime(timeSuggestion);
            while (!isValid) {
                systemPresenter.invalidInput();
                timeSuggestion = bufferedReader.readLine().trim();
                isValid = checkDateTime(timeSuggestion);
            }
            time = LocalDateTime.of(getYear(), getMonth(),
                    getDay(), getHour(), getMinute());
            tradesInWeek = tradeManager.getNumMeetingsThisWeek(currentUser.getUsername(), time.toLocalDate());
            if (tradesInWeek >= currentUser.getWeeklyTradeMax()) {
                systemPresenter.failedSuggestion();
            }
        } while (tradesInWeek >= currentUser.getWeeklyTradeMax());
        return time;
    }

    private boolean checkDateTime(String suggestion) {
        if (!suggestion.contains("-")) {
            return false;
        }
        dateTime = suggestion.split("-");

        if (dateTime.length == 2 && isThisDateValid(dateTime[0]) && isThisTimeValid(dateTime[1])) {
            return LocalDateTime.of(getYear(), getMonth(), getDay(), getHour(), getMinute()).isAfter(LocalDateTime.now());
        } else {
            return false;
        }
    }

    private int getYear() {
        date = dateTime[0].split("/");
        return Integer.parseInt(date[0]);
    }

    private int getMonth() {
        return Integer.parseInt(date[1]);
    }

    private int getDay() {
        return Integer.parseInt(date[2]);
    }

    private int getHour() {
        time = dateTime[1].split(":");
        return Integer.parseInt(time[0]);
    }

    private int getMinute() {
        return Integer.parseInt(time[1]);
    }


    private boolean isThisTimeValid(String s) {
        String[] arr = s.split(":");
        if (arr.length != 2 || !arr[0].matches("[0-9]+") || !arr[1].matches("[0-9]+")) {
            return false;
        }
        int hr = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        if (hr < 0 || hr > 23) {
            return false;
        }
        return min >= 0 && min <= 59;
    }


    /*
     * Checks if the given date is valid.
     * Based on code by mkyong from https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/.
     */
    private boolean isThisDateValid(String dateToValidate) {
        if (dateToValidate.isEmpty() || dateToValidate.contains(" ")) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {
            sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
