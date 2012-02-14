import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Text;

/**
 * @author antaresyee
 * 
 * README: Pass makeOutput() an ArrayList<Event> to create a .html file.  The file will be located on 
 * your desktop (I think it works on all systems, but I only tested it on mac. Change the filename's 
 * path if its not working for you.)
 */
public class htmlOutput {
    
    public static File makeOutput(ArrayList<Event> processedEvents) throws IOException {
        //Create file for writing
        String fileName = System.getProperty("user.home") + "/Desktop/TiVOOoutput.html";
        File out = new File(fileName);
        boolean exist = out.createNewFile();
        
        if (!exist) {
        System.out.println("File already exists.");
        System.exit(0);
        }
        
        //set up FileWriter and BufferedWriter
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);
        
        //setup HTML tag
        Html html = new Html();
        Body body = new Body();
        
        //write each event to file.
        for (Event e : processedEvents) {
            body.appendChild(outputEvent(e));
        }
        html.appendChild(body);
        bw.write(html.write());
        bw.close();
        return out;
    }
    
    public static Text outputEvent(Event e) {
        //creates Text out of e
        Text eventText = new Text("Name of Event: " + e.myName + " Location: " + e.myLocation + " Description: " + e.myDescription + "<br>");
        return eventText;
       
        
    }
    
    public static void main(String[] args) throws IOException {
        ArrayList<Event> allEvents = new ArrayList<Event>();
        allEvents.add(new Event("Madonna", "Cat's Cradle", "Her Big Summer Show", new DateTime("021312"), new DateTime("021312")));
        allEvents.add(new Event("Weezer", "Griffith", "blah blah blah...", new DateTime("041412"), new DateTime("041412")));
        makeOutput(allEvents);
    }
}
