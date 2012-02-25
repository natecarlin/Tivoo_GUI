package html_output;

import java.util.List;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;

import Process.Event;

public abstract class CalendarPage extends HtmlPage {

    public CalendarPage(List<Event> events, String path) {
        super(events, path);
    }
    
    @Override
    public abstract boolean createHTMLpage();
    
    public boolean addCalendarEvents(DateTime startDate, int numDays, Body body) {
        List<Event> sortedEvents = sortEventsByTime(); //make list of events sorted chronologically
        Event firstEvent = getFirstEvent(sortedEvents, startDate, numDays);
        
        if (firstEvent == null) { //if no events in timeframe
            System.out.println("Could not make Calendar output. no events in this timeframe");
            return false;
        }
       
        HtmlUtility.addTitleH2((firstEvent.getStartTime().dayOfWeek().getAsText() + " " + firstEvent.getStartTime().dayOfMonth().getAsText()), body); //add first date H2.
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = firstEvent.getStartTime();
        DateTime endDate = currentDate.plusDays(numDays); //last date to include in calendar
        
        System.out.println(startDate);
        System.out.println(endDate);
        
        for (Event e : sortedEvents) { 
            if (e.getStartTime().dayOfMonth().equals(currentDate.dayOfMonth()) && e.getStartTime().monthOfYear().equals(currentDate.monthOfYear())) {
                HtmlUtility.addEventInfo(e, body);
            }
            else {
                //if more than numDays days after startDate, finished.
                if (e.getStartTime().compareTo(endDate) > 0) {
                    break;  
                }
                currentDate = e.getStartTime(); //currentDate ++
                
                HtmlUtility.addTitleH2(e.getStartTime().dayOfWeek().getAsText() + " " + e.getStartTime().dayOfMonth().getAsText(), body);
                HtmlUtility.addEventInfo(e, body);
            }
        }
        return true;
    }
    
    /**
     * Returns first event that occurs after specified startDate but before endDate. If none, returns null.
     * IMPORTANT: removes all elements in list prior to firstEvent.
     */
    public Event getFirstEvent(List<Event> sortedEvents, DateTime startDate, int numDays) {
        Event firstEvent = null;
        int toRemove = 0;
        for (int i=0; i<sortedEvents.size(); ++i) {
            if (sortedEvents.get(i).getStartTime().compareTo(startDate) > 0 && sortedEvents.get(i).getStartTime().compareTo(startDate.plusDays(numDays)) < 0) { //if event starts after startDate but before endDate
                firstEvent = sortedEvents.get(i);
                toRemove = i;
                break;
            }
        }
        //remove elements up to firstEvent from sortedEvents
        for (int i=0; i< toRemove; ++i) {
            sortedEvents.remove(i);
        }
        return firstEvent;
    }

}
