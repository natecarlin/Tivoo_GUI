package HTMLoutput;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Process.Event;

public abstract class HTMLpage {
    ArrayList<Event> myEvents;
    String myPath; //the path where you want the file created
    
    public HTMLpage(ArrayList<Event> events, String path) {
        myEvents = events;
        myPath = path;
    }
    
    /**
     * Creates an HTML page of specified subclass type.
     */
    public abstract void createHTMLpage(ArrayList<Event> processedEvents, String path);
    
    
}
