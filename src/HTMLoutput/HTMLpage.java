/**
 *  @author Antares Yee
 */

package HTMLoutput;

import java.util.List;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hp.gagawa.java.elements.Html;

import Process.Event;

/**
 *@author Antares Yee
 */

public abstract class HTMLpage {
    private List<Event> myEvents;
    private String myPath; //the path where you want the file created
    
    public HTMLpage(List<Event> events, String path) {
        myEvents = events;
        myPath = path;
    }
    
    /**
     * getter for field myEvents
     */
    protected List<Event> getMyEvents() {
        return myEvents;
    }
    
    /**
     * getter for field myPath
     */
    protected String getMyPath() {
        return myPath;
    }
    
    
    /**
     * Creates an HTML page of specified subclass type.
     */
    public abstract boolean createHTMLpage();
    
    /**
     *Create file at designated path, write Html object to file.
     */
    public boolean makeFile(Html html, String fileName) {
        //Create file, FileWriter, BufferedWriter for writing
        String filePath = System.getProperty("user.home") + myPath + fileName;
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
            System.out.println("File already exists at" + myPath);
            return false;
        }
        return true;
    }
    
    /**
     * Create a directory given a directory name
     */
    public boolean makeDirFromPath(String dirName) {
        String dirPath = System.getProperty("user.home") + myPath + dirName;
        return new File(dirPath).mkdir();
    }
}

