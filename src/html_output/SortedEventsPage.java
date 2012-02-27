package html_output;

import java.util.List;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;

import Process.Event;
import Process.EventCalendar;

public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(String path) {
        super(path);
    }
    
    @Override
    public boolean createHTMLpage(EventCalendar events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, "/TiVOOSortedEventsPage.html");
    }

    private Html makeHtmlObject(EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
   
        addTitleH2("Sorted Events", body);     
        for (Event e : events.getList()) {
            addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }

}
