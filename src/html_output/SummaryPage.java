package html_output;

import html_output.HtmlUtility;

import java.util.List;
import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;

import Process.Event;


/**
 *@author Antares Yee
 */

public class SummaryPage extends HtmlPage {

    public SummaryPage(String path) {
        super(path);
    }

    @Override
    /**
     * Create summaryPage for 1 week of events.  
     * The file is saved at super.myPath.
     * 
     */
    public boolean createHTMLpage(List<Event> events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, "/TiVOOsummaryPage.html");
    }
    
    /**
     * Create Html object with 1 week summary of events.  
     * Html object contains list of days of week/day of month, 
     * and their Events.  Each Event is hyperlinked to detail page.
     * Includes Event start and end times.
     */
    public Html makeHtmlObject(List<Event> events) {
        Html html = new Html();
        Body body = new Body();
        List<Event> sortedEvents = sortEventsByTime(events); //make list of events sorted chronologically
        
        
        HtmlUtility.addTitleH2((sortedEvents.get(0).getStartTime().dayOfWeek().getAsText() + " " + sortedEvents.get(0).getStartTime().dayOfMonth().getAsText()), body); //add first date H2.
        
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
                
                HtmlUtility.addTitleH2(e.getStartTime().dayOfWeek().getAsText() + " " + e.getStartTime().dayOfMonth().getAsText(), body);
                addEventInfo(e, body);
            }
        }
            
        html.appendChild(body);
        return html;
    }
    
    /**
     * Add info of Event to body. Name of event is a hyperlink.
     */
    private boolean addEventInfo(Event e, Body body) {
        addEventLink(e, body);
        body.appendChild(new Br()); //add </br>
        
        HtmlUtility.addEventTime(e, body);
        body.appendChild(new Br());
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add an A object to body
     */
    private boolean addEventLink(Event e, Body body) {
        A eventNameLink = new A();
        eventNameLink.setHref(DetailPage.DETAIL_DIR_PATH + HtmlUtility.makeFileName(e));
        eventNameLink.appendChild(new Text(e.getName()));
        
        body.appendChild(eventNameLink);
        return true;
    }
}