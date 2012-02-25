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
        int indexFirstEvent = getIndexFirstEvent(sortedEvents, startDate, numDays);
        
        if (indexFirstEvent == -1) { //if no events in timeframe
            System.out.println("No events in this timeframe.");
            return false;
        }
       
        HtmlUtility.addTitleH2((sortedEvents.get(indexFirstEvent).getStartTime().dayOfWeek().getAsText() + " " + sortedEvents.get(indexFirstEvent).getStartTime().dayOfMonth().getAsText()), body); //add first date H2.
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = sortedEvents.get(indexFirstEvent).getStartTime();
        DateTime endDate = currentDate.plusDays(numDays); //last date to include in calendar
        
        for (int i = indexFirstEvent; i<sortedEvents.size(); ++i) {
            if (sortedEvents.get(i).getStartTime().dayOfMonth().equals(currentDate.dayOfMonth()) && sortedEvents.get(i).getStartTime().monthOfYear().equals(currentDate.monthOfYear())) {
                HtmlUtility.addEventInfo(sortedEvents.get(i), body);
            }
            else {
                //if more than numDays days after startDate, finished.
                if (sortedEvents.get(i).getStartTime().compareTo(endDate) > 0) {
                    break;  
                }
                currentDate = sortedEvents.get(i).getStartTime(); //currentDate ++
                
                HtmlUtility.addTitleH2(sortedEvents.get(i).getStartTime().dayOfWeek().getAsText() + " " + sortedEvents.get(i).getStartTime().dayOfMonth().getAsText(), body);
                HtmlUtility.addEventInfo(sortedEvents.get(i), body);
            }
        }
        return true;
    }
    
    /**
     * Returns first event that occurs after specified startDate but before endDate. If none, returns null.
     * 
     */
    public int getIndexFirstEvent(List<Event> sortedEvents, DateTime startDate, int numDays) {
        int firstEvent = -1;
        
        for (int i=0; i<sortedEvents.size(); ++i) {
            if (sortedEvents.get(i).getStartTime().compareTo(startDate) > 0 && sortedEvents.get(i).getStartTime().compareTo(startDate.plusDays(numDays)) < 0) { //if event starts after startDate but before endDate
                firstEvent = i;
                break;
            }
        }
        return firstEvent;
    }

}
