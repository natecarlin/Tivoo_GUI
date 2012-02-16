package HTMLoutput;

import java.util.ArrayList;
import java.util.Arrays;

public class HTMLUtility {
    ArrayList<String> days = new ArrayList<String>(Arrays.asList("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "monday"));
    
    /**
     * Input a day, returns next day
     */
    public static String getNextDay(String s) {
        s = s.trim().toLowerCase();
        
        for (String day : days) {
            if (s.equals(day)) {
                return day;
            }
        }
        
        System.out.println("Did not recognize input day: getNextDay()");
        System.exit(0);
        
        return "";
    
    }
}