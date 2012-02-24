package html_output;

import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

import Process.Event;

public class SortedEventsPage extends HtmlPage {

    public SortedEventsPage(List<Event> events, String path) {
        super(events, path);
    }
    
    @Override
    public boolean createHTMLpage() {
        Html html = makeHtmlObject();
        return makeFile(html, "/TiVOOSortedEventsPage.html");
    }

    private Html makeHtmlObject() {
        Html html = new Html();
        Body body = new Body();
   
        HtmlUtility.addTitleH2("Sorted Events", body);     
        for (Event e : getMyEvents()) {
            HtmlUtility.addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }

}
