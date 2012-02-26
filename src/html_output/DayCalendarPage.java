package html_output;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;

import Process.Event;

import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;

public class DayCalendarPage extends CalendarPage {
    DateTime myStartDate;
    
    public DayCalendarPage(List<Event> events, String path, DateTime startDate) {
        super(events, path);
        myStartDate = startDate;
    }
    
    public static class DayCalendarPageFactory extends HtmlPageFactory {
      
        @Override
        public boolean isThisTypeOfPage(HtmlPageFactory factory) {
            if (factory.getClass().equals(new DayCalendarPageFactory().getClass())) return true;
            return false;
        }
        
        /**
         * Factory method
         */
        public HtmlPage makePage(List<Event> events, String localPathSummary, DateTime startDate) {
            return new DayCalendarPage(events, localPathSummary, startDate);
        }
        
    }

    /**
     * Create a calendar view that lists events for one month starting from myStartDate
     */
    @Override
    public boolean createHTMLpage() {
        Html html = makeHtmlObject();
        return makeFile(html, "/TiVOOdayCalendarPage.html");
    }
    
    public Html makeHtmlObject() {
        Html html = new Html();
        Body body = new Body();
        
        HtmlUtility.addTitleH2("DayCalendarPage", body);
        
        //add events to calendar
        DateTime endDate = myStartDate.plusDays(1);
        int numDays = Days.daysBetween(myStartDate, endDate).getDays(); 
        super.addCalendarEvents(new DateTime(myStartDate), numDays, body);
        
        html.appendChild(body);
        return html;
    }

}
