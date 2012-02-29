package html_output;

import java.util.List;

import org.joda.time.DateTime;

import process.Event;
import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;


public abstract class CalendarPage extends HtmlPage {

    public CalendarPage(String path) {
        super(path);
    }
    
    @Override
    public abstract String createHTMLpage(EventCalendar events);
    
    public boolean addCalendarEvents(EventCalendar events, DateTime startDate, int numDays, Body body) {
        events.sortByStartTime();
        int indexFirstEvent = getIndexFirstEvent(events.getList(), startDate, numDays);
        
        if (indexFirstEvent == -1) { //if no events in timeframe
            System.out.println("No events in this timeframe.");
            return false;
        }
       
        addTitleH2((events.getList().get(indexFirstEvent).getStartTime().dayOfWeek().getAsText() + " " + events.getList().get(indexFirstEvent).getStartTime().dayOfMonth().getAsText()), body); //add first date H2.
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = events.getList().get(indexFirstEvent).getStartTime();
        DateTime endDate = currentDate.plusDays(numDays); //last date to include in calendar
        
        for (int i = indexFirstEvent; i<events.getList().size(); ++i) {
            if (events.getList().get(i).getStartTime().dayOfMonth().equals(currentDate.dayOfMonth()) && events.getList().get(i).getStartTime().monthOfYear().equals(currentDate.monthOfYear())) {
                addEventInfo(events.getList().get(i), body);
            }
            else {
                //if more than numDays days after startDate, finished.
                if (events.getList().get(i).getStartTime().compareTo(endDate) > 0) {
                    break;  
                }
                currentDate = events.getList().get(i).getStartTime(); //currentDate ++
                
                addTitleH2(events.getList().get(i).getStartTime().dayOfWeek().getAsText() + " " + events.getList().get(i).getStartTime().dayOfMonth().getAsText(), body);
                addEventInfo(events.getList().get(i), body);
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
