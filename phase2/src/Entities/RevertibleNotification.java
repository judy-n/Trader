package Entities;

/**
 * Represents an action that can be reverted by an admin.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-01
 * last modified 2020-08-01
 */
public abstract class RevertibleNotification extends Notification {
    String revertedUsername;
    String subject;
    // subject is either the ID of an item just approved by an admin (yes we are using parseLong to convert back)
    // or the username of a user who received a trade request from revertedUsername

    public RevertibleNotification(String message, String revertedUsername, String subject) {
        super(message);
        this.revertedUsername = revertedUsername;
        this.subject = subject;
    }
}
