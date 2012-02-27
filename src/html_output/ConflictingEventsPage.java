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
        Html html = makeHtmlObject("Conflicting Events",events);
        return makeFile(html, "/TiVOOConflictingEventsPage.html");
    }

}