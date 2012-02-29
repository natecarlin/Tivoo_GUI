package html_output;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTime.Property;

import process.Event;
import process.EventCalendar;

import com.hp.gagawa.java.elements.Body;


public abstract class CalendarPage extends HtmlPage {

    public CalendarPage(String path) {
        super(path);
    }
    
    public boolean addCalendarEvents(EventCalendar events, DateTime startDate, int numDays, Body body) {
        events.sortByStartTime();
        EventCalendar sortedEvents = events.eventsBetweenTimes(startDate, startDate.plusDays(numDays));
        //int indexFirstEvent = getIndexFirstEvent(events.getList(), startDate, numDays);
        
        if (sortedEvents.getList().size() == 0) { //if no events in timeframe
            System.out.println("No events in this timeframe.");
            return false;
        }
        
        //add first date H2.
        String dayOfWeekFirstEvent = sortedEvents.getList().get(0).getStartTime().dayOfWeek().getAsText();
        String dayOfMonthFirstEvent = sortedEvents.getList().get(0).getStartTime().dayOfMonth().getAsText();
        addTitleH2((dayOfWeekFirstEvent + " " + dayOfMonthFirstEvent), body); 
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = sortedEvents.getList().get(0).getStartTime();
        DateTime endDate = currentDate.plusDays(numDays); //last date to include in calendar
        
        for (int i = 0; i<events.getList().size(); ++i) {
            Property eventDayOfMonth = sortedEvents.getList().get(i).getStartTime().dayOfMonth();
            Property eventMonthOfYear = sortedEvents.getList().get(i).getStartTime().monthOfYear();
            
            if (eventDayOfMonth.equals(currentDate.dayOfMonth()) && eventMonthOfYear.equals(currentDate.monthOfYear())) {
                addEventInfo(events.getList().get(i), body);
            }
            else {
                currentDate = sortedEvents.getList().get(i).getStartTime(); //currentDate ++
                
                String strEventDayOfWeek = sortedEvents.getList().get(i).getStartTime().dayOfWeek().getAsText();
                String strEventDayOfMonth = sortedEvents.getList().get(i).getStartTime().dayOfMonth().getAsText();
                addTitleH2(strEventDayOfWeek + " " + strEventDayOfMonth, body);
                addEventInfo(events.getList().get(i), body);
            }
        }
        return true;
    }

}
