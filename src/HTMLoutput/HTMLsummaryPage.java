package HTMLoutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Div;
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
    public void createHTMLpage() {
        // TODO: create 1 week calendar using table.  
        
        //Create file for writing
        String fileName = System.getProperty("user.home") + "/Desktop/TiVOOsummaryPage.html";
        File out = new File(fileName);
        boolean exist = false;
        
        try {
            exist = out.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        if (!exist) {
        System.out.println("File already exists.");
        System.exit(0);
        }
        
        //set up FileWriter and BufferedWriter
        FileWriter fw = null;
        try {
            fw = new FileWriter(out);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
       
        Html html = makeHTML();
        
        //write to bw
        try {
            bw.write(html.write());
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Html makeHTML() {
        //get first event, getDayOfWeek, getDate; use this info to fill in date labels in table
        if (super.myEvents == null) System.out.println("myEvents is null!");
        DateTime firstEventStart = super.myEvents.get(0).getStartTime();
        DateTime currentDT = firstEventStart;
        //create HTML page
        Html html = new Html();
        
        //H2 month title
        H2 month = new H2();
        if (currentDT != null) {//THIS IS TEMPORARY: firstEventStart SHOULD NOT BE NULL!!
            month.appendChild(new Text(currentDT.monthOfYear().getAsText()));
        }
        html.appendChild(month);
        
        //add table (calendar)
        Table calendarTable = new Table();
        calendarTable.setBorder("1");
        Tr tr = new Tr();
        
        //Enter first day into table
        Td td = new Td();
        if (currentDT != null) {//THIS IS TEMPORARY: firstEventStart SHOULD NOT BE NULL!!
            td.appendChild(new Text(currentDT.dayOfWeek().getAsText() + "</br>" + Integer.toString(currentDT.getDayOfMonth())));
        }
        tr.appendChild(td);
         
        //add remaining 6 days to table
        for (int i=0; i<6; i++) {
            
            //currentDate ++
            if (currentDT != null) {//THIS IS TEMPORARY: firstEventStart SHOULD NOT BE NULL!!
                currentDT = currentDT.plusDays(1);
            }
            
            //add dayOfWeek and dayOfMonth to table
            td = new Td();
            
            Text dayAndDate = null;
            if (currentDT != null) { //THIS IS TEMPORARY: firstEventStart SHOULD NOT BE NULL!!
                dayAndDate = new Text(currentDT.dayOfWeek().getAsText() + "</br>" + Integer.toString(currentDT.getDayOfMonth()));
                td.appendChild(dayAndDate);
            }
            
           
            
            //add events to each day of week
            Div div = new Div(); //div contains events of the day
            for (Event e : super.myEvents) {
                if (currentDT != null) {//THIS IS TEMPORARY: firstEventStart SHOULD NOT BE NULL!!
                    if (e.getStartTime().getDayOfMonth() == currentDT.getDayOfMonth()) {
                        Text eDescription = new Text(e.getName() + "</br>" + "Time: " + e.getStartTime() + "</br>" + "</br>");
                        div.appendChild(eDescription);
                    }
                }
            }
            td.appendChild(div);
            tr.appendChild(td);
        }
        
        if (currentDT != null) {//THIS IS TEMPORARY: firstEventStart SHOULD NOT BE NULL!!
            currentDT.minusDays(6);
        }
        calendarTable.appendChild(tr);
         
        html.appendChild(calendarTable);
        
        return html;
    }

}
