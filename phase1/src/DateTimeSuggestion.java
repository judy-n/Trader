import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeSuggestion {
    private final String dateFormat = "dd/MM/yyyy";
    private String [] dateTime;
    private String [] date;
    private String [] time;

    public boolean checkDateTime(String suggestion){
        if(!suggestion.contains("-")){
            return false;
        }
        dateTime = suggestion.split("-");
        return (isThisDateValid(dateTime[0])||isThisTimeValid(dateTime[1]));
    }

    public int getYear(){
        date = dateTime[0].split("/");
        return Integer.parseInt(date[2]);
    }
    public int getMonth(){
        return Integer.parseInt(date[1]);
    }

    public int getDay(){
        return Integer.parseInt(date[0]);
    }

    public int getHour(){
        time = dateTime[1].split("/");
        return Integer.parseInt(time[0]);
    }
    public int getMinute(){
        return Integer.parseInt(time[1]);
    }



    private boolean isThisTimeValid(String s) {
        String[] arr = s.split("/");
        int hr = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        if (hr < 0 || hr > 23) {
            return false;
        }
        return min >= 0 && min <= 59;
    }


    //Checks if the given date is valid.
    //based on code by mkyong from https://mkyong.com/java/how-to-check-if-date-is-valid-in-java/.
    private boolean isThisDateValid(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            //System.out.println(date);

        } catch (ParseException e) {
            //sp.exceptionMessage();
            //e.printStackTrace();
            return false;
        }
        return true;
    }
}
