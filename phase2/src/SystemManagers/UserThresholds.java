package SystemManagers;

import java.io.Serializable;

/**
 * Stores and manages the system threshold values used to restrict the actions of all normal users of the program.
 *
 * @author Yingjia Liu
 * @version 1.0
 * @since 2020-08-06
 * last modified 2020-08-07
 */
public class UserThresholds implements Serializable {

    private int numOfThresholds;

    /* the maximum number of transactions a user can schedule in a week */
    private int weeklyTradeMax;

    /* the maximum number of times a user may edit any one of their trade's meeting details */
    private int meetingEditMax;

    /* to initiate a non-lending transaction,
     a user must have lent at least lendMinimum item(s) more than they have borrowed */
    private int lendMinimum;

    /* the maximum number of incomplete trades a user can have before their account is at risk of being frozen */
    private int incompleteTradeMax;

    /**
     * Creates a <UserThresholds></UserThresholds> and sets the thresholds to default values.
     */
    public UserThresholds() {
        weeklyTradeMax = 3;
        meetingEditMax = 3;
        lendMinimum = 1;
        incompleteTradeMax = 5;
    }

    /**
     * Sets the current default threshold values to the given values.
     *
     * @param thresholds the new threshold values
     */
    public void setAllThresholds(int[] thresholds) {
        numOfThresholds = thresholds.length;
        weeklyTradeMax = thresholds[0];
        meetingEditMax = thresholds[1];
        lendMinimum = thresholds[2];
        incompleteTradeMax = thresholds[3];
    }

    /**
     * Getter for the current default threshold values.
     *
     * @return the system's current default threshold values
     */
    public int[] getAllThresholds() {
        return new int[]{weeklyTradeMax, meetingEditMax, lendMinimum, incompleteTradeMax};
    }

    /**
     * Getter for the current maximum number of transactions a user can schedule in a week.
     *
     * @return the current weekly trade max
     */
    public int getWeeklyTradeMax() {
        return weeklyTradeMax;
    }

    /**
     * Getter for the current maximum number of times a user may edit any one of their trade's meeting details.
     *
     * @return the current meeting edit max
     */
    public int getMeetingEditMax() {
        return meetingEditMax;
    }

    /**
     * Getter for the current lend minimum; a user must have lent at least lendMinimum item(s)
     * more than they have borrowed in order to initiate a non-lending trade.
     *
     * @return the current lend minimum
     */
    public int getLendMinimum() {
        return lendMinimum;
    }

    /**
     * Getter for the current maximum number of incomplete trades a user
     * can have before their account is at risk of being frozen.
     *
     * @return the current incomplete trade max
     */
    public int getIncompleteTradeMax() {
        return incompleteTradeMax;
    }

    /**
     * Returns the number of threshold values in the program.
     *
     * @return the number of threshold values in the program
     */
    public int getNumOfThresholds() {
        return numOfThresholds;
    }

    /**
     * Takes in a number and returns the corresponding threshold type.
     *
     * @param type a number corresponding to a threshold type
     * @return a string representing a threshold type
     */
    public String getThresholdType(int type) {
        switch (type) {
            case 0:
                return ("weekly trade max");
            case 1:
                return ("meeting edit max");
            case 2:
                return("lend minimum");
            case 3:
                return ("incomplete trade max");
            default:
                return "";
        }
    }
}
