package html_output;

import html_output.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;

import Process.Event;
import Process.TimeComp;


/**
 *@author Antares Yee
 */

public class SummaryPage extends HtmlPage {

    public SummaryPage(List<Event> events, String path) {
        super(events, path);
    }

    @Override
    /**
     * Create summaryPage for 1 week of events.  
     * The file is saved at super.myPath.
     * 
     */
    public boolean createHTMLpage() {
        Html html = makeHtmlObject();
        return makeFile(html, "/TiVOOsummaryPage.html");
    }
    
    /**
     * Create Html object with 1 week summary of events.  
     * Html object contains list of days of week/day of month, 
     * and their Events.  Each Event is hyperlinked to detail page.
     * Includes Event start and end times.
     */
    public Html makeHtmlObject() {
        Html html = new Html();
        Body body = new Body();
        List<Event> sortedEvents = sortEventsByTime(); //make list of events sorted chronologically
        
        
        addDateH2(sortedEvents.get(0), body); //add first date H2.
        
        //loop over all events (sorted by time), add event info and date H2's.
        DateTime currentDate = sortedEvents.get(0).getStartTime();
        DateTime lastCalendarDate = currentDate.plusDays(7); //last date to include in calendar
        for (Event e : sortedEvents) { 
            if (e.getStartTime().dayOfMonth().equals(currentDate.dayOfMonth())) {
                addEventInfo(e, body);
            }
            else {
                //if more than 7 days after first date on calendar, finished.
                if (e.getStartTime().compareTo(lastCalendarDate) > 0) {
                    break;  
                }
                currentDate = e.getStartTime(); //currentDate ++
                addDateH2(e, body);
                addEventInfo(e, body);
            }
        }
            
        html.appendChild(body);
        return html;
    }
    
    /**
     * Add H2 with current day of week and day of month to body.
     */
    private boolean addDateH2(Event e, Body body) {
        H2 dateH2 = new H2();
        Text dayOfWeekText = new Text(e.getStartTime().dayOfWeek().getAsText() + " ");
        Text dayOfMonthText = new Text(e.getStartTime().dayOfMonth().getAsText());
        
        dateH2.appendChild(dayOfWeekText);
        dateH2.appendChild(dayOfMonthText);
        body.appendChild(dateH2);
        return true;
    }

    /**
     * Add info of Event to body.
     */
    private boolean addEventInfo(Event e, Body body) {
        addEventLink(e, body);
        body.appendChild(new Br()); //add </br>
        
        addEventTime(e, body);
        body.appendChild(new Br());
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add event start and end time to body
     */
    private boolean addEventTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        return true;
    }

    /**
     * Add an A object to body
     */
    private boolean addEventLink(Event e, Body body) {
        A eventNameLink = new A();
        eventNameLink.setHref(DetailPage.DETAIL_DIR_PATH + Utility.makeFileName(e));
        eventNameLink.appendChild(new Text(e.getName()));
        
        body.appendChild(eventNameLink);
        return true;
    }
        
    
    /**
     * Sort events chronologically with TimeComp
     */
    public List<Event> sortEventsByTime() {
        List<Event> sortedEvents = new ArrayList<Event>(getMyEvents());
        Collections.sort(sortedEvents, new TimeComp());
        return sortedEvents;
    }
}