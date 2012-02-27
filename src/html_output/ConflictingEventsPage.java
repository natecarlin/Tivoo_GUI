package html_output;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

import Process.Event;

/**
 * @author Antares Yee
 *
 */
public class ConflictingEventsPage extends HtmlPage {

    public ConflictingEventsPage(String path) {
        super(path);
    }
    
    /**
     * Creates an html page that lists conflicting events and their times. 
     */
    @Override
    public boolean createHTMLpage(List<Event> events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, "/TiVOOConflictingEventsPage.html");
    }
    
    public Html makeHtmlObject(List<Event> events) {
        Html html = new Html();
        Body body = new Body();
   
        HtmlUtility.addTitleH2("Conflicting Events", body);     
        for (Event e : getConflictingEvents(events)) {
            HtmlUtility.addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
        
    }
    
    //move this over to EventCalendar
    /**
     * Returns a list of conflicting events.
     */
    private List<Event> getConflictingEvents(List<Event> events) {
        List<Event> conflicting = new ArrayList<Event>();
        TreeSet<Event> used = new TreeSet<Event>();
        
        //loop over all events, see if they conflict with each other
        for (Event e1 : events) {
            for (Event e2 : events) {
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

}