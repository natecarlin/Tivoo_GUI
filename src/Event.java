import org.joda.time.DateTime;


public class Event {
    public String myName;
    public String myLocation;
    public String myDescription;
    public DateTime myStartTime;
    public DateTime myEndTime;
    
    public Event(String name, String location, String description, DateTime startTime, DateTime endTime) {
        myName = name;
        myLocation = location;
        myDescription = description;
        myStartTime = startTime;
        myEndTime = endTime;
    }
    
    public String toString(){
    	return "Event[" + myName + ", " + myLocation + ", " + myDescription + "]\n";
    }
    
}
