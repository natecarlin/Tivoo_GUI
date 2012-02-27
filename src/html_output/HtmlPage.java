package html_output;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hp.gagawa.java.elements.Html;

import Process.Event;
import Process.TimeComp;

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
    public abstract boolean createHTMLpage(List<Event> events);
    
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
     * Sort events chronologically with TimeComp
     */
    public List<Event> sortEventsByTime(List<Event> events) {
        List<Event> sortedEvents = new ArrayList<Event>(events);
        Collections.sort(sortedEvents, new TimeComp());
        return sortedEvents;
    }
}

