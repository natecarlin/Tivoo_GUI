package html_output;

import Process.Event;

/**
 *  @author Antares Yee
 */

public class Utility {
    
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
}
