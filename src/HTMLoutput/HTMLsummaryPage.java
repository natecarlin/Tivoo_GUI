package HTMLoutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.H2;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Table;
import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Text;
import com.hp.gagawa.java.elements.Tr;

import Process.Event;

public class HTMLsummaryPage extends HTMLpage {

    public HTMLsummaryPage(ArrayList<Event> events, String path) {
        super(events, path);
    }

    @Override
    public void createHTMLpage(ArrayList<Event> processedEvents, String path) {
        // TODO: create 1 week calendar using table.  
        
        //Create file for writing
        String fileName = System.getProperty("user.home") + "/Desktop/TiVOOsummaryPage.html";
        File out = new File(fileName);
        boolean exist = out.createNewFile();
        
        if (!exist) {
        System.out.println("File already exists.");
        System.exit(0);
        }
        
        //set up FileWriter and BufferedWriter
        FileWriter fw = new FileWriter(out);
        BufferedWriter bw = new BufferedWriter(fw);
       
        bw.write(html.write());
        bw.close();
        
    }
    
    public Html makeHTML() {
        //get first event, getDayOfWeek, getDate; use this info to fill in date labels in table
        Event firstEvent = super.myEvents.get(0);
        String startDayOfWeek = firstEvent.getStartDayOfWeek();
        String startDayOfMonth = firstEvent.getStartDayOfMonth();
        
        //create HTML page
        Html html = new Html();
        
        //H2 month title
        H2 month = new H2();
        month.appendChild(new Text(firstEvent.getStartMonth()));
        html.appendChild(month);
        
        //add table (calendar)
        Table t = new Table();
        t.setBorder("1");
        Tr tr = new Tr();
        
        //Enter first day into table
        Td td = new Td();
        td.appendChild(new Text(startDayOfWeek + "\n" + startDayOfMonth));
        tr.appendChild(td);
        //TODO: read over each event and make table from this.  consider making from interval.
        
        //add remaining 6 days to table
        for (int i=0; i<6; i++) {
            td = new Td();
            //TODO: update startDay and startDate from joda/otherwise
            Text dayAndDate = new Text(startDay + "/n" + startDate);
            td.appendChild(dayAndDate);
            tr.appendChild(td);
            
            
            startDay++;
            startDate++;
        }
        t.appendChild(tr);
        
        // TODO: append Title(link to detail), start, end time to table
        
        
        html.appendChild(t);
        
        return t;
        
    }

}
