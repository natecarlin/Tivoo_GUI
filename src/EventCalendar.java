import java.util.*;

import org.joda.time.DateTime;

public class EventCalendar {
	private List<Event> myList;
	
	public EventCalendar(){
		myList = new ArrayList<Event>();
	}
	
	public void addEvent(Event event){
		myList.add(event);
	}
	
	public List<Event> searchCalendar(String keyword){
		List<Event> searchresults = new ArrayList<Event>();
		for (Event e : myList){
			if(e.myName.contains(keyword)){
				searchresults.add(e);
			}
		}
		return searchresults;
	}
	
	public List<Event> eventsAtTime(DateTime time){
		List<Event> searchresults = new ArrayList<Event>();
		for(Event e : myList){
			if(e.myInterval.contains(time)){
				searchresults.add(e);
			}
		}
		return searchresults;
	}

}
