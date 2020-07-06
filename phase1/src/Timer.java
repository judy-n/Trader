/**
 * A timer used in by ConfirmTempTradeTransaction; entity class.
 *
 * @author Yiwei Chen
 * @version 1.0
 * @since 2020-07-06
 * last modified 2020-07-06
 */

public class Timer {

    private int time;


    /**
     * Class constructor.
     * Creates an Timer with a time.
     *
     * @param countDownTime the time that the Timer will count down from
     */
    public Timer(int countDownTime){
        this.time = countDownTime;
    }

    /**
     * Getter for Timer's time.
     *
     * @return this Timer's time
     */

    public int getTime() {
        return this.time;
    }

}
