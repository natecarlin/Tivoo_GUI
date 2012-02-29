package html_output;

import java.util.List;

import org.joda.time.DateTime;

import process.Event;

public abstract class HtmlPageFactory {
    public abstract boolean isThisTypeOfPage(HtmlPageFactory factory);
    
    public abstract HtmlPage makePage(List<Event> events, String localPathSummary, DateTime startDate);
    
}
