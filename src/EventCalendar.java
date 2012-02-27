import java.util.*;

import org.joda.time.DateTime;

import Process.EndTimeComp;
import Process.Event;
import Process.NameComp;
import Process.TimeComp;

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
