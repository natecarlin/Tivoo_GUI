
package HTMLoutput;

import java.util.ArrayList;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;

import Process.Event;

/**
 *@author Antares Yee
 */

public class HTMLdetailPage extends HTMLpage {
    private static final String myDetailDirPath = "/DetailDir";
    
    public HTMLdetailPage(ArrayList<Event> events, String path) {
        super(events, path);
        super.makeDirFromPath(myDetailDirPath);
    }

    public static String getDetailDirPath() {
        return myDetailDirPath;
    }
    
    /**
     * Returns fileName for an Event.
     * Removes unusable characters from fileName.
     */
    public static String makeFileName(Event e) {
      //TODO: QUOTING URLS/ESCAPE URLS for fileNames
        return myDetailDirPath + "/" + e.getName() + e.getStartTime().toString() + ".html";
    }
    
    
    
    /**
     * Creates a .html detail page for each event in myDetailDirPath.
     */
    @Override
    public boolean createHTMLpage() {
        for (Event e : super.getMyEvents()) {
            Html html = makeHtmlObject(e);
            super.makeFile(html, makeFileName(e));
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
