package Entities;

import java.time.LocalDateTime;

/***
 * Represents a notification that stores a message and the time at which the notification was created.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-07-30
 * last modified 2020-07-30
 */
public class Notification {

    private String message;
    private LocalDateTime dateTime;

    public Notification(String message) {
        this.message = message;
        dateTime = LocalDateTime.now();
    }
}
