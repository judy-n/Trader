package Entities;

/**
 * Represents an action that can be reverted by an admin.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-01
 * last modified 2020-08-07
 */
public class RevertibleNotification extends Notification {
    String revertUsername;
    String subject;
    String type;
    // subject is either the ID of an item just approved by an admin (yes we are using parseLong to convert back)
    // or the username of a user who received a trade request from revertedUsername

    public RevertibleNotification(String message, String revertUsername, String subject, String type) {
        super(message);
        this.revertUsername = revertUsername;
        this.subject = subject;
        this.type = type;
    }

    public String getRevertedUsername() {
        return revertUsername;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }
}
