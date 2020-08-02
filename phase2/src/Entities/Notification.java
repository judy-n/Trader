package Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a notification that stores a message and the time at which the notification was created.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-08-01
 */
public class Notification {

    private final String MESSAGE;
    private final LocalDateTime DATE_TIME;

    public Notification(String message) {
        MESSAGE = message;
        DATE_TIME = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm");
        return DATE_TIME.format(formatter) + String.format("%5s", MESSAGE);
    }
}
