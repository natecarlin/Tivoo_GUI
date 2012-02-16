/**
 *  @author Antares Yee
 */

package HTMLoutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Text;

import Process.Event;

public class HTMLdetailPage extends HTMLpage {
    String myDetailDirPath;
    
    public HTMLdetailPage(ArrayList<Event> events, String path) {
        super(events, path);
        myDetailDirPath = System.getProperty("user.home") + super.myPath + "/DetailDir"; //path to folder DetailDir
    }

    
    /**
     * Creates directory DetailDir and sub-files that are detail pages for each event.
     */
    @Override
    public void createHTMLpage() {
        //make dir for detail pages at path/DetailDir
        boolean exists = new File(myDetailDirPath).mkdir();
        
        if (! exists) {
            System.out.println("Failed to make Directory. Check to make sure it doesn't already exist.");
            System.exit(0);
        }
        
        //call createDetailpage() for each Event in myEvents
        System.out.println(super.myEvents);
        for (Event e : super.myEvents) {
                try {
                    createDetailPage(e);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        }
    }
        
    
    /**
     * 
     * Creates HTML page for each event (name, start, end time, description) and put it into DetailDir
     * @throws IOException 
     */
    private void createDetailPage(Event e) throws IOException {
        //Create file for writing
        String fileName = myDetailDirPath + "/" + e.getName() + ".html";
        System.out.println(fileName);
        File out = new File(fileName);
        boolean exist = out.createNewFile();
       
        if (! exist) {
        System.out.println("File already exists.");
        System.exit(0);
        }
        
        //set up FileWriter and BufferedWriter
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);
        
        //write html
        bw.write(createDetailPageHtml(e).write());
        bw.close();
    }
    
    /**
     * Returns Html object for detail page for one Event e
     */
    private Html createDetailPageHtml(Event e) {
        Html html = new Html();
        Body body = new Body();
        
        //add title
        H2 title = new H2();
        title.appendChild(new Text(e.getName()));
        body.appendChild(title);
        
        //add startTime, endTime
        P p = new P();
        Text startTime = new Text("Starts: " + e.getStartTime());
        Text endTime = new Text("Ends: " + e.getEndTime());
        Text eventDescription = new Text("Description: " + e.getEventDescription());
        
        body.appendChild(startTime);
        body.appendChild(new Br()); //add </br>
        body.appendChild(endTime);
        body.appendChild(new Br()); //add </br>
        body.appendChild(eventDescription);
        
        html.appendChild(body);
        
        return html;
    }
   

}
