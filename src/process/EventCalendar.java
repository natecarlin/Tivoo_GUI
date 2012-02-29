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
	
	public void removeAll(List <Event> list){
		myList.removeAll(list);
	}
	
	public List <Event> getList(){
		return myList;
	}
	
	public EventCalendar removeAllContaining(String category, String keyword){
		EventCalendar searchresults = new EventCalendar(myList);
		EventCalendar toberemoved = searchresults.searchCalendar(category, keyword);
		searchresults.removeAll(toberemoved.getList());
		return searchresults;
	}
	
	public Set<String> findSearchableTerms(){
		Set<String> returnset = new HashSet<String>();
		for(Event e : myList){
			returnset.addAll(e.getKeys());
		}
		return returnset;
	}
	
	public EventCalendar findAllContaining(String category, String [] keywords){
		EventCalendar searchresults = new EventCalendar();
		for(String word : keywords){
			searchresults.addAll(searchCalendar(category, word).getList());
			}
		return searchresults;
	}
	
	public EventCalendar searchCalendar(String category, String keyword){
		EventCalendar searchresults = new EventCalendar();
		for (Event e : myList){
			if(e.hasFeature(category)){
				for(String s : e.getFeature(category)){
					if(s.contains(keyword)){
						searchresults.addEvent(e);
					}
				}
			}
		}
		return searchresults;
	}
	
	public EventCalendar filterByName(String keyword){
		return this.searchCalendar("title", keyword);
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
