package HTMLoutput;

import Process.Event;

public class HTMLutility {
    
    /**
     * Returns fileName for an Event.
     * Removes unusable characters from fileName.
     */
    public static String makeFileName(Event e) {
      //TODO: QUOTING URLS/ESCAPE URLS for fileNames
        return "/" + e.getName() + e.getStartTime().toString() + ".html";
    }
}
