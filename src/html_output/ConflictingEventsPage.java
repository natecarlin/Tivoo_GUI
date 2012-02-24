package html_output;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;

import Process.Event;

public class ConflictingEventsPage extends HtmlPage {

    public ConflictingEventsPage(List<Event> events, String path) {
        super(events, path);
    }
    
    /**
     * Creates an html page that lists conflicting events and their times. 
     */
    @Override
    public boolean createHTMLpage() {
        Html html = makeHtmlObject();
        return makeFile(html, "/TiVOOConflictingEventsPage.html");
    }
    
    public Html makeHtmlObject() {
        Html html = new Html();
        Body body = new Body();
   
        addTitleH2(body);     
        for (Event e : getConflictingEvents()) {
            addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
        
    }
    
    /**
     * Add "Conflicting Events" H2 to body
     */
    public boolean addTitleH2(Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text("Conflicting Events"));
        body.appendChild(h2);
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add info of Event to body.
     */
    private boolean addEventInfo(Event e, Body body) {
        addEventH2(e, body);
        body.appendChild(new Br()); //add </br>
        
        addEventTime(e, body);
        body.appendChild(new Br());
        body.appendChild(new Br());
        return true;
    }
    
    private boolean addEventH2(Event e, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(e.getName()));
        body.appendChild(h2);
        return true;
    }

    /**
     * Add event start and end time to body
     */
    protected boolean addEventTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        return true;
    }
    
    
    
    /**
     * Returns a list of conflicting events.
     */
    private List<Event> getConflictingEvents() {
        List<Event> conflicting = new ArrayList<Event>();
        TreeSet<Event> used = new TreeSet<Event>();
        
        //loop over all events, see if they conflict with each other
        for (Event e1 : getMyEvents()) {
            for (Event e2 : getMyEvents()) {
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