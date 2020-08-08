package SystemFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores the algorithm for checking the validity of a date/time and methods to convert strings to
 * <LocalDateTime></LocalDateTime> and vice versa.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-08
 * last modified 2020-08-07
 */
public class DateTimeHandler {
    private final String DATE_FORMAT = "yyyy/MM/dd";
    private final String DATE_TIME_FORMAT = DATE_FORMAT + "-hh:mm";
    private String[] dateTime;
    private String[] date;
    private String[] time;

    /**
     * Creates a <DateTimeHandler></DateTimeHandler>.
     */
    public DateTimeHandler() {
    }

    /**
     * Checks the validity of the given date/time.
     * The given date/time must be formatted properly and must be in the future.
     *
     * @param suggestion the date and time suggested by the user
     * @return whether or not the date and time suggested by the user are valid
     */
    public boolean checkDateTime(String suggestion) {
        dateTime = suggestion.split("-");
        if (dateTime.length == 2 && isThisDateValid(dateTime[0]) && isThisTimeValid(dateTime[1])) {
            return LocalDateTime.of(getYear(), getMonth(), getDay(), getHour(), getMinute()).isAfter(LocalDateTime.now());
        } else {
            return false;
        }
    }

    /**
     * Takes in a string representation of a valid date/time and returns
     * a corresponding <LocalDateTime></LocalDateTime> representation.
     *
     * @param dateTimeToConvert the date/time string being converted
     * @return the <LocalDateTime></LocalDateTime> version of the given string
     */
    public LocalDateTime getLocalDateTime(String dateTimeToConvert) {
        dateTime = dateTimeToConvert.split("-");
        return LocalDateTime.of(getYear(), getMonth(), getDay(), getHour(), getMinute());
    }

    /**
     * Takes in a <LocalDateTime></LocalDateTime> and returns its string representation
     * with both date and time.
     *
     * @param dateTimeToConvert the <LocalDateTime></LocalDateTime> being converted
     * @return the string representation of the given <LocalDateTime></LocalDateTime> with both date and time
     */
    public String getDateTimeString(LocalDateTime dateTimeToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return dateTimeToConvert.format(formatter);
    }

    /**
     * Takes in a <LocalDateTime></LocalDateTime> and returns the date portion's string representation.
     *
     * @param dateTimeToConvert the <LocalDateTime></LocalDateTime> being converted
     * @return the string representation of the given <LocalDateTime></LocalDateTime>'s date portion
     */
    public String getDateString(LocalDateTime dateTimeToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return dateTimeToConvert.format(formatter);
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

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {
            sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
