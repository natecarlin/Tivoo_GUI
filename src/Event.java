import org.joda.time.DateTime;
import org.joda.time.Interval;



public class Event {
    public String myName;
    public String myLocation;
    public String myDescription;

    public  Interval myInterval;
    
    public Event(String name, String location, String description, DateTime starttime, DateTime endtime) {
        myName = name;
        myLocation = location;
        myDescription = description;
        myInterval = new Interval(starttime,endtime);
    }
    
    public String toString(){
    	return "Event[" + myName + ", " + myLocation + ", " + myDescription + "]\n";
    }
    
}
