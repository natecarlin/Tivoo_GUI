package html_output;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

import Process.Event;
import Process.EventCalendar;

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
    public boolean createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, "/TiVOOConflictingEventsPage.html");
    }
    
    public Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
   
        addTitleH2("Conflicting Events", body);     
        for (Event e : events.getConflictingEvents()) {
            addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
        
    }

}