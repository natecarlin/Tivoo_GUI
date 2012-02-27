package Process;

import java.util.ArrayList;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class Event implements Comparable<Event> {
	
	private Map<String, ArrayList<String>> myMap;

	public DateTime myStartTime;
	public DateTime myEndTime;
	public Interval myInterval;

	public void addFeature(String key, ArrayList<String> value){		
		myMap.put(key, value);
	}
	public ArrayList<String> getFeature(String key){
		return myMap.get(key);
	}

	public String toString() {
		
		StringBuilder myStrBuilder=new StringBuilder();
		myStrBuilder.append("Event[ ");
		for(String str: myMap.keySet()){
			
			ArrayList<String> list=myMap.get(str);
			for(String s: list){
				myStrBuilder.append(s+", ");
			}	
		}
		myStrBuilder.append(" ]\n");
		return myStrBuilder.toString();
	}

	public DateTime getStartTime() {
		return myStartTime;
	}

	public DateTime getEndTime() {
		return myEndTime;
	}

	public Interval getInterval() {
		return myInterval;
	}

	public String getStartYear() {
		return Integer.toString(myStartTime.getYear());
	}

	public String getStartMonth() {
		return Integer.toString(myStartTime.getMonthOfYear());
	}

	public String getStartDayOfMonth() {
		return Integer.toString(myStartTime.getDayOfMonth());
	}

	public String getStartDayOfWeek() {
		return Integer.toString(myStartTime.getDayOfWeek());
	}

	public String getStartHourOfDay() {
		return Integer.toString(myStartTime.getHourOfDay());
	}

	public String getStartMinuteOfHour() {
		return Integer.toString(myStartTime.getMinuteOfHour());
	}

	public String getEndYear() {
		return Integer.toString(myEndTime.getYear());
	}

	public String getEndMonth() {
		return Integer.toString(myEndTime.getMonthOfYear());
	}

	public String getEndDayOfWeek() {
		return Integer.toString(myEndTime.getDayOfWeek());
	}

	public String getEndDayOfMonth() {
		return Integer.toString(myEndTime.getDayOfMonth());
	}

	public String getEndHourOfDay() {
		return Integer.toString(myEndTime.getHourOfDay());
	}

	public String getEndMinuteOfHour() {
		return Integer.toString(myEndTime.getMinuteOfHour());
	}

    @Override
    public int compareTo(Event other) {
        if (myMap.get("myName").equals(other.myMap.get("myName"))&& myInterval.equals(other.myInterval) && myMap.get("myLocation").equals(other.myMap.get("myLocation"))) {
            return 0;
        }
        
        //compare by time
        if (getStartTime().compareTo(other.getStartTime()) < 0)
            return -1;
        else if (getStartTime().compareTo(other.getStartTime()) > 0)
            return 1;
        else {
            if (getEndTime().compareTo(other.getEndTime()) < 0)
                return -1;
            else if (getEndTime().compareTo(other.getEndTime()) > 0)
                return 1;
        }
        return 0;
    }


}
