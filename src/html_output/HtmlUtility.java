package html_output;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Text;

import Process.Event;

/**
 *  @author Antares Yee
 */

public class HtmlUtility {
    
    /**
     * Returns fileName for an Event.
     * Removes unusable characters from fileName.
     */
    public static String makeFileName(Event e) {
      //TODO: QUOTING URLS/ESCAPE URLS for fileNames
        // illegal:  / ? < > \ : * | Ó : ^ .
        //return "/" + e.getName() + e.getStartTime().toString() + ".html";
        String name = e.getName() + e.getStartTime().toString();
        return "/" + Integer.toString(name.hashCode()) + ".html";
    }
    
    public static boolean addEventH2(Event e, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(e.getName()));
        body.appendChild(h2);
        return true;
    }
    
    /**
     * Add info of Event to body.
     */
    public static boolean addEventInfo(Event e, Body body) {
        addEventH2(e, body);
        body.appendChild(new Br()); //add </br>
        
        addEventTime(e, body);
        body.appendChild(new Br());
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add event start and end time to body
     */
    public static boolean addEventTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        return true;
    }
    
    /**
     * Add "Conflicting Events" H2 to body
     */
    public static boolean addTitleH2(String name, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(name));
        body.appendChild(h2);
        body.appendChild(new Br());
        return true;
    }
    
}
