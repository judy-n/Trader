package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a temporary trade.
 *
 * @author Ning Zhang
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-06-26
 * last modified 2020-07-12
 */

public class TemporaryTrade extends Trade implements Serializable, Comparable<Trade> {
    private LocalDateTime meetingDateTime2;
    private String meetingLocation2;
    private boolean[] transactionConfirmed2 = new boolean[2];
    private final int TIME_LIMIT = 30;

    /**
     * Creates a <TemporaryTrade></TemporaryTrade> with given username array, item ID array,
     * and first suggestions for meeting time/date/location.
     *
     * @param usernames     an array containing the usernames of the two Users involved in this <TemporaryTrade></TemporaryTrade>
     * @param itemIDs       an array containing the IDs of the items being traded (parallel to usernames)
     * @param firstDateTime the first date and time suggested for this <TemporaryTrade></TemporaryTrade>'s meeting
     * @param firstLocation the first location suggested for this <TemporaryTrade></TemporaryTrade>'s meeting
     */
    public TemporaryTrade(String[] usernames, long[] itemIDs, LocalDateTime firstDateTime, String firstLocation) {
        super(usernames, itemIDs, firstDateTime, firstLocation);
        meetingLocation2 = "";
    }

    /**
     * Getter for this temporary trade's second meeting date and time.
     *
     * @return this temporary trade's second meeting date and time
     */
    public LocalDateTime getMeetingDateTime2() {
        return meetingDateTime2;
    }

    /**
     * Getter for the meeting location of this temporary trade's second transaction.
     *
     * @return this temporary trade's second meeting location
     */
    public String getMeetingLocation2() {
        return meetingLocation2;
    }

    /**
     * Getter for this temporary trade's second meeting date and time.
     * Should only be called on temporary trades with a second meeting in place.
     *
     * @return the second meeting time
     */
    @Override
    public LocalDateTime getFinalMeetingDateTime() {
        return meetingDateTime2;
    }

    /**
     * Confirms this temporary trade's first transaction for the given user.
     * Automatically schedules the second meeting if both traders have confirmed.
     *
     * @param username the username of the user confirming the first transaction
     */
    @Override
    public void confirmTransaction1(String username) {

        super.confirmTransaction1(username);

        if (getUserTransactionConfirmation1(username) &&
                getUserTransactionConfirmation1(getOtherUsername(username))) {

            /*
             * Second meeting automatically set to 30 days after the first
             * at the same location.
             */
            meetingDateTime2 = getMeetingDateTime1().plusDays(TIME_LIMIT);
            meetingLocation2 = getMeetingLocation1();
        }
    }

    /**
     * Confirms this temporary trade's second transaction for the given user.
     * Automatically closes the transaction if both traders have confirmed.
     *
     * @param username the username of the user confirming the second transaction
     */
    public void confirmTransaction2(String username) {
        if (username.equals(getInvolvedUsernames()[0])) {
            transactionConfirmed2[0] = true;
        } else {
            transactionConfirmed2[1] = true;
        }

        if (transactionConfirmed2[0] && transactionConfirmed2[1]) {
            closeTransaction();
        }
    }

    /**
     * Return whether or not the user with the given username has confirmed this
     * temporary trade's second transaction.
     *
     * @param username the username of the user to query
     * @return true if the user has confirmed, false otherwise
     */
    public boolean getUserTransactionConfirmation2(String username) {
        if (username.equals(getInvolvedUsernames()[0])) {
            return transactionConfirmed2[0];
        } else {
            return transactionConfirmed2[1];
        }
    }

    /**
     * Return whether or not this temporary trade's second meeting has been scheduled.
     * This is synonymous to whether or not both traders have confirmed the first transaction.
     *
     * @return true if the second meeting time has been scheduled, false otherwise
     */
    public boolean hasSecondMeeting() {
        return meetingDateTime2 != null;
    }

    /**
     * Return String representation of this temporary trade.
     *
     * @param currentUsername the username of the user currently logged in
     * @return the trade type and the given user's trade partner in a string
     */
    @Override
    public String toString(String currentUsername) {
        return "Temporary trade with < " + getOtherUsername(currentUsername) + " > - ";
    }

    /**
     * Allows this temporary trade to be compared to both types of trade base on the date and time of their meetings.
     *
     * @param trade the trade being compared with this temporary trade
     * @return a negative value if this temporary trade comes before the given trade,
     * 0 if exactly the same, and a positive value if it comes after the given trade
     */
    @Override
    public int compareTo(Trade trade) {
        if (trade instanceof TemporaryTrade) {
            if (((TemporaryTrade) trade).hasSecondMeeting() && this.hasSecondMeeting()) {
                return meetingDateTime2.compareTo(trade.getFinalMeetingDateTime());
            } else if (((TemporaryTrade) trade).hasSecondMeeting()) {
                return getMeetingDateTime1().compareTo(trade.getFinalMeetingDateTime());
            } else if (this.hasSecondMeeting()) {
                return meetingDateTime2.compareTo(trade.getMeetingDateTime1());
            } else {
                return getMeetingDateTime1().compareTo(trade.getMeetingDateTime1());
            }
        } else {
            if (this.hasSecondMeeting()) {
                return meetingDateTime2.compareTo(trade.getMeetingDateTime1());
            } else {
                return getMeetingDateTime1().compareTo(trade.getMeetingDateTime1());
            }
        }
    }
}
