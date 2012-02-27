package html_output;

import java.util.List;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;

import Process.Event;

public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(String path) {
        super(path);
    }
    
    @Override
    public boolean createHTMLpage(List<Event> events) {
        Html html = makeHtmlObject(events);
        return makeFile(html, "/TiVOOSortedEventsPage.html");
    }

    private Html makeHtmlObject(List<Event> events) {
        Html html = new Html();
        Body body = new Body();
   
        HtmlUtility.addTitleH2("Sorted Events", body);     
        for (Event e : events) {
            HtmlUtility.addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }

}
