package HTMLoutput;

import java.util.List;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;
import Process.Event;
import HTMLoutput.HTMLUtility;

/**
 *  @author Antares Yee
 */

public class HTMLdetailPage extends HTMLpage {
    public static final String DETAIL_DIR_PATH = "/DetailDir";
    
    public HTMLdetailPage(List<Event> events, String path) {
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
            makeFile(html, DETAIL_DIR_PATH + HTMLUtility.makeFileName(e));
        }
        return true;
    }
    
    /**
     * Returns Html object for detail page for one Event e
     */
    private Html makeHtmlObject(Event e) {
        Html html = new Html();
        Body body = new Body();
        
        addTitle(e, body);   
        addStartEndTime(e, body);
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
    private boolean addStartEndTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add H2 tag with name of event to body.
     */
    private boolean addTitle(Event e, Body body) {
        H2 title = new H2();
        title.appendChild(new Text(e.getName()));
        
        body.appendChild(title);
        return true;
    }
}
