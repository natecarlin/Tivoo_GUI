package html_output;

import java.util.List;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
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
            addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }
    
    /**
     * Add info of Event to body.
     */
    private boolean addEventInfo(Event e, Body body) {
        HtmlUtility.addEventH2(e, body);
        body.appendChild(new Br()); //add </br>
        
        HtmlUtility.addEventTime(e, body);
        body.appendChild(new Br());
        body.appendChild(new Br());
        return true;
    }

}
