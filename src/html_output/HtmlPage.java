package html_output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;

import Process.Event;
import Process.EventCalendar;

/**
 *  @author Antares Yee
 */

public abstract class HtmlPage {
    private String myPath; //the path where you want the file created
    
    public HtmlPage(String path) {
        myPath = path;
    }
    
    /**
     * Creates an HTML page of specified subclass type.
     */
    public abstract boolean createHTMLpage(EventCalendar events);
    
    public Html makeHtmlObject(String title, EventCalendar events) {
        Html html = new Html();
        Body body = new Body();
   
        addTitleH2(title, body);     
        for (Event e : events.getList()) {
            addEventInfo(e,body);
        }
        html.appendChild(body);
        return html;
    }
    
    /**
     *Create file at designated path, write Html object to file.
     */
    public boolean makeFile(Html html, String fileName) {
        //Create file, FileWriter, BufferedWriter for writing
        String filePath = myPath + fileName;
        File out = new File(filePath);
        
        try {
            out.createNewFile();
            FileWriter fw = new FileWriter(out);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(html.write());
            bw.close();
        } 
        catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("error creating file at " + myPath + fileName);
            return false;
        }
        return true;
    }
    
    /**
     * Create a directory given a directory name
     */
    public boolean makeDirFromPath(String dirName) {
        String dirPath = myPath + dirName;
        return new File(dirPath).mkdir();
    }
    
    /**
     * Returns fileName for an Event.
     * Removes unusable characters from fileName.
     */
    public String makeFileName(Event e) {
        String name = e.getFeature("name").get(0) + e.getStartTime().toString();
        return "/" + Integer.toString(name.hashCode()) + ".html";
    }
    
    public boolean addEventH2(Event e, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(e.getFeature("name").get(0)));
        body.appendChild(h2);
        return true;
    }
    
    /**
     * Add event start and end time to body
     */
    public boolean addEventTime(Event e, Body body) {
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        
        body.appendChild(startTime);
        body.appendChild(new Br());
        body.appendChild(endTime);
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add A title H2 to body
     */
    public boolean addTitleH2(String name, Body body) {
        H2 h2 = new H2();
        h2.appendChild(new Text(name));
        body.appendChild(h2);
        return true;
    }
    
    /**
     * Add event description of an event to body
     */
    public boolean addEventDescription(Event e, Body body) {
        body.appendChild(new Text("Details: " + e.getFeature("description").get(0)));
        body.appendChild(new Br());
        return true;
    }
    
    /**
     * Add info of Event to body.
     */
     public boolean addEventInfo(Event e, Body body) {
        addEventH2(e, body);
        addEventTime(e, body);
        addEventDescription(e, body);
        body.appendChild(new Br());
        return true;
    }
}

