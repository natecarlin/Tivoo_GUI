package html_output;

import html_output.HtmlUtility;

import java.util.List;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;
import Process.Event;

/**
 *  @author Antares Yee
 */

public class DetailPage extends HtmlPage {
    public static final String DETAIL_DIR_PATH = "DetailDir";
    
    public DetailPage(List<Event> events, String path) {
        super(events, path);
        makeDirFromPath(DETAIL_DIR_PATH);
    }
    
    /**
     * Creates a .html detail page for each event in myDetailDirPath.
     */
    @Override
    public boolean createHTMLpage() {
        for (Event e : getMyEvents()) {
            Html html = makeHtmlObject(e);
            makeFile(html, DETAIL_DIR_PATH + HtmlUtility.makeFileName(e));
        }
        return true;
    }
    
    /**
     * Returns Html object for detail page for one Event e
     */
    private Html makeHtmlObject(Event e) {
        Html html = new Html();
        Body body = new Body();
        
        HtmlUtility.addTitleH2(e.getName(), body);
        addEventTime(e, body);
        addEventDescription(e, body);
 
        html.appendChild(body);
        return html;
    }
    
    /**
     * Add event description of an event to body
     */
    private boolean addEventDescription(Event e, Body body) {
        Text eventDescription = new Text(e.getEventDescription());
        body.appendChild(new Text("Details:"));
        body.appendChild(new Br());
        body.appendChild(eventDescription);
        body.appendChild(new Br());
        return true;
    }
    /**
     * Add start and end time of an event to body.
     */
    private boolean addEventTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        body.appendChild(new Br());
        return true;
    }
    
}
