package process;
import java.util.*;

import org.joda.time.DateTime;


public class EventCalendar {
	private List<Event> myList;
	
	public EventCalendar(){
		myList = new ArrayList<Event>();
	}
	
	public EventCalendar(List <Event> list){
	    myList = list;
	}
	
	public void addEvent(Event event){
		myList.add(event);
	}
	
	public void addAll(List <Event> list){
		myList.addAll(list);
	}
	
	public List <Event> getList(){
		return myList;
	}
	
	public void removeAllContaining(String keyword){
		List <Event> searchresults = new ArrayList <Event>();
		for (Event e : myList){
			if(!e.toString().contains(keyword)){
				searchresults.add(e);
			}
		}
		myList = searchresults;
	}
	
	public void findAllContaining(String [] keywords){
		List <Event> searchresults = new ArrayList <Event>();
		for(Event e : myList){
			for (String word : keywords){
				if(e.toString().contains(word)){
					searchresults.add(e);
				}
			}
		}
		myList = searchresults;
	}
	
	public void searchCalendar(String keyword){
		List<Event> searchresults = new ArrayList<Event>();
		for (Event e : myList){
			if(e.toString().contains(keyword)){
				searchresults.add(e);
			}
		}
		myList = searchresults;
	}
	
	public void eventsAtTime(DateTime time){
		List<Event> searchresults = new ArrayList<Event>();
		for(Event e : myList){
			if(e.getInterval().contains(time)){
				searchresults.add(e);
			}
		}
		myList = searchresults;
	}
	
	public void eventsWithDescription(String input){
		List<Event> searchresults = new ArrayList<Event>();
		for(Event e : myList){
			if(e.getEventDescription().equals(input)){
				searchresults.add(e);
			}
		}
		myList = searchresults;
	}
	
	public void eventsWithName(String input){
		List<Event> searchresults = new ArrayList<Event>();
		for(Event e : myList){
			if(e.getName().equals(input)){
				searchresults.add(e);
			}
		}
		myList = searchresults;
	}
	
	public void eventsAtLocation(String input){
		List<Event> searchresults = new ArrayList<Event>();
		for(Event e : myList){
			if(e.getLocation().contains(input)){
				searchresults.add(e);
			}
		}
		myList = searchresults;
	}
	
	/**
     * Returns a list of conflicting events.
     */
    public List<Event> getConflictingEvents() {
        List<Event> conflicting = new ArrayList<Event>();
        TreeSet<Event> used = new TreeSet<Event>();
        
        //loop over all events, see if they conflict with each other
        for (Event e1 : myList) {
            for (Event e2 : myList) {
                if (e1.getInterval().overlap(e2.getInterval()) != null)  {
                    if (! used.contains(e1)) {
                        conflicting.add(e1);
                        used.add(e1);
                    }
                    if (! used.contains(e2)) {
                        conflicting.add(e2);
                        used.add(e2);
                    }   
                }
            }
        }
        
        return conflicting;
    }
	
	public void sortByStartTime(){
		Collections.sort(myList, new TimeComp());
	}
	
	public void sortByEndTime(){
		Collections.sort(myList, new EndTimeComp());
	}
	
	public void sortByName(){
		Collections.sort(myList, new NameComp());
	}
	
	public void reverse(){
		Collections.reverse(myList);
	}

}
